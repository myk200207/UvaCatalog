package edu.virginia.cs.hw4;

import java.util.*;

public class CourseCatalog {
    Set<Course> courseList;

    public CourseCatalog(Set<Course> courseList) {
        this.courseList = courseList;
    }

    public CourseCatalog() {
        this(new HashSet<>());
    }

    public void addCourse(Course course) {
        courseList.add(course);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courseList);
    }

    public List<Course> getCoursesEnrolledIn(Student student) {
        return courseList.stream()
                .filter(course -> course.isStudentEnrolled(student))
                .toList();
    }

    public List<Course> getCoursesWaitListedIn(Student student) {
        return courseList.stream()
                .filter(course -> course.isStudentWaitListed(student))
                .toList();
    }

    public Course getCourseByRegistrationNumber(int registrationNumber) {
        for (Course course : courseList) {
            if (course.getCourseRegistrationNumber() == registrationNumber) {
                return course;
            }
        }
        throw new IllegalArgumentException("No course with registration number: " + registrationNumber);
    }

    public List<Course> getCoursesByDepartment(String department) {
        return courseList.stream()
                .filter(course -> course.getDepartment().equals(department))
                .sorted(Comparator.comparing(Course::getCatalogNumber))
                .toList();
    }

    public List<Course> getCoursesByDepartmentAndNumber(String department, int number) {
        return courseList.stream()
                .filter(course -> course.getDepartment().equals(department))
                .filter(course -> course.getCatalogNumber() == number)
                .sorted(Comparator.comparing(Course::getCatalogNumber))
                .toList();
    }

    public List<Course> getCoursesByInstructorName(String instructorName) {
        return courseList.stream()
                .filter(course -> course.getInstructorName().equals(instructorName))
                .toList();
    }
}
