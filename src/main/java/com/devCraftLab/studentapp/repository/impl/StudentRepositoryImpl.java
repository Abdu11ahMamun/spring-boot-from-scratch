package com.devCraftLab.studentapp.repository.impl;

import com.devCraftLab.studentapp.database.DatabaseService;
import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private List<Student> database;
    private DatabaseService databaseService; // 🔴 NEW DEPENDENCY

    // 🔴 Constructor now needs DatabaseService
    public StudentRepositoryImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.database = new ArrayList<>();
        System.out.println("📦 StudentRepository initialized with DatabaseService");
    }
    @Override
    public Student save(Student student) {
        // Use DatabaseService
        databaseService.executeQuery("INSERT INTO students VALUES (...)");

        if (existsById(student.getId())) {
            System.out.println("⚠️  Student with ID " + student.getId() + " already exists!");
            return null;
        }

        database.add(student);
        System.out.println("💾 Saved to database: " + student.getName());
        return student;
    }

    @Override
    public List<Student> findAll() {
        databaseService.executeQuery("SELECT * FROM students");
        return new ArrayList<>(database);
    }

    @Override
    public Student findById(int id) {
        databaseService.executeQuery("SELECT * FROM students WHERE id = " + id);
        for (Student student : database) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    @Override
    public Student update(Student student) {
        Student existing = findById(student.getId());

        if (existing == null) {
            System.out.println("⚠️  Student not found with ID: " + student.getId());
            return null;
        }

        databaseService.executeQuery("UPDATE students SET ...");

        existing.setName(student.getName());
        existing.setCourse(student.getCourse());
        existing.setAge(student.getAge());

        System.out.println("✏️  Updated in database: " + student.getName());
        return existing;
    }
    @Override
    public boolean deleteById(int id) {
        Student student = findById(id);

        if (student == null) {
            System.out.println("⚠️  Student not found with ID: " + id);
            return false;
        }

        databaseService.executeQuery("DELETE FROM students WHERE id = " + id);

        database.remove(student);
        System.out.println("🗑️  Deleted from database: " + student.getName());
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
