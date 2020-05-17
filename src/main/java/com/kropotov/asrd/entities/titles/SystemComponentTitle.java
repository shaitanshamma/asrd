package com.kropotov.asrd.entities.titles;


import com.kropotov.asrd.entities.common.TitleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "system_component_titles")
@Getter
@Setter
@NoArgsConstructor
public class SystemComponentTitle extends TitleEntity {

    @Column(name = "path")
    private String path;

    @ManyToMany(mappedBy = "systemComponentTitles")
    private List<SystemTitle> systemTitles;

}
