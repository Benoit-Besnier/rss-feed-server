package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "feeds")
@Builder
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Feed {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty
    private String title;

    @NotEmpty
    private String link;

    @Column(length = 1024)
    private String copyright;

    private String description;

}
