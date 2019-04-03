package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "categories")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Category {

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;

    private String taxonomyUri;

}
