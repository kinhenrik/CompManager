module com.comp.compmanager{
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires mysql.connector.j;


    opens com.comp.compmanager.entities to org.hibernate.orm.core, javafx.base;
    exports com.comp.compmanager;
}