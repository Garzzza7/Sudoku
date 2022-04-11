package pl.cp.sudoku.dao;

import java.io.*;

import pl.cp.sudoku.SudokuBoard;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {

    private final String path;

    public FileSudokuBoardDao(String fileName) {
        this.path = fileName;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path))){
            sudokuBoard = (SudokuBoard) inputStream.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard object) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(object);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("FileSudokuBoardDao closed!");
    }
}
