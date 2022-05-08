package com.studentmanagement.studentmanagementproject;

/**
 *
 * @author truon
 */
public class DataHolder {

    public static Student student;
    public static ScoreDetail score;

    public static void holdStudentData(Student s) {
        student = s;
    }

    public static void holdScoreData(ScoreDetail c) {
        score = c;
    }

}
