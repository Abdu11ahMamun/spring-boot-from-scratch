package com.devCraftLab.studentapp.service;

import com.devCraftLab.studentapp.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students;
    public StudentService() {
        this.students = new ArrayList<>();
        System.out.println("StudentService initialized.");
    }
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Added student: " + student.getName());
    }
    public List<Student> getAllStudents() {
        return students;
    }
    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }
    public boolean updateStudentCourse(int id, String newCourse) {
        Student student = getStudentById(id);
        if (student != null) {
            student.setCourse(newCourse);
            System.out.println("Updated student ID " + id + " to course " + newCourse);
            return true;
        }
        return false;
    }
    public boolean deleteStudent(int id) {
        Student student = getStudentById(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Deleted student ID " + id);
            return true;
        }
        return false;
    }
    public int getStudentCount() {
        return students.size();
    }
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.println("All Students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }

}
