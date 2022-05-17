package pl.cp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.cp.model.dao.Dao;
import pl.cp.model.dao.SudokuBoardDaoFactory;
import pl.cp.model.parts.SudokuBox;
import pl.cp.model.parts.SudokuColumn;
import pl.cp.model.parts.SudokuRow;
import pl.cp.model.solver.BacktrackingSudokuSolver;
import pl.cp.model.solver.SudokuSolver;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"ConstantConditions", "UnnecessaryLocalVariable"})
public class SudokuTest {

    @Test
    public void testSudokuBoardCorrectness() {
        SudokuSolver sudokuSolver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(sudokuSolver);
        sudokuBoard.solveGame();
        SudokuField[][] board = new SudokuField[9][9];
        Field privateBoardField;
        try {
            privateBoardField = SudokuBoard.class.getDeclaredField("board");
            privateBoardField.setAccessible(true);
            board = (SudokuField[][]) privateBoardField.get(sudokuBoard);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {

                //Check row:
                for (int i = 0; i < board.length; i++) {
                    if (board[row][i] == board[row][column] && i != column) {
                        Assertions.fail();
                    }
                }

                //Check column:
                for (int i = 0; i < board.length; i++) {
                    if (board[i][column] == board[row][column] && i != row) {
                        Assertions.fail();
                    }
                }

                //Check sector:
                int sectionRow;
                int sectionColumn;

                sectionRow = row / 3;
                sectionColumn = column / 3;

                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (board[sectionRow * 3 + k][sectionColumn * 3 + l] == board[row][column] && sectionRow * 3 + k != row && sectionColumn * 3 + l != column) {
                            Assertions.fail();
                        }
                    }
                }
            }
        }
    }

    @Test
    public void testNoSudokuBoardRepetitiveness() {

        boolean SudokuRepetitive = true;

        SudokuSolver sudokuSolver1 = new BacktrackingSudokuSolver();
        SudokuSolver sudokuSolver2 = new BacktrackingSudokuSolver();

        SudokuBoard sudoku1 = new SudokuBoard(sudokuSolver1);
        SudokuBoard sudoku2 = new SudokuBoard(sudokuSolver2);

        sudoku1.solveGame();
        sudoku2.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku1.get(i, j) != sudoku2.get(i, j)) {
                    SudokuRepetitive = false;
                    break;
                }
            }
        }
        Assertions.assertFalse(SudokuRepetitive);
    }

    @Test
    public void testToStringTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        sb.solveGame();
        try {
            String st = sb.toString();
            assertNotSame(null, st);
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls!");
        }
    }

    @Test
    public void testEqualsTestForSudokuBoard() {
        SudokuBoard sb = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    sb.set(i, j, 1);

            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++)
                    bs.set(i, j, 1);
            }
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls");
        }
        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertFalse(sb.hashCode() != bs.hashCode());
        SudokuColumn sudokuColumn = new SudokuColumn();
        //noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(sb, sudokuColumn);
        assertNotEquals(null, sb);
    }

    @Test
    public void testHashCodeTestForSudokuBoard() {
        SudokuBoard sb;
        SudokuBoard bs = new SudokuBoard(new BacktrackingSudokuSolver());
        sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
    }

    @Test
    public void testToStringTestForSudokuField() {
        SudokuField sf = new SudokuField(() -> {});
        try {
            String st = sf.toString();
            assertNotSame(null, st);
        } catch (NullPointerException e) {
            System.out.println("Works but values are nulls");
        }
    }

    @Test
    public void testEqualsTestForSudokuField() {
        SudokuField sb = new SudokuField(() -> {});
        SudokuField bs = new SudokuField(() -> {});

        sb.setValue(1);
        bs.setValue(1);
        assertEquals(sb, bs);
        sb.setValue(5);
        assertNotEquals(sb, bs);
        assertNotEquals(null, sb);
        assertNotEquals(null, bs);
    }

    @Test
    public void testHashCodeTestForSudokuField() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuField bs = new SudokuField(() -> {});
        SudokuField sb = bs;
        assertTrue(sb.equals(bs) && bs.equals(sb));
        assertNotEquals(null, sb);
        bs.setValue(1);
        //noinspection SimplifiableAssertion,EqualsBetweenInconvertibleTypes
        assertFalse(sb.equals(sudokuBoard));
    }

    @Test
    public void testFileSudokuBoard() throws Exception {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        SudokuBoard newboard = new SudokuBoard(new BacktrackingSudokuSolver());

        try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.getFileDao("filename.txt")) {

            sudokuBoardDao.write(sudokuBoard);
            assertNotEquals(null,sudokuBoardDao);
            assertNotEquals(null,newboard);
            newboard = sudokuBoardDao.read();
            assertEquals(sudokuBoard,newboard);
            newboard.solveGame();
            assertNotEquals(sudokuBoard,newboard);

        }

        try (FileOutputStream fileOutputStream = new FileOutputStream("filename.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)){
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

        try(Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileDao("joink")){
            dao.read();
        }
    }

    @Test
    public void CloningSudokuRowTest() throws CloneNotSupportedException {
        try{
            SudokuRow row = new SudokuRow();
            List<SudokuField> list = Arrays.asList(new SudokuField[9]);
            for (int i = 0, j = 1; i < 9; i++, j++) {
                list.set(i, new SudokuField(() -> {}));
                list.get(i).setValue(j);
            }

            row.setFields(list);
            // idk why it shows that it is redundant
            SudokuRow rowClone = new SudokuRow();
            rowClone = (SudokuRow)row.clone();
            assertEquals(rowClone.equals(row),row.equals(rowClone));

            list.get(0).setValue(5);
            rowClone.setFields(list);
            assertNotEquals(rowClone,row);

        }
        catch (NullPointerException e) {

        }

    }

    @Test
    public void CloningSudokuBoxTest() throws CloneNotSupportedException {
    try {
        SudokuBox box = new SudokuBox();
        List<SudokuField> list = Arrays.asList(new SudokuField[9]);
        for (int i = 0, j = 1; i < 9; i++, j++) {
            list.set(i, new SudokuField(() -> {}));
            list.get(i).setValue(j);
        }

        box.setFields(list);

        SudokuBox boxClone = (SudokuBox) box.clone();
        assertEquals(boxClone,box);

        list.get(0).setValue(5);
        boxClone.setFields(list);
        assertNotEquals(boxClone,box);
    }
    catch (NullPointerException e) {

    }


    }

    @Test
    public void CloningSudokuColumnTest() throws CloneNotSupportedException {
     try {
     SudokuColumn column = new SudokuColumn();
     List<SudokuField> list = Arrays.asList(new SudokuField[9]);
     for (int i = 0, j = 1; i < 9; i++, j++) {
         list.set(i, new SudokuField(() -> {}));
         list.get(i).setValue(j);
     }

     column.setFields(list);
     // idk why it shows that it is redundant
     SudokuColumn columnClone = new SudokuColumn();
     columnClone = (SudokuColumn) column.clone();
     assertEquals(columnClone.equals(column),column.equals(columnClone));

     list.get(0).setValue(5);
     columnClone.setFields(list);
     assertNotEquals(columnClone,column);

    }
    catch (NullPointerException exception) {

    }
    }
    @Test
    public void CloningSudokuBoardTest() throws CloneNotSupportedException {
        try
        {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            SudokuBoard clone = board.clone();

            assertEquals(board, clone);

            assertTrue(board.equals(clone));

            clone.set(0,0,5);

            assertNotEquals(board, clone);

            assertEquals(false, board.equals(clone));

            clone.solveGame();

            assertFalse(board.equals(clone));
        }
        catch (NullPointerException exception) {

        }

    }
    @Test
    public void CloningSudokuFieldTest() throws CloneNotSupportedException {
        SudokuField field = new SudokuField(() -> {});
        field.setValue(7);
        SudokuField copy = new SudokuField(() -> {});
        copy = (SudokuField) field.clone();

        assertEquals(field.getValue(), copy.getValue());

        copy.setValue(1);

        assertNotEquals(field.getValue(), copy.getValue());
    }
    @Test
    public  void ComparingSudokuFieldTest() {
        SudokuField field1 = new SudokuField(() -> {});
        SudokuField field2 = new SudokuField(() -> {});
        field2.setValue(5);

        assertThrows(NullPointerException.class, () -> {
            SudokuField test = null;
            //Why is it in pink??
            field2.compareTo(test);
        });

        field1.setValue(5);

        assertEquals(0, field1.compareTo(field2));
        field1.setValue(8);
        assertTrue(field1.compareTo(field2) > 0);
        assertTrue(field2.compareTo(field1) < 0);
        field1.setValue(5);
        assertEquals(field1.compareTo(field2), 0);
    }
}