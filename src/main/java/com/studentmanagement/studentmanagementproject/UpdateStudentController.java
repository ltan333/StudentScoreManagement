package com.studentmanagement.studentmanagementproject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author truon
 */
public class UpdateStudentController implements Initializable {

    //Update Scene Var
    @FXML
    private ScrollPane ScrollPaneCourseUpdate;
    @FXML
    private Label label;
    @FXML
    private TextField birthdayFieldUpdate;

    @FXML
    private TextField classNameFieldUpdate;

    @FXML
    private TextField majorFieldUpdate;

    @FXML
    private TextField meowFieldUpdate;

    @FXML
    private TextField nameFieldUpdate;

    @FXML
    private TextField phoneNumberFieldUpdate;

    @FXML
    private RadioButton radioGenderFemaleUpdate;

    @FXML
    private RadioButton radioGenderMaleUpdate;

    @FXML
    private RadioButton radioGenderUnknownUpdate;

    @FXML
    private Button updateBtnUpdate;

    private ArrayList<CheckBox> checkboxCourse = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameFieldUpdate.setText(DataHolder.student.getName());
        classNameFieldUpdate.setText(DataHolder.student.getClassName());
        majorFieldUpdate.setText(DataHolder.student.getMajor());
        birthdayFieldUpdate.setText(DataHolder.student.getBirthDay());
        meowFieldUpdate.setText(DataHolder.student.getMeow());
        phoneNumberFieldUpdate.setText(DataHolder.student.getPhoneNumber());
        createCheckboxCoursesGroup();
        createGenderRadio();
        updateBtnClicked();
    }

    private void createGenderRadio() {
        ToggleGroup genderRadioGroup = new ToggleGroup();
        radioGenderFemaleUpdate.setToggleGroup(genderRadioGroup);
        radioGenderMaleUpdate.setToggleGroup(genderRadioGroup);
        radioGenderUnknownUpdate.setToggleGroup(genderRadioGroup);
        if (DataHolder.student.getGenderNum() == 0) {
            radioGenderFemaleUpdate.setSelected(true);
        }
        if (DataHolder.student.getGenderNum() == 1) {
            radioGenderMaleUpdate.setSelected(true);
        }
        if (DataHolder.student.getGenderNum() == 2) {
            radioGenderUnknownUpdate.setSelected(true);
        }
    }

    public void createCheckboxCoursesGroup() {
        createCheckboxCourses();
        int x = 0, y = 0;
        GridPane g = new GridPane();
        g.setPadding(new Insets(15, 15, 15, 15));
        g.setHgap(10);
        g.setVgap(10);
        ScrollPaneCourseUpdate.setContent(g);
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

    private void createCheckboxCourses() {
        for (Course course : DBConnection.getAllCourse()) {
            CheckBox checkbox = new CheckBox(course.getCode());
            if (DataHolder.student.getCourses().contains(checkbox.getText())) {
                checkbox.setSelected(true);
            }
            checkboxCourse.add(checkbox);
        }

    }

    public void updateBtnClicked() {
        updateBtnUpdate.setOnAction((t) -> {

            Student s = new Student(DataHolder.student.getRollNumber(), InputValidation.setNameFormat(nameFieldUpdate.getText()), birthdayFieldUpdate.getText(), meowFieldUpdate.getText(), whatGenderRadioSelected(), phoneNumberFieldUpdate.getText(), classNameFieldUpdate.getText(), majorFieldUpdate.getText());
            if (InputValidation.isEmptyString(nameFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Name must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(nameFieldUpdate.getText(), 40)) {
                CreateMessBox.popupBoxMess("Name must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(classNameFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Class name must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(classNameFieldUpdate.getText(), 40)) {
                CreateMessBox.popupBoxMess("Class name must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(majorFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Major must be not empty!", 2);
                return;
            }

            if (InputValidation.checkMaxStringLength(majorFieldUpdate.getText(), 40)) {
                CreateMessBox.popupBoxMess("Major must be lesser than 40 character!", 2);
                return;
            }

            if (InputValidation.isEmptyString(birthdayFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Birthday must be not empty!", 2);
                return;
            }

            if (!InputValidation.isBirthday(birthdayFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Invalid date format!", 2);
                return;
            }

            if (InputValidation.isEmptyString(meowFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Email must be not empty!", 2);
                return;
            }

            if (!InputValidation.isEmail(meowFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Invalid email format!", 2);
                return;
            }

            if (InputValidation.isEmptyString(phoneNumberFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Phone number must be not empty!", 2);
                return;
            }

            if (!InputValidation.isPhoneNumber(phoneNumberFieldUpdate.getText())) {
                CreateMessBox.popupBoxMess("Invalid phone number format!", 2);
                return;
            }

            for (CheckBox element : checkboxCourse) {
                if (element.isSelected()) {
                    System.out.println(element.getText());
                    s.addCourse(element.getText());
                }
            }

            ArrayList<String> delCourse = new ArrayList<>();
            ArrayList<String> addCourse = new ArrayList<>();

            boolean flag = false;
            for (String oldCourse : DataHolder.student.getCourses()) {
                for (String newCourse : s.getCourses()) {
                    if (oldCourse.equalsIgnoreCase(newCourse)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    delCourse.add(oldCourse);
                }
                flag = false;
            }
            System.out.println(delCourse);

            for (String newCourse : s.getCourses()) {
                for (String oldCourse : DataHolder.student.getCourses()) {
                    if (newCourse.equalsIgnoreCase(oldCourse)) {
                        flag = true;
                    }
                }
                if (!flag) {
                    addCourse.add(newCourse);
                }
                flag = false;
            }
            System.out.println(addCourse);

            if (DBConnection.updateInfoStudent(s)) {
                for (String element : delCourse) {
                    if (!DBConnection.delScoreCourse(s, element)) {
                        flag = true;
                    }

                }

                if (!DBConnection.addScoreTable(s, addCourse)) {
                    flag = true;
                }

            } else {
                CreateMessBox.popupBoxMess("Update Fail", 2);
            }
            if (!flag) {
                CreateMessBox.popupBoxMess("Update Sucessfully", 1);
            } else {
                CreateMessBox.popupBoxMess("Update Fail", 2);
            }

            Stage stage = (Stage) updateBtnUpdate.getScene().getWindow();
            stage.close();
        });

    }

    private int whatGenderRadioSelected() {
        if (radioGenderFemaleUpdate.isSelected()) {
            return 0;
        }
        if (radioGenderMaleUpdate.isSelected()) {
            return 1;
        }
        return 2;
    }

}
