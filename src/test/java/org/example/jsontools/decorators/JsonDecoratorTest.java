package org.example.jsontools.decorators;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.jsontools.entity.BaseJsonProcessor;
import org.example.jsontools.entity.JsonProcessor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonDecoratorTest {
    JsonProcessor processor;

    static private JsonNode jsonNode;

    @Test
    void TestMinify() throws JsonProcessingException {
        String jsonBody = "{     \"name\":\"John\",\n\t\t \"age\":30,\n \"car\":null}";
        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(jsonBody);

        String oczekiwane = "{\"name\":\"John\",\"age\":30,\"car\":null}";
        processor = new MinifyJsonDecorator(new BaseJsonProcessor());
        String dane = processor.process(jsonNode);
        assertEquals(oczekiwane,dane);
    }

    @Test
    void TestPretty() throws JsonProcessingException {

        String jsonBody = "{     \"name\":\"John\",\n\t\t \"age\":30,\n \"car\":null}";
        String oczekiwane = "{\n" +
                "  \"name\" : \"John\",\n" +
                "  \"age\" : 30,\n" +
                "  \"car\" : null\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(jsonBody);
        processor = new PrettyPrintJsonDecorator(new BaseJsonProcessor());
        String dane = processor.process(jsonNode);
        assertNotNull(dane);

    }

    @Test
    void TestFilter() throws JsonProcessingException {
        String jsonBody = "{     \"name\":\"John\"}";

        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(jsonBody);
        processor = new PrettyPrintJsonDecorator(new BaseJsonProcessor());
        String oczekiwane = processor.process(jsonNode);

        List<String> keysToleave= new ArrayList<>();
        keysToleave.add("name");
        processor = new FilterFieldsJsonDecorator(new BaseJsonProcessor(),keysToleave);
        String dane = processor.process(jsonNode);
        assertEquals(oczekiwane,dane);
    }

    @Test
    void TestRemove() throws JsonProcessingException {
        String jsonBody = "{    \n\t\t \"age\":30,\n \"car\":null}";

        ObjectMapper mapper = new ObjectMapper();
        jsonNode = mapper.readTree(jsonBody);
        processor = new PrettyPrintJsonDecorator(new BaseJsonProcessor());
        String oczekiwane = processor.process(jsonNode);

        List<String> keysToleave= new ArrayList<>();
        keysToleave.add("name");
        processor = new RemoveFieldsJsonDecorator(new BaseJsonProcessor(),keysToleave);
        String dane = processor.process(jsonNode);
        assertEquals(oczekiwane,dane);

    }


}