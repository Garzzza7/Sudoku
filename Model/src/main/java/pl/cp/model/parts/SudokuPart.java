package pl.cp.model.parts;

import pl.cp.model.SudokuField;

import java.io.Serializable;
import java.util.List;

public abstract class SudokuPart implements Serializable,Cloneable {

    protected SudokuField[] fields = new SudokuField[9];

    public void setFieldValue(int position, SudokuField field) {
        this.fields[position] = field;
    }

    public SudokuField[] getFields() {
        return fields;
    }

    public final void setFields(List<SudokuField> fields) {

        for (int i = 0; i < 9; i++) {

            fields.get(i).setFieldValue(fields.get(i).getFieldValue());
        }
    }

    public boolean verify() {

        for (SudokuField x : fields) {

            for (SudokuField y : fields) {

                if (x != y && x.getFieldValue() == y.getFieldValue() && (x.getFieldValue() != 0 | y.getFieldValue() != 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}
