package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "feed_categories")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class FeedCategory {

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_feeds")
    private Feed feed;

    private String taxonomyUri;

}
