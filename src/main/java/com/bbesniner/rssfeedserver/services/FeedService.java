package com.bbesniner.rssfeedserver.services;

import com.bbesniner.rssfeedserver.entities.exceptions.UserNotFoundException;
import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.repositories.FeedRepository;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
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

    public Feed findOneById(final Long id) {
        return this.feedRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Feed createFromUrl(final String url) {
        final Feed feed = this.parseFeedFromTargetUrl(url);

        return this.feedRepository.save(feed);
    }

    public void deleteById(final Long id) {
        this.feedRepository.deleteById(id);
    }

    private Feed parseFeedFromTargetUrl(final String feedUrl) {
        final SyndFeed parsedFeed = this.fetchFeed(feedUrl);

        return Feed.builder()
                .title(parsedFeed.getTitle())
                .link(parsedFeed.getLink())
                .copyright(parsedFeed.getCopyright())
                .description(parsedFeed.getDescription())
                .build();
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
