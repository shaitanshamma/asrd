package com.kropotov.asrd.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "system_components_titles")
@Data
@NoArgsConstructor
public class SystemComponentTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @ManyToMany
    @JoinTable(
            name = "system_titles_system_components_titles",
            joinColumns = @JoinColumn(name = "system_components_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_titles_id")
    )
    private List<SystemTitle> systemTitles;

}
