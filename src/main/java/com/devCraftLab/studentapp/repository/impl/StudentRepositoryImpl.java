package com.devCraftLab.studentapp.repository.impl;

import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private List<Student> database;

    public StudentRepositoryImpl() {
        this.database = new ArrayList<>();
        System.out.println("Repository initialized with database.");
    }
    @Override
    public Student save(Student student) {
        if (existsById(student.getId())) {
            System.out.println("Student: " + student.getId()+" already exists.");
            return null;
        }
        database.add(student);
        System.out.println("Saved student: " + student.getName());
        return student;
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(database); //why doing this?
    }
    @Override
    public Student findById(int id) {
        for (Student student : database) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    @Override
    public Student update(Student student) {
        Student existingStudent = findById(student.getId());
       if(existingStudent == null){
           System.out.println("Student: " + student.getId()+" not found for update.");
           return null;
       }
       existingStudent.setName(student.getName());
       existingStudent.setCourse(student.getCourse());
       existingStudent.setAge(student.getAge());
       System.out.println("Updated student: " + student.getId());
       return existingStudent;

    }
    @Override
    public boolean deleteById(int id) {
        Student student = findById(id);
        if (student == null) {
            System.out.println("⚠️  Student not found with ID: " + id);
            return false;
        }
            database.remove(student);
            System.out.println("Deleted student ID " + id);
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
