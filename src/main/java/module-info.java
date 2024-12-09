module com.comp.compmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.comp.compmanager to javafx.fxml;
    exports com.comp.compmanager;
}