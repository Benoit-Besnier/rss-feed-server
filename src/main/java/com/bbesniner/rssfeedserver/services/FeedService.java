package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.entities.exceptions.CreateConflictException;
import com.bbesniner.rssfeedserver.entities.exceptions.ResourceNotFound;
import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.repositories.FeedRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    private final ModelMapper modelMapper;

    public List<Feed> findAll() {
        return this.feedRepository.findAll();
    }

    public Feed findOneById(final String uuid) {
        return this.feedRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFound(uuid, Feed.class));
    }

    public Feed createFromSourceUrl(final String url) throws CreateConflictException {
        final Feed feed = this.parseFeedFromTargetUrl(url);

        if (!this.feedRepository.findBySourceFeedUrl(url).isPresent()) {
            feed.setSourceFeedUrl(url);
            feed.setUuid(this.generateNewUuid());

            return this.feedRepository.save(feed);
        } else {
            throw new CreateConflictException("Cannot create " + feed.getLink() + " because it already exist.");
        }
    }

    private String generateNewUuid() {
        String uuid = UUID.randomUUID().toString();

        if (!this.feedRepository.findByUuid(uuid).isPresent())
            return uuid;
        else
            return this.generateNewUuid();
    }

    public void deleteByUuid(final String uuid) {
        this.feedRepository.deleteByUuid(uuid);
    }

    private Feed parseFeedFromTargetUrl(final String feedUrl) {
        final SyndFeed parsedFeed = this.fetchFeed(feedUrl);

        return this.convertToDTO(parsedFeed);
    }

    private Feed convertToDTO(SyndFeed parsedFeed) {
        return this.modelMapper.map(parsedFeed, Feed.class);
    }

    private SyndFeed fetchFeed(final String feedUrl) {
        final RestTemplate restTemplate = new RestTemplate();

        return restTemplate.execute(feedUrl, HttpMethod.GET, null, response -> {
            SyndFeedInput input = new SyndFeedInput();
            try {
                return input.build(new XmlReader(response.getBody()));
            } catch (FeedException e) {
                throw new IOException("Could not parse response", e);
            }
        });
    }

    public void updateFeed(final Map.Entry<Feed, Feed> feedEntry) {
        final Feed source = feedEntry.getKey();
        final Feed toUpdate = feedEntry.getValue();

        toUpdate.setUuid(source.getUuid());
        toUpdate.setSourceFeedUrl(source.getSourceFeedUrl());
        toUpdate.setAutoUpdatedDate(new Date());
        this.feedRepository.save(toUpdate);
    }

    @Scheduled(fixedDelay = 300000)
    public void updateFeed() {
        final long startTime = System.currentTimeMillis();

        final List<Feed> feedList = this.feedRepository.findAll();
        final Map<Feed, Feed> feedMap = new HashMap<>();

        for (final Feed feed : feedList) {
            feedMap.put(feed, this.convertToDTO(this.fetchFeed(feed.getSourceFeedUrl())));
        }

        feedMap.entrySet().forEach(this::updateFeed);

        log.info("New task executed at {} and take {} ms to be executed",
                Date.from(Instant.ofEpochMilli(startTime)),
                System.currentTimeMillis() - startTime);
    }
}
