package com.devCraftLab;

import com.devCraftLab.studentapp.model.Student;

public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    Plain Java Student Application     ");
        System.out.println("========================================\n");

        // Way 1: Using default constructor + setters
        Student student1 = new Student();
        student1.setId(1);
        student1.setName("Rahim Khan");
        student1.setCourse("Computer Science");
        student1.setAge(22);

        // Way 2: Using parameterized constructor
        Student student2 = new Student(2, "Karim Ahmed", "Software Engineering", 23);

        // Display
        System.out.println("Student 1: " + student1);
        System.out.println("Student 2: " + student2);

        System.out.println("\n========================================");
    }
}