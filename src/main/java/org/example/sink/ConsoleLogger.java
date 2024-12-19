package org.example.sink;

import org.example.model.pojo.LogLevel;
import org.example.model.pojo.LogSinkType;

public class ConsoleLogger extends LogSink {
    public ConsoleLogger(String name, LogLevel level){
        super(name, level, LogSinkType.CONSOLE);
    }

    @Override
    public void entry(String message) {
        System.out.println(message);
    }
}
