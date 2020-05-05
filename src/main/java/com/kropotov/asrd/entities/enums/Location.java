package com.kropotov.asrd.entities.enums;

import lombok.Getter;

@Getter
public enum Location {
    ANYWHERE("Нет на СГП"), SGP("На СГП");

    private String title;

    Location(String title) {
        this.title = title;
    }
}
