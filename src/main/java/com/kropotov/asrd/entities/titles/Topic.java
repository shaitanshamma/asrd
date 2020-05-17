package com.kropotov.asrd.entities.titles;

import com.kropotov.asrd.entities.common.TitleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
public class Topic extends TitleEntity {

    @Column(name = "path")
    private String path;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "topic_system_title",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "system_title_id")
    )
    private List<SystemTitle> systemTitles;
}
