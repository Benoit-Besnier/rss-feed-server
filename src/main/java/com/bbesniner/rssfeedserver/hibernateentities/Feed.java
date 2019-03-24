package com.bbesniner.rssfeedserver.hibernateentities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Builder
public class Feed {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    @Column(length = 1024)
    private String copyright;

    private String link;
}
