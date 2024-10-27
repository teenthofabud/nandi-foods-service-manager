package com.teenthofabud.wizard.nandifoods.wms.settings.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class JsonFlattener {

    private final Map<String, JsonNode> json = new LinkedHashMap<>(64);
    private JsonNode root;
    //private final JsonNode root;

    /*public JsonFlattener(JsonNode node) {
        this.root = Objects.requireNonNull(node);
    }*/

    public Map<String, JsonNode> flatten() {
        process(root, "");
        return json;
    }

    public Map<String, JsonNode> flatten(JsonNode root) {
        json.clear();
        process(root, "");
        return json;
    }

    private void process(JsonNode node, String prefix) {
        if (node.isObject()) {
            ObjectNode object = (ObjectNode) node;
            object
                    .fields()
                    .forEachRemaining(
                            entry -> {
                                process(entry.getValue(), prefix + "/" + entry.getKey());
                            });
        } else if (node.isArray()) {
            ArrayNode array = (ArrayNode) node;
            AtomicInteger counter = new AtomicInteger();
            array
                    .elements()
                    .forEachRemaining(
                            item -> {
                                process(item, prefix + "/" + counter.getAndIncrement());
                            });
        } else {
            json.put(prefix, node);
        }
    }
}