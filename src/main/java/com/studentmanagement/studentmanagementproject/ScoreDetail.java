
package com.studentmanagement.studentmanagementproject;

/**
 *
 * @author Ngan
 */
public class ScoreDetail {
    private String rollNumber;
    private String fullName;
    private String courseCode;
    private float testScore;
    private float midleExamScore;
    private float finalExamScore;

    public ScoreDetail() {
    }

    public ScoreDetail(String rollNumber, String fullName, String courseCode, float testScore, float midleExamScore, float finalExamScore) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.courseCode = courseCode;
        this.testScore = testScore;
        this.midleExamScore = midleExamScore;
        this.finalExamScore = finalExamScore;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    

    
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public float getTestScore() {
        return testScore;
    }

    public void setTestScore(float testScore) {
        this.testScore = testScore;
    }

    public float getMidleExamScore() {
        return midleExamScore;
    }

    public void setMidleExamScore(float midleExamScore) {
        this.midleExamScore = midleExamScore;
    }

    public float getFinalExamScore() {
        return finalExamScore;
    }

    public void setFinalExamScore(float finalExamScore) {
        this.finalExamScore = finalExamScore;
    }
    
    
}
