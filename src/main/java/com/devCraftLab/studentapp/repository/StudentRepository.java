package com.devCraftLab.studentapp.repository;

import com.devCraftLab.studentapp.model.Student;

import java.util.List;

public interface StudentRepository {
    Student save (Student student);
    List<Student> findAll();
    Student findById(int id);
    Student update(Student student);
    boolean deleteById(int id);
    int count();
    boolean existsById(int id);
}
