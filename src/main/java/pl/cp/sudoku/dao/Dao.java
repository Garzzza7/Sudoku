package pl.cp.sudoku.dao;

public interface Dao<T> {
    T read();
    void write(T object);
}
