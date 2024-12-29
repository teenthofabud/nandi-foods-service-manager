package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColumnPositionNameMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

    private final List<Field> fields;

    public ColumnPositionNameMappingStrategy(Class<T> clazz) {
        fields = Stream.of(clazz.getDeclaredFields(), clazz.getSuperclass().getDeclaredFields())
                .flatMap(Stream::of)
                .filter(f -> f.isAnnotationPresent(CsvBindByName.class) && f.isAnnotationPresent(CsvBindByPosition.class))
                .collect(Collectors.toList());
        fields.sort(Comparator.comparing(o -> o.getDeclaredAnnotation(CsvBindByPosition.class).position()));
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        List<String> headerNames = new ArrayList<>(fields.size());
        for (Field f : fields) {
            headerNames.add(getName(f));
        }
        String header[] = headerNames.toArray(String[]::new);
        headerIndex.initializeHeaderIndex(header);
        return header;
    }

    private String getName(Field f) {
        CsvBindByName csvBindByName = f.getAnnotation(CsvBindByName.class);
        CsvCustomBindByName csvCustomBindByName = f.getAnnotation(CsvCustomBindByName.class);
        return csvCustomBindByName != null
                ? csvCustomBindByName.column() == null || csvCustomBindByName.column().isEmpty() ? f.getName() : csvCustomBindByName.column()
                : csvBindByName.column() == null || csvBindByName.column().isEmpty() ? f.getName() : csvBindByName.column();
    }

}
