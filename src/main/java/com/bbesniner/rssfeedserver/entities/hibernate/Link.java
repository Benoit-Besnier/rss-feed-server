package com.bbesniner.rssfeedserver.entities.hibernate;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "links")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"href"})
public class Link {

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String href;

    private String hreflang;

    private long length;

    private String ref;

    private String title;

    private String type;

}
