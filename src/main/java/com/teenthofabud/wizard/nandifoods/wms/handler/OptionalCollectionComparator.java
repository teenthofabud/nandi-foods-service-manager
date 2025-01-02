package com.teenthofabud.wizard.nandifoods.wms.handler;

import org.javers.core.diff.custom.CustomValueComparator;

import java.util.Collection;
import java.util.Optional;

public class OptionalCollectionComparator implements CustomValueComparator<Optional<? extends Collection>> {

    @Override
    public boolean equals(Optional<? extends Collection> a, Optional<? extends Collection> b) {
        return (a.isEmpty() && b.isEmpty())
                || (a.isPresent() && b.isEmpty())
                || (a.isEmpty() && b.isPresent())
                || (a.isPresent() && b.isPresent()
                    ? a.get().containsAll(b.get()) && b.get().containsAll(a.get()) : false);
    }

    @Override
    public String toString(Optional<? extends Collection> value) {
        return value.isPresent() ? value.get().toString() : "";
    }
}