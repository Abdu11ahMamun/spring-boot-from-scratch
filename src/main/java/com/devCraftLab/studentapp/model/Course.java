package com.devCraftLab.studentapp.model;
/**
 * Course Model
 *
 * Represents a course that students can enroll in
 */
public class Course {

    private int id;
    private String code;        // e.g., "CS101"
    private String name;        // e.g., "Introduction to Programming"
    private int credits;
    private String instructor;

    // Constructors
    public Course() {
    }

    public Course(int id, String code, String name, int credits, String instructor) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.instructor = instructor;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}
