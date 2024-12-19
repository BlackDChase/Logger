package org.example.model.pojo;

import lombok.Getter;

@Getter
public enum LogLevel {
    DEBUG(0),
    INFO(10),
    WARN(20),
    ERROR(30),
    FATAL(40),
    ;
    private int level;
    LogLevel(int level) {
        this.level = level;
    }

}
