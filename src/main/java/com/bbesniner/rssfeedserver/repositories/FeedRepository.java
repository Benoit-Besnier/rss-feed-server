package com.bbesniner.rssfeedserver.repositories;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    Optional<Feed> findByUuid(final String uuid);

    Optional<Feed> findBySourceFeedUrl(final String sourceFeedUrl);

    void deleteByUuid(final String uuid);

}
