package com.bbesniner.rssfeedserver.entities.hibernate;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "entry_categories")
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class EntryCategory {

    @Id
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_entries")
    private Entry entry;

    private String taxonomyUri;

}
