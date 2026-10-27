/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bussiness;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Course;
import model.Student;
import tools.ConsoleInputter;

/**
 *
 * @author hoang
 */
public class CourseList {
    // FIELDSS
    List<Course> courseList = new ArrayList<>();
    final String DATE_PAT = "dd/MM/yyyy";
    final String COURSENAME_PAT = ".+";
    
    // METHODS 
    public void printall() {
        for (Course c : courseList) {
            System.out.println(c);
        }
    }
    /*
    Description: Add a new course to the list
    Input: StudentList studentList
    Output: None (Add directly to the list)
    
    Algorithm:
    Begin
        Print the function header
        Create fields and get input from console. For student id, must be check from student list
        (courseId, studentId, courseName, durationWeeks, startDate)
        Ask user's decision before add
        If true create new course object, then add to the list and notify result
        Otherwise, display notification on the screen
    End
    */
    public void add(StudentList studentList) {
        System.out.println("-----  ADD NEW COURSE  -----");
        
        // input course id, student id, course name, duration week, and start date
        String courseId;
        while (true) {
            courseId = ConsoleInputter.getStr("Course ID");
            if (findById(courseId) == null) {
                break;
            } else {
                System.out.println("The course ID is already exist.");
            }
        }
        String studentId = null;
        while (true) {
            studentId = ConsoleInputter.getStr("Student ID");
            if (studentList.isStudentExist(studentId)) 
                break;
        }
        String courseName = ConsoleInputter.getString("Course name",COURSENAME_PAT, "Course name cannot be empty!");
        int durationWeeks = ConsoleInputter.getInt("Duration weeks", 1, 10);
        
        boolean isFutureToday = false;
        Date startDate;
        do {
            startDate = ConsoleInputter.getDate("Start date(dd/MM/yyyy)", DATE_PAT);
            isFutureToday = startDate.after(new Date());  // kiem tra thoi gian co o hien tai hay tuong lai ko
            if (isFutureToday == false) System.out.println("Start date must be in an future date!");
        } while (isFutureToday == false);
        
        // ask user decision
        boolean response = ConsoleInputter.getBoolean("Add the course?");
        if (response == true) {
            Course anCourse = new Course(courseId, studentId, courseName, durationWeeks, startDate);
            courseList.add(anCourse);
            System.out.println("Add the course successfully!");
        } else {
            System.out.println("Cancelled process");
        }   
    } // Add new course method
    
    /*
    Description: Print all registered course by each student
    Input: None (inputs taken from console)
    Output: None (displays directly on screen)
    
    Algorithm:
    Begin
        Print the function header
        For each  in the student the student list {
            Print its id and name
            For each course in the course list {
                
            }
        }
    End
    */
    public void printByStudent(List<Student> studentList) {
        System.out.println("-----  LIST ALL COURSES BY STUDENT  -----");

        if (studentList.isEmpty()) {
            System.out.println("The student list is empty.");
            return;
        }

        for (Student student : studentList) {
            // print id and name student
            System.out.printf("%s - %s%n", student.getId(), student.getFullName());

            boolean hasCourse = false;

            for (Course course : courseList) {
                if (student.getId().equals(course.getStudentId())) {
                    if (!hasCourse) {
                        System.out.printf(" %-10s| %-13s| %-12s|%n",
                                "Course ID", "Duration(wks)", "Start Date");
                        hasCourse = true;
                    }
                    System.out.printf(" %-10s| %-13d| %-12s|%n",
                            course.getCourseId(),
                            course.getDurationWeeks(),
                            ConsoleInputter.dateStr(course.getStartDate(), DATE_PAT));
                }
            } // end for course

            if (!hasCourse) {
                System.out.println("** No courses assigned.");
            }
            System.out.println("-----------------------------------------");
        } // end for student
    } // List all courses by student method
    
    /*
    Description: Calculate Total duration by Student ID
    Input: None (inputs taken from console)
    Output: None (directly value display on the screen)
    
    Algorithm:
    Begin
        Print function header
        Input id 
        Create a result variable, with 0 value
        For each course {
            If the course matches id, then plus its duration to the result variable
        }
        Display result on screen
    End
    */
    public void calDurationByStudent() {
        System.out.println("-----  CALCULATE TOTAL DURATION BY STUDENT ID  -----");
        String id = ConsoleInputter.getString("Student ID[STUxxxx]", "^STU\\d{4}$", "The student id must follow the format.");
        
        int result = 0;
        for (Course anCourse : courseList) {
            if (anCourse.getStudentId().equals(id)) {
                result += anCourse.getDurationWeeks();
            }
        }
        
        System.out.println("Total duration: " + result);
    } // Calculate total duration by student ID menthod
    
    /*
    Description: Convert a Course object to a String line
    Input: Course anCourse
    Output: Return a String line
    
    Algorithm:
    Begin
        Create a String variable to get formatted Date
        Return a String line with join ", "
    End
    */
    private String courseToLine(Course anCourse) {
        String startDate = ConsoleInputter.dateStr(anCourse.getStartDate(), "dd/MM/yyyy");
        return String.join(", ",
                anCourse.getCourseId(),
                anCourse.getStudentId(),
                anCourse.getCourseName(),
                String.valueOf(anCourse.getDurationWeeks()),
                startDate);
    } // Convert a course into a string method
    
    /*
    Description: Covert a line into a Course object
    Input: String line
    Output: Return a Course object, or null if invalid
    
    Algorithm:
    Begin
        Split line by regex "\\s*,\\s*" into a String array
        If array length is not equal to 5, return null
        try {
            Covert elements to suitable data types according to Course model
            If startDate field is null, duration is less than 1, or any string is empty:
                return null
            Return the Course object using constructor
        } catch (NumberFormatException e) {
            return null
        }
    End
    */
    private Course linetoCourse(String line) {
        String[] arr = line.split("\\s*,\\s*");
        if (arr.length != 5) return null;
        
        try {
            String courseId = arr[0];
            String studentId = arr[1];
            String courseName = arr[2];
            int durationWeeks = Integer.parseInt(arr[3]);
            Date startDate = ConsoleInputter.parseDate(arr[4], "dd/MM/yyyy");
            
            if (startDate == null || durationWeeks < 1 || courseId.isEmpty() ||
                    studentId.isEmpty() || courseName.isEmpty()) {
                return null;
            }
            
            return new Course(courseId, studentId, courseName, durationWeeks, startDate);
        } catch (NumberFormatException e) {
            return null;
        }
    } // conver line to a course method
    
    /*
    Description: Read Course data from file
    Input: String filePath, StudentList studentList
    Output: None (Updates courseList directly)
    
    Algorithm:
    Begin
        Try (Initialize BufferedReader with FileReader using filePath) {
            Declare a String variable for reading lines
            While reading each line until end of file (line != null):
                Convert line to a Course object using linetoCourse()
                If the course is null:
                    Display warning for invalid or missing data
                Else if the course ID already exists:
                    Display warning for duplicate Course ID
                Else if the student ID does not exist in student list:
                    Display warning for non-existent Student ID
                Else:
                    Add the Course object to courseList
            Display success notification on screen
        } catch (IOException e) {
            Display error notification on screen
        }
    End
    */
    public void readFromFile(String filePath, StudentList studentList) {      
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Course anCourse = linetoCourse(line);
                if (anCourse == null) {
                    System.out.println("Warning: Invalid or missing data skipped -> " + line);
                } else if (findById(anCourse.getCourseId()) != null) {
                    System.out.println("Warning: Duplicate Course ID skipped -> " + line);
                } else if (!studentList.isStudentExist(anCourse.getStudentId())) {
                    System.out.println("Warning: Non-existent Student ID skipped -> " + line);
                } else {
                    courseList.add(anCourse);
                }
            }
            System.out.println("Reading all data from " + filePath + " successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file from " + filePath + ": " + e.getMessage());
        }
    } // read course data from file method
    
    /*
    Description: Write Course data to file
    Input: String filePath
    Output: None (Outputs data directly to file)
    
    Algorithm:
    Begin
        Create a File object using filePath 
        If the file does not exist, return
        try (Initialize BufferedWriter with FileWriter) {
            For each Course in courseList:
                Convert Course to line using courseToLine() and write to file
                Write a new line
        } catch (IOException e) {
            Display error notification on screen
        }
    End
    */
    public void writeToFile(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) return;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            for (Course anCourse : courseList) {
                writer.write(courseToLine(anCourse));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file to " + filePath + ": " + e.getMessage());
        }
    } // write course data to file method
    
    public Course findById(String courseId) {
        for (Course c : courseList) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }
    
    public boolean hasStudent(String id) {
        boolean has = false;
        for (Course course : courseList) {
            if (course.getStudentId().equals(id)) {
                has = true;
                break;
            }
        }
        return has;
    }
}
