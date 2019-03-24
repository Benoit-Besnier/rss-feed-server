package com.bbesniner.rssfeedserver.resources;

import com.rometools.rome.feed.atom.Feed;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feeds")
public class FeedResource {

    @GetMapping
    public List<Feed> findAll() {
        return List.of();
    }

    @GetMapping(value = "/{name}")
    public Feed findOneByName(@PathVariable("name") final String name) {
        return null;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody final String url) {

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity delete(@PathVariable("name") final String name) {
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{name}")
    public ResponseEntity update(@PathVariable("name") final String name, @RequestBody final Feed updatedFeed) {
        return ResponseEntity.ok().build();
    }

}
