package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*CREATE TABLE system_titles
(
    id        INT(11)      NOT NULL AUTO_INCREMENT,
    title     VARCHAR(50)  NOT NULL,
    path VARCHAR(128) NOT NULL,*/

@Entity
@Table(name = "system_titles")
@Data
@NoArgsConstructor
public class SystemTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "title"/*, fetch = FetchType.LAZY*/) // fetch - доставать либо не доставать список со всеми зависимостями при запросе SystemTitle
    @JsonIgnore
    private List<ControlSystem> systems;

    @ManyToMany
    @JoinTable(
            name = "topic_titles_system_titles",
            joinColumns = @JoinColumn(name = "system_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_titles_id")
    )
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
