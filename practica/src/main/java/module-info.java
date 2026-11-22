module farklepractica {
    requires javafx.controls;
    requires javafx.fxml;

    opens farklepractica to javafx.fxml;
    exports farklepractica;
}
