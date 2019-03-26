package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "feeds")
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
