package com.devCraftLab.studentapp.repository.impl;
import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.repository.CourseRepository;
import com.devCraftLab.studentapp.database.DatabaseService;
import java.util.ArrayList;
import java.util.List;


/**
 * CourseRepositoryImpl - Now depends on DatabaseService
 *
 * 🔴 DEPENDENCY ADDED: DatabaseService
 */
public class CourseRepositoryImpl implements CourseRepository {

    private List<Course> database;
    private DatabaseService databaseService; // 🔴 NEW DEPENDENCY

    // 🔴 Constructor now needs DatabaseService
    public CourseRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.database = new ArrayList<>();
        System.out.println("📦 CourseRepository initialized with DatabaseService");
    }

    @Override
    public Course save(Course course) {
        databaseService.executeQuery("INSERT INTO courses VALUES (...)");

        if (existsById(course.getId())) {
            System.out.println("⚠️  Course with ID " + course.getId() + " already exists!");
            return null;
        }

        database.add(course);
        System.out.println("💾 Course saved: " + course.getCode());
        return course;
    }

    @Override
    public List<Course> findAll() {
        databaseService.executeQuery("SELECT * FROM courses");
        return new ArrayList<>(database);
    }

    @Override
    public Course findById(int id) {
        databaseService.executeQuery("SELECT * FROM courses WHERE id = " + id);
        for (Course course : database) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    @Override
    public Course findByCode(String code) {
        databaseService.executeQuery("SELECT * FROM courses WHERE code = '" + code + "'");
        for (Course course : database) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public Course update(Course course) {
        Course existing = findById(course.getId());

        if (existing == null) {
            System.out.println("⚠️  Course not found with ID: " + course.getId());
            return null;
        }

        databaseService.executeQuery("UPDATE courses SET ...");

        existing.setCode(course.getCode());
        existing.setName(course.getName());
        existing.setCredits(course.getCredits());
        existing.setInstructor(course.getInstructor());

        System.out.println("✏️  Course updated: " + course.getCode());
        return existing;
    }

    @Override
    public boolean deleteById(int id) {
        Course course = findById(id);

        if (course == null) {
            System.out.println("⚠️  Course not found with ID: " + id);
            return false;
        }

        databaseService.executeQuery("DELETE FROM courses WHERE id = " + id);

        database.remove(course);
        System.out.println("🗑️  Course deleted: " + course.getCode());
        return true;
    }

    @Override
    public int count() {
        return database.size();
    }

    @Override
    public boolean existsById(int id) {
        return findById(id) != null;
    }
}