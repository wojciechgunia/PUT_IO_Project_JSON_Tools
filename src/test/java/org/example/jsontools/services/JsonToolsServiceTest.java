package org.example.jsontools.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class JsonToolsServiceTest {

    private JsonToolsService service;
    ResponseEntity<?> response;
    String jsonBody = "{  \"name\":\"John\",\n\t\t \"age\":30,\n \"car\":null}";
    String jsonName = "testJson";

    @BeforeEach
    void setUp() {
        service = new JsonToolsService();
        service.saveJSON(jsonBody, jsonName);
    }

    @Test
    void TestSaveJSONSuccess(){
        jsonName = "testJson2";
        response = service.saveJSON(jsonBody, jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestSaveJSONClientError() {
        response = service.saveJSON(jsonBody, jsonName);
        assertTrue(response.getStatusCode().is4xxClientError());

    }

    @Test
    void TestgetJSONSuccess() {
        response = service.getJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    void TestgetJSONClientError() {
        response = service.getJSON("jsonName2");
        assertTrue(response.getStatusCode().is4xxClientError());

    }

    @Test
    void TestgetOriginalSuccess() {
        response = service.getOriginal(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestgetOriginalClientError() {
        response = service.getOriginal("jsonName2");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestfullJSONSuccess() {
        response = service.fullJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestfullJSONClientError() {
        response = service.fullJSON("jsonName");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestminJSONSuccess() {
        response = service.minJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void TestminJSONClientError() {
        response = service.minJSON("jsonName");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestfiltJSONSuccess() {
        List<String> keys = new ArrayList<>();
        keys.add("name");
        response = service.filtJSON(jsonName,keys);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestfiltJSONClientError() {
        List<String> keys = new ArrayList<>();
        keys.add("name");
        response = service.filtJSON("jsonName2",keys);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestwithoutJSONSuccess() {
        List<String> keys = new ArrayList<>();
        keys.add("name");
        response = service.withoutJSON(jsonName,keys);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestwithoutJSONClientError() {
        List<String> keys = new ArrayList<>();
        keys.add("name");
        response = service.withoutJSON("jsonName2",keys);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestcompareJSONSuccess() {
        response = service.getJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void TestcompareJSONClientError() {
        response = service.getJSON("jsonName2");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestgetJsonListSuccess() {
        response = service.getJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void TestgetJsonListClientError() {
        response = service.getJSON("jsonName2");
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void TestgetOriginalListSuccess() {
        response = service.getJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
    @Test
    void TestgetOriginalListClientError() {
        response = service.getJSON("jsonName2");
        assertTrue(response.getStatusCode().is4xxClientError());
    }
}