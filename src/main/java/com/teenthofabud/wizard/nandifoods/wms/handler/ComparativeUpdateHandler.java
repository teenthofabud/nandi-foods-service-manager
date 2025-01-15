package com.teenthofabud.wizard.nandifoods.wms.handler;

import com.diffplug.common.base.Errors;
import jodd.bean.BeanUtil;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;

public interface ComparativeUpdateHandler<T> {

    static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ComparativeUpdateHandler.class);

    default T comparativelyUpdateMandatoryFields(Diff diff, T bean, boolean includeCollectionNamePrefix) {
        diff.getChangesByType(ValueChange.class).forEach(Errors.rethrow().wrap(p -> {
            String fqdnPropertyName = p.getPropertyNameWithPath();
            int i = fqdnPropertyName.indexOf("/");
            int j = fqdnPropertyName.indexOf(".");
            if(i != -1 && j != -1) {
                String previous = fqdnPropertyName.substring(0, i);
                String index = String.format("[%s].", fqdnPropertyName.substring(i + 1, j));
                String next = fqdnPropertyName.substring(j + 1);
                fqdnPropertyName = String.join("", includeCollectionNamePrefix ? previous : "", index, next);
            }
            log.debug("{} changed from {} to {}", fqdnPropertyName, p.getLeft(), p.getRight());
            if(fqdnPropertyName.compareTo(p.getPropertyNameWithPath()) == 0) {
                getBeanUtilsBean().copyProperty(bean, fqdnPropertyName, p.getRight());
            } else {
                BeanUtil.pojo.setProperty(bean, fqdnPropertyName, p.getRight());
            }
        }));
        return bean;
    }

    public BeanUtilsBean getBeanUtilsBean();

}
