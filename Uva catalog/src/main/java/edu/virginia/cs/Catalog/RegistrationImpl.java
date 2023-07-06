package edu.virginia.cs.hw4;

import java.sql.Time;
import java.util.List;
import java.util.Date;

public class RegistrationImpl implements Registration {
    //TODO: Implement class
    private CourseCatalog catalog;
    private Time FirstCourseStartTime;
    private Time FirstCourseEndTime;
    private Time SecondCourseStartTime;
    private Time SecondCourseEndTime;
    private int endHour;
    public RegistrationImpl(CourseCatalog catalog){
        this.catalog = catalog;
    }
    @Override
    public CourseCatalog getCourseCatalog() {

        return this.catalog;
    }

    @Override
    public void setCourseCatalog(CourseCatalog courseCatalog) {
        this.catalog = courseCatalog;

    }
    @Override
    public boolean isEnrollmentFull(Course course) {
        return course.getCurrentEnrollmentSize() >= course.getEnrollmentCap();
    }

    @Override
    public boolean isWaitListFull(Course course) {
        //return false;
        return (course.getCurrentWaitListSize() >= course.getWaitListCap());
    }

    @Override
    public Course.EnrollmentStatus getEnrollmentStatus(Course course) {
        return course.getEnrollmentStatus();
    }

    @Override
    public boolean areCoursesConflicted(Course first, Course second) {
        FirstCourseStartTime = new Time(first.getMeetingStartTimeHour(),first.getMeetingStartTimeMinute(),0);
        FirstCourseEndTime = new Time(first.getMeetingStartTimeHour(),first.getMeetingStartTimeMinute()+first.getMeetingDurationMinutes(),0);

        SecondCourseStartTime = new Time(second.getMeetingStartTimeHour(),second.getMeetingStartTimeMinute(),0);
        SecondCourseEndTime = new Time(second.getMeetingStartTimeHour(),second.getMeetingStartTimeMinute()+second.getMeetingDurationMinutes(),0);

        if(isSameMeetingDays(first, second)) {

            if(   ( (FirstCourseStartTime.before(SecondCourseStartTime) || FirstCourseStartTime.equals(SecondCourseStartTime)    )&& (SecondCourseStartTime.before(FirstCourseEndTime)) ) || ((SecondCourseStartTime.before(FirstCourseStartTime)  || SecondCourseStartTime.equals(FirstCourseStartTime))&& (FirstCourseStartTime.before(SecondCourseEndTime)) ) ){
                return true;
            }
        }

        return false;
    }
    public boolean isSameMeetingDays(Course first, Course second){
        return first.getMeetingDays().equals(second.getMeetingDays());
    }

    @Override
    public boolean hasConflictWithStudentSchedule(Course course, Student student) {
        //System.out.println("first");
        for(Course courses: catalog.getCoursesEnrolledIn(student)){
          //  System.out.println("here");
                if (areCoursesConflicted(courses, course)) {
                    return true;
                }
        }

        return false;
    }
    @Override
    public boolean hasStudentMeetsPrerequisites(Student student, List<Prerequisite> prerequisites) {
        for (Prerequisite prereq: prerequisites){
            if(!student.meetsPrerequisite(prereq)){
                System.out.println("false");
                return false;
            }
        }

        System.out.println("true");
        return true;
    }
    @Override
    public RegistrationResult registerStudentForCourse(Student student, Course course) {
        /**
         * Attempts to register the student for the course and returns the Registration outcome:
         *
         *     COURSE_CLOSED - The course is closed to registration, so no students can be added
         */
        if (getEnrollmentStatus(course) == Course.EnrollmentStatus.CLOSED) {
            return RegistrationResult.COURSE_CLOSED;
        }
//         *     COURSE_FULL - Both the enrollment and wait list for the course are full
        if(isWaitListFull(course) && isEnrollmentFull(course)){
            return RegistrationResult.COURSE_FULL;
        }
//         *     SCHEDULE_CONFLICT - Student cannot register because of a schedule conflict with one of their other courses
        if (hasConflictWithStudentSchedule(course, student)) {
            return RegistrationResult.SCHEDULE_CONFLICT;
        }
//         *     PREREQUISITE_NOT_MET - Student has not met the Prerequisite
        if (!hasStudentMeetsPrerequisites(student, course.getPrerequisites())) {
            return RegistrationResult.PREREQUISITE_NOT_MET;
        }
        //         *     WAIT_LISTED - Student was added to the course Wait List
        if(isEnrollmentFull(course) && !isWaitListFull(course)){
            course.addStudentToWaitList(student);
            //If adding the student causes the course wait list to become full, set class to CLOSED
            if(isWaitListFull(course)){
                course.setEnrollmentStatus(Course.EnrollmentStatus.CLOSED);
            }
            return RegistrationResult.WAIT_LISTED;
        }
        //         *     ENROLLED - Student was enrolled in the course
        if(!isEnrollmentFull(course)){
            course.addStudentToEnrolled(student);
            //If adding the student causes the course enrollment to become full, set class to WAIT_LIST
            if(isEnrollmentFull(course)){
                course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
            }
            return RegistrationResult.ENROLLED;
        }
        return null;
    }

    @Override
    public void dropCourse(Student student, Course course) {
//        /**
//         * Attempts to remove the student from the course enrollment OR the wait list
//         *
//         * If the student is enrolled and the course is in WAIT_LIST mode, then first student on the wait list
//         * should be moved into the enrolled list. If the wait list is empty, then the state course enrollmentStatus
//         * should be changed from WAIT_LIST to OPEN
//         *
//         * If the course enrollmentStatus is CLOSED, it should be changed to WAIT_LIST after the student is removed
//         *
//         * @throws IllegalArgumentException if Student is not in the course - that is neither enrolled nor wait listed)
//         */
        if(course.isStudentEnrolled(student) || course.isStudentWaitListed(student)){
            if(course.isStudentEnrolled(student)){
                course.removeStudentFromEnrolled(student);
                if(getEnrollmentStatus(course)== Course.EnrollmentStatus.CLOSED){
                    course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
                    Student firstWaitListStudent = course.getFirstStudentOnWaitList();
                    registerStudentForCourse(firstWaitListStudent, course);
                    course.removeStudentFromWaitList(firstWaitListStudent);
                }
                else if(getEnrollmentStatus(course)== Course.EnrollmentStatus.WAIT_LIST){
                    if(course.getCurrentWaitListSize()>0){
//                        System.out.println("here");
                        Student firstWaitListStudent = course.getFirstStudentOnWaitList();
                        registerStudentForCourse(firstWaitListStudent, course);
                        course.removeStudentFromWaitList(firstWaitListStudent);
                    }
                    else if(course.getCurrentWaitListSize()<=0){
                        course.setEnrollmentStatus(Course.EnrollmentStatus.OPEN);
                    }
                }

            }
            else if(course.isStudentWaitListed(student)){
                course.removeStudentFromWaitList(student);
                if(getEnrollmentStatus(course)== Course.EnrollmentStatus.CLOSED){
                    course.setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
                }
            }
        }
        else{
            throw new IllegalArgumentException("Student is neither waitlisted nor enrolled in this course.");
        }
    }
}
