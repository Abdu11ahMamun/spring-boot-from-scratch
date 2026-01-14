package com.devCraftLab;

import com.devCraftLab.studentapp.config.AppConfig;
import com.devCraftLab.studentapp.container.SimpleDIContainer;
import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.service.StudentService;
import com.devCraftLab.studentapp.repository.StudentRepository;
import com.devCraftLab.studentapp.repository.CourseRepository;
import com.devCraftLab.studentapp.repository.impl.StudentRepositoryImpl;
import com.devCraftLab.studentapp.repository.impl.CourseRepositoryImpl;
import com.devCraftLab.studentapp.database.DatabaseService;
import com.devCraftLab.studentapp.container.BeanFactory;;
import com.devCraftLab.studentapp.container.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        System.out.println("╔" + "═".repeat(70) + "╗");
        System.out.println("║" + " ".repeat(15) + "Student Management System v7.0" + " ".repeat(24) + "║");
        System.out.println("║" + " ".repeat(17) + "(With Spring Framework!)" + " ".repeat(28) + "║");
        System.out.println("╚" + "═".repeat(70) + "╝\n");

        // ============================================================
        // STEP 1: Create Spring Container (ApplicationContext)
        // ============================================================
        System.out.println("STEP 1: Initializing Spring Container");
        System.out.println("=".repeat(70));

        // This ONE line does EVERYTHING!
        // - Scans @Configuration class
        // - Finds all @Bean methods
        // - Creates beans in correct order
        // - Resolves dependencies automatically
        // - Stores as singletons
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println("\n✅ Spring Container initialized!");
        System.out.println("   All beans created and wired automatically! 🎉");

        // ============================================================
        // STEP 2: Explore Container
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 2: Exploring Spring Container");
        System.out.println("=".repeat(70));

        // Get all bean names
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("\n📦 Total beans in Spring Container: " + beanNames.length);

        System.out.println("\n📋 Our Application Beans:");
        for (String beanName : beanNames) {
            // Filter to show only our beans (skip Spring internal beans)
            if (beanName.contains("database") || beanName.contains("repository") ||
                    beanName.contains("service") || beanName.equals("appConfig")) {
                Object bean = context.getBean(beanName);
                System.out.println("   • " + beanName + " → " + bean.getClass().getSimpleName());
            }
        }

        // ============================================================
        // STEP 3: Get Beans from Container
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 3: Retrieving Beans from Spring Container");
        System.out.println("=".repeat(70));

        // Method 1: Get by type (recommended)
        System.out.println("\n➤ Method 1: Get bean by type");
        StudentService service = context.getBean(StudentService.class);
        System.out.println("✅ Got: " + service.getClass().getSimpleName());

        // Method 2: Get by name
        System.out.println("\n➤ Method 2: Get bean by name");
        StudentService service2 = (StudentService) context.getBean("studentService");
        System.out.println("✅ Got: " + service2.getClass().getSimpleName());

        // Method 3: Get by name and type
        System.out.println("\n➤ Method 3: Get bean by name and type");
        StudentService service3 = context.getBean("studentService", StudentService.class);
        System.out.println("✅ Got: " + service3.getClass().getSimpleName());

        // ============================================================
        // STEP 4: Singleton Verification
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 4: Singleton Verification");
        System.out.println("=".repeat(70));

        System.out.println("\nAre all three references same object?");
        System.out.println("service == service2: " + (service == service2));
        System.out.println("service == service3: " + (service == service3));
        System.out.println("service2 == service3: " + (service2 == service3));
        System.out.println("\n✅ Yes! Spring returns same singleton instance!");

        // ============================================================
        // STEP 5: Use the Service
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 5: Using Spring-Managed Beans");
        System.out.println("=".repeat(70));

        // Add courses
        System.out.println("\n➤ Adding courses...");
        service.addCourse(new Course(1, "CS101", "Programming Fundamentals", 3, "Dr. Rahman"));
        service.addCourse(new Course(2, "CS201", "Data Structures", 4, "Dr. Karim"));
        service.addCourse(new Course(3, "CS301", "Algorithms", 4, "Dr. Fatema"));

        // Add students
        System.out.println("\n➤ Adding students...");
        service.addStudent(new Student(1, "Rahim Khan", "Not Enrolled", 22));
        service.addStudent(new Student(2, "Karim Ahmed", "Not Enrolled", 23));
        service.addStudent(new Student(3, "Fatema Begum", "Not Enrolled", 21));

        // Enroll students
        System.out.println("\n➤ Enrolling students...");
        service.enrollStudentInCourse(1, "CS101");
        service.enrollStudentInCourse(2, "CS201");
        service.enrollStudentInCourse(3, "CS301");

        // Display
        service.displayAllStudents();

        // Statistics
        System.out.println("\n📊 Statistics:");
        System.out.println("   Total Students: " + service.getTotalStudents());
        System.out.println("   Total Courses: " + service.getTotalCourses());

        // ============================================================
        // STEP 6: Access Other Beans
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 6: Accessing Other Beans from Container");
        System.out.println("=".repeat(70));

        // Get repository directly from container
        System.out.println("\n➤ Getting StudentRepository bean...");
        StudentRepository repo = context.getBean(StudentRepository.class);
        System.out.println("✅ Got: " + repo.getClass().getSimpleName());
        System.out.println("   Total students in repo: " + repo.count());

        // Get database service
        System.out.println("\n➤ Getting DatabaseService bean...");
        DatabaseService db = context.getBean(DatabaseService.class);
        System.out.println("✅ Got: " + db.getClass().getSimpleName());
        System.out.println("   Connection URL: " + db.getConnectionUrl());
        System.out.println("   Is Connected: " + db.isConnected());

        // ============================================================
        // STEP 7: Comparison - Our BeanFactory vs Spring
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("STEP 7: Our BeanFactory vs Spring Container");
        System.out.println("=".repeat(70));
        printComparison();

        // ============================================================
        // CLEANUP
        // ============================================================
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Cleanup");
        System.out.println("=".repeat(70));

        // Close context (good practice)
        ((AnnotationConfigApplicationContext) context).close();
        System.out.println("✅ Spring Container closed");

        System.out.println("\n╔" + "═".repeat(70) + "╗");
        System.out.println("║" + " ".repeat(27) + "Application Ended" + " ".repeat(26) + "║");
        System.out.println("╚" + "═".repeat(70) + "╝");
    }

    private static void printComparison() {
        System.out.println("\n🏭 OUR BEANFACTORY (Branch 06):");
        System.out.println("───────────────────────────────────────");
        System.out.println("❌ We manually registered bean definitions");
        System.out.println("❌ We manually specified dependencies");
        System.out.println("✅ Factory created beans automatically");
        System.out.println("✅ Factory resolved dependencies");
        System.out.println("✅ Singleton pattern implemented");

        System.out.println("\n🌱 SPRING CONTAINER:");
        System.out.println("───────────────────────────────────────");
        System.out.println("✅ Scans @Configuration classes automatically");
        System.out.println("✅ Finds @Bean methods automatically");
        System.out.println("✅ Creates beans automatically");
        System.out.println("✅ Resolves dependencies automatically");
        System.out.println("✅ Singleton by default");
        System.out.println("✅ Manages complete bean lifecycle");
        System.out.println("✅ AOP, Transactions, Security support");
        System.out.println("✅ Much more powerful!");

        System.out.println("\n💡 KEY DIFFERENCES:");
        System.out.println("───────────────────────────────────────");
        System.out.println("Our BeanFactory:");
        System.out.println("   BeanDefinition def = new BeanDefinition(...);");
        System.out.println("   factory.registerBeanDefinition(def);");
        System.out.println("   Object bean = factory.getBean(\"name\");");

        System.out.println("\nSpring Container:");
        System.out.println("   @Configuration + @Bean (that's it!)");
        System.out.println("   ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);");
        System.out.println("   Object bean = ctx.getBean(Type.class);");

        System.out.println("\n🎯 WHAT WE LEARNED:");
        System.out.println("───────────────────────────────────────");
        System.out.println("✅ Built our own IoC container → Understood the concept");
        System.out.println("✅ Implemented dependency injection → Understood the mechanism");
        System.out.println("✅ Now using Spring → Understood what Spring does!");
        System.out.println("✅ We built a simplified Spring ourselves!");
    }


}

