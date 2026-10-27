/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import tools.ConsoleInputter;

/**
 *
 * @author hoang
 */
public class Course {
    // fields
    private String courseId;
    private String studentId;
    private String courseName;
    private int durationWeeks;
    private Date startDate;
    
    // constructors
    public Course(String courseId, String studentId, String courseName, int durationWeeks, Date startDate) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.courseName = courseName;
        this.durationWeeks = durationWeeks;
        this.startDate = startDate;
    }
    
    // accessor and mutator
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentIdCode(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    // methods
    @Override
    public String toString() {
        return String.join(", ", courseId, studentId, courseName);
    }
    
    
}
