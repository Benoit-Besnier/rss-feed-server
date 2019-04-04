package com.bbesniner.rssfeedserver.entities.hibernate;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class FeedTest {

    @Test
    public void shouldInstantiate() {
        final Long id = 123456789L;
        final String link = "Michel";
        final String uri = "Michel";
        final String title = "Michel";
        final String description = "Michel";
        final Date publishedDate = Date.from(Instant.now());

        final Feed feed = Feed.builder()
                .id(id)
                .link(link)
                .uri(uri)
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