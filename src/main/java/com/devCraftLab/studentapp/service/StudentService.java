package com.devCraftLab.studentapp.service;

import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.repository.StudentRepository;
import com.devCraftLab.studentapp.repository.impl.StudentRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    // Dependency: Repository
    private StudentRepository repository;

    // Constructor - manually creating repository
    public StudentService() {
        this.repository = new StudentRepositoryImpl();
        System.out.println("✅ StudentService initialized!");
    }

    public boolean addStudent(Student student) {
        if (student.getAge()<18){
            System.out.println("❌ Student must be at least 18 years old.");
            return false;
        }
        if (student.getName() == null || student.getName().isEmpty()) {
            System.out.println("❌ Student name cannot be empty.");
            return false;
        }
        Student savedStudent = repository.save(student);
        return savedStudent != null;

    }
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(int id) {
        return repository.findById(id);
    }

    public boolean updateStudentCourse(int id, String newCourse) {
        Student student = getStudentById(id);
       if (student == null) {
           System.out.println("❌ Student not found with ID: " + id);
           return false;
       }
         student.setCourse(newCourse);
        Student updatedStudent = repository.update(student);
        return updatedStudent != null;
    }
    public boolean updateStudent(Student student) {
        // Business validation
        if (student.getAge() < 18) {
            System.out.println("❌ Student must be at least 18 years old!");
            return false;
        }

        Student updated = repository.update(student);
        return updated != null;
    }
    public boolean deleteStudent(int id) {
        return repository.deleteById(id);
    }
    public int getTotalStudents() {
        return repository.count();
    }
    public boolean studentExists(int id) {
        return repository.existsById(id);
    }

    public void displayAllStudents() {
        List<Student> students = repository.findAll();

        if (students.isEmpty()) {
            System.out.println("📭 No students found!");
            return;
        }

        System.out.println("\n📚 All Students:");
        System.out.println("═══════════════════════════════════════");
        for (Student student : students) {
            System.out.println(student);
            System.out.println("───────────────────────────────────────");
        }
    }

}
