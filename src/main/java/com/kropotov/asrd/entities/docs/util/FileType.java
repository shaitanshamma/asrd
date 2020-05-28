package com.kropotov.asrd.entities.docs.util;

import com.kropotov.asrd.entities.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "file_types")
@AllArgsConstructor
@NoArgsConstructor
public class FileType extends BaseEntity implements Serializable {

    private String title;
    private String directory;
}
