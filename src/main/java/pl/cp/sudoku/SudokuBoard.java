package pl.cp.sudoku;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SudokuBoard implements Observer {

    private final SudokuField[][] board = new SudokuField[9][9];
    private final SudokuSolver sudokuSolver;
    /*
    private final SudokuField[] board = new SudokuField[81];
    private final SudokuSolver sudokuSolver;
    private List<SudokuField> boardlist = Arrays.asList(board);

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
        for (int i = 0; i < 81; i++) {
            boardlist.set(i,new SudokuField(this));
        }
     */

    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
    }

    @Override
    public void update() {
        if (checkBoard()) {
            System.out.println("Board correct!");
        }
    }

    public int get(int x, int y) {
        return board[x][y].getFieldValue();
    }
    /*
       public int get(int x, int y) {
        return boardlist.get(x * 9 + y).getFieldValue();
    }
     */

    public void set(int x, int y, int value) {
        board[x][y].setFieldValue(value);
    }
    /*
        public void set(int x, int y, int value) {
        boardlist.get(x * 9 + y).getFieldValue();
    }
     */

    private boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify() | !getColumn(i).verify()) {
                return false;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!getBox(i * 3, j * 3).verify()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void solveGame() {

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(this);
            }
        }

        sudokuSolver.solve(this);
        /*

       for (int i = 0; i < 81; i++){
            boardlist.get(i)=new SudokuField(this);
        }
        sudokuSolver.solve(this);
         */
    }

    public SudokuRow getRow(int row) {
        SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.setFieldValue(i, board[row][i]);
        }
        return sudokuRow;
        /*
                SudokuRow sudokuRow = new SudokuRow();
        for (int i = 0; i < 9; i++) {
            sudokuRow.setFieldValue(i, boardlist.get(row*9+i));
        }
        return sudokuRow;
         */
    }

    public SudokuColumn getColumn(int col) {
        SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setFieldValue(i, board[i][col]);
        }
        return sudokuColumn;
        /*
                SudokuColumn sudokuColumn = new SudokuColumn();
        for (int i = 0; i < 9; i++) {
            sudokuColumn.setFieldValue(i, boardlist.get(i*9+col));
        }
        return sudokuColumn;
         */
    }

    public SudokuBox getBox(int row, int col) {

        SudokuBox box = new SudokuBox();

        int boxRow;
        int boxColumn;

        boxRow = row / 3;
        boxColumn = col / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box.setFieldValue(i + j * 3, board[boxRow * 3 + i][boxColumn * 3 + j]);
            }
        }
        return box;
        /*
                SudokuBox box = new SudokuBox();
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                box.setFieldValue(counter, boardlist.get((row + i) * 9 + col + j));
                counter=counter+1;
            }
        }

        return box;
         */
    }
    @Override
    public String toString(){
        ToStringBuilder stringBuilder = new ToStringBuilder(this);
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                stringBuilder.append(board[row][col].getFieldValue());
            }
        }
        return stringBuilder.toString();
    }
    @Override
    public boolean equals(Object object){
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SudokuBoard sb = (SudokuBoard) object;
        return new EqualsBuilder().append(board,sb.board).isEquals();
    }
    @Override
    public int hashCode() {return new HashCodeBuilder(17,37).append(board).toHashCode();}
}