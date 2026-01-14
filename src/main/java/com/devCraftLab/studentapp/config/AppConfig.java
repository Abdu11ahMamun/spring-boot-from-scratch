package com.devCraftLab.studentapp.config;

import com.devCraftLab.studentapp.database.DatabaseService;
import com.devCraftLab.studentapp.repository.StudentRepository;
import com.devCraftLab.studentapp.repository.CourseRepository;
import com.devCraftLab.studentapp.repository.impl.StudentRepositoryImpl;
import com.devCraftLab.studentapp.repository.impl.CourseRepositoryImpl;
import com.devCraftLab.studentapp.service.StudentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig - Spring Configuration Class
 *
 * এটা Spring কে বলে কোন কোন beans তৈরি করতে হবে
 *
 * @Configuration = এটা একটা configuration class
 * @Bean = এই method একটা bean return করে
 */
@Configuration
public class AppConfig {

    /**
     * DatabaseService Bean
     *
     * Spring এই method call করে bean তৈরি করবে
     */
    @Bean
    public DatabaseService databaseService() {
        System.out.println("🔧 Spring creating DatabaseService bean...");
        DatabaseService service = new DatabaseService("jdbc:mysql://localhost:3306/studentdb");
        service.connect();
        return service;
    }

    /**
     * StudentRepository Bean
     *
     * Method parameter এ DatabaseService লিখলে
     * Spring automatically inject করে দেয়!
     */
    @Bean
    public StudentRepository studentRepository(DatabaseService databaseService) {
        System.out.println("🔧 Spring creating StudentRepository bean...");
        System.out.println("   └─ Injecting DatabaseService dependency");
        return new StudentRepositoryImpl(databaseService);
    }

    /**
     * CourseRepository Bean
     */
    @Bean
    public CourseRepository courseRepository(DatabaseService databaseService) {
        System.out.println("🔧 Spring creating CourseRepository bean...");
        System.out.println("   └─ Injecting DatabaseService dependency");
        return new CourseRepositoryImpl(databaseService);
    }

    /**
     * StudentService Bean
     *
     * Multiple dependencies!
     * Spring automatically resolves both!
     */
    @Bean
    public StudentService studentService(
            StudentRepository studentRepository,
            CourseRepository courseRepository) {
        System.out.println("🔧 Spring creating StudentService bean...");
        System.out.println("   └─ Injecting StudentRepository dependency");
        System.out.println("   └─ Injecting CourseRepository dependency");
        return new StudentService(studentRepository, courseRepository);
    }
}
