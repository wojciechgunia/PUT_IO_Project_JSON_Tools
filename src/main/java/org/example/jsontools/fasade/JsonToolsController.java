package org.example.jsontools.fasade;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.services.JsonToolsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jsontools")
@RequiredArgsConstructor
@Slf4j

public class JsonToolsController
{
    private final JsonToolsService jsonToolsService;

    @RequestMapping(path="/load-json",method = RequestMethod.POST)
    public ResponseEntity<?> loadJSON(@RequestBody String JSONbody, @RequestParam String JSONname,
                                      HttpServletResponse response)
    {
        return jsonToolsService.saveJSON(JSONbody, JSONname);
    }

    @RequestMapping(path="/get-json",method = RequestMethod.GET)
    public ResponseEntity<?> getJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.getJSON(JSONname);
    }

    @RequestMapping(path="/get-original",method = RequestMethod.GET)
    public ResponseEntity<?> getOriginal(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.getOriginal(JSONname);
    }

    @RequestMapping(path="/get-minimalize",method = RequestMethod.GET)
    public ResponseEntity<?> minJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.minJSON(JSONname);
    }

    @RequestMapping(path="/get-full",method = RequestMethod.GET)
    public ResponseEntity<?> fullJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return jsonToolsService.fullJSON(JSONname);
    }

    @RequestMapping(path="/get-filtered",method = RequestMethod.GET)
    public ResponseEntity<?> filterJSON(@RequestParam String JSONname, @RequestParam List<String> keysToLeave, HttpServletResponse response)
    {
        return jsonToolsService.filtJSON(JSONname,keysToLeave);
    }

    @RequestMapping(path="/get-without",method = RequestMethod.GET)
    public ResponseEntity<?> withoutJSON(@RequestParam String JSONname, @RequestParam List<String> keysToRemove, HttpServletResponse response)
    {
        return jsonToolsService.withoutJSON(JSONname, keysToRemove);
    }

    @RequestMapping(path="/get-differences",method = RequestMethod.GET)
    public ResponseEntity<?> diffJSONs(@RequestParam String JSONname1,  @RequestParam String JSONname2, HttpServletResponse response)
    {
        return jsonToolsService.compareJSON(JSONname1, JSONname2);
    }


}
