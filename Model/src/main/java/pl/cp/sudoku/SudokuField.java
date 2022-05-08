package pl.cp.sudoku;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SudokuField extends Observable implements Serializable,Cloneable,Comparable<SudokuField> {

    private int value;

    public SudokuField(Observer observer) {
        super(observer);
        this.value = 0;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int value) {
        this.value = value;
        notifyObservers();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("Value fieldName",value).toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SudokuField sb = (SudokuField) object;
        return new EqualsBuilder().append(value,sb.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17,37).append(value).toHashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            SudokuField clone = (SudokuField) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException();
        }

    }

    @Override
    public int compareTo(SudokuField o) {
        return Integer.compare(this.value, o.value);
    }

}
