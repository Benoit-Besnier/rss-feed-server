package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "persons")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Person {

    private String email;

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;

    private String uri;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Module> modules;

}
