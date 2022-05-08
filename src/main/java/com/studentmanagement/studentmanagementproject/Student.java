package com.studentmanagement.studentmanagementproject;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Ngan
 */
public class Student {

    private String rollNumber;
    private String name;
    private Date birthDay;
    private String meow;
    private int gender;
    private String phoneNumber;
    private String className;
    private String major;
    private ArrayList<String> courses = new ArrayList<>();

    public Student(){
        
    }
    public Student(String rollNumber, String name, String birthDay, String meow, int gender, String phoneNumber, String className, String major) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.setBirthDay(birthDay);
        this.meow = meow;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.className = className;
        this.major = major;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void replaceCourses(ArrayList<String> courses){
        this.courses = courses;
    }
    
    public void addCourse(String course) {
        this.courses.add(course);
    }
    
    public void delCourse(String course){
        this.courses.remove(course);
    }

    
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public String getBirthDay() {
        return format.format(this.birthDay);
    }

    public boolean setBirthDay(String birthDay) {
        try {
            this.birthDay = format.parse(birthDay);
            return true;
        } catch (ParseException ex) {
            return false;
        }
    }

    public String getMeow() {
        return meow;
    }

    public void setMeow(String meow) {
        this.meow = meow;
    }

    public String getGenderString() {
        switch (this.gender) {
            case 0:
                return "Female";
            case 1:
                return "Male";
            default:
                return "Unknown";
        }
    }
    
    public int getGender(){
        return this.gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
