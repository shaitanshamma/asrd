package com.kropotov.asrd.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/*    id        INT(11)      NOT NULL AUTO_INCREMENT,
    title     VARCHAR(50)  NOT NULL,
    path VARCHAR(128) NOT NULL,*/

@Entity
@Table(name = "device_titles")
@Getter
@Setter
@NoArgsConstructor
public class DeviceTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    @OneToMany(mappedBy = "title", fetch = FetchType.LAZY) // fetch - доставать либо не доставать список со всеми зависимостями при запросе SystemTitle
    @JsonBackReference
    private List<Device> devices;

    @ManyToMany
    @JoinTable(
            name = "system_titles_device_titles",
            joinColumns = @JoinColumn(name = "device_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_titles_id")
    )
    @JsonIgnore
    private List<SystemTitle> systemTitles;

    @OneToMany(mappedBy = "deviceTitle", fetch = FetchType.LAZY)
    private List<DeviceComponentTitle> componentTitles;
}
