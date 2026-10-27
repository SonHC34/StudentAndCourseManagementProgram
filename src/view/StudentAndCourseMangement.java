/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package view;

import bussiness.CourseList;
import bussiness.StudentList;
import tools.ConsoleInputter;

/**
 *
 * @author hoang
 */
public class StudentAndCourseMangement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // FILE PATH
        final String courseFile = "data/Courses.txt";
        final String studentFile = "data/Students.txt";
        
        // INITIALIZE LISTS AND LOAD DATA FROM FILE
        CourseList courses = new CourseList();
        courses.readFromFile(courseFile);
        StudentList students = new StudentList();
        students.readFromFile(studentFile);
        
        boolean studentsChanged = false;
        boolean coursesChanged = false;
        
        int choice;
        
        Object[] menuOptions = {
            "List all students",
            "Add a new student",
            "Search for a student by ID",
            "Update a student by ID",
            "List all students by major",
            "Add a new course",
            "List all courses by student (grouped)",
            "Calculate total study duration by student ID",
            "Remove a student by ID",
            "Sort students by GPA",
            "Save data to files",
            "Quit program"
        };
        
        String title = "----- CAR INSURANCE MANAGEMENT -----";
        do {
            System.out.println(title);
            choice = ConsoleInputter.intMenu(menuOptions);
            
            switch (choice) {
                case 1: {
                    // Print all students
                    students.print();
                    break;
                }
                case 2: {
                    // Add a new student
                    students.add();
                    studentsChanged = true;
                    break;
                }
                case 3: {
                    // Search for a student by ID
                    students.searchById();
                    break;
                }
                case 4: {
                    // Update a student by ID
                    students.updateById();
                    studentsChanged = true;
                    break;
                }
                case 5: {
                    // Print all students by major
                    students.printByMajor();
                    break;
                }
                case 6: {
                    // Add a new course
                    courses.add(students);
                    coursesChanged = true;
                    break;
                }
                case 7: {
                    // Print all courses by student (grouped)
                    courses.printByStudent(students.getAll());
                    break;
                }
                case 8: {
                    // Calculate total study duration by student ID
                    courses.calDurationByStudent();
                    break;
                }
                case 9: {
                    // Remove a student by ID
                    students.removeById();
                    studentsChanged = true;
                    break;
                }
                case 10: {
                    // Sort students by GPA
                    students.sortByGpa();
                    break;
                }
                case 11: {
                    // Save data to files
                    if (studentsChanged || coursesChanged) {
                        students.writeToFile(studentFile);
                        System.out.println("Writing student data to " + studentFile + " successfully.");
                        courses.writeToFile(courseFile);
                        System.out.println("Writing course data to " + courseFile + " successfully.");
                    } else {
                        System.out.println("There is not change in program");
                    }
                    break;
                }
                case 12: {
                    // Quit program
                    if (studentsChanged || coursesChanged) {
                        System.out.println("WARNING: All missing data will lost if saving");

                        boolean response = ConsoleInputter.getBoolean("Do you want to save changes before existing? ");
                        if (response) {
                            // write data
                            students.writeToFile(studentFile);
                            System.out.println("Writing student data to " + studentFile + " successfully.");
                            courses.writeToFile(courseFile);
                            System.out.println("Writing course data to " + courseFile + " successfully.");
                        }
                    }
                    System.out.println("Quiting program...");
                    break;
                }         
            }
        } while (choice < menuOptions.length);
    }
}
