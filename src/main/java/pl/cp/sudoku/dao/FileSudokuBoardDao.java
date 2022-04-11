package pl.cp.sudoku.dao;

import pl.cp.sudoku.SudokuBoard;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable{

    private final String path;

    public FileSudokuBoardDao(String fileName) {
        this.path = fileName;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = null;
        try{
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
            sudokuBoard = (SudokuBoard) inputStream.readObject();
            inputStream.close();
        }
        catch (IOException | ClassNotFoundException exception){
            System.out.println(exception.getMessage());
        }
        return sudokuBoard;
    }

    @Override
    public void write(SudokuBoard object) {

    }

    @Override
    public void close() throws Exception {

    }
}
