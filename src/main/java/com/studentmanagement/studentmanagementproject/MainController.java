package com.studentmanagement.studentmanagementproject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ngan
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane AnchorPaneInput;

    @FXML
    private ScrollPane ScrollPaneCourse;

    @FXML
    private Button addStudentBtn;

    @FXML
    private TextField birthdayField;

    @FXML
    private BorderPane borderPaneMain;

    @FXML
    private BorderPane borderPaneStage;

    @FXML
    private ChoiceBox choiceBoxSearch;

    @FXML
    private TextField classNameField;

    @FXML
    private Button delBtn;

    @FXML
    private TextField majorField;

    @FXML
    private TextField meowField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private RadioButton radioGenderFemale;

    @FXML
    private RadioButton radioGenderMale;

    @FXML
    private RadioButton radioGenderUnknown;

    @FXML
    private TextField searchField;

    @FXML
    private Button updateBtn;
    @FXML
    private Button reloadBtn;
    @FXML
    private Label scoreBtn;

    @FXML
    private Label studentInfoBtn;
    //========================================================================================
    private ArrayList<CheckBox> checkboxCourse = new ArrayList<>();
    private TableView tbw;
    boolean isStuInfoSelected = false;
    boolean isScoreSelected = false;
    //==================================================================================

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showTable(0);
        createGenderRadio();
        createCheckboxCoursesGroup();
        createChoiceBoxSearch();
        search();
        updateBtnClicked();
        delBtnClicked();
        addStudentBtnClicked();
        studentInfoBtnClicked();
        scoreBtnClicked();
        reloadBtnClicked();

        studentInfoBtn.setStyle("-fx-background-color:  #333333; ");
        scoreBtn.setStyle("-fx-background-color:  null; -fx-cursor: hand;");
        isStuInfoSelected = true;
        isScoreSelected = false;
    }

    private void createCheckboxCourses() {
        checkboxCourse.clear();
        for (Course course : DBConnection.getAllCourse()) {
            checkboxCourse.add(new CheckBox(course.getCode()));
        }
    }

    private void createChoiceBoxSearch() {
        choiceBoxSearch.setValue("Search by Name");
        choiceBoxSearch.getItems().add("Search by Name");
        choiceBoxSearch.getItems().add("Search by RollNumber");
        choiceBoxSearch.getItems().add("Search by Class");
        choiceBoxSearch.getItems().add("Search by Major");

    }

    public void createCheckboxCoursesGroup() {
        createCheckboxCourses();
        int x = 0, y = 0;
        GridPane g = new GridPane();
        g.setPadding(new Insets(15, 15, 15, 15));
        g.setHgap(10);
        g.setVgap(10);
        ScrollPaneCourse.setContent(g);
        if (!checkboxCourse.isEmpty()) {
            for (CheckBox element : checkboxCourse) {
                g.add(element, x++, y);
                if (x == 3) {
                    x = 0;
                    y++;
                }
            }
        } else {
            g.add(new Label("Not found any course"), 0, 0);
        }
    }

    public void addStudentBtnClicked() {
        addStudentBtn.setOnAction((t) -> {
            if (InputValidation.isEmptyString(nameField.getText())) {
                CreateMessBox.popupBoxMess("Name must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(nameField.getText(), 40)) {
                CreateMessBox.popupBoxMess("Name must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(classNameField.getText())) {
                CreateMessBox.popupBoxMess("Class name must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(classNameField.getText(), 40)) {
                CreateMessBox.popupBoxMess("Class name must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(majorField.getText())) {
                CreateMessBox.popupBoxMess("Major must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(majorField.getText(), 40)) {
                CreateMessBox.popupBoxMess("Major must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(birthdayField.getText())) {
                CreateMessBox.popupBoxMess("Birthday must be not empty!", 2);
                return;
            }

            if (!InputValidation.isBirthday(birthdayField.getText())) {
                CreateMessBox.popupBoxMess("Invalid date format!", 2);
                return;
            }

            if (InputValidation.isEmptyString(meowField.getText())) {
                CreateMessBox.popupBoxMess("Email must be not empty!", 2);
                return;
            }

            if (!InputValidation.isEmail(meowField.getText())) {
                CreateMessBox.popupBoxMess("Invalid email format!", 2);
                return;
            }

            if (InputValidation.isEmptyString(phoneNumberField.getText())) {
                CreateMessBox.popupBoxMess("Phone number must be not empty!", 2);
                return;
            }

            if (!InputValidation.isPhoneNumber(phoneNumberField.getText())) {
                CreateMessBox.popupBoxMess("Invalid phone number format!", 2);
                return;
            }

            Student s = new Student(DBConnection.createRollnumber(), InputValidation.setNameFormat(nameField.getText()), birthdayField.getText(), meowField.getText(), whatGenderRadioSelected(), phoneNumberField.getText(), classNameField.getText(), majorField.getText());
            for (CheckBox element : checkboxCourse) {
                if (element.isSelected()) {
                    System.out.println(element.getText());
                    s.addCourse(element.getText());
                }
            }

            if (DBConnection.addStudentIntoDB(s) && DBConnection.addScoreTable(s, s.getCourses())) {
                CreateMessBox.popupBoxMess("Add Student Successfully", 1);
                nameField.setText("");
                classNameField.setText("");
                majorField.setText("");
                birthdayField.setText("");
                meowField.setText("");
                phoneNumberField.setText("");
            } else {
                CreateMessBox.popupBoxMess("Add Student Fail", 3);
            }
            showTable(1);
        });

    }

    public void delBtnClicked() {
        delBtn.setOnAction((e) -> {
            ObservableList<Student> selected = tbw.getSelectionModel().getSelectedItems();
            if (!selected.isEmpty()) {
                if (CreateMessBox.popupChoose("Are you sure delete " + selected.get(0).getName())) {
                    for (String element : selected.get(0).getCourses()) {
                        DBConnection.delScoreCourse(selected.get(0), element);
                    }
                    DBConnection.delAStudent(selected.get(0));
                }
            } else {
                CreateMessBox.popupBoxMess("Please choose a student!", 2);
            }
            showTable(1);
        });
    }

    public void showTable(int allOrFind) {
        createTableView();
        if (allOrFind == 0) {
            createTableViewAll();
        } else {
            createTableViewFind();
        }
        borderPaneMain.setCenter(tbw);
    }

    public void createTableView() {
        TableView tableView = new TableView();
        tableView.setPlaceholder(new Label("Not Found!"));
        tableView.setStyle("-fx-alignment: CENTER;");

        TableColumn<Student, String> column1 = new TableColumn<>("Roll Number");
        column1.setStyle("-fx-alignment: CENTER;");

        column1.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));

        TableColumn<Student, String> column2 = new TableColumn<>("Full Name");

        column2.setCellValueFactory(new PropertyValueFactory<>("name"));
        column2.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column3 = new TableColumn<>("Class");

        column3.setCellValueFactory(new PropertyValueFactory<>("className"));
        column3.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column4 = new TableColumn<>("Birthday");

        column4.setCellValueFactory(new PropertyValueFactory<>("birthDay"));
        column4.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column5 = new TableColumn<>("Email");

        column5.setCellValueFactory(new PropertyValueFactory<>("meow"));
        column5.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column6 = new TableColumn<>("Gender");

        column6.setCellValueFactory(new PropertyValueFactory<>("gender"));
        column6.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column7 = new TableColumn<>("Phone Number");

        column7.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        column7.setStyle("-fx-alignment: CENTER;");
        TableColumn<Student, String> column8 = new TableColumn<>("Major");

        column8.setCellValueFactory(new PropertyValueFactory<>("major"));
        column8.setStyle("-fx-alignment: CENTER;");
        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);
        tableView.getColumns().add(column7);
        tableView.getColumns().add(column8);

        tbw = tableView;

    }

    public void createTableViewAll() {
        for (Student student : DBConnection.getAllStudent()) {
            tbw.getItems().add(student);
        }
    }

    public void createTableViewFind() {
        for (Student student : DBConnection.getAllStudent()) {
            if (choiceBoxSearch.getSelectionModel().getSelectedIndex() == 0) {
                if (student.getName().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    tbw.getItems().add(student);
                }
            }
            if (choiceBoxSearch.getSelectionModel().getSelectedIndex() == 1) {
                if (student.getRollNumber().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    tbw.getItems().add(student);
                }
            }
            if (choiceBoxSearch.getSelectionModel().getSelectedIndex() == 2) {
                if (student.getClassName().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    tbw.getItems().add(student);
                }
            }
            if (choiceBoxSearch.getSelectionModel().getSelectedIndex() == 3) {
                if (student.getMajor().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    tbw.getItems().add(student);
                }
            }
        }
    }

    public void search() {
        searchField.setOnKeyReleased((t) -> {
            showTable(1);
        });
    }

    public void updateBtnClicked() {
        updateBtn.setOnAction((e) -> {
            ObservableList<Student> selected = tbw.getSelectionModel().getSelectedItems();
            if (selected.size() > 0) {
                DataHolder.holdStudentData(selected.get(0));
                createUpdateStage();
            } else {
                CreateMessBox.popupBoxMess("Please choose a student!", 2);
            }
            showTable(0);
        });
    }

    private void createGenderRadio() {
        ToggleGroup genderRadioGroup = new ToggleGroup();
        radioGenderFemale.setToggleGroup(genderRadioGroup);
        radioGenderMale.setToggleGroup(genderRadioGroup);
        radioGenderUnknown.setToggleGroup(genderRadioGroup);
    }

    public void createUpdateStage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PopupUpdate.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Update Student Info");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int whatGenderRadioSelected() {
        if (radioGenderFemale.isSelected()) {
            return 0;
        }
        if (radioGenderMale.isSelected()) {
            return 1;
        }
        return 2;
    }

    public void studentInfoBtnClicked() {

        studentInfoBtn.setOnMouseClicked((t) -> {
            borderPaneStage.setCenter(borderPaneMain);
            borderPaneStage.setRight(AnchorPaneInput);
            studentInfoBtn.setStyle("-fx-background-color:  #333333; ");
            scoreBtn.setStyle("-fx-background-color:  null; -fx-cursor: hand;");
            isStuInfoSelected = true;
            isScoreSelected = false;
            showTable(1);
            createCheckboxCoursesGroup();
        });

        studentInfoBtn.setOnMouseEntered(mouseEvent -> {
            if (!isStuInfoSelected) {
                studentInfoBtn.setScaleX(1.05);
                studentInfoBtn.setScaleZ(1.05);
            }
        });
        studentInfoBtn.setOnMouseExited(mouseEvent -> {
            studentInfoBtn.setScaleX(1);
            studentInfoBtn.setScaleZ(1);
        });
    }

    public void scoreBtnClicked() {
        scoreBtn.setOnMouseClicked((t) -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ScoreScene.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                BorderPane p = (BorderPane) scene.getRoot();
                borderPaneStage.setCenter(p);
                borderPaneStage.setRight(new AnchorPane());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            scoreBtn.setStyle("-fx-background-color:  #333333; ");
            studentInfoBtn.setStyle("-fx-background-color:  null; -fx-cursor: hand;");
            isStuInfoSelected = false;
            isScoreSelected = true;
        });

        scoreBtn.setOnMouseEntered(mouseEvent -> {
            if (!isScoreSelected) {
                scoreBtn.setScaleX(1.05);
                scoreBtn.setScaleZ(1.05);
            }
        });
        scoreBtn.setOnMouseExited(mouseEvent -> {
            scoreBtn.setScaleX(1);
            scoreBtn.setScaleZ(1);
        });
    }

    public void reloadBtnClicked() {
        reloadBtn.setOnAction((t) -> {
            showTable(1);
        });
    }

}
