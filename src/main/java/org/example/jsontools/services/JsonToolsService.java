package org.example.jsontools.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.decorators.FilterFieldsJsonDecorator;
import org.example.jsontools.decorators.MinifyJsonDecorator;
import org.example.jsontools.decorators.PrettyPrintJsonDecorator;
import org.example.jsontools.decorators.RemoveFieldsJsonDecorator;
import org.example.jsontools.entity.BaseJsonProcessor;
import org.example.jsontools.entity.Code;
import org.example.jsontools.entity.JsonProcessor;
import org.example.jsontools.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
        log.info("Saving JSON with name: {}", JSONname);
        if (storageString.containsKey(JSONname) || storageJSON.containsKey(JSONname)) {
            log.warn("JSON with name '{}' already exists", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR2));
        }

        try {
            storageString.put(JSONname, JSONbody);
            storageJSON.put(JSONname, convertStringToJSON(JSONbody));
            log.info("JSON '{}' saved successfully", JSONname);
            return ResponseEntity.ok(new Response(Code.SUCCESS));
        } catch (RuntimeException | JsonProcessingException e) {
            log.error("Error saving JSON: {}", e.getMessage());
            return ResponseEntity.status(400).body(new Response(Code.BR1));
        }
    }

    public ResponseEntity<?> getJSON(String JSONname) {
        log.info("Fetching JSON with name: {}", JSONname);
        JsonNode JSONbody = storageJSON.get(JSONname);
        if (JSONbody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }

        log.debug("JSON fetched successfully: {}", JSONbody);
        return ResponseEntity.ok(JSONbody);
    }

    public ResponseEntity<?> getOriginal(String JSONname)
    {
        String Stringbody = storageString.get(JSONname);
        if (Stringbody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        log.debug("Original string fetched successfully: {}", Stringbody);
        return ResponseEntity.ok(Stringbody);
    }

    public ResponseEntity<?> fullJSON(String JSONname) {
        log.info("Returning pretty JSON for: {}", JSONname);
        JsonNode jsonBody = storageJSON.get(JSONname);
        if (jsonBody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            JsonProcessor processor = new PrettyPrintJsonDecorator(new BaseJsonProcessor());
            return ResponseEntity.ok(processor.process(jsonBody));
        } catch (JsonProcessingException e) {
            log.error("Error processing pretty JSON: {}", e.getMessage());
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> minJSON(String JSONname) {
        log.info("Returning minified JSON for: {}", JSONname);
        JsonNode jsonBody = storageJSON.get(JSONname);
        if (jsonBody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            JsonProcessor processor = new MinifyJsonDecorator(new BaseJsonProcessor());
            return ResponseEntity.ok(processor.process(jsonBody));
        } catch (JsonProcessingException e) {
            log.error("Error processing minified JSON: {}", e.getMessage());
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> filtJSON(String JSONname, List<String> keysToLeave) {
        log.info("Filtering JSON fields for: {}, Keys to leave: {}", JSONname, keysToLeave);
        JsonNode jsonBody = storageJSON.get(JSONname);
        if (jsonBody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            JsonProcessor processor = new FilterFieldsJsonDecorator(new BaseJsonProcessor(), keysToLeave);
            return ResponseEntity.ok(processor.process(jsonBody));
        } catch (JsonProcessingException e) {
            log.error("Error filtering JSON fields: {}", e.getMessage());
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> withoutJSON(String JSONname, List<String> keysToRemove) {
        log.info("Removing JSON fields for: {}, Keys to remove: {}", JSONname, keysToRemove);
        JsonNode jsonBody = storageJSON.get(JSONname);
        if (jsonBody == null) {
            log.warn("JSON with name '{}' not found", JSONname);
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            JsonProcessor processor = new RemoveFieldsJsonDecorator(new BaseJsonProcessor(), keysToRemove);
            return ResponseEntity.ok(processor.process(jsonBody));
        } catch (JsonProcessingException e) {
            log.error("Error removing JSON fields: {}", e.getMessage());
            return ResponseEntity.status(400).body(new Response(Code.BR4));
        }
    }

    public ResponseEntity<?> compareJSON(String firstJSONname, String secondJSONname) {
        log.info("Comparing JSONs {} and {}", firstJSONname, secondJSONname);
        String firstJSONstring = storageString.get(firstJSONname);
        String secondJSONstring = storageString.get(secondJSONname);

        if (firstJSONstring == null || secondJSONstring == null) {
            return ResponseEntity.status(400).body(new Response(Code.BR3));
        }
        try {
            String differences = findDifferences(firstJSONname, secondJSONname);
            return ResponseEntity.ok(differences);
        } catch (RuntimeException e) {
            log.error("Error comparing JSON fields: {}", e.getMessage());
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
        if(firstJSONlines.length != secondJSONlines.length)
        {
            differences.append("Difference between amount of lines: ").append(": \n");
            differences.append(" - ").append(firstJSONname).append(": ").append(firstJSONlines.length).append("\n");
            differences.append(" - ").append(secondJSONname).append(": ").append(secondJSONlines.length).append("\n");
        }
        return differences.toString();
    }
}

