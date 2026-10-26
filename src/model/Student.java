/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hoang
 */
public class Student {
    // fields
    private String id;
    private String fullName;
    private String major;
    private double gpa;
    
    // constructors   
    public Student(String id, String fullName, String major, double gpa) {
        this.id = id;
        this.fullName = fullName;
        this.major = major;
        this.gpa = gpa;
    }
    
    // accessors and mutators
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    // methods
    @Override
    public String toString() {
        return String.format(" %-8s| %-26s| %-25s| %4.2f",
                id, fullName, major, gpa);
    }
    
    
}
