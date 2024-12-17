package org.example.jsontools.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// === BAZOWY PROCESOR ===
public class BaseJsonProcessor implements JsonProcessor {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String process(JsonNode jsonNode) throws JsonProcessingException {
        return objectMapper.writeValueAsString(jsonNode);
    }
}
