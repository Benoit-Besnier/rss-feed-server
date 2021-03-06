package com.bbesniner.rssfeedserver.repositories;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedRepositoryTest {

    @Autowired
    private FeedRepository feedRepository;

    @Test
    public void assertThatRepositoryProvideCorrectValues() {
        final Feed feed = Feed.builder()
                .uuid("369045b8-ea4d-4ac0-8b78-83ae8b7ccec4")
                .link("http://some.where.wl/")
                .uri("https://some.where.wl/")
                .sourceFeedUrl("https://some.where.wl/")
                .title("This is a title for the test case")
                .description("When we need to check if a repository work as expected, we make a testing case, " +
                        "then whe push this through a mocked instance of the repository and " +
                        "finally get the result which should be the 'same instance'")
                .publishedDate(Date.from(Instant.now()))
                .build();
        final Feed savedFeed = this.feedRepository.save(feed);
        final Feed foundFeed = this.feedRepository.findByUuid(savedFeed.getUuid()).get();

        assertEquals(foundFeed.getLink(), feed.getLink());
        assertEquals(foundFeed.getPublishedDate(), feed.getPublishedDate());
        assertNotNull(foundFeed.getUuid());
    }

}