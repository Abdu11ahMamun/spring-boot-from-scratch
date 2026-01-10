package com.devCraftLab.studentapp.repository;

import com.devCraftLab.studentapp.model.Course;

import java.util.List;

/**
 * CourseRepository Interface
 *
 * Data access operations for Course entity
 */
public interface CourseRepository {

    /**
     * Save a course
     */
    Course save(Course course);

    /**
     * Find all courses
     */
    List<Course> findAll();

    /**
     * Find course by ID
     */
    Course findById(int id);

    /**
     * Find course by code
     */
    Course findByCode(String code);

    /**
     * Update a course
     */
    Course update(Course course);

    /**
     * Delete course by ID
     */
    boolean deleteById(int id);

    /**
     * Count total courses
     */
    int count();

    /**
     * Check if course exists
     */
    boolean existsById(int id);
}
