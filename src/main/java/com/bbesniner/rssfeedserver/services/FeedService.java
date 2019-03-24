package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.hibernateentities.Feed;
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

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    public Feed findOneByTitle(final String name) throws Exception {
        return this.feedRepository.findOne(Example.of(new Feed(name, null))).orElseThrow(Exception::new);
    }

    public void createFromUrl(final String url) throws FeedException {
        final Feed feed = this.parseFeedFromTargetUrl(url);

        this.feedRepository.save(feed);
    }

    public void updateFromUrl(final String url) throws FeedException {
        final Feed feed = this.parseFeedFromTargetUrl(url);

        this.feedRepository.save(feed);
    }

    private Feed parseFeedFromTargetUrl(final String feedUrl) throws FeedException {
        final SyndFeed parsedFeed = this.fetchFeed(feedUrl);
        final Feed feed = new Feed();

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
