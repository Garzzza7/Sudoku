package pl.cp.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuController implements Initializable {

    @FXML private Button button;
    @FXML private MenuButton menuButton;

    //Sudoku board fields:
    @FXML private GridPane grid1;
    @FXML private GridPane grid2;
    @FXML private GridPane grid3;
    @FXML private GridPane grid4;
    @FXML private GridPane grid5;
    @FXML private GridPane grid6;
    @FXML private GridPane grid7;
    @FXML private GridPane grid8;
    @FXML private GridPane grid9;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
