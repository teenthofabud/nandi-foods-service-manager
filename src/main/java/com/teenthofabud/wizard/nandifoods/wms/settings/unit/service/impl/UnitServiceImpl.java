package com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.impl;

import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.teenthofabud.wizard.nandifoods.wms.handler.ColumnPositionNameMappingStrategy;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.constants.UnitClassStatus;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.dto.FileDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.service.UnitService;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.converter.*;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.dto.UOMPageDto;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.entity.UOMEntity;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.repository.UOMJpaRepository;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMPageImplVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMSelfLinkageVo;
import com.teenthofabud.wizard.nandifoods.wms.settings.unit.uom.vo.UOMVo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class UnitServiceImpl implements UnitService {

    private UOMJpaRepository uomJpaRepository;
    private UOMEntityToVoConverter uomEntityToVoConverter;
    private UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter;
    private UOMPageDtoToPageableConverter uomPageDtoToPageableConverter;
    //private UOMSummaryProjectionRepository uomSummaryProjectionRepository;

    private List<String> searchFields;
    private String fileNameDateFormat;
    private String csvFileNameFormat;
    private String pdfFileNameFormat;

    @Autowired
    public UnitServiceImpl(UOMJpaRepository uomJpaRepository,
                           UOMEntityToVoConverter uomEntityToVoConverter,
                           UOMSelfLinkageEntityToVoConverter uomSelfLinkageEntityToVoConverter,
                           UOMPageDtoToPageableConverter uomPageDtoToPageableConverter,
                           //UOMSummaryProjectionRepository uomSummaryProjectionRepository,
                           @Value("#{'${wms.settings.uom.search.fields}'.split(',')}") List<String> searchFields,
                           @Value("${wms.settings.unit.fileNameDateTimeFormat}") String fileNameDateFormat,
                           @Value("${wms.settings.uom.fileNameFormat.csv}") String csvFileNameFormat,
                           @Value("${wms.settings.uom.fileNameFormat.pdf}") String pdfFileNameFormat) {
        this.uomJpaRepository = uomJpaRepository;
        this.uomEntityToVoConverter = uomEntityToVoConverter;
        this.uomSelfLinkageEntityToVoConverter = uomSelfLinkageEntityToVoConverter;
        this.uomPageDtoToPageableConverter = uomPageDtoToPageableConverter;
        this.searchFields = searchFields;
        this.fileNameDateFormat = fileNameDateFormat;
        this.csvFileNameFormat = csvFileNameFormat;
        this.pdfFileNameFormat = pdfFileNameFormat;
        //this.uomSummaryProjectionRepository = uomSummaryProjectionRepository;
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveUOMByLongName(Optional<String> optionalLongName, UOMPageDto uomPageDto) {
        Pageable pageable = uomPageDtoToPageableConverter.convert(uomPageDto);
        Page<UOMEntity> uomEntityPage = new PageImpl<>(List.of());
        Specification<UOMEntity> uomSearchQueryLikeLongNameSpecification = optionalLongName.map(o -> uomJpaRepository.likeProperty("longName", optionalLongName.get()))
                .orElse(uomJpaRepository.skipThisSpecificationWithEmptyCriteriaConjunction());
        Specification<UOMEntity> uomSearchQueryEqualStatusSpecification = uomPageDto.getStatus().map(o ->
                uomJpaRepository.equalsProperty("status", UnitClassStatus.valueOf(uomPageDto.getStatus().get()).getOrdinal()))
                .orElse(uomJpaRepository.skipThisSpecificationWithEmptyCriteriaConjunction());
        Specification<UOMEntity> uomSearchQuerySpecification = Specification
                .where(uomSearchQueryLikeLongNameSpecification).and(uomSearchQueryEqualStatusSpecification);
        log.debug("Created specification for UOMEntity using long name: {} and status: {} and page", optionalLongName, uomPageDto.getStatus(), pageable);
        uomEntityPage = uomJpaRepository.findAll(uomSearchQuerySpecification, pageable);
        List<UOMVo> uomVoList = uomEntityPage.stream().map(i -> {
            List<UOMSelfLinkageVo> uomSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(uomSelfLinkageVoList);
            return uomVo;
        }).collect(Collectors.toList());
        UOMPageImplVo uomPageImplVo = new UOMPageImplVo(uomVoList, uomEntityPage.getPageable(), uomEntityPage.getTotalElements());
        log.debug("Found {} UOM in page {}", uomEntityPage.getTotalElements(), uomPageImplVo.getNumber());
        return uomPageImplVo;
    }

    @Transactional
    @Override
    public UOMPageImplVo retrieveUOMWithinRange(Optional<String> optionalQuery, @Valid UOMPageDto uomPageDto) {
        Pageable pageable = uomPageDtoToPageableConverter.convert(uomPageDto);
        Page<UOMEntity> uomEntityPage = new PageImpl<>(List.of());
        if(optionalQuery.isPresent()) {
            String q = optionalQuery.get();
            List<Specification<UOMEntity>> uomEntitySpecifications = searchFields.stream()
                    .flatMap(k -> Arrays.stream(q.split("\\s+"))
                            .map(v -> uomJpaRepository.likeProperty(k, v))).collect(Collectors.toList());
            Specification<UOMEntity> uomSearchQuerySpecification = Specification.anyOf(uomEntitySpecifications);
            log.debug("Created specification for UOMEntity using query: {}", q);
            uomEntityPage = uomJpaRepository.findAll(uomSearchQuerySpecification, pageable);
        } else {
            log.debug("Searching UOMEntity with page: {}", pageable);
            uomEntityPage = uomJpaRepository.findAll(pageable);
        }
        List<UOMVo> uomVoList = uomEntityPage.stream().map(i -> {
            List<UOMSelfLinkageVo> uomSelfLinkageVoList = i.getFromUOMs().stream().map(j -> uomSelfLinkageEntityToVoConverter.convert(j)).collect(Collectors.toList());
            UOMVo uomVo = uomEntityToVoConverter.convert(i);
            uomVo.setSelfLinksTo(uomSelfLinkageVoList);
            return uomVo;
        }).collect(Collectors.toList());
        UOMPageImplVo uomPageImplVo = new UOMPageImplVo(uomVoList, uomEntityPage.getPageable(), uomEntityPage.getTotalElements());
        log.debug("Found {} UOM in page {}", uomEntityPage.getTotalElements(), uomPageImplVo.getNumber());
        return uomPageImplVo;
    }

    @Transactional
    @Override
    public FileDto downloadUOMAsCSV() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Writer streamWriter = new OutputStreamWriter(stream);
        CSVWriter writer = new CSVWriter(streamWriter);
        ColumnPositionNameMappingStrategy<UOMVo> mappingStrategy = new ColumnPositionNameMappingStrategy<UOMVo>(UOMVo.class);
        mappingStrategy.setType(UOMVo.class);
        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        //List<UOMSummaryProjection> uomSummaryList = uomSummaryProjectionRepository.findAllWithMeasuredValueForMetricSystem(MetricSystem.SI);
        //List<UOMSummaryVo> uomSummaryVoList = uomSummaryList.stream().map(f -> uomSummaryProjectionToVoConverter.convert(f)).collect(Collectors.toList());
        StatefulBeanToCsv<UOMVo> sbc = new StatefulBeanToCsvBuilder<UOMVo>(writer)
        //StatefulBeanToCsv<UOMSummaryVo> sbc = new StatefulBeanToCsvBuilder<UOMSummaryVo>(writer)
                .withQuotechar('\'')
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withMappingStrategy(mappingStrategy)
                .build();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fileNameDateFormat);
        String csvFileName = String.format(csvFileNameFormat, dtf.format(LocalDateTime.now()));
        try {
            log.debug("Creating UOM list in CSV with {} UOMs", uomVoList.size());
            sbc.write(uomVoList);
            //sbc.write(uomSummaryVoList);
            streamWriter.flush();
        } catch (CsvDataTypeMismatchException e) {
            throw new IllegalArgumentException("CSV bean field not configured properly", e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new IllegalArgumentException("CSV bean field is empty", e);
        }
        FileDto fileDto = FileDto.builder()
                .fileName(csvFileName)
                .rawContent(new ByteArrayInputStream(stream.toByteArray()))
                .build();
        log.info("UOM is available for download in CSV as {}", fileDto.getFileName());
        return fileDto;
    }

    @Override
    public FileDto downloadUOMAsPDF() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(stream));
        Document doc = new Document(pdfDocument);

        List<Field> fields = Stream.concat(
                Arrays.stream(UOMVo.class.getDeclaredFields()), Arrays.stream(UOMVo.class.getSuperclass().getDeclaredFields()))
                .filter(f -> f.isAnnotationPresent(CsvBindByName.class) && f.isAnnotationPresent(CsvBindByPosition.class))
                .collect(Collectors.toList());
        fields.sort(Comparator.comparing(o -> o.getDeclaredAnnotation(CsvBindByPosition.class).position()));
        Table table = new Table(fields.size()).useAllAvailableWidth();
        fields.stream().forEach(f -> table.addHeaderCell(
                new Cell().add(
                        new Paragraph(
                                f.getDeclaredAnnotation(CsvBindByName.class).column()))
                        .setBackgroundColor(DeviceGray.GRAY))
                .setTextAlignment(TextAlignment.LEFT));

        List<UOMEntity> uomEntityList = uomJpaRepository.findAll();
        List<UOMVo> uomVoList = uomEntityList.stream().map(f -> uomEntityToVoConverter.convert(f)).collect(Collectors.toList());
        log.debug("Creating UOM list in PDF with {} UOMs", uomVoList.size());
        uomVoList.stream().forEach(f ->
                fields.stream().forEach(d -> {
                try {
                    table.addCell(new Cell().add(new Paragraph(BeanUtils.getProperty(f, d.getName()))));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }));

        doc.add(table);
        doc.close();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fileNameDateFormat);
        String pdfFileName = String.format(pdfFileNameFormat, dtf.format(LocalDateTime.now()));

        FileDto fileDto = FileDto.builder()
                .fileName(pdfFileName)
                .rawContent(new ByteArrayInputStream(stream.toByteArray()))
                .build();
        log.info("UOM is available for download in PDF as {}", fileDto.getFileName());
        return fileDto;
    }
}
