package com.devCraftLab.studentapp.repository.impl;
import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.repository.CourseRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * CourseRepositoryImpl - Implementation of CourseRepository
 */
public class CourseRepositoryImpl implements CourseRepository {

    private List<Course> database;

    public CourseRepositoryImpl() {
        this.database = new ArrayList<>();
        System.out.println("📦 CourseRepository initialized");
    }

    @Override
    public Course save(Course course) {
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
        return new ArrayList<>(database);
    }

    @Override
    public Course findById(int id) {
        for (Course course : database) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    @Override
    public Course findByCode(String code) {
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