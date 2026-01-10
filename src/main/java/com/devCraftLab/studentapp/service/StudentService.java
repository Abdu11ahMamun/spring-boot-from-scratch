package com.devCraftLab.studentapp.service;

import com.devCraftLab.studentapp.model.Course;
import com.devCraftLab.studentapp.model.Student;
import com.devCraftLab.studentapp.repository.CourseRepository;
import com.devCraftLab.studentapp.repository.StudentRepository;
import com.devCraftLab.studentapp.repository.impl.CourseRepositoryImpl;
import com.devCraftLab.studentapp.repository.impl.StudentRepositoryImpl;

import java.util.List;

public class StudentService {
    // Dependency: Repository
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;

    // Constructor - manually creating repository
    public StudentService() {
        this.studentRepository = new StudentRepositoryImpl();
        this.courseRepository = new CourseRepositoryImpl();
        System.out.println("✅ StudentService initialized with 2 repositories");
    }

    // ========== Student Operations ==========

    public boolean addStudent(Student student) {
        if (student.getAge() < 18) {
            System.out.println("❌ Student must be at least 18 years old!");
            return false;
        }

        if (student.getName() == null || student.getName().trim().isEmpty()) {
            System.out.println("❌ Student name cannot be empty!");
            return false;
        }

        Student saved = studentRepository.save(student);
        return saved != null;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int id) {
        return studentRepository.findById(id);
    }

    public boolean updateStudent(Student student) {
        if (student.getAge() < 18) {
            System.out.println("❌ Student must be at least 18 years old!");
            return false;
        }

        Student updated = studentRepository.update(student);
        return updated != null;
    }

    public boolean deleteStudent(int id) {
        return studentRepository.deleteById(id);
    }

    public int getTotalStudents() {
        return studentRepository.count();
    }

    // ========== Course Operations ==========
    public boolean addCourse(Course course) {
        if (course.getCredits() <= 0) {
            System.out.println("❌ Course must have at least 1 credit!");
            return false;
        }

        Course saved = courseRepository.save(course);
        return saved != null;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id);
    }

    public Course getCourseByCode(String code) {
        return courseRepository.findByCode(code);
    }

    public int getTotalCourses() {
        return courseRepository.count();
    }
    // ========== Business Logic: Enrollment ==========

    /**
     * Enroll student in a course
     * Uses BOTH repositories - shows dependency complexity
     */
    public boolean enrollStudentInCourse(int studentId, String courseCode) {
        // Check if student exists
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return false;
        }

        // Check if course exists
        Course course = courseRepository.findByCode(courseCode);
        if (course == null) {
            System.out.println("❌ Course not found!");
            return false;
        }

        // Business logic: Check if student already enrolled in this course
        if (student.getCourse().equalsIgnoreCase(course.getName())) {
            System.out.println("⚠️  Student already enrolled in this course!");
            return false;
        }

        // Update student's course
        student.setCourse(course.getName());
        studentRepository.update(student);

        System.out.println("✅ " + student.getName() + " enrolled in " + course.getCode());
        return true;
    }

    // ========== Display Methods ==========

    public void displayAllStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            System.out.println("📭 No students found!");
            return;
        }

        System.out.println("\n👥 All Students:");
        System.out.println("═".repeat(60));
        for (Student student : students) {
            System.out.println(student);
            System.out.println("─".repeat(60));
        }
    }

    public void displayAllCourses() {
        List<Course> courses = courseRepository.findAll();

        if (courses.isEmpty()) {
            System.out.println("📭 No courses found!");
            return;
        }

        System.out.println("\n📚 All Courses:");
        System.out.println("═".repeat(60));
        for (Course course : courses) {
            System.out.println(course);
            System.out.println("─".repeat(60));
        }
    }




}
