package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "feeds")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"sourceFeedUrl"})
public class Feed {

    private String uuid;

    @NotEmpty
    private String title;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String link;

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String sourceFeedUrl;

    @Column(length = 1024)
    private String copyright;

    private String description;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Person> authors;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "fk_feeds")
    private List<FeedCategory> categories;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Person> contributors;
//
//    private Content descriptionEx;
//
//    private String docs;
//
    private String encoding;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Entry> entries;

    private String feedType;

    private String generator;

//    private Image icon;
//
//    private Image image;

    private String language;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<Link> links;

//    private String managingEditor;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Module> modules;

    private Date publishedDate;

    private String styleSheet;

//    private List<String> supportedFeedTypes;
//
//    private Content titleEx;

    private String uri;

    private String webMaster;

    private Date autoUpdatedDate;

}
