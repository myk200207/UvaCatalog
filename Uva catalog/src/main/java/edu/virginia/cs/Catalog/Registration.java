package edu.virginia.cs.hw4;

import java.util.List;

public interface Registration {
    /**
     * Gets the course catalog
     */
    public CourseCatalog getCourseCatalog();

    /**
     * Set the course catalog
     */
    public void setCourseCatalog(CourseCatalog courseCatalog);

    /**
     * Returns true if the course enrollment is full
     */
    public boolean isEnrollmentFull(Course course);

    /**
     * Returns true if the course wait list is full
     */
    public boolean isWaitListFull(Course course);

    /**
     * Returns whether the course is OPEN, WAIT_LISTED, or CLOSED
     */
    public Course.EnrollmentStatus getEnrollmentStatus(Course course);

    /**
     * returns true if the two courses have **any** overlapping time on any Days of the Week
     */
    public boolean areCoursesConflicted(Course first, Course second);

    /**
     * returns true if the student is already enrolled in any
     */
    public boolean hasConflictWithStudentSchedule(Course course, Student student);

    /**
     * Returns true if the student meets the entire List of prerequisites
     */
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites);

    /**
     * Attempts to register the student for the course and returns the Registration outcome:
     *     COURSE_CLOSED - The course is closed to registration, so no students can be added
     *     COURSE_FULL - Both the enrollment and wait list for the course are full
     *     SCHEDULE_CONFLICT - Student cannot register because of a schedule conflict with one of their other courses
     *     PREREQUISITE_NOT_MET - Student has not met the Prerequisite
     *     ENROLLED - Student was enrolled in the course
     *     WAIT_LISTED - Student was added to the course Wait List
     *
     *   This function can also change the Courses EnrollmentStatus
     *   - If adding the student causes the course enrollment to become full, set class to WAIT_LIST
     *   - If adding the student causes the course wait list to become full, set class to CLOSED
     */
    public RegistrationResult registerStudentForCourse(Student student, Course course);

    /**
     * Attempts to remove the student from the course enrollment OR the wait list
     *
     * If the student is enrolled and the course is in WAIT_LIST mode, then first student on the wait list
     * should be moved into the enrolled list. If the wait list is empty, then the state course enrollmentStatus
     * should be changed from WAIT_LIST to OPEN
     *
     * If the course enrollmentStatus is CLOSED, it should be changed to WAIT_LIST after the student is removed
     *
     * @throws IllegalArgumentException if Student is not in the course - that is neither enrolled nor wait listed)
     */
    public void dropCourse(Student student, Course course);
}
