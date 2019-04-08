package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "enclosures")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"url"})
public class Enclosure {

    private String type;

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String url;

    private long length;
}
