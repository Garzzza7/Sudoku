package pl.cp.sudoku.parts;

import pl.cp.sudoku.SudokuField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SudokuBox extends SudokuPart implements Serializable,Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(List.of(getFields()));
        SudokuBox copy = new SudokuBox();
        copy.setFields(fields);
        return copy;
    }

}
