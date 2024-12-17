package org.example.jsontools.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface JsonProcessor {
    String process(JsonNode jsonNode) throws JsonProcessingException;
}
