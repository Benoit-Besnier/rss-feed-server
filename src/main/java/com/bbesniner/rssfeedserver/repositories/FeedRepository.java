package com.bbesniner.rssfeedserver.repositories;

import com.bbesniner.rssfeedserver.hibernateentities.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

}
