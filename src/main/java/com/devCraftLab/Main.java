package com.devCraftLab;

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
        System.out.println("║" + " ".repeat(10) + "Student Management System v5.0" + " ".repeat(17) + "║");
        System.out.println("║" + " ".repeat(15) + "(Dependency Hell!)" + " ".repeat(24) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝\n");

        System.out.println("🔴 MANUAL DEPENDENCY WIRING - WATCH THE COMPLEXITY!\n");
        System.out.println("=".repeat(60));

        // 🔴 Step 1: Create DatabaseService (root dependency)
        System.out.println("Step 1: Creating DatabaseService...");
        DatabaseService databaseService = new DatabaseService("jdbc:mysql://localhost:3306/studentdb");
        databaseService.connect();

        System.out.println("\n=".repeat(60));

        // 🔴 Step 2: Create Repositories (depend on DatabaseService)
        System.out.println("Step 2: Creating Repositories...");
        System.out.println("   (Each repository needs DatabaseService)");
        StudentRepository studentRepository = new StudentRepositoryImpl(databaseService);
        CourseRepository courseRepository = new CourseRepositoryImpl(databaseService);

        System.out.println("\n=".repeat(60));

        // 🔴 Step 3: Create Service (depends on Repositories)
        System.out.println("Step 3: Creating StudentService...");
        System.out.println("   (Service needs both repositories)");
        StudentService service = new StudentService(studentRepository, courseRepository);

        System.out.println("\n=".repeat(60));
        System.out.println("✅ ALL DEPENDENCIES WIRED! (Manually... 😰)");
        System.out.println("=".repeat(60));

        // Now use the service
        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 1: Adding Courses");
        System.out.println("=".repeat(60));

        service.addCourse(new Course(1, "CS101", "Introduction to Programming", 3, "Dr. Rahman"));
        service.addCourse(new Course(2, "CS201", "Data Structures", 4, "Dr. Karim"));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 2: Adding Students");
        System.out.println("=".repeat(60));

        service.addStudent(new Student(1, "Rahim Khan", "Not Enrolled", 22));
        service.addStudent(new Student(2, "Karim Ahmed", "Not Enrolled", 23));

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SCENARIO 3: Enrolling Students");
        System.out.println("=".repeat(60));

        service.enrollStudentInCourse(1, "CS101");
        service.enrollStudentInCourse(2, "CS201");

        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL STATE");
        System.out.println("=".repeat(60));

        service.displayAllStudents();

        System.out.println("\n📊 Total Students: " + service.getTotalStudents());
        System.out.println("📊 Total Courses: " + service.getTotalCourses());

        // Cleanup
        System.out.println();
        databaseService.disconnect();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔴 PROBLEMS WITH THIS APPROACH:");
        System.out.println("=".repeat(60));
        printProblems();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("💡 WHAT IF WE NEED MORE SERVICES?");
        System.out.println("=".repeat(60));
        printWhatIfScenario();

        System.out.println("\n╔" + "═".repeat(58) + "╗");
        System.out.println("║" + " ".repeat(20) + "Application Ended" + " ".repeat(21) + "║");
        System.out.println("╚" + "═".repeat(58) + "╝");
    }

    private static void printProblems() {
        System.out.println("1. 🔴 Manual Dependency Chain:");
        System.out.println("   - Must create DatabaseService first");
        System.out.println("   - Then Repositories (need DatabaseService)");
        System.out.println("   - Then Service (needs Repositories)");
        System.out.println("   - Order matters! Wrong order = Compilation error");
        System.out.println();
        System.out.println("2. 🔴 Tight Coupling in Main:");
        System.out.println("   - Main knows about ALL dependencies");
        System.out.println("   - Main must manage object lifecycle");
        System.out.println("   - Main becomes complex");
        System.out.println();
        System.out.println("3. 🔴 Hard to Change:");
        System.out.println("   - Want to change DatabaseService implementation?");
        System.out.println("   - Have to modify Main class");
        System.out.println("   - Ripple effect throughout codebase");
        System.out.println();
        System.out.println("4. 🔴 Scalability Issues:");
        System.out.println("   - Adding new service? More wiring in Main!");
        System.out.println("   - 10 services? 50 dependencies? NIGHTMARE!");
    }

    private static void printWhatIfScenario() {
        System.out.println("Imagine we need to add:");
        System.out.println("- EnrollmentService (needs StudentRepo + CourseRepo)");
        System.out.println("- NotificationService (needs EmailService + SMSService)");
        System.out.println("- EmailService (needs ConfigService)");
        System.out.println("- SMSService (needs ConfigService + TwilioClient)");
        System.out.println("- ReportService (needs all above services)");
        System.out.println();
        System.out.println("Main class would become:");
        System.out.println("DatabaseService db = new DatabaseService(...);");
        System.out.println("ConfigService config = new ConfigService();");
        System.out.println("TwilioClient twilio = new TwilioClient(config);");
        System.out.println("EmailService email = new EmailService(config);");
        System.out.println("SMSService sms = new SMSService(config, twilio);");
        System.out.println("StudentRepository studentRepo = new StudentRepositoryImpl(db);");
        System.out.println("CourseRepository courseRepo = new CourseRepositoryImpl(db);");
        System.out.println("EnrollmentService enrollment = new EnrollmentService(studentRepo, courseRepo);");
        System.out.println("NotificationService notification = new NotificationService(email, sms);");
        System.out.println("StudentService studentService = new StudentService(studentRepo, courseRepo);");
        System.out.println("ReportService report = new ReportService(enrollment, notification, studentService);");
        System.out.println();
        System.out.println("😱 IMPOSSIBLE TO MAINTAIN!");
        System.out.println();
        System.out.println("💡 THIS IS WHY WE NEED:");
        System.out.println("   - Inversion of Control (IoC)");
        System.out.println("   - Dependency Injection Framework");
        System.out.println("   - Spring Container!");
    }
}