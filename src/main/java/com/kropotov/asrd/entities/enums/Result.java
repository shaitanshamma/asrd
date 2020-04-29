package com.kropotov.asrd.entities.enums;

import lombok.Getter;

@Getter
public enum Result {
    NEGATIVE("Отрицательный"), POSITIVE("Положительный");

    private String title;

    Result(String title) {
        this.title = title;
    }
}
