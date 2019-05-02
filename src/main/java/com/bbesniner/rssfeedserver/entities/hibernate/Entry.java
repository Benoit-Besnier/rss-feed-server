package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "entries")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"link"})
public class Entry {

    @GeneratedValue
    private Long id;

    @ManyToOne
    private Feed feed;

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String link;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Person> authors;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_entries")
    private List<EntryCategory> categories;

    private String comments;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Person> contributors;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Content> contents;
//
//    private Content description;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Enclosure> enclosures;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Link> links;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Module> modules;

    private Date publishedDate;

    private String title;

//    private Content titleEx;

    private Date updatedDate;

    private String uri;

//    private Object wireEntry;

}
