package pl.cp.sudoku.dao;

public interface Dao<T> extends AutoCloseable {
    T read();

    void write(T object);
}
