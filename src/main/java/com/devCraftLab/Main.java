package com.devCraftLab;

import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.service.StudentService;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Student Management System v3.0      ║");
        System.out.println("║   (With Repository Pattern)           ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // Initialize service (which initializes repository)
        StudentService service = new StudentService();

        System.out.println("\n" + "=".repeat(45));
        System.out.println("SCENARIO 1: Adding Students");
        System.out.println("=".repeat(45));

        service.addStudent(new Student(1, "Rahim Khan", "Computer Science", 22));
        service.addStudent(new Student(2, "Karim Ahmed", "Software Engineering", 23));
        service.addStudent(new Student(3, "Fatema Begum", "Data Science", 21));

        // Try adding duplicate
        System.out.println("\n➤ Trying to add duplicate ID...");
        service.addStudent(new Student(1, "Duplicate Student", "CS", 20));

        // Try adding invalid student
        System.out.println("\n➤ Trying to add student below 18...");
        service.addStudent(new Student(4, "Minor Student", "CS", 16));

        System.out.println("\n" + "=".repeat(45));
        System.out.println("SCENARIO 2: Retrieving Students");
        System.out.println("=".repeat(45));

        service.displayAllStudents();

        System.out.println("\n" + "=".repeat(45));
        System.out.println("SCENARIO 3: Updating Student");
        System.out.println("=".repeat(45));

        System.out.println("\n➤ Updating course for student ID 2...");
        service.updateStudentCourse(2, "Machine Learning");

        System.out.println("\n➤ After update:");
        Student updated = service.getStudentById(2);
        System.out.println(updated);

        System.out.println("\n" + "=".repeat(45));
        System.out.println("SCENARIO 4: Deleting Student");
        System.out.println("=".repeat(45));

        System.out.println("\n➤ Deleting student ID 3...");
        service.deleteStudent(3);

        service.displayAllStudents();

        System.out.println("\n" + "=".repeat(45));
        System.out.println("SCENARIO 5: Statistics");
        System.out.println("=".repeat(45));

        System.out.println("\n📊 Total Students: " + service.getTotalStudents());
        System.out.println("📊 Student ID 1 exists? " + service.studentExists(1));
        System.out.println("📊 Student ID 99 exists? " + service.studentExists(99));

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         Application Ended             ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}