package com.teenthofabud.wizard.nandifoods.wms.settings.unit.type;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import systems.uom.common.USCustomary;
import tech.units.indriya.format.SimpleUnitFormat;

import javax.measure.Unit;
import javax.measure.format.MeasurementParseException;
import javax.measure.quantity.Length;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class JSR385LengthType implements UserType<Unit<Length>>, ParameterizedType {

    private Unit<Length> parseUnit(String symbol) {
        try {
            return SimpleUnitFormat.getInstance().parse(symbol).asType(Length.class);
        } catch (MeasurementParseException e) {
            return USCustomary.getInstance().getUnit(symbol).asType(Length.class);
        }
    }

    @Override
    public void setParameterValues(Properties parameters) {

    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<Unit<Length>> returnedClass() {
        return (Class<Unit<Length>>)(Class<?>) Unit.class;
    }

    @Override
    public boolean equals(Unit<Length> x, Unit<Length> y) {
        return x != null && x.equals(y);
    }

    @Override
    public int hashCode(Unit<Length> x) {
        return x.hashCode();
    }

    @Override
    public Unit<Length> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String symbol = rs.getString(position);
        return parseUnit(symbol);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Unit<Length> value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null || value.toString() == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String symbol = value.toString();
            st.setString(index, symbol);
        }
    }

    @Override
    public Unit<Length> deepCopy(Unit<Length> value) {
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(value.toString()).asType(Length.class);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Unit<Length> value) {
        return value.toString();
    }

    @Override
    public Unit<Length> assemble(Serializable cached, Object owner) {
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse((CharSequence) cached).asType(Length.class);
    }
}
