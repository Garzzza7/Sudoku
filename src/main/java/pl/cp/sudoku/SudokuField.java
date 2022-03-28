package pl.cp.sudoku;

public class SudokuField extends Observable {

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
}
