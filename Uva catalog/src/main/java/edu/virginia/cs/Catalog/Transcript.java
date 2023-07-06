package edu.virginia.cs.hw4;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Transcript {
    Student student;
    Map<Course, Grade> courseHistory;

    public Transcript(Student student) {
        this.student = student;
        courseHistory = new HashMap<>();
    }
    public Transcript(Student student, Map<Course,Grade> courseHistory){
        this.student=student;
        this.courseHistory = courseHistory;

    }

    public void addCourse(Course course, Grade grade) {
        if(!Taken(course)) {
            courseHistory.put(course, grade);

        }
        else{
            for (Map.Entry<Course, Grade> each : courseHistory.entrySet()) {
                if (each.getKey().getDepartment().equals(course.getDepartment())) {
                    if (each.getKey().getCatalogNumber() == course.getCatalogNumber()) {
                        courseHistory.put(each.getKey(), grade);

                    }
                }
            }
        }

    }

    public boolean checkCourseHistory(Course course) {
        return courseHistory.containsKey(course);
    }

    public Grade courseGradeFromHistory(Course course) {
        return courseHistory.get(course);
    }

    public boolean courseHistoryIsEmpty() {
        return courseHistory.isEmpty();
    }

    public Set<Course> courseHistoryKeys(){
        return courseHistory.keySet();
    }

    public Map<Course, Grade> getCourseHistory() {
        return courseHistory;
    }

    public boolean Taken(Course course){
        for( Course each : courseHistory.keySet()){
            if(each.getDepartment().equals(course.getDepartment())){
                if(each.getCatalogNumber() == course.getCatalogNumber()){

                    return true;
                }
            }
        }
        return false;
    }
}
