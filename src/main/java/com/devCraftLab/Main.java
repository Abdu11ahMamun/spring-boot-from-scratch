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
import com.devCraftLab.studentapp.container.BeanFactory;;
import com.devCraftLab.studentapp.container.BeanDefinition;

public class Main {
    public static void main(String[] args) {

        System.out.println("╔" + "═".repeat(70) + "╗");
        System.out.println("║" + " ".repeat(15) + "Student Management System v6.1" + " ".repeat(24) + "║");
        System.out.println("║" + " ".repeat(12) + "(BeanFactory with Auto-Wiring!)" + " ".repeat(26) + "║");
        System.out.println("╚" + "═".repeat(70) + "╝\n");

        // Demo 1: Simple Container (previous)
        System.out.println("\n" + "=".repeat(70));
        System.out.println("PART 1: SimpleContainer Demo (Manual Registration)");
        System.out.println("=".repeat(70));
        demoSimpleContainer();

        // Demo 2: BeanFactory (new - automatic!)
        System.out.println("\n\n" + "=".repeat(70));
        System.out.println("PART 2: BeanFactory Demo (Automatic Dependency Resolution!)");
        System.out.println("=".repeat(70));
        demoBeanFactory();

        System.out.println("\n╔" + "═".repeat(70) + "╗");
        System.out.println("║" + " ".repeat(27) + "Application Ended" + " ".repeat(26) + "║");
        System.out.println("╚" + "═".repeat(70) + "╝");
    }

    /**
     * Demo: Simple Container (old way)
     */
    private static void demoSimpleContainer() {
        SimpleDIContainer container = new SimpleDIContainer();

        // Manual creation and registration
        DatabaseService db = new DatabaseService("jdbc:mysql://localhost:3306/db");
        db.connect();
        container.registerBean("databaseService", db);

        StudentRepository studentRepo = new StudentRepositoryImpl(db);
        container.registerBean("studentRepository", studentRepo);

        CourseRepository courseRepo = new CourseRepositoryImpl(db);
        container.registerBean("courseRepository", courseRepo);

        StudentService service = new StudentService(studentRepo, courseRepo);
        container.registerBean("studentService", service);

        container.listAllBeans();

        // Use
        StudentService s = container.getBean("studentService", StudentService.class);
        s.addStudent(new Student(1, "Rahim", "CS", 22));

        System.out.println("\n💡 Note: We manually created all objects and registered them.");

        db.disconnect();
    }

    /**
     * Demo: BeanFactory (new way - automatic!)
     */
    private static void demoBeanFactory() {
        BeanFactory factory = new BeanFactory();

        System.out.println("\n📝 STEP 1: Register Bean Definitions (Metadata Only)");
        System.out.println("─".repeat(70));

        // Define DatabaseService (no dependencies)
        BeanDefinition dbDef = new BeanDefinition(
                "databaseService",
                DatabaseService.class,
                new Class<?>[] { String.class },  // Constructor param types
                new String[] { }  // No bean dependencies, we'll handle this differently
        );

        // Actually, let's use a simpler approach for DatabaseService
        // We'll manually create it since it needs a String parameter
        DatabaseService db = new DatabaseService("jdbc:mysql://localhost:3306/db");
        db.connect();
        factory.singletonBeans.put("databaseService", db);  // Pre-register
        System.out.println("✅ Pre-registered: databaseService (has String param)");

        // Define StudentRepository (depends on DatabaseService)
        BeanDefinition studentRepoDef = new BeanDefinition(
                "studentRepository",
                StudentRepositoryImpl.class,
                new Class<?>[] { DatabaseService.class },  // Constructor needs DatabaseService
                new String[] { "databaseService" }  // Bean name to inject
        );
        factory.registerBeanDefinition(studentRepoDef);

        // Define CourseRepository (depends on DatabaseService)
        BeanDefinition courseRepoDef = new BeanDefinition(
                "courseRepository",
                CourseRepositoryImpl.class,
                new Class<?>[] { DatabaseService.class },
                new String[] { "databaseService" }
        );
        factory.registerBeanDefinition(courseRepoDef);

        // Define StudentService (depends on both repositories)
        BeanDefinition serviceDef = new BeanDefinition(
                "studentService",
                StudentService.class,
                new Class<?>[] { StudentRepository.class, CourseRepository.class },
                new String[] { "studentRepository", "courseRepository" }
        );
        factory.registerBeanDefinition(serviceDef);

        factory.listBeanDefinitions();

        System.out.println("\n🔨 STEP 2: Get Bean (Factory Creates Automatically!)");
        System.out.println("─".repeat(70));
        System.out.println("Requesting: studentService");
        System.out.println();

        // This is the MAGIC! Just ask for service, factory resolves everything!
        StudentService service = factory.getBean("studentService", StudentService.class);

        System.out.println("\n📊 STEP 3: Factory Status");
        System.out.println("─".repeat(70));
        factory.listSingletons();

        System.out.println("\n🎯 STEP 4: Use the Bean");
        System.out.println("─".repeat(70));
        if (service != null) {
            service.addCourse(new Course(1, "CS101", "Programming", 3, "Dr. Khan"));
            service.addStudent(new Student(1, "Karim", "Not Enrolled", 23));
            service.enrollStudentInCourse(1, "CS101");

            System.out.println("\n✅ Service working perfectly!");
        }

        System.out.println("\n🔄 STEP 5: Singleton Test");
        System.out.println("─".repeat(70));
        StudentService service2 = factory.getBean("studentService", StudentService.class);
        System.out.println("service == service2? " + (service == service2));
        System.out.println("Both are same instance! (Singleton pattern)");

        System.out.println("\n💡 Key Difference:");
        System.out.println("SimpleContainer: We created all objects manually");
        System.out.println("BeanFactory: Factory created objects automatically!");
        System.out.println("             Factory resolved dependencies automatically!");
        System.out.println("             This is what Spring does!");

        db.disconnect();
    }


}