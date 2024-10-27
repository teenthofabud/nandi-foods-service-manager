package com.teenthofabud.wizard.nandifoods.wms.settings.unit.type;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import tech.units.indriya.format.SimpleUnitFormat;

import javax.measure.Unit;
import javax.measure.quantity.Mass;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class JSR385MassType implements UserType<Unit<Mass>>, ParameterizedType {


    @Override
    public void setParameterValues(Properties parameters) {

    }

    @Override
    public int getSqlType() {
        return Types.VARCHAR;
    }

    @Override
    public Class<Unit<Mass>> returnedClass() {
        return (Class<Unit<Mass>>)(Class<?>) Unit.class;
    }

    @Override
    public boolean equals(Unit<Mass> x, Unit<Mass> y) {
        return x != null && x.equals(y);
    }

    @Override
    public int hashCode(Unit<Mass> x) {
        return x.hashCode();
    }

    @Override
    public Unit<Mass> nullSafeGet(ResultSet rs, int position, SharedSessionContractImplementor session, Object owner) throws SQLException {
        String symbol = rs.getString(position);
        return symbol != null ? SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(symbol).asType(Mass.class): null;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Unit<Mass> value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (value == null || value.toString() == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            String symbol = value.toString();
            st.setString(index, symbol);
        }
    }

    @Override
    public Unit<Mass> deepCopy(Unit<Mass> value) {
        //return Units.getInstance().getUnit(value.toString()).asType(Mass.class);
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse(value.toString()).asType(Mass.class);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Unit<Mass> value) {
        return value.toString();
    }

    @Override
    public Unit<Mass> assemble(Serializable cached, Object owner) {
        return SimpleUnitFormat.getInstance(SimpleUnitFormat.Flavor.ASCII).parse((CharSequence) cached).asType(Mass.class);
    }
}
