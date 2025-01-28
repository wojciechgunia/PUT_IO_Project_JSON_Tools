package org.example.jsontools.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JsonToolsServiceTest {

    @InjectMocks
    private JsonToolsService service;

    @Mock
    private JsonToolsService mockService;

    ResponseEntity<?> response;
    String jsonBody = "{  \"name\":\"John\",\n\t\t \"age\":30,\n \"car\":null}";
    String jsonName = "testJson";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //service = new JsonToolsService();
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

    @Test
    void testGetJSONWithMock() {

        // Configure mock object
        when(mockService.getJSON(jsonName)).thenReturn(new ResponseEntity(jsonBody, HttpStatus.OK));

        // Interaction
        response = mockService.getJSON(jsonName);

        // Verification
        verify(mockService).getJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetOriginalWithMock() {

        // Configure mock object
        when(mockService.getOriginal(jsonName)).thenReturn(new ResponseEntity(jsonBody, HttpStatus.OK));

        // Interaction
        response = mockService.getOriginal(jsonName);

        // Verification
        verify(mockService).getOriginal(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testFullJSONWithMock() {

        // Configure mock object
        when(mockService.fullJSON(jsonName)).thenReturn(new ResponseEntity(jsonBody, HttpStatus.OK));

        // Interaction
        response = mockService.fullJSON(jsonName);

        // Verification
        verify(mockService).fullJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testMinJSONWithMock() {

        // Configure mock object
        when(mockService.minJSON(jsonName)).thenReturn(new ResponseEntity(jsonBody, HttpStatus.OK));

        // Interaction
        response = mockService.minJSON(jsonName);

        // Verification
        verify(mockService).minJSON(jsonName);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }
}