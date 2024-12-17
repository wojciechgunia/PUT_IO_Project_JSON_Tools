package org.example.jsontools.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.entity.JsonProcessor;

import java.util.Iterator;
import java.util.List;

@Slf4j
public// === USUWANIE POL JSON ===
class RemoveFieldsJsonDecorator extends JsonProcessorDecorator {
    private final List<String> keysToRemove;

    public RemoveFieldsJsonDecorator(JsonProcessor wrappedProcessor, List<String> keysToRemove) {
        super(wrappedProcessor);
        this.keysToRemove = keysToRemove;
    }

    @Override
    public String process(JsonNode jsonNode) throws JsonProcessingException {
        log.debug("Removing JSON fields");
        JsonNode filteredNode = removeKeys(jsonNode.deepCopy(), keysToRemove);
        return super.process(filteredNode);
    }

    private JsonNode removeKeys(JsonNode rootNode, List<String> keys) {
        if (rootNode.isObject()) {
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String name = fieldNames.next();
                if (keys.contains(name)) {
                    fieldNames.remove();
                } else {
                    removeKeys(rootNode.get(name), keys);
                }
            }
        } else if (rootNode.isArray()) {
            rootNode.forEach(element -> removeKeys(element, keys));
        }
        return rootNode;
    }
}
