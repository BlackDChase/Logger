package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import org.example.model.config.LogSinkConfig;
import org.example.model.config.LoggingConfig;
import org.example.model.pojo.LogLevel;
import org.example.model.pojo.LogSinkType.LogSinkTypeVisitor;
import org.example.sink.ConsoleLogger;
import org.example.sink.LogSink;

public class Logger implements Serializable {
    private volatile static Logger loggerInstance;
    private volatile static Set<LogSink> logSink;
    private static LoggingConfig loggingConfig;
    private static DateTimeFormatter dataTimeFormat;


    private Logger(LoggingConfig config) {
        if (loggerInstance != null) {
            throw new IllegalStateException("Logger has already been initialized");
        }
        loggingConfig = config;
        dataTimeFormat = DateTimeFormatter.ofPattern(loggingConfig.getTimeFormat());
        logSink = loggingConfig.getSinks().stream().map(this::buildSink)
            .collect(Collectors.toSet());

    }

    private LogSink buildSink(LogSinkConfig sinkConfig) {
        return sinkConfig.getType().accept(new LogSinkTypeVisitor<>() {
            @Override
            public LogSink visitConsole() {
                ConsoleLogger log = new ConsoleLogger(sinkConfig.getName(), sinkConfig.getLevel());
                log.asyncStart();
                return log;
            }
        });
    }

    public static void init(LoggingConfig config) {
        if (Objects.isNull(loggerInstance)) {
            synchronized (Logger.class) {
                if (loggerInstance == null) {
                    loggerInstance = new Logger(config);
                }
            }
        }
    }

    private void createLog(LogLevel level, String message) {
        logSink.stream()
            .filter(sink -> sink.getLevel().getLevel()<level.getLevel())
            .forEach(sink -> sink.log(buildMessage(level, message)));
    }

    private String buildMessage(LogLevel level, String message) {
        return String.format("[%s] | %s | %s",
            level.name(),
            dataTimeFormat.format(LocalDateTime.now()),
            message);
    }

    public void info(String message) {
        createLog(LogLevel.INFO, message);
    }
    public void error(String message) {
        createLog(LogLevel.ERROR, message);
    }
    public void warn(String message) {
        createLog(LogLevel.WARN, message);
    }
    public void debug(String message) {
        createLog(LogLevel.DEBUG, message);
    }
    public void fatal(String message) {
        createLog(LogLevel.FATAL, message);
    }
}