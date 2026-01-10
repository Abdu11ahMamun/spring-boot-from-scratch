package com.devCraftLab;

import com.devCraftLab.studentapp.container.SimpleDIContainer;
import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.service.StudentService;
import com.devCraftLab.studentapp.repository.StudentRepository;
import com.devCraftLab.studentapp.repository.CourseRepository;
import com.devCraftLab.studentapp.repository.impl.StudentRepositoryImpl;
import com.devCraftLab.studentapp.repository.impl.CourseRepositoryImpl;
import com.devCraftLab.studentapp.database.DatabaseService;

public class Main {
    public static void main(String[] args) {

        System.out.println("╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(10) + "Student Management System v6.0" + " ".repeat(17) + "║");
        System.out.println("║" + " ".repeat(12) + "(With IoC Container!)" + " ".repeat(25) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝\n");

        // ============================================================
        // STEP 1: Create Container
        // ============================================================
        System.out.println("STEP 1: Creating IoC Container");
        System.out.println("=".repeat(60));

        SimpleDIContainer container = new SimpleDIContainer();

        // ============================================================
        // STEP 2: Register All Beans (Dependency Wiring)
        // ============================================================
        System.out.println("\nSTEP 2: Registering Beans (Dependency Wiring)");
        System.out.println("=".repeat(60));

        // Create and register DatabaseService
        DatabaseService databaseService = new DatabaseService("jdbc:mysql://localhost:3306/studentdb");
        databaseService.connect();
        container.registerBean("databaseService", databaseService);

        // Create and register Repositories
        StudentRepository studentRepository = new StudentRepositoryImpl(databaseService);
        container.registerBean("studentRepository", studentRepository);

        CourseRepository courseRepository = new CourseRepositoryImpl(databaseService);
        container.registerBean("courseRepository", courseRepository);

        // Create and register Service
        StudentService studentService = new StudentService(studentRepository, courseRepository);
        container.registerBean("studentService", studentService);

        // ============================================================
        // STEP 3: List All Beans
        // ============================================================
        System.out.println("\nSTEP 3: Container Status");
        System.out.println("=".repeat(60));
        container.listAllBeans();
        System.out.println("📊 Total beans in container: " + container.getBeanCount());

        // ============================================================
        // STEP 4: Retrieve and Use Beans
        // ============================================================
        System.out.println("\nSTEP 4: Retrieving Beans from Container");
        System.out.println("=".repeat(60));

        // Get service from container
        StudentService service = container.getBean("studentService", StudentService.class);

        if (service == null) {
            System.out.println("❌ Failed to get StudentService from container!");
            return;
        }

        // ============================================================
        // STEP 5: Use the Service (Business Logic)
        // ============================================================
        System.out.println("\nSTEP 5: Using the Service");
        System.out.println("=".repeat(60));

        // Add courses
        System.out.println("\n➤ Adding Courses...");
        service.addCourse(new Course(1, "CS101", "Introduction to Programming", 3, "Dr. Rahman"));
        service.addCourse(new Course(2, "CS201", "Data Structures", 4, "Dr. Karim"));

        // Add students
        System.out.println("\n➤ Adding Students...");
        service.addStudent(new Student(1, "Rahim Khan", "Not Enrolled", 22));
        service.addStudent(new Student(2, "Karim Ahmed", "Not Enrolled", 23));

        // Enroll students
        System.out.println("\n➤ Enrolling Students...");
        service.enrollStudentInCourse(1, "CS101");
        service.enrollStudentInCourse(2, "CS201");

        // Display
        service.displayAllStudents();

        // ============================================================
        // STEP 6: Demonstrate Container Benefits
        // ============================================================
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STEP 6: Container Benefits Demonstration");
        System.out.println("=".repeat(60));

        // Can retrieve any bean anytime
        System.out.println("\n➤ Retrieving DatabaseService from container...");
        DatabaseService db = container.getBean("databaseService", DatabaseService.class);
        System.out.println("✅ Got DatabaseService: " + db.getConnectionUrl());

        // Can check if bean exists
        System.out.println("\n➤ Checking bean existence...");
        System.out.println("Does 'studentService' exist? " + container.containsBean("studentService"));
        System.out.println("Does 'unknownBean' exist? " + container.containsBean("unknownBean"));

        // ============================================================
        // COMPARISON: Manual vs Container
        // ============================================================
        System.out.println("\n" + "=".repeat(60));
        System.out.println("COMPARISON: Manual Wiring vs Container");
        System.out.println("=".repeat(60));
        printComparison();

        // ============================================================
        // WHAT'S STILL MISSING?
        // ============================================================
        System.out.println("\n" + "=".repeat(60));
        System.out.println("WHAT'S STILL MANUAL? (What Spring Automates)");
        System.out.println("=".repeat(60));
        printWhatSpringDoes();

        // Cleanup
        System.out.println();
        databaseService.disconnect();

        System.out.println("\n╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(20) + "Application Ended" + " ".repeat(21) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝");
    }

    private static void printComparison() {
        System.out.println("\n🔴 MANUAL WIRING (Branch 05):");
        System.out.println("   DatabaseService db = new DatabaseService(...);");
        System.out.println("   StudentRepository repo1 = new StudentRepositoryImpl(db);");
        System.out.println("   CourseRepository repo2 = new CourseRepositoryImpl(db);");
        System.out.println("   StudentService service = new StudentService(repo1, repo2);");
        System.out.println("   - Scattered code");
        System.out.println("   - Hard to manage");
        System.out.println("   - Objects not centralized");

        System.out.println("\n🟢 WITH CONTAINER (Branch 06):");
        System.out.println("   container.registerBean(\"db\", new DatabaseService(...));");
        System.out.println("   container.registerBean(\"repo1\", new StudentRepositoryImpl(db));");
        System.out.println("   container.registerBean(\"service\", new StudentService(...));");
        System.out.println("   ");
        System.out.println("   StudentService service = container.getBean(\"service\", StudentService.class);");
        System.out.println("   - Centralized management");
        System.out.println("   - Easy to retrieve anywhere");
        System.out.println("   - Single source of truth");
    }

    private static void printWhatSpringDoes() {
        System.out.println("\n❌ STILL MANUAL IN OUR CONTAINER:");
        System.out.println("1. We manually create objects: new StudentService(...)");
        System.out.println("2. We manually resolve dependencies");
        System.out.println("3. We manually register beans");
        System.out.println("4. No automatic wiring");
        System.out.println("5. No annotation support");

        System.out.println("\n✅ WHAT SPRING DOES AUTOMATICALLY:");
        System.out.println("1. Scans for @Component, @Service, @Repository");
        System.out.println("2. Automatically creates objects");
        System.out.println("3. Automatically resolves dependencies (@Autowired)");
        System.out.println("4. Automatically registers in container");
        System.out.println("5. Manages lifecycle (init, destroy)");
        System.out.println("6. Handles singletons, prototypes, etc.");

        System.out.println("\n💡 NEXT: We'll learn Spring Core to see this magic!");
    }
}