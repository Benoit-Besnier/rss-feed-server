package com.bbesniner.rssfeedserver.controller;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.entities.requestbody.FeedCandidate;
import com.bbesniner.rssfeedserver.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(FeedController.PATH)
@RequiredArgsConstructor
public class FeedController {

    static final String PATH = "/feeds";

    private final FeedService feedService;

    @GetMapping("")
    public ResponseEntity<List<Feed>> findAll() {
        List<Feed> feeds = this.feedService.findAll();

        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    @GetMapping(value = "/{uuid}")
    public ResponseEntity<Feed> findOneById(@PathVariable("uuid") final String uuid) {
        return ResponseEntity.ok(this.feedService.findOneById(uuid));
    }

    @PostMapping("")
    public ResponseEntity create(@RequestBody final FeedCandidate feedCandidate, final HttpServletRequest request)
            throws CreateConflictException {
        final Feed saved = this.feedService.createFromSourceUrl(feedCandidate.getUrl());
        final URI location = ServletUriComponentsBuilder
                .fromContextPath(request)
                .path(FeedController.PATH + "/{uuid}")
                .buildAndExpand(saved.getUuid())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{uuid}")
    public ResponseEntity delete(@PathVariable("uuid") final String uuid) {
        this.feedService.deleteByUuid(uuid);

        return ResponseEntity.noContent().build();
    }
}
