package com.kropotov.asrd.entities.titles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kropotov.asrd.entities.items.ControlSystem;
import com.kropotov.asrd.entities.utils.TitleEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;



@Entity
@Table(name = "system_titles")
@Getter
@Setter
@NoArgsConstructor
public class SystemTitle extends TitleEntity {

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "title"/*, fetch = FetchType.LAZY*/) // fetch - доставать либо не доставать список со всеми зависимостями при запросе SystemTitle
    @JsonIgnore
    private List<ControlSystem> systems;

    @ManyToMany(mappedBy = "systemTitles")
    @JsonBackReference
    private List<Topic> topics;

    @ManyToMany
    @JoinTable(
            name = "system_titles_device_titles",
            joinColumns = @JoinColumn(name = "system_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "device_titles_id")
    )
    private List<DeviceTitle> deviceTitles;

    @ManyToMany
    @JoinTable(
            name = "system_titles_system_components_titles",
            joinColumns = @JoinColumn(name = "system_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_components_titles_id")
    )
    private List<SystemComponentTitle> systemComponentTitles;

}
