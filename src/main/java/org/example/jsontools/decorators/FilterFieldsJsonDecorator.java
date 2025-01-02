package org.example.jsontools.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.entity.JsonProcessor;

import java.util.Iterator;
import java.util.List;

@Slf4j
public// === FILTROWANIE POL JSON ===
class FilterFieldsJsonDecorator extends JsonProcessorDecorator {
    private final List<String> keysToLeave;

    public FilterFieldsJsonDecorator(JsonProcessor wrappedProcessor, List<String> keysToLeave) {
        super(wrappedProcessor);
        this.keysToLeave = keysToLeave;
    }

    @Override
    public String process(JsonNode jsonNode) throws JsonProcessingException {
        log.debug("Filtering JSON fields");
        JsonNode filteredNode = filterKeys(jsonNode.deepCopy(), keysToLeave);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(filteredNode);
    }

    private JsonNode filterKeys(JsonNode rootNode, List<String> keys) {
        if (rootNode.isObject()) {
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String name = fieldNames.next();
                if (!keys.contains(name)) {
                    fieldNames.remove();
                } else {
                    filterKeys(rootNode.get(name), keys);
                }
            }
        } else if (rootNode.isArray()) {
            rootNode.forEach(element -> filterKeys(element, keys));
        }
        return rootNode;
    }
}
