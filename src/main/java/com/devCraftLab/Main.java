package com.devCraftLab;

import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.service.StudentService;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   Student Management System v2.0      ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        StudentService studentService = new StudentService();

        System.out.println("\n➤ Adding students...\n");

        Student student1 = new Student(1, "Alice Johnson", "Computer Science", 20);
        Student student2 = new Student(2, "Bob Smith", "Mathematics", 22);
        Student student3 = new Student(3, "Charlie Brown", "Physics", 21);
        studentService.addStudent(student1);
        studentService.addStudent(student2);
        studentService.addStudent(student3);
        System.out.println("\n➤ Displaying all students:\n");
        studentService.displayAllStudents();
        System.out.println("\n ➤ Finding student with ID 2'...\n");
        Student foundStudent = studentService.getStudentById(2);
        if (foundStudent != null) {
            System.out.println("Found: " + foundStudent);
        } else {
            System.out.println("Student with ID 2 not found.");
        }
        System.out.println("\n➤ Updating course for student ID 3...\n");
        boolean updated = studentService.updateStudentCourse(3, "Astronomy");
        if (updated) {
            System.out.println("Updated student ID 3 successfully.");
        } else {
            System.out.println("Failed to update student ID 3.");
        }

        studentService.displayAllStudents();

        System.out.println("\n📊 Total Students: " + studentService.getStudentCount() + "\n");

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         Application Ended             ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}