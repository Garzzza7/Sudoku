package pl.cp.model.dao;

import pl.cp.model.SudokuBoard;

public class SudokuBoardDaoFactory {

    private SudokuBoardDaoFactory() {
    }

    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }
}
