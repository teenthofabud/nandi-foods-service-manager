package com.teenthofabud.wizard.nandifoods.wms.constants;

import java.util.List;
import java.util.NoSuchElementException;

public interface EnumKeyValue<T> {

    public String getKey();
    public String getValue();
    public List<EnumKeyValue<T>> getEnumConstants();
    public String getKeyName();
    public String getValueName();
    public String getTypeName();

    default EnumKeyValue<T> getByKey(String key) throws NoSuchElementException {
        EnumKeyValue<T> x = null;
        for(EnumKeyValue<T> t : getEnumConstants()) {
            if(t.getKey().compareTo(key) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No " + getTypeName() + " available by " + getKeyName() + ": " + key);
        }
    }

    default EnumKeyValue<T> getByValue(String value) throws NoSuchElementException {
        EnumKeyValue<T> x = null;
        for(EnumKeyValue<T> t : getEnumConstants()) {
            if(t.getValue().compareTo(value) == 0) {
                x = t;
                break;
            }
        }
        if(x != null) {
            return x;
        } else {
            throw new NoSuchElementException("No " + getTypeName() + " available by " + getValueName() + ": " + value);
        }
    }

}
