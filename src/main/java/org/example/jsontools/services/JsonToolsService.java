package org.example.jsontools.services;

import com.fasterxml.jackson.annotation.JsonValue;
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

import java.util.Iterator;
import java.util.HashMap;
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
            return ResponseEntity.ok(minifiedJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> compareJSON(String firstJSONname, String secondJSONname) {
        JsonNode firstJSON = storageJSON.get(firstJSONname);
        JsonNode secondJSON = storageJSON.get(secondJSONname);

        if (firstJSON == null || secondJSON == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }

        try {
            String differences = findDifferences(firstJSON, secondJSON);
            return ResponseEntity.ok(differences);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR5));
        }
    }

    private String findDifferences(JsonNode node1, JsonNode node2) {
        StringBuilder differences = new StringBuilder();
        int line_iter = 2; // Zaczynamy od drugiej linii bo pierwsza to '{'
        Iterator<Map.Entry<String, JsonNode>> fields = node1.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value1 = entry.getValue();
            JsonNode value2 = node2.get(key);

            if (value2 == null) {
                differences.append(line_iter).append(": ").append("Key missing in second JSON: ").append(key).append(
                        "\n");
            } else if (!value1.equals(value2)) {
                differences.append(line_iter).append(": ").append("Difference in key \"").append(key).append("\": ").append(value1).append(" vs ").append(value2).append("\n");
            }
            line_iter++;
        }

        // Sprawdź dodatkowe pola w drugim JSONie
        fields = node2.fields();
        line_iter = 2; // Zaczynamy od drugiej linii bo pierwsza to '{'
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            if (!node1.has(entry.getKey())) {
                differences.append(line_iter).append(": ").append("Key missing in first JSON: ").append(entry.getKey()).append("\n");
            }
            line_iter++;
        }
        if (!differences.isEmpty()) return differences.toString();
        else return "Brak różnic!";
    }
}
