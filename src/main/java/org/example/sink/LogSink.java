package org.example.sink;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import org.example.model.pojo.IngestionType;
import org.example.model.pojo.LogLevel;
import org.example.model.pojo.LogMessage;
import org.example.model.pojo.LogSinkType;

@Getter
public abstract class LogSink {
    private String name;
    private LogLevel level;
    private static LinkedBlockingQueue<String> quque = new LinkedBlockingQueue<>();
    private LogSinkType logSinkType;
    private IngestionType ingestionType;


    LogSink(String name, LogLevel level, LogSinkType logSinkType) {
        this.name = name;
        this.level = level;
        this.logSinkType = logSinkType;
    }

    public void asyncStart(){
        Runnable loggingTask = () -> {
            while (true) {
                try{
                    String message = quque.take();
                    log(message);
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        };
    }



    public void log(String message){
        if (ingestionType.equals(IngestionType.SYNCHRONOUS)) {
            entry(message);
        }else {
            quque.offer(message);
        }
    }

    public abstract void entry(String message);
}
