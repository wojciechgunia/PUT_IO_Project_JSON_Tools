package org.example.jsontools.fasade;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jsontools.entity.Code;
import org.example.jsontools.entity.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/jsontools")
@RequiredArgsConstructor
@Slf4j
public class JsonToolsController
{
    @RequestMapping(path="/load-json",method = RequestMethod.POST)
    public ResponseEntity<?> loadJSON(@RequestBody JsonNode json, @RequestParam String JSONname, HttpServletResponse response)
    {
        return ResponseEntity.ok(new Response(Code.SUCCESS));
    }

    @RequestMapping(path="/get-minimalize",method = RequestMethod.GET)
    public ResponseEntity<?> minJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path="/get-full",method = RequestMethod.GET)
    public ResponseEntity<?> fullJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path="/get-filtered",method = RequestMethod.GET)
    public ResponseEntity<?> filterJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path="/get-without",method = RequestMethod.GET)
    public ResponseEntity<?> withoutJSON(@RequestParam String JSONname, HttpServletResponse response)
    {
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(path="/get-differences",method = RequestMethod.GET)
    public ResponseEntity<?> diffJSONs(@RequestParam String JSONname1,  @RequestParam String JSONname2, HttpServletResponse response)
    {
        return ResponseEntity.ok("ok");
    }


}
