package com.kropotov.asrd.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topic_titles")
@Getter
@Setter
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "topic_titles_system_titles",
            joinColumns = @JoinColumn(name = "topic_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_titles_id")
    )
    private List<SystemTitle> systemTitles;
}
