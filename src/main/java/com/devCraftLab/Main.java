package com.devCraftLab;

import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.service.StudentService;

public class Main {
    public static void main(String[] args) {

        System.out.println("╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(10) + "Student Management System v4.0" + " ".repeat(17) + "║");
        System.out.println("║" + " ".repeat(10) + "(Multiple Repositories)" + " ".repeat(24) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝\n");

        // 🔴 PROBLEM: We just create service, but we don't know:
        // - Which repository implementation is being used
        // - How many dependencies it has
        // - How to change implementation without modifying service code

        StudentService service = new StudentService();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 1: Adding Courses");
        System.out.println("=".repeat(60));

        service.addCourse(new Course(1, "CS101", "Introduction to Programming", 3, "Dr. Rahman"));
        service.addCourse(new Course(2, "CS201", "Data Structures", 4, "Dr. Karim"));
        service.addCourse(new Course(3, "CS301", "Algorithms", 4, "Dr. Fatema"));
        service.addCourse(new Course(4, "ML101", "Machine Learning Basics", 3, "Dr. Ahmed"));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 2: Adding Students");
        System.out.println("=".repeat(60));

        service.addStudent(new Student(1, "Rahim Khan", "Not Enrolled", 22));
        service.addStudent(new Student(2, "Karim Ahmed", "Not Enrolled", 23));
        service.addStudent(new Student(3, "Fatema Begum", "Not Enrolled", 21));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 3: Displaying All Data");
        System.out.println("=".repeat(60));

        service.displayAllCourses();
        service.displayAllStudents();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 4: Enrolling Students in Courses");
        System.out.println("=".repeat(60));

        service.enrollStudentInCourse(1, "CS101");
        service.enrollStudentInCourse(2, "CS201");
        service.enrollStudentInCourse(3, "ML101");

        // Try duplicate enrollment
        System.out.println("\n➤ Attempting duplicate enrollment...");
        service.enrollStudentInCourse(1, "CS101");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 5: After Enrollment");
        System.out.println("=".repeat(60));

        service.displayAllStudents();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("STATISTICS");
        System.out.println("=".repeat(60));

        System.out.println("📊 Total Courses: " + service.getTotalCourses());
        System.out.println("📊 Total Students: " + service.getTotalStudents());

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔴 PROBLEMS WITH CURRENT APPROACH:");
        System.out.println("=".repeat(60));
        System.out.println("1. StudentService creates its own dependencies (tight coupling)");
        System.out.println("2. Can't change repository implementation without modifying service");
        System.out.println("3. Can't test service in isolation (can't mock repositories)");
        System.out.println("4. No control over object lifecycle");
        System.out.println("5. If service needs more dependencies, constructor becomes complex");
        System.out.println("=".repeat(60));

        System.out.println("\n╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(20) + "Application Ended" + " ".repeat(21) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝");
    }
}