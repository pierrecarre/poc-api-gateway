package com.ssodemo.apiservice.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class WhoamiRestController {

    private static final String HEADER_FORMAT = "%s: %s";

    @GetMapping(path = "/whoami")
    public ResponseEntity<String> getAuthorizedUserName(@RequestHeader MultiValueMap<String, String> headers, HttpServletRequest request) {
        final List<String> whoamiAnswer = new ArrayList<>();
        whoamiAnswer.add(String.format("Hostname: %s", request.getRemoteHost()));
        whoamiAnswer.add(String.format("RemoteAddr: %s:%s", request.getRemoteAddr(), request.getRemotePort()));
        whoamiAnswer.add(String.format("%s %s HTTP/1.1", request.getMethod(), request.getRequestURI()));
        whoamiAnswer.add(String.format(HEADER_FORMAT, HttpHeaders.HOST, request.getHeader(HttpHeaders.HOST)));
        whoamiAnswer.add(String.format(HEADER_FORMAT, HttpHeaders.USER_AGENT, request.getHeader(HttpHeaders.USER_AGENT)));
        headers.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).filter((entry) -> !(entry.getKey().equalsIgnoreCase(HttpHeaders.HOST) || entry.getKey().equalsIgnoreCase(HttpHeaders.USER_AGENT)))
                .forEach((entry) -> entry.getValue().forEach((value) -> whoamiAnswer.add(String.format(HEADER_FORMAT, StringUtils.capitalize(entry.getKey()), value))));
        return ResponseEntity.ok(String.join("\n", whoamiAnswer));
    }
}
