package org.example.model.config;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import org.example.model.pojo.IngestionType;
import org.example.model.pojo.LogLevel;
import org.example.model.pojo.LogSinkType;

@Getter
@AllArgsConstructor
public class LogSinkConfig {
    private LogLevel level;
    private LogSinkType type;
    private IngestionType ingestionType;
    @Include
    private String name;

}
