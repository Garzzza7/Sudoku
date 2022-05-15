package pl.cp.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.UnaryOperator;


import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import pl.cp.model.SudokuBoard;
import pl.cp.model.dao.Dao;
import pl.cp.model.dao.SudokuBoardDaoFactory;
import pl.cp.model.solver.BacktrackingSudokuSolver;

public class SudokuController {

    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

    public static List<Integer> listLoad = new ArrayList<>();

    public static List<Integer> list = new ArrayList<>();

    private ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles.language");

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public Button easy_button;

    @FXML
    public Button medium_button;

    @FXML
    public Button hard_button;

    @FXML
    public Button start_button;

    public enum diff_level {
        EASY(1),MEDIUM(2),HARD(3);

        private final int level_number;

        diff_level(final int level_number) {
            this.level_number = level_number;
        }

        public int getValue() {
            return level_number;
        }
    }
    @FXML
    void easyButton(ActionEvent event) {
        list.add(diff_level.EASY.getValue());
    }

    @FXML
    void mediumButton(ActionEvent event) {
        list.add(diff_level.MEDIUM.getValue());
    }

    @FXML
    void hardButton(ActionEvent event) {
        list.add(diff_level.HARD.getValue());
    }

    @FXML
    void LanguagePLButton(ActionEvent event) throws IOException {
        Locale locale = new Locale("pl");
        ResourceBundle r = ResourceBundle.getBundle("bundles/language",locale);
        easy_button.setText(r.getString("difficulty_easy"));
        medium_button.setText(r.getString("difficulty_medium"));
        hard_button.setText(r.getString("difficulty_hard"));


    }

    @FXML
    void LanguageENGButton(ActionEvent event) throws IOException {
        Locale locale = new Locale("en");
        ResourceBundle r = ResourceBundle.getBundle("bundles/language",locale);
        easy_button.setText(r.getString("difficulty_easy"));
        medium_button.setText(r.getString("difficulty_medium"));
        hard_button.setText(r.getString("difficulty_hard"));

    }
    @FXML
    public void startButton(ActionEvent event) throws IOException {
        /*
       // URL url = new File("View/src/main/resources/pl.cp.view/board.fxml").toURI().toURL();
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("View/src/main/resources/pl/cp/view/board.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

         */
        Stage stageGame = new Stage();
        sudokuBoard.solveGame();
        listLoad.add(0);
        displayBoard(stageGame, sudokuBoard);
    }

    public void loadBoard() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();
        Stage stage2= new Stage();
        StackPane r = new StackPane();
        r.getChildren().add(grid);
        root.setCenter(grid);

        Button load = new Button("load");
        BorderPane bottom = new BorderPane();
        bottom.setRight(load);
        root.setBottom(bottom);
        Label label1 = new Label("please enter sudoku name: ");

        TextField textField = new TextField();
        textField.setPrefSize(200, 25);
        textField.setFont(Font.font("Verdana", 15));
        textField.setAlignment(Pos.CENTER);
        root.setRight(textField);

        load.setFont(Font.font("Verdana", 15));
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 500, 150));

        load.setOnAction(event -> {
            try {
                listLoad.add(1);
                displayBoard(stage2,sudokuBoard);


            } catch (Exception e) {}
            stage.close();

        });

        stage.show();

    }
    public void randomCordinates(int files, SudokuBoard board) {
        Random random = new Random();
        int x,y;
        int count = 0;
        do {
            x = random.nextInt(9);
            y = random.nextInt(9);
            if (board.get(x, y) != 0) {
                board.set(x, y, 0);
                count++;
            }
        } while (count < files);
    }

    public void loadSavingScreen() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        GridPane grid = new GridPane();

        StackPane r = new StackPane();
        r.getChildren().add(grid);
        root.setCenter(grid);

        Button save = new Button("save");
        BorderPane bottom = new BorderPane();
        bottom.setRight(save);
        root.setBottom(bottom);
        Label label1 = new Label("please enter sudoku name: ");

        label1.setFont(Font.font("Verdana", 15));
        root.setLeft(label1);

        TextField textField = new TextField();
        textField.setPrefSize(200, 25);
        textField.setFont(Font.font("Verdana", 15));
        textField.setAlignment(Pos.CENTER);
        root.setRight(textField);

        save.setFont(Font.font("Verdana", 15));
        root.setPadding(new Insets(20));
        stage.setScene(new Scene(root, 500, 150));

        save.setOnAction(event -> {
            String sudokuName = textField.getText();

            stage.close();

        });

        stage.show();
    }
    public void displayBoard(Stage stage, SudokuBoard board){
        try {

            BorderPane root = new BorderPane();
            GridPane grid = new GridPane();
            BorderPane bottom = new BorderPane();

            root.setCenter(grid);
            stage.setTitle("Sudoku");
            stage.setScene(new Scene(root, 900, 900));
            stage.setResizable(false);

            if(listLoad.get(listLoad.size()-1) < 0) {
                randomCordinates(list.get(list.size()), board);
            }


            Button resumeButton = new Button("Resume");
            bottom.setLeft(resumeButton);
            resumeButton.setOnAction(event -> {
                SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
                try (Dao<SudokuBoard> sudokuBoardDao = factory.getFileDao("SudokuStorage.txt")) {
                    loadBoard();
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            resumeButton.setFont(Font.font("Verdana", 15));

            Button saveButton = new Button("SAVE");
            saveButton.setOnAction(event -> {
                loadSavingScreen();
            });
            bottom.setRight(saveButton);
            root.setBottom(bottom);
            saveButton.setFont(Font.font("Verdana", 15));

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    TextField txt = new TextField(String.valueOf(board.get(i, j)));
                    txt.setPrefSize(666, 666);
                    txt.setFont(Font.font("Calibri", 30));
                    txt.setAlignment(Pos.CENTER);
                    if (board.get(i, j) == 0) {
                        txt.clear();
                        UnaryOperator<TextFormatter.Change> textFilter = c -> {

                            if (c.getText().matches("[1-9]")) {
                                c.setRange(0, txt.getText().length());
                                return c;
                            } else
                            if (c.getText().isEmpty()) {
                                return c;
                            }
                            return null;
                        };

                        TextFormatter<Integer> formatter =
                                new TextFormatter<Integer>(stringConverter, 0, textFilter);

                        txt.setTextFormatter(formatter);

                        JavaBeanIntegerProperty intProperty =
                                JavaBeanIntegerPropertyBuilder
                                        .create()
                                        .bean(sudokuBoard.get(i, j))
                                        .name("value")
                                        .getter("getFieldValue")
                                        .setter("setFieldValue")
                                        .build();

                        txt.setText(String.valueOf(sudokuBoard.get(i, j)));
                        Bindings.bindBidirectional(txt.textProperty(), intProperty,
                                new NumberStringConverter());
                        grid.add(txt, i, j);
                    } else {
                        txt.setEditable(false);
                        grid.add(txt, i, j);
                    }
                }
            }
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    StringConverter<Integer> stringConverter = new StringConverter<Integer>() {

        @Override
        public String toString(Integer object) {
            if (object == null || object.intValue() == 0) {
                return "";
            }
            return object.toString();
        }

        @Override
        public Integer fromString(String string) {
            if (string == null || string.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(string);
        }

    };


}