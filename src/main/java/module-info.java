module com.studentmanagement.studentmanagementproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;

    opens com.studentmanagement.studentmanagementproject to javafx.fxml;
    exports com.studentmanagement.studentmanagementproject;
}
