package juja.microservices.links.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import juja.microservices.links.model.Link;
import juja.microservices.links.model.SaveLinkRequest;
import juja.microservices.links.service.LinksService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author Vladimir Zadorozhniy
 */

@Slf4j
@RestController
@AllArgsConstructor
@Api(tags = "Links Microservice", description = "The Links Microservice API")
@RequestMapping(value = "/v1/links")
public class LinksController {
    private final LinksService linksService;

    @ApiOperation(
            value = "Save new link in links storage",
            notes = "Returns a message with saved Link",
            response = Link.class, tags = {}
    )
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The link has been successfully saved"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found")
    })
    @PostMapping(consumes = "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Link> saveLink(@Valid @RequestBody SaveLinkRequest request) throws Exception {
        log.info("Received saveLink request: '{}'", request);
        return ResponseEntity.ok(linksService.saveLink(request));
    }

    @ApiOperation(
            value = "Get all saved links list",
            notes = "Returns previously saved Links",
            response = Link.class, tags = {})
    @ApiResponses(value = {
            @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "The list of saved links has been delivered"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_REQUEST, message = "Bad request"),
            @ApiResponse(code = HttpURLConnection.HTTP_BAD_METHOD, message = "Bad method"),
            @ApiResponse(code = HttpURLConnection.HTTP_UNSUPPORTED_TYPE, message = "Unsupported request media type"),
            @ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Link>> getAllLinks() {
        List<Link> result = linksService.getAllLinks();
        return ResponseEntity.ok(result);
    }
}
