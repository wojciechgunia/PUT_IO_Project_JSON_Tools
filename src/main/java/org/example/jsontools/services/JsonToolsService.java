package org.example.jsontools.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.entity.Code;
import org.example.jsontools.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonToolsService {

    private final Map<String, JsonNode> storageJSON = new HashMap<>();

    public ResponseEntity<?> saveJSON(JsonNode json, String JSONname) {
        if(storageJSON.containsKey(JSONname)) {
            return ResponseEntity.status(400).body(new Response(Code.BR2));
        }

        try {
            storageJSON.put(JSONname, json);
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR1));
        }
    }

    public ResponseEntity<?> minJSON(String JSONname) {
        JsonNode jsonBody = storageJSON.get(JSONname);
        if (jsonBody == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writer();
        String minifiedJson;
        try {
            minifiedJson = writer.writeValueAsString(jsonBody);
            return ResponseEntity.status(200).body(minifiedJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> withoutJSON(String JSONname, List<String> keysToRemove) {
        JsonNode jsonBody = storageJSON.get(JSONname).deepCopy();

        JsonNode filteredNode = filterKeys(jsonBody, keysToRemove);

        return ResponseEntity.status(200).body(filteredNode);
    }

    public static JsonNode filterKeys(JsonNode rootNode, List<String> keys) {
        if (rootNode.isObject()) {
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (keys.contains(fieldName)) {
                    fieldNames.remove();
                } else {
                    filterKeys(rootNode.get(fieldName), keys);
                }
            }
        } else if (rootNode.isArray()) {
            for (JsonNode arrayElement : rootNode) {
                filterKeys(arrayElement, keys);
            }
        }
        return rootNode;
    }
}
