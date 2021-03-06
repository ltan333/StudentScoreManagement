package com.studentmanagement.studentmanagementproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author truon
 */
public class DBConnection {

    private static final String dbName = "TestDB";
    private static final String connectionUrl = "jdbc:sqlserver://localhost;databaseName=" + dbName + ";integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
    private static final String userName = "sa";
    private static final String pass = "1234563";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(connectionUrl, userName, pass);
            return conn;
        } catch (Exception e) {
            System.out.println("Err");
            e.printStackTrace();
            return null;
        }
    }
    
    public static ArrayList<Course> getAllCourse() {
        ArrayList<Course> courses = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT * FROM courses";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
//                System.out.println(rs.getString(1)+" "+rs.getString(2));
                courses.add(new Course(rs.getString(2), rs.getString(1)));
            }
            stament.close();
            conn.close();
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Student> getAllStudent() {
        ArrayList<Student> sList = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT * FROM students";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setRollNumber(rs.getString(1));
                s.setName(rs.getString(2));
                s.setBirthDay(rs.getString(3));
                s.setClassName(rs.getString(4));
                s.setMajor(rs.getString(5));
                s.setGender(rs.getInt(6));
                s.setMeow(rs.getString(7));
                s.setPhoneNumber(rs.getString(8));
                s.replaceCourses(getCourseOfStudent(rs.getString(1)));
                sList.add(s);
            }
            stament.close();
            conn.close();
            return sList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static ArrayList<Student> getSearchStudents(int searchBy, String keySearch) {
        ArrayList<Student> sList = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT * FROM students";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setRollNumber(rs.getString(1));
                s.setName(rs.getString(2));
                s.setBirthDay(rs.getString(3));
                s.setClassName(rs.getString(4));
                s.setMajor(rs.getString(5));
                s.setGender(rs.getInt(6));
                s.setMeow(rs.getString(7));
                s.setPhoneNumber(rs.getString(8));
                sList.add(s);
            }
            stament.close();
            conn.close();
            return sList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static String createRollnumber() {
        String random = "CT" + Calendar.getInstance().get(Calendar.YEAR) + (int) (Math.random() * 9999);
        Connection conn = getConnection();
        System.out.println(random);
        String query = "SELECT rollnumber FROM students";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
                if (random.equals(rs.getString(1))) {
                    createRollnumber();
                }
            }
            stament.close();
            conn.close();
            return random;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public static boolean addStudentIntoDB(Student s) {
        Connection conn = getConnection();
        String sqlInsert = "INSERT INTO students VALUES(?, ?, ?,?,?,?,?,?)";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlInsert);
            stament.setString(1, s.getRollNumber());
            stament.setString(2, s.getName());
            stament.setString(3, s.getBirthDay());
            stament.setString(4, s.getClassName());
            stament.setString(5, s.getMajor());
            stament.setInt(6, s.getGenderNum());
            stament.setString(7, s.getMeow());
            stament.setString(8, s.getPhoneNumber());
            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getCourseOfStudent(String rollnumber) {
        ArrayList<String> courses = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT codeCourse FROM score where rollnumber = ?";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            stament.setString(1, rollnumber);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString(1));
            }
            stament.close();
            conn.close();
            return courses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static ArrayList<ScoreDetail> getScoreOfCourse(String courseCode) {
        ArrayList<ScoreDetail> score = new ArrayList<>();
        Connection conn = getConnection();
        String query = "SELECT * from score,students where codeCourse = ? AND score.rollnumber = students.rollnumber";
        try {
            PreparedStatement stament = conn.prepareStatement(query);
            stament.setString(1, courseCode);
            ResultSet rs = stament.executeQuery();
            while (rs.next()) {
                ScoreDetail s = new ScoreDetail(rs.getString(2), rs.getString(7), rs.getString(1), rs.getFloat(3), rs.getFloat(4), rs.getFloat(5));
                System.out.println(rs.getFloat(3) + rs.getFloat(4) + rs.getFloat(5));
                score.add(s);
            }
            stament.close();
            conn.close();
            return score;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public static boolean addScoreTable(Student s, ArrayList<String> courses) {
        Connection conn = getConnection();
        String sqlInsert = "INSERT INTO score VALUES(?, ?,?,?,?)";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlInsert);
            for (String course : courses) {
                stament.setString(1, course);
                stament.setString(2, s.getRollNumber());
                stament.setNull(3, Types.FLOAT);
                stament.setNull(4, Types.FLOAT);
                stament.setNull(5, Types.FLOAT);
                stament.executeUpdate();
            }
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateScore(ScoreDetail s) {
        Connection conn = getConnection();
        String sqlUpdate = "UPDATE score SET testScore = ?, midleExamScore = ?, finalExamScore = ? where rollnumber=? AND codeCourse=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlUpdate);
            stament.setFloat(1, s.getTestScore());
            stament.setFloat(2, s.getMidleExamScore());
            stament.setFloat(3, s.getFinalExamScore());

            stament.setString(4, s.getRollNumber());
            stament.setString(5, s.getCourseCode());

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addCourse(Course c) {
        Connection conn = getConnection();
        String sqlInsert = "INSERT INTO courses VALUES(?, ?)";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlInsert);
            stament.setString(1, c.getCode());
            stament.setString(2, c.getName());

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateInfoStudent(Student s) {
        Connection conn = getConnection();
        String sqlUpdate = "UPDATE students SET name=?, class=?, birthday=?, major=?, gender=?, email=?, phone=? where rollnumber=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlUpdate);
            stament.setString(1, s.getName());
            stament.setString(2, s.getClassName());
            stament.setString(3, s.getBirthDay());
            stament.setString(4, s.getMajor());
            stament.setInt(5, s.getGenderNum());
            stament.setString(6, s.getMeow());
            stament.setString(7, s.getPhoneNumber());

            stament.setString(8, s.getRollNumber());
            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delScoreCourse(Student s, String codeCourse) {
        Connection conn = getConnection();
        String sqlDel = "DELETE FROM score where codeCourse=? AND rollnumber=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlDel);
            stament.setString(1, codeCourse);
            stament.setString(2, s.getRollNumber());

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delScoreCourse(String codeCourse) {
        Connection conn = getConnection();
        String sqlDel = "DELETE FROM score where codeCourse=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlDel);
            stament.setString(1, codeCourse);

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delCourse(String codeCourse) {
        Connection conn = getConnection();
        String sqlDel = "DELETE FROM courses where codeCourse=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlDel);
            stament.setString(1, codeCourse);

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delAStudent(Student s) {
        Connection conn = getConnection();
        String sqlDel = "DELETE FROM students where rollnumber=?";
        try {
            PreparedStatement stament = conn.prepareStatement(sqlDel);
            stament.setString(1, s.getRollNumber());

            stament.executeUpdate();
            stament.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
