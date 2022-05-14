package com.studentmanagement.studentmanagementproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Ngan
 */
public class ScoreSceneController implements Initializable {

    @FXML
    private Button addCourseBtn;

    @FXML
    private Button delCourseBtn;

    @FXML
    private BorderPane borderPaneMain;

    @FXML
    private ChoiceBox choiceBoxCourse;

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private Button updateScoreBtn;

    @FXML
    private Button exportBtn;

    @FXML
    private Button reloadBtn;

    TableView tbw;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showTableCourse();
        createChoiceBoxSearch();
        addCourseBtnClicked();
        delCourseBtnClicked();
        updateScoreBtnCLicked();
        reloadBtnClicked();
        exportBtnClicked();
    }

    public void createTableViewScore() {
        TableView tableView = new TableView();

        tableView.setPlaceholder(new Label("Not Found!"));

        TableColumn<ScoreDetail, String> column1 = new TableColumn<>("Roll Number");

        column1.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));
        column1.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, String> column2 = new TableColumn<>("Full Name");

        column2.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        column2.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, String> column3 = new TableColumn<>("Course Code");

        column3.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        column3.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, Float> column4 = new TableColumn<>("Test");

        column4.setCellValueFactory(new PropertyValueFactory<>("testScore"));
        column4.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, Float> column5 = new TableColumn<>("Midle Exam");

        column5.setCellValueFactory(new PropertyValueFactory<>("midleExamScore"));
        column5.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, Float> column6 = new TableColumn<>("Final Exam");

        column6.setCellValueFactory(new PropertyValueFactory<>("finalExamScore"));
        column6.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);

        tbw = tableView;

    }

    public void createTableViewCourse() {
        TableView tableView = new TableView();

        tableView.setPlaceholder(new Label("Not Found!"));

        TableColumn<Course, String> column1 = new TableColumn<>("Course Name");

        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setStyle("-fx-alignment: CENTER;");
        TableColumn<ScoreDetail, String> column2 = new TableColumn<>("Code");

        column2.setCellValueFactory(new PropertyValueFactory<>("code"));
        column2.setStyle("-fx-alignment: CENTER;");

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        tbw = tableView;

    }

    public void createTableViewScoreItem(String code) {
        for (ScoreDetail score : DBConnection.getScoreOfCourse(code)) {
            tbw.getItems().add(score);
        }
    }

    public void createTableViewCourseItem() {
        for (Course course : DBConnection.getAllCourse()) {
            tbw.getItems().add(course);
        }
    }

    public void showTableScore(String course) {
        createTableViewScore();
        createTableViewScoreItem(course);
        borderPaneMain.setCenter(tbw);
    }

    public void showTableCourse() {
        createTableViewCourse();
        createTableViewCourseItem();
        borderPaneMain.setCenter(tbw);
    }

    private void createChoiceBoxSearch() {
        choiceBoxCourse.setValue("Course Table");
        choiceBoxCourse.getItems().add("Course Table");
        for (Course course : DBConnection.getAllCourse()) {
            choiceBoxCourse.getItems().add(course.getCode());

        }

        choiceBoxCourse.setOnAction((t) -> {
            System.out.println(choiceBoxCourse.getValue());
            if ((choiceBoxCourse.getValue() + "").equalsIgnoreCase("Course table")) {
                showTableCourse();
            } else {
                showTableScore(choiceBoxCourse.getValue() + "");
            }
        });
    }

    public void addCourseBtnClicked() {
        addCourseBtn.setOnAction(((t) -> {
            if (InputValidation.isEmptyString(courseNameField.getText())) {
                CreateMessBox.popupBoxMess("Coure name must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(courseNameField.getText(), 40)) {
                CreateMessBox.popupBoxMess("Coure name must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(courseCodeField.getText())) {
                CreateMessBox.popupBoxMess("Coure code must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(courseCodeField.getText(), 7)) {
                CreateMessBox.popupBoxMess("Coure code must be lesser than 7 character!", 2);
                return;
            }

            for (Course c : DBConnection.getAllCourse()) {
                if (c.getCode().equalsIgnoreCase(courseCodeField.getText())) {
                    CreateMessBox.popupBoxMess("Coure code is exist!", 2);
                    return;
                }
            }

            if (!DBConnection.addCourse(new Course(courseNameField.getText(), courseCodeField.getText()))) {
                CreateMessBox.popupBoxMess("Add Fail!", 3);
                return;
            }

            CreateMessBox.popupBoxMess("Add Successfully", 1);
            showTableCourse();
            choiceBoxCourse.setValue("Course Table");
        }));
    }

    public void delCourseBtnClicked() {
        delCourseBtn.setOnAction((t) -> {
//            System.out.println(choiceBoxCourse.getValue().toString());
            if (!choiceBoxCourse.getValue().toString().equalsIgnoreCase("Course table")) {
                CreateMessBox.popupBoxMess("Please select a course to delete!", 2);
                return;
            }

            ObservableList<Course> selected = tbw.getSelectionModel().getSelectedItems();
            if (selected.isEmpty()) {
                CreateMessBox.popupBoxMess("Please select a course to delete!", 2);
                return;
            }

            if (CreateMessBox.popupChoose("Are you sure delete?")) {
                if (DBConnection.delScoreCourse(selected.get(0).getCode()) && DBConnection.delCourse(selected.get(0).getCode())) {
                    CreateMessBox.popupBoxMess("Delete Successfully!", 1);
                    showTableCourse();
                    return;
                }
            } else {
                return;
            }

            CreateMessBox.popupBoxMess("Delete FailF!", 3);

        });
    }

    public void updateScoreBtnCLicked() {
        updateScoreBtn.setOnAction((t) -> {

            if (choiceBoxCourse.getValue().toString().equalsIgnoreCase("Course table")) {
                CreateMessBox.popupBoxMess("Please select a student to update score!", 2);
                return;
            }
            ObservableList<ScoreDetail> selected = tbw.getSelectionModel().getSelectedItems();
            if (selected.isEmpty()) {
                CreateMessBox.popupBoxMess("Please select a student to update score!", 2);
                return;
            }

            DataHolder.holdScoreData(selected.get(0));
            createUpdateStage();

        });
    }

    public void createUpdateStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PopupUpdateScore.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Update Score");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reloadBtnClicked() {
        reloadBtn.setOnAction((t) -> {
            System.out.println(choiceBoxCourse.getValue());
            if ((choiceBoxCourse.getValue() + "").equalsIgnoreCase("Course table")) {
                showTableCourse();
            } else {
                showTableScore(choiceBoxCourse.getValue() + "");
            }
        });
    }

    static int fileNum = 1;

    public void exportBtnClicked() {

        exportBtn.setOnAction((t) -> {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File file = directoryChooser.showDialog(new Stage());
                System.out.println(file.getAbsolutePath());

                String filename = file.getAbsolutePath() + "\\ExportScoreTable-" + (fileNum++) + ".xls";
                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet("FirstSheet");

                HSSFRow rowhead = sheet.createRow((short) 0);
                rowhead.createCell(0).setCellValue("Course Name");
                rowhead.createCell(1).setCellValue("Roll Number");
                rowhead.createCell(2).setCellValue("Full Name");
                rowhead.createCell(3).setCellValue("Test Score");
                rowhead.createCell(4).setCellValue("Midle Exam Score");
                rowhead.createCell(5).setCellValue("Final Exam Score");
                rowhead.createCell(6).setCellValue("GPA");

                HSSFRow row = sheet.createRow((short) 1);
                row.createCell(0).setCellValue("1");
                row.createCell(1).setCellValue("Sankumarsingh");
                row.createCell(2).setCellValue("India");
                row.createCell(3).setCellValue("sankumarsingh@gmail.com");

                int i = 1;
                for (Course c : DBConnection.getAllCourse()) {
                    HSSFRow row2 = sheet.createRow((short) i++);
                    for (ScoreDetail s : DBConnection.getScoreOfCourse(c.getCode())) {
                        row2.createCell(0).setCellValue(c.getName());
                        HSSFRow row3 = sheet.createRow((short) i++);
                        row3.createCell(1).setCellValue(s.getRollNumber());
                        row3.createCell(2).setCellValue(s.getFullName());
                        row3.createCell(3).setCellValue(s.getTestScore());
                        row3.createCell(4).setCellValue(s.getMidleExamScore());
                        row3.createCell(5).setCellValue(s.getFinalExamScore());
                        row3.createCell(6).setCellValue((s.getTestScore() + (s.getMidleExamScore() * 2) + (s.getFinalExamScore() * 3)) / 6);
                    }
                }

                FileOutputStream fileOut = new FileOutputStream(filename);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();

                System.out.println("Your excel file has been generated!");
            } catch (Exception e) {
                return;
            }
        }
        );

    }

}
