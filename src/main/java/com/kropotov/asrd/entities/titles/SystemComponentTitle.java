package com.kropotov.asrd.entities.titles;


import com.kropotov.asrd.entities.utils.TitleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "system_components_titles")
@Getter
@Setter
@NoArgsConstructor
public class SystemComponentTitle extends TitleEntity {

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
