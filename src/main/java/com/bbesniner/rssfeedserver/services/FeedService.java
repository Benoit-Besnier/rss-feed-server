package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.repositories.FeedRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public List<Feed> findAll() {
        return this.feedRepository.findAll();
    }

    public Feed findOneById(final Long id) throws Exception {
        return this.feedRepository.findOne(Example.of(Feed.builder().id(id).build())).orElse(null);
    }

    private Feed findOneByTitle(final String title) throws Exception {
        return this.feedRepository.findOne(Example.of(Feed.builder().title(title).build())).orElse(null);
    }

    public void createFromUrl(final String url) throws Exception {
        final Feed feed = this.parseFeedFromTargetUrl(url);
        final Feed duplicate = this.findOneByTitle(feed.getTitle());

        if (duplicate == null) {
            this.feedRepository.save(feed);
        } else {
            // TODO : Should be a custom Exception in order to return a better code response
            throw new Exception("Conflict");
        }
    }

    public void deleteOneById(final Long id) throws Exception {
        final Feed toDelete = this.findOneById(id);
        this.feedRepository.delete(toDelete);
    }

    private Feed parseFeedFromTargetUrl(final String feedUrl) throws FeedException {
        final SyndFeed parsedFeed = this.fetchFeed(feedUrl);
        final Feed feed = Feed.builder().build();

        feed.setTitle(parsedFeed.getTitle());
        feed.setLink(parsedFeed.getLink());
        feed.setCopyright(parsedFeed.getCopyright());
        feed.setDescription(parsedFeed.getDescription());

        return feed;
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

}
