package org.example.jsontools.decorators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.entity.JsonProcessor;

@Slf4j
public// === MINIFIKACJA JSON ===
class MinifyJsonDecorator extends JsonProcessorDecorator {
    public MinifyJsonDecorator(JsonProcessor wrappedProcessor) {
        super(wrappedProcessor);
    }

    @Override
    public String process(JsonNode jsonNode) throws JsonProcessingException {
        log.debug("Minifying JSON");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writer().writeValueAsString(jsonNode);
    }
}
