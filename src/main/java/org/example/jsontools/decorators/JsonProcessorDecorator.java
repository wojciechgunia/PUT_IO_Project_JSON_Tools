package org.example.jsontools.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.example.jsontools.entity.JsonProcessor;

// === DEKORATOR BAZOWY ===
abstract class JsonProcessorDecorator implements JsonProcessor {
    protected final JsonProcessor wrappedProcessor;

    public JsonProcessorDecorator(JsonProcessor wrappedProcessor) {
        this.wrappedProcessor = wrappedProcessor;
    }

    @Override
    public String process(JsonNode jsonNode) throws JsonProcessingException {
        return wrappedProcessor.process(jsonNode);
    }
}
