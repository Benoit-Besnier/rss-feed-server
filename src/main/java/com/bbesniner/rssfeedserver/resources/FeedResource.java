package com.bbesniner.rssfeedserver.resources;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.entities.requestbody.FeedCandidate;
import com.bbesniner.rssfeedserver.services.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedResource {

//    static final String PATH =;

    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<List<Feed>> findAll() {
        List<Feed> feeds = this.feedService.findAll();

        return new ResponseEntity<>(feeds, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Feed> findOneById(@PathVariable("id") final Long id) {
        try {
            return ResponseEntity.ok(this.feedService.findOneById(id));
        } catch (Exception e) {
            // TODO : Should not be generic Exception
            e.printStackTrace();
            // TODO : Adapt error code depending of root cause
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody final FeedCandidate feedCandidate) {
        try {
            this.feedService.createFromUrl(feedCandidate.getUrl());
        } catch (Exception e) {
            // TODO : Should not be generic Exception
            e.printStackTrace();
            // TODO : Adapt error code depending of root cause
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") final Long id) {
        this.feedService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
