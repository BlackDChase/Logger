package org.example.model.config;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.sink.LogSink;

@Getter
@AllArgsConstructor
public class LoggingConfig {
    String timeFormat;
    List<LogSinkConfig> sinks;

}
