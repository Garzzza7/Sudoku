module pl.cp.view {
    requires javafx.controls;
    requires javafx.fxml;

    opens pl.cp.view to javafx.fxml;
    exports pl.cp.view;
}