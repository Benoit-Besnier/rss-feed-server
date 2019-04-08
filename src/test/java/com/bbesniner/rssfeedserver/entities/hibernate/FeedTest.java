package com.bbesniner.rssfeedserver.entities.hibernate;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FeedTest {

    @Test
    public void shouldInstantiate() {
        final String uuid = "3a8a2cba-b973-4a1e-9925-ee3957528688";
        final String link = "Michel";
        final String uri = "Michel";
        final String title = "Michel";
        final String description = "Michel";
        final Date publishedDate = Date.from(Instant.now());

        final Feed feed = Feed.builder()
                .uuid(uuid)
                .link(link)
                .sourceFeedUrl(uri)
                .title(title)
                .description(description)
                .publishedDate(publishedDate)
                .build();

        feed.setEncoding("UTF-8");
        assertEquals(link, feed.getLink());
        assertEquals(publishedDate, feed.getPublishedDate());
        assertEquals("UTF-8", feed.getEncoding());
    }

}