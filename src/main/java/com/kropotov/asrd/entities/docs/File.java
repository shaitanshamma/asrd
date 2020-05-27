package com.kropotov.asrd.entities.docs;

import com.kropotov.asrd.entities.common.BaseEntity;
import com.kropotov.asrd.entities.docs.util.FileType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "files")
@AllArgsConstructor
@NoArgsConstructor
public class File extends BaseEntity implements Serializable {

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private FileType type;
}
