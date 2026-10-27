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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Student;
import tools.ConsoleInputter;

/**
 *
 * @author hoang
 */
public class StudentList {
    // FIELDS
    List<Student> studentList = new ArrayList<>();
    final String ID_PAT = "^STU\\d{4}$";
    final String NAME_PAT = "^[A-Za-z\\s]{2,25}$";
    final String[] majors = {
        "Software Engineering",
        "Information Systems",
        "Computer Science",
        "Artificial Intelligence",
        "Data Science"
    };
    
    // METHODS
    /*
    Description: Print all students in the list
    Input: None (inputs taken from console)
    Output: None (directly display on the screen)
    
    Algorithm:
    Begin
        Print function header
        Print table header, format: [ %-8s| %-26s| %-25s| %4s\n]
        For each student {
            Print the student
        }
        Print table footer
    End
    */
    public void print() {
        System.out.println("----- LIST ALL STUDENTS  -----\n");
        System.out.printf(" %-8s| %-25s| %-25s| %4s|\n"
                + "----------------------------------------------------------------------\n", 
                "ID", "Name", "Major", "GPA");
        for (Student anStudent : studentList) {
            System.out.println(anStudent);
        }
        
        System.out.println("----------------------------------------------------------------------");
    } // Print all students method
    
    /*
    Description: Add a new student in the list
    Input: None (inputs taken from console)
    Output: None (directly add in the list)
    
    Algorithm:
    Begin
        Print function header
        Loop until input a unique id
        Input name
        Print majors
        Input major, gpa
        Ask user's decision before add
        If true create new student object, then add to the list and notify result
        Otherwise, display notification on the screen
    End
    */
    public void add() {
        System.out.println("----- ADD A NEW STUDENT  -----");
        
        String id = null;
        while (true) {
            id = ConsoleInputter.getString("Student ID[STUxxxx]", ID_PAT, "The student id must follow the format.");
            if (isStudentExist(id)) {
                System.out.println("The id is already exist.");
            } else {
                break;
            }
        }
        String name = ConsoleInputter.getString("Student name[2~25 characters]", NAME_PAT, "The student name must be from 2 to 25 characters.");
        printMajors();
        int index = ConsoleInputter.getInt("Select major", 1, majors.length);
        String major = getMajor(index);
        double gpa = ConsoleInputter.getDouble("Gpa", 0.0, 4.0);
        
        // ask user decision
        boolean response = ConsoleInputter.getBoolean("Add the student?");
        if (response == true) {
            Student anStudent = new Student(id, name, major, gpa);
            studentList.add(anStudent);
            System.out.println("Add a new student successfully!");
        } else {
            System.out.println("Cancelled process");
        } 
    } // Add a new student method
    
    /*
    Description: Search a student by ID
    Input: None (inputs taken from console)
    Output: None (directly display on the screen)
    
    Algorithm:
    Begin
        Print function header
        Print table header, format: [ %-8s| %-26s| %-25s| %4s\n]
        If the student ID is exist {
            Print table header, format: [ %-8s| %-26s| %-25s| %4s\n]
            Print the student
            Print table footer
        } else {
            Display notification on the screen
        }
    End
    */
    public void searchById() {
        System.out.println("-----  SEARCH FOR A STUDENT BY ID  -----\n");
        
        String id = ConsoleInputter.getString("Student ID[STUxxxx]", ID_PAT, "The student ID must follow the format.");
        if (isStudentExist(id)) {
            System.out.printf(" %-8s| %-25s| %-25s| %4s|\n"
                    + "----------------------------------------------------------------------\n",
                    "ID", "Name", "Major", "GPA");
            System.out.println(getExistingStudent(id));
            System.out.println("----------------------------------------------------------------------");
        } else {
            System.out.println("The student ID does not exist!");
        }
    } // Search for a student by ID method
    
    /*
    Description: Update a student by ID
    Input: None (inputs taken from console)
    Output: None (directly update)
    
    Algorithm:
    Begin
        Print function header
        If the student ID is exist {
            Create and get the student object by ID
            Create and get a new gpa variable
            Set new value
        } else {
            Display notification on the screen
        }
    End
    */
    public void updateById() {
        System.out.println("----- UPDATE A STUDENT BY ID  -----");
        
        String id = ConsoleInputter.getString("Student ID[STUxxxx]", ID_PAT, "The student ID must follow the format.");
        if (isStudentExist(id)) {
            Student anStudent = getExistingStudent(id);
            double gpa = ConsoleInputter.getDouble("GPA", 0.0, 4.0);
            anStudent.setGpa(gpa);
        } else {
            System.out.println("The student ID does not exist!");
        }
        
    } // Update a student by ID method
    
    /*
    Description: Print all students by major
    Input: None (inputs taken from console)
    Output: None (directly display on the screen)
    
    Algorithm:
    Begin
        Print function header
        Print majors
        Create temporary variable to get major option from user
        Get the validate major input
        Create a empty list
        For each student {
            If the student matches the major, then add it to the list
        }
        If the filtered list is empty {
            Display notification on the screen
        } else {
            Print table header
            For each a student in the filtered list, print the student details
            Print table footer
        }
    End
    */
    public void printByMajor() {
        System.out.println("-----  LIST ALL STUDENTS BY MAJOR  -----");
        
        printMajors();
        int index = ConsoleInputter.getInt("Select major", 1, majors.length);
        String major = getMajor(index);
        List<Student> filteredStudents = new ArrayList<>();
        
        for (Student anStudent : studentList) {
            if (anStudent.getMajor().equals(major)) {
                filteredStudents.add(anStudent);
            }
        }
        
        if (filteredStudents.isEmpty()) {
            System.out.println("The list is empty");
        } else {
            System.out.printf(" %-8s| %-25s| %-25s| %4s|\n"
                    + "-----------------------------------------------------------------------\n",
                    "ID", "Name", "Major", "GPA");
            for (Student student : filteredStudents) {
                System.out.println(student);
            }
            System.out.println("-----------------------------------------------------------------------");
        }
    } // Print all students by major method
    
    /*
    Description: Remove a student by ID
    Input: None (inputs taken from console)
    Output: None (directly remove)
    
    Algorithm:
    Begin
        Print function header
        If the student ID is exist {
            Remove the student
        } else {
            Display notification on the screen
        }
    End
    */
    public void removeById(CourseList courseList) { 
        System.out.println("----- REMOVE A STUDENT BY ID  -----");
        
        String id = ConsoleInputter.getString("Student ID[STUxxxx]", ID_PAT, "The student ID must follow the format.");
        if (isStudentExist(id)) {
            if (courseList.hasStudent(id)) {
                studentList.remove(getExistingStudent(id));
                System.out.println("Remove successfully");
            } else {
                System.out.println("Cannot delete: The student is assigned to courses.");
            }
        } else {
            System.out.println("The student ID does not exist!");
        }  
    } // Remove a student by ID method
    
    /*
    Description: Display the list of Students sorted by GPA in ascending order
    Input: None (inputs taken from console)
    Output: None (directly display on the screen)
    
    Algorithm:
    Begin
        Print function header
        Create a new list with construct from the main list
        Define a comparator to compare students based on their GPA
        Sort the new list in ascending order using the comparator
        If the sorted list is empty {\
            Display notification on the screen
        } else {
            Print table header
            For each student, print the student details
            Print table footer
        }
    End
    */
    public void sortByGpa() {
        System.out.println("----- SORT STUDENTS BY GPA  -----");
        
        List<Student> sortedList = new ArrayList<>(studentList);
        
        Comparator<Student> sort = (Student s1, Student s2) -> {
            return Double.compare(s1.getGpa(), s2.getGpa());
        };     
        Collections.sort(sortedList, sort);
        
        if (sortedList.isEmpty()) {
            System.out.println("The list is empty");
        } else {
            System.out.printf(" %-8s| %-25s| %-25s| %4s|\n"
                    + "----------------------------------------------------------------------\n",
                    "ID", "Name", "Major", "GPA");
            for (Student student : sortedList) {
                System.out.println(student);
            }
            System.out.println("----------------------------------------------------------------------");
        }
    } // Sort students by GPA method
    
    /*
    Description: Convert a Student object to a String line
    Input: Course anStudent
    Output: Return a String line
    
    Algorithm:
    Begin
        Create a new String gpa, to parse from double to String
        Return a String line with join ", "
    End
    */
    private String studentToLine(Student anStudent) {
        String gpa = String.valueOf(anStudent.getGpa());
        return String.join(", ",
                anStudent.getId(),
                anStudent.getFullName(),
                anStudent.getMajor(),
                gpa);
    } // Covert an student fields to a string method
    
    /*
    Description: Covert a line into a Student object
    Input: String line
    Output: Return a Student object, or null if invalid
    
    Algorithm:
    Begin
        Split line by regex ",\\s*", into a String array
        If array length is not equal to 5, return null
        try {
            Covert elements to suitable data types according to Student model
            Return the Course object using constructor
        } catch (NumberFormatException e) {
            return null
        }
    End
    */
    private Student linetoStudent(String line) {
        String[] arr = line.split(",\\s*");
        if (arr.length != 4) return null;
        
        try {
            String id = arr[0].trim();
            String name = arr[1].trim();
            String major = arr[2].trim();
            double gpa = Double.parseDouble(arr[3]);
            
            return new Student(id, name, major, gpa);
        } catch (NumberFormatException e) {
            return null;
        }
    } // conver an line to a course method
    
    /*
    Description: Read Course data from file
    Input: String filePath
    Output: None (Updates courseList directly)
    
    Algorithm:
    Begin
        Try (Initialize BufferedReader with FileReader using filePath) {
            Declare a String variable for reading lines
            While reading each line until end of file (line != null):
                Convert line to a Student object using linetoStudent()
                If the student is null:
                    Display warning for invalid or missing data
                Else if the student ID already exists:
                    Display warning for duplicate Student ID
                Else:
                    Add the Student object to studentList
            Display success notification on screen
        } catch (IOException e) {
            Display error notification on screen
        }
    End
    */
    public void readFromFile(String filePath) {      
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student student = linetoStudent(line);
                if (student == null) {
                    System.out.println("Warning: Invalid or missing data skipped -> " + line);
                } else if (isStudentExist(student.getId())) {
                    System.out.println("Warning: Duplicate Student ID skipped -> " + line);
                } else {
                    studentList.add(student);
                }
            }
            System.out.println("Reading all data from " + filePath + " successfully.");
        } catch (IOException e) {
            System.out.println("Error reading file from " + filePath + ": " + e.getMessage());
        }
    } // read student data from file method
    
    /*
    Description: Write Course data to file
    Input: String filePath
    Output: None (Outputs data directly to file)
    
    Algorithm:
    Begin
        Create a File object using filePath 
        If the file does not exist, return
        try (Initialize BufferedWriter with FileWriter) {
            For each Student in courseList:
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
            for (Student anStudent : studentList) {
                writer.write(studentToLine(anStudent));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file to " + filePath + ": " + e.getMessage());
        }
    } // write student data to file method
 
    /*
    Description: Get an existing student from the student list
    Input: String studentId
    Output: Return the needed student, or null if student is NOT exist
    
    Algorithm:
    Begin
        If parameter is null, or empty, then return null
    
        Create a temporary student object with null variable
        for each student in the list {
            If the student's id matches the parameter
            Get the student
            Exit loop
        }
        Return the found student 
    End
    */
    public Student getExistingStudent(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return null;
        }
        Student foundStudent = null;
        
        for (Student student : studentList) {
            if (student.getId().equals(studentId)) {
                foundStudent = student;
                break;
            }
        }
        
        return foundStudent;
    } // Get existing student method
    
    /*
    Description: Check whether a student exists in the list
    Input: String studentId
    Output: Return true if student's id contains in the list, false otherwise
    
    Algorithm:
    Begin
        If parameter is null, or empty, then return false
        Return true if student's id is not null, false otherwise
    End
    */
    public boolean isStudentExist(String studentId) {
        if (studentId == null || studentId.isEmpty()) {
            return false;
        }
        
        return getExistingStudent(studentId) != null;
    }
    
    /*
    Description: Print all majors in the list
    Input: None (inputs taken from console)
    Output: None (directly display on the screen)
    
    Algorithm:
    Begin
        Print fuction header
        For loop {
            Print major
        }
    End
    */
    public void printMajors() {
        System.out.println("MAJOR LIST: ");
        
        for (int i = 0; i < majors.length; i++) {
            System.out.println((i+1) + ". " + majors[i]);
        }
    } // Print majors method
    
    /*
    Description: Get a major
    Input: int index
    Output: Return a String major
    
    Algorithm:
    Begin
        If parameter less than 0, and greater than major length, then return null
        Return a String major
    End
    */
    public String getMajor(int index) {
        if (index < 0 || index > majors.length) {
            return null;
        }
        
        return majors[index - 1];
    } // Get major method
    
    public boolean isEmpty() {
        return studentList.isEmpty();
    }
    
    public List<Student> getAll() {
        return Collections.unmodifiableList(studentList);
    }
}
