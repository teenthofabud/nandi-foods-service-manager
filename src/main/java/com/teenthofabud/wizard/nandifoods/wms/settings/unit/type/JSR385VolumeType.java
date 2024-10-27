package com.teenthofabud.wizard.nandifoods.wms.settings.unit.type;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import tech.units.indriya.format.SimpleUnitFormat;

import javax.measure.Unit;
import javax.measure.quantity.Volume;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class JSR385VolumeType implements UserType<Unit<Volume>>, ParameterizedType {


    @Override
    public void setParameterValues(Properties parameters) {

    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<Unit<Volume>> returnedClass() {
        return (Class<Unit<Volume>>)(Class<?>) Unit.class;
    }

    @Override
    public boolean equals(Unit<Volume> x, Unit<Volume> y) {
        return x != null && x.equals(y);
    }

    @Override
    public int hashCode(Unit<Volume> x) {
        return x.hashCode();
    }

    @Override
    public Unit<Volume> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String symbol = rs.getString(position);
        return symbol != null ? SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(symbol).asType(Volume.class): null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Unit<Volume> value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null || value.toString() == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String symbol = value.toString();
            st.setString(index, symbol);
        }
    }

    @Override
    public Unit<Volume> deepCopy(Unit<Volume> value) {
        //return Units.getInstance().getUnit(value.toString()).asType(Volume.class);
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(value.toString()).asType(Volume.class);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Unit<Volume> value) {
        return value.toString();
    }

    @Override
    public Unit<Volume> assemble(Serializable cached, Object owner) {
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse((CharSequence) cached).asType(Volume.class);
    }
}
