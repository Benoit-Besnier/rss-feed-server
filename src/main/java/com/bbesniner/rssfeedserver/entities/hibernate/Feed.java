package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> authors;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Person> contributors;
//
//    private Content descriptionEx;
//
//    private String docs;
//
    private String encoding;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entry> entries;

    private String feedType;

    private String generator;

//    private Image icon;
//
//    private Image image;

    private String language;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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
