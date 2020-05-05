package com.kropotov.asrd.entities.titles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kropotov.asrd.entities.common.TitleEntity;
import com.kropotov.asrd.entities.items.ControlSystem;
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
    @JsonIgnore
    private String path;

    @OneToMany(mappedBy = "title"/*, fetch = FetchType.LAZY*/) // fetch - доставать либо не доставать список со всеми зависимостями при запросе SystemTitleDto
    @JsonIgnore
    private List<ControlSystem> systems;

    @ManyToMany(mappedBy = "systemTitles")
    @JsonIgnore
    private List<Topic> topics;

    @ManyToMany
    @JoinTable(
            name = "system_titles_device_titles",
            joinColumns = @JoinColumn(name = "system_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "device_titles_id")
    )
    @JsonIgnore
    private List<DeviceTitle> deviceTitles;

    @ManyToMany
    @JoinTable(
            name = "system_titles_system_components_titles",
            joinColumns = @JoinColumn(name = "system_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_components_titles_id")
    )
    @JsonIgnore
    private List<SystemComponentTitle> systemComponentTitles;

}
