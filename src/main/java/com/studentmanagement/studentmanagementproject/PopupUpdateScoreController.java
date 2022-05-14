package com.studentmanagement.studentmanagementproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ngan
 */
public class PopupUpdateScoreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField courseField;

    @FXML
    private TextField finalExamScoreField;

    @FXML
    private Label label;

    @FXML
    private TextField midleScoreField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField testScoreField;

    @FXML
    private Button updateBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nameField.setText(DataHolder.score.getFullName());
        courseField.setText(DataHolder.score.getCourseCode());
        testScoreField.setText(DataHolder.score.getTestScore() + "");
        midleScoreField.setText(DataHolder.score.getMidleExamScore() + "");
        finalExamScoreField.setText(DataHolder.score.getFinalExamScore() + "");
        updateBtnClicked();
    }

    public void updateBtnClicked() {
        updateBtn.setOnAction((t) -> {

            if (InputValidation.isEmptyString(testScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }
            if (!InputValidation.isFloat(testScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }

            if (InputValidation.isEmptyString(midleScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }
            if (!InputValidation.isFloat(midleScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }

            if (InputValidation.isEmptyString(finalExamScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }
            if (!InputValidation.isFloat(finalExamScoreField.getText())) {
                CreateMessBox.popupBoxMess("Invalid Test Score", 2);
                return;
            }

            float Tscore = Float.parseFloat(testScoreField.getText());
            float Mscore = Float.parseFloat(midleScoreField.getText());
            float Fscore = Float.parseFloat(finalExamScoreField.getText());
            if (Tscore > 10 || Tscore < 0) {
                CreateMessBox.popupBoxMess("Test Score must be a number 0 to 10", 2);
                return;
            }

            if (Mscore > 10 || Mscore < 0) {
                CreateMessBox.popupBoxMess("Midle Exam Score must be a number 0 to 10", 2);
                return;
            }

            if (Fscore > 10 || Fscore < 0) {
                CreateMessBox.popupBoxMess("Final Exam Score must be a number 0 to 10", 2);
                return;
            }
            ScoreDetail score = new ScoreDetail(DataHolder.score.getRollNumber(), DataHolder.score.getFullName(), DataHolder.score.getCourseCode(), Tscore, Mscore, Fscore);
            if (DBConnection.updateScore(score)) {
                CreateMessBox.popupBoxMess("Update Successfully", 1);
            }

            Stage stage = (Stage) updateBtn.getScene().getWindow();
            stage.close();
        });
    }

}
