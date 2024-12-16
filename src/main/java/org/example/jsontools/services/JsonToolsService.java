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
    private final Map<String, String> storageString = new HashMap<>();
  
    private JsonNode convertStringToJSON(String JSONbody) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(JSONbody);
    }

    public ResponseEntity<?> saveJSON(String JSONbody, String JSONname) {
        if(storageString.containsKey(JSONname) || storageJSON.containsKey(JSONname)) {
            return ResponseEntity.status(400).body(new Response(Code.BR2));
        }

        try {
            storageString.put(JSONname, JSONbody);
            storageJSON.put(JSONname, convertStringToJSON(JSONbody));
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        } catch (RuntimeException e | JsonProcessingException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR1));
        }
    }

    public ResponseEntity<?> minJSON(String JSONname) {
        String JSONstring = storageJSON.get(JSONname);
        if (JSONstring == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter writer = objectMapper.writer();
        String minifiedJson;

        try {
            JsonNode JSONbody = convertStringToJSON(JSONstring);
            minifiedJson = writer.writeValueAsString(JSONbody);
            return ResponseEntity.ok(minifiedJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }
  
    public ResponseEntity<?> fullJSON(String JSONname) {
        JsonNode jsonBody = storageJSON.get(JSONname).deepCopy();
        if (jsonBody == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        JsonNode filteredNode = filterKeys(jsonBody, keysToRemove);
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectWriter writer = objectMapper.writerWithDefaultPrettyPrinter();
        String fulliedJson;
        try {
            fulliedJson = writer.writeValueAsString(jsonBody);
            return ResponseEntity.status(200).body(fulliedJson);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> filtJSON(String JSONname, List<String> keysToLeave) {
        if (storageJSON.get(JSONname) == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        JsonNode jsonBody = storageJSON.get(JSONname).deepCopy();

        JsonNode filteredNode = filterKeys(jsonBody, keysToLeave);

        return ResponseEntity.status(200).body(filteredNode);
    }

    private static JsonNode filterKeys(JsonNode rootNode, List<String> keys) {
        if (rootNode.isObject()) {
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (keys.contains(fieldName)) {
                    filterKeys(rootNode.get(fieldName), keys);
                } else {
                    fieldNames.remove();
                }
            }
        } else if (rootNode.isArray()) {
            for (JsonNode arrayElement : rootNode) {
                filterKeys(arrayElement, keys);
            }
        }
        return rootNode;
    }
  
    public ResponseEntity<?> withoutJSON(String JSONname, List<String> keysToRemove) {
        JsonNode jsonBody = storageJSON.get(JSONname).deepCopy();
        if (jsonBody == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }

        JsonNode filteredNode = withoutKeys(jsonBody, keysToRemove);

        return ResponseEntity.status(200).body(filteredNode);
    }

    private static JsonNode withoutKeys(JsonNode rootNode, List<String> keys) {
        if (rootNode.isObject()) {
            Iterator<String> fieldNames = rootNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (keys.contains(fieldName)) {
                    fieldNames.remove();
                } else {
                    withoutKeys(rootNode.get(fieldName), keys);
                }
            }
        } else if (rootNode.isArray()) {
            for (JsonNode arrayElement : rootNode) {
                filterKeys(arrayElement, keys);
            }
        }
        return rootNode;
    }
      
    public ResponseEntity<?> compareJSON(String firstJSONname, String secondJSONname) {
        String firstJSONstring = storageString.get(firstJSONname);
        String secondJSONstring = storageString.get(secondJSONname);

        if (firstJSONstring == null || secondJSONstring == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            String differences = findDifferences(firstJSONname, secondJSONname);
            return ResponseEntity.ok(differences);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(new Response(Code.BR5));
        }
    }

    private String findDifferences(String firstJSONname, String secondJSONname) {
        StringBuilder differences = new StringBuilder();

        String firstJSONstring = storageString.get(firstJSONname);
        String secondJSONstring = storageString.get(secondJSONname);

        String[] firstJSONlines = firstJSONstring.split("\\r?\\n");
        String[] secondJSONlines = secondJSONstring.split("\\r?\\n");

        int minLines = Math.min(firstJSONlines.length, secondJSONlines.length);

        for (int i = 0; i < minLines; i++) {
            String lineFirstJSON = firstJSONlines[i];
            String lineSecondJSON = secondJSONlines[i];
            if (!lineFirstJSON.equals(lineSecondJSON)) {
                differences.append(i+1).append(": \n");
                differences.append(" - ").append(firstJSONname).append(": ").append(lineFirstJSON).append("\n");
                differences.append(" - ").append(secondJSONname).append(": ").append(lineSecondJSON).append("\n");
            }
        }
        differences.append("Difference between amount of lines: ").append(": \n");
        differences.append(" - ").append(firstJSONname).append(": ").append(firstJSONlines.length).append("\n");
        differences.append(" - ").append(secondJSONname).append(": ").append(secondJSONlines.length).append("\n");

        return differences.toString();
    }
}

