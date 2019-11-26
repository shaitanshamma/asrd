package com.kropotov.asrd.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*    id        INT(11)      NOT NULL AUTO_INCREMENT,
    title     VARCHAR(50)  NOT NULL,
    path VARCHAR(128) NOT NULL,*/

@Entity
@Table(name = "device_titles")
@Data
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

    @ManyToMany
    @JoinTable(
            name = "system_titles_device_titles",
            joinColumns = @JoinColumn(name = "device_titles_id"),
            inverseJoinColumns = @JoinColumn(name = "system_titles_id")
    )
    private List<SystemTitle> systemTitles;
}
