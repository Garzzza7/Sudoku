package pl.cp.model;

import org.junit.jupiter.api.Test;
import pl.cp.model.dao.Dao;
import pl.cp.model.dao.SudokuBoardDaoFactory;
import pl.cp.model.solver.BacktrackingSudokuSolver;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFileTest {
    @Test
    public void testFileSudokuBoard() throws Exception {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        SudokuBoard newboard = new SudokuBoard(new BacktrackingSudokuSolver());

        try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.getFileDao("filename.txt")) {

            sudokuBoardDao.write(sudokuBoard);
            assertNotEquals(null, sudokuBoardDao);
            assertNotEquals(null, newboard);
            newboard = sudokuBoardDao.read();
            assertEquals(sudokuBoard, newboard);
            newboard.solveGame();
            assertNotEquals(sudokuBoard, newboard);

        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("filename.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
            sb.solveGame();


            objectOutputStream.writeObject(sb);
            objectOutputStream.flush();

        }
    }

    @Test
    public void testFileSudokuBoardFactory() {

        assertNotNull(SudokuBoardDaoFactory.getFileDao("filename.txt"));

    }

    @Test
    public void testFileSudokuBoardExceptions() throws Exception {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        SudokuBoardDaoFactory.getFileDao("xd").write(sudokuBoard);

        try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao("joink")) {
            dao.read();
        }
    }
}
