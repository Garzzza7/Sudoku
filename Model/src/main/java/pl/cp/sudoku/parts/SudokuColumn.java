package pl.cp.sudoku.parts;

import pl.cp.sudoku.SudokuField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuColumn extends SudokuPart implements Serializable,Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(List.of(getFields()));
        SudokuColumn copy = new SudokuColumn();
        copy.setFields(fields);
        return copy;
    }

}
