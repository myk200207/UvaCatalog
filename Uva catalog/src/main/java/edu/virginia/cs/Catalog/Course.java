package edu.virginia.cs.hw4;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private final int courseRegistrationNumber;
    private String name; //i.e. Software Development Essentials
    private final String department; //i.e. CS
    private final int catalogNumber; //i.e. 3140
    private final int sectionNumber; //i.e. 001
    private int creditHours;
    private String instructorName;
    private String instructorEmail;
    private int enrollmentCap;
    private int waitListCap;
    private List<DayOfWeek> meetingDays;
    private int meetingStartTimeHour; // i.e., if 1:30 p.m., then 13
    private int meetingStartTimeMinute;  //i.e. if 1:30 p.m., then 30
    private int meetingDurationMinutes;  //i.e., if 75, class ends at 2:45p.m.
    private String meetingLocation; //i.e. GIL 301

    private List<Student> enrolledStudents;
    private List<Student> waitListedStudents;

    private EnrollmentStatus enrollmentStatus;

    private List<Prerequisite> prerequisites;

    public Course(int courseRegistrationNumber, String name, String department, int catalogNumber,
                  int sectionNumber, int creditHours, String instructorName, String instructorEmail,
                  int enrollmentCap, int waitListCap, List<DayOfWeek> meetingDays,
                  int meetingStartTimeHour, int meetingStartTimeMinute,
                  int meetingDurationMinutes, String meetingLocation,
                  List<Prerequisite> prerequisites) {
        if (courseRegistrationNumber <= 0 || name == null || department == null ||
                catalogNumber <= 0 || sectionNumber <= 0 || creditHours < 0 || instructorName == null ||
                instructorEmail == null || enrollmentCap <= 0 || waitListCap <= 0 || meetingStartTimeHour < 0
        || meetingStartTimeHour > 23 || meetingStartTimeMinute < 0 || meetingStartTimeMinute > 59 || meetingLocation ==null) {
            throw new IllegalArgumentException("Bad constructor arguments");
        }

        this.courseRegistrationNumber = courseRegistrationNumber;
        this.name = name;
        this.department = department;
        this.catalogNumber = catalogNumber;
        this.sectionNumber = sectionNumber;
        this.creditHours = creditHours;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.enrollmentCap = enrollmentCap;
        this.enrollmentStatus = EnrollmentStatus.OPEN;
        this.waitListCap = waitListCap;
        this.meetingDays = meetingDays;
        this.meetingStartTimeHour = meetingStartTimeHour;
        this.meetingStartTimeMinute = meetingStartTimeMinute;
        this.meetingDurationMinutes = meetingDurationMinutes;
        this.meetingLocation = meetingLocation;
        this.prerequisites = prerequisites;

        this.enrolledStudents = new ArrayList<>();
        this.waitListedStudents = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public int getEnrollmentCap() {
        return enrollmentCap;
    }

    public void setEnrollmentCap(int enrollmentCap) {
        this.enrollmentCap = enrollmentCap;
    }

    public int getWaitListCap() {
        return waitListCap;
    }

    public void setWaitListCap(int waitListCap) {
        this.waitListCap = waitListCap;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public int getCourseRegistrationNumber() {
        return courseRegistrationNumber;
    }

    public String getDepartment() {
        return department;
    }

    public int getCatalogNumber() {
        return catalogNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public int getCurrentEnrollmentSize() {
        return enrolledStudents.size();
    }
//    public void setCurrentEnrollmentSize(int size){
//        enrolledStudents = size;
//    }

    public int getCurrentWaitListSize() {
        return waitListedStudents.size();
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public List<Student> getWaitListedStudents() {
        return waitListedStudents;
    }

    public void setWaitListedStudents(List<Student> waitListedStudents) {
        this.waitListedStudents = waitListedStudents;
    }

    public List<DayOfWeek> getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(List<DayOfWeek> meetingDays) {
        this.meetingDays = meetingDays;
    }

    public int getMeetingStartTimeHour() {
        return meetingStartTimeHour;
    }

    public void setMeetingStartTimeHour(int meetingStartTimeHour) {
        this.meetingStartTimeHour = meetingStartTimeHour;
    }

    public int getMeetingStartTimeMinute() {
        return meetingStartTimeMinute;
    }

    public void setMeetingStartTimeMinute(int meetingStartTimeMinute) {
        this.meetingStartTimeMinute = meetingStartTimeMinute;
    }

    public int getMeetingDurationMinutes() {
        return meetingDurationMinutes;
    }

    public void setMeetingDurationMinutes(int meetingDurationMinutes) {
        this.meetingDurationMinutes = meetingDurationMinutes;
    }

    public String getMeetingLocation() {
        return meetingLocation;
    }

    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }

    public List<Prerequisite> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Prerequisite> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public void addStudentToEnrolled(Student student) {
        if (enrollmentCap <= enrolledStudents.size()) {
            throw new IllegalStateException("Course enrollment full, cannot add student");
        }
        enrolledStudents.add(student);
    }

    public void addStudentToWaitList(Student student) {
        if (waitListCap <= waitListedStudents.size()) {
            throw new IllegalStateException("Course wait list full, cannot add student");
        }
        waitListedStudents.add(student);
    }

    public void removeStudentFromEnrolled(Student student) {
        if (!enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }
        enrolledStudents.remove(student);
    }

    public void removeStudentFromWaitList(Student student) {
        if (!waitListedStudents.contains(student)) {
            throw new IllegalArgumentException("Student not enrolled in course");
        }
        waitListedStudents.remove(student);
    }

    public boolean isWaitListEmpty() {
        return waitListedStudents.isEmpty();
    }

    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    public boolean isStudentWaitListed(Student student) {
        return waitListedStudents.contains(student);
    }

    public Student getFirstStudentOnWaitList() {
        if (isWaitListEmpty()) {
            throw new IllegalStateException("Cannot get first student from empty wait list");
        }
        return waitListedStudents.get(0);
    }
    public void addPrerequisite(Prerequisite prerequisite) {
        prerequisites.add(prerequisite);
    }

    @Override
    public int hashCode() {
        return courseRegistrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Course otherCourse) {
            return courseRegistrationNumber == otherCourse.courseRegistrationNumber;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("""
                CRN: %d - %s %d-%3d - %s""",
                courseRegistrationNumber, department, catalogNumber, sectionNumber, name);
    }

    public enum EnrollmentStatus {
        OPEN, // Course is open, meaning there are enrollment spots available
        WAIT_LIST, // Course is currently wait listed
        CLOSED; // Course is closed, and no more registration can occur
    }
}
