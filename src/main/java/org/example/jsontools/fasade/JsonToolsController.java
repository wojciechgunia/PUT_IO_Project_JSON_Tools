package org.example.jsontools.fasade;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.services.JsonToolsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Klasa zajmującia się zarządzaniem zapytan otrzymanych przez klienta
 * Przetwarza ona wszystkie otrzymane żądania,
 * przekazuje odpowiednie paramentry danym funkcją klasy JsonToolsService
 * oraz odsyła otrzymane odpowiedzi
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/jsontools")
@RequiredArgsConstructor
@Slf4j

public class JsonToolsController
{
    private final JsonToolsService jsonToolsService;

    /**
     * Funckja zapisuje Jsona z przesłanego zapytania POST
     *
     * @param JSONbody - treść wysyłanego pliku JSON
     * @param JSONname - nazwa wysyłanego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu
     */
    @RequestMapping(path="/load-json",method = RequestMethod.POST)
    public ResponseEntity<?> loadJSON(@RequestBody String JSONbody, @RequestParam String JSONname,
                                      HttpServletResponse response)
    {
        return jsonToolsService.saveJSON(JSONbody, JSONname);
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-json",method = RequestMethod.GET)
    public ResponseEntity<?> getJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.getJSON(JSONname);
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON w orginalnej formie
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-original",method = RequestMethod.GET)
    public ResponseEntity<?> getOriginal(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.getOriginal(JSONname);
    }

    @RequestMapping(path="/get-json-list",method = RequestMethod.GET)
    public ResponseEntity<?> getJsonList(HttpServletResponse response)
    {
        return jsonToolsService.getJsonList();
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON w zminimalizowanej formie,
     * w celu oszczędzania danych
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-minimalize",method = RequestMethod.GET)
    public ResponseEntity<?> minJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.minJSON(JSONname);
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON w pełnej formie,
     * w celu zwiększenia czytelności
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-full",method = RequestMethod.GET)
    public ResponseEntity<?> fullJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.fullJSON(JSONname);
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON w pełnej formie,
     * w którym pozostawiono tylko wybrane przez klienta atrybuty
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param keysToLeave - lista wybranych atrybutów do zachowania
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-filtered",method = RequestMethod.GET)
    public ResponseEntity<?> filterJSON(@RequestParam String JSONname, @RequestParam List<String> keysToLeave, HttpServletResponse response)
    {
        return jsonToolsService.filtJSON(JSONname,keysToLeave);
    }

    /**
     * Funkcja odsyła klientowi wybrany przez niego plik JSON w pełnej formie,
     * w którym usunięto wybrane przez klienta atrybuty
     *
     * @param JSONname - nazwa wybranego pliku JSON
     * @param keysToRemove - wybrne do usunięcia atrybuty
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-without",method = RequestMethod.GET)
    public ResponseEntity<?> withoutJSON(@RequestParam String JSONname, @RequestParam List<String> keysToRemove, HttpServletResponse response)
    {
        return jsonToolsService.withoutJSON(JSONname, keysToRemove);
    }

    /**
     * Funkcja odsyła klientowi plik JSON w pełnej formie,
     * w którym są zapisane wszystkie wartości,
     * które występują tylko w jednym z dwóch wybranych przez niego plików
     *
     * @param JSONname1 - nazwa pierwszego wybranego pliku JSON
     * @param JSONname2 - nazwa drugiego wybranego pliku JSON
     * @param response - opowiedź servera HTTP
     * @return - odpowiedź aplikacji do klienta, kod potwierdzenia lub błędu oraz treść JSON-a
     */
    @RequestMapping(path="/get-differences",method = RequestMethod.GET)
    public ResponseEntity<?> diffJSONs(@RequestParam String JSONname1,  @RequestParam String JSONname2, HttpServletResponse response)
    {
        return jsonToolsService.compareJSON(JSONname1, JSONname2);
    }


}
