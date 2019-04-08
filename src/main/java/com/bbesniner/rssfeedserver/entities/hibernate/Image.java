package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "images")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"url"})
public class Image {

    @Id
    @NotEmpty
    private String url;

    private String title;

    private Integer width;

    private Integer height;

    private String description;
}
