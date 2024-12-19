package org.example.model.pojo;

public enum LogSinkType {
    CONSOLE {
        @Override
        public <T> T accept(LogSinkTypeVisitor<T> visitor) {
            return visitor.visitConsole();
        }
    };

    public abstract <T> T accept(LogSinkTypeVisitor<T> var1);

    public interface LogSinkTypeVisitor<T> {
        T visitConsole() ;
    }
}
