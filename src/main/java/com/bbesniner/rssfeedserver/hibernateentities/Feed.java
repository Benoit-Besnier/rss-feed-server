package com.bbesniner.rssfeedserver.hibernateentities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter @Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Feed {

    // TEMP Constructor
    public Feed(final String title, final String description) {
        super();
        this.title = title;
        this.description = description;
    }

    @Id
    @GeneratedValue
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    private String copyright;

    private String link;
}
