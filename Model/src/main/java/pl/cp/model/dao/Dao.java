package pl.cp.model.dao;

public interface Dao<T> extends AutoCloseable {
    T read();

    void write(T object);
}
