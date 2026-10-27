# Student and Short Course Management System (J1.L.P0039)

## 1. Overview
The **Student and Short Course Management System** is a Java console-based application designed to support academic record management in a university environment[cite: 4]. The program strictly adheres to Object-Oriented Programming (OOP) principles, focusing on data integrity constraints, robust input validation, and bidirectional file persistence[cite: 4, 6].

## 2. Core Entities & Constraints
The system operates on two primary entities persisted across flat text files (`Students.txt` and `Courses.txt`)[cite: 4]:

* **Student (`Students.txt`)**:
  - Fields: `Student ID`, `Full Name`, `Major`, `GPA`[cite: 4].
  - Constraints: `Student ID` must be unique and follow the `STU0000` pattern; `Full Name` must not be empty and must contain at least two words; `Major` cannot be empty; `GPA` must be a double value within the range `[0.0, 4.0]`[cite: 4].
* **Course (`Courses.txt`)**:
  - Fields: `Course ID`, `Student ID`, `Course Name`, `Duration (Weeks)`, `Start Date`[cite: 4].
  - Constraints: `Course ID` must be unique; `Student ID` must exist in the current student list; `Course Name` cannot be empty; `Duration` must be a positive integer (minimum 1 week); `Start Date` must strictly be a future date (`dd/MM/yyyy`)[cite: 4, 5].

## 3. Features
The interactive console menu provides 12 standard functional requirements[cite: 5]:
1. **List all students**: Displays the formatted student directory in a tabular view with column headers[cite: 5].
2. **Add a new student**: Enforces pattern validation and uniqueness before persisting new records[cite: 5].
3. **Search for a student by ID**: Retrieves and displays specific student profiles or outputs error messages[cite: 5].
4. **Update a student by ID**: Allows updating the GPA value within the range `[0.0, 4.0]`[cite: 5].
5. **List all students by major**: Filters and displays students enrolled in a selected academic discipline[cite: 5].
6. **Add a new course**: Registers a new course assignment linked to an existing student[cite: 5].
7. **List all courses by student (Grouped)**: Renders a hierarchical view of courses assigned under each student[cite: 6].
8. **Calculate total study duration by student ID**: Aggregates total registered study duration in weeks for a target student[cite: 6].
9. **Remove a student by ID**: Deletes a student record only if there are no existing course assignments[cite: 6].
10. **Sort students by GPA**: Displays the student list in ascending order of GPA using custom comparators[cite: 6].
11. **Save data to files**: Explicitly writes current runtime datasets back to `Students.txt` and `Courses.txt`[cite: 6].
12. **Quit program**: Detects unsaved modifications and prompts for confirmation (`Y/N`) before system termination[cite: 6].

## 4. Technical Highlights
* **File I/O & Dirty Data Filtering**: Features dynamic detection, logging warnings, and skipping corrupted records, duplicate identifiers, or broken foreign-key references (`non-existent Student IDs`) during startup initialization.
* **Separation of Concerns**: Modular architectural breakdown across domain entities (`model`), collection controllers (`business`), presentation layer (`view`), and reusable utility handlers (`tools`).
* **Strict Console Validation**: Comprehensive handling of parsing exceptions (`NumberFormatException`, `ParseException`), boundary conditions, and invalid string inputs.
