package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentTests {

    private Student mockStudent,testStudent, testStudent2;
    private Course mockCourse;
    private Course mockCourseTwo;

    private Grade grade;
    private Grade minGrade;
    private Grade failGrade;
    private Transcript transcript;
    private Map<Course, Grade> mockHistory;
    @BeforeEach
    public void setUp(){
        mockStudent = mock(Student.class);
        mockCourse = mock(Course.class);
        mockCourseTwo = mock(Course.class);

        when(mockCourse.getDepartment()).thenReturn("CS");
        when(mockCourse.getCatalogNumber()).thenReturn(3140);

        when(mockCourseTwo.getDepartment()).thenReturn("CS");
        when(mockCourseTwo.getCatalogNumber()).thenReturn(3140);
        grade = Grade.A;
        minGrade = Grade.C;
        failGrade = Grade.F;
        testStudent = new Student(1234, "Joshua Seiden", "vuc7cu@virginia.edu");
        testStudent2 = new Student(1235, "Mk", "yk4vs@virginia.edu");
        mockHistory = mock(HashMap.class);
        transcript = new Transcript(testStudent,mockHistory);

    }
    @Test
    public void addCourseGradeSideEffect(){
        when(mockHistory.size()).thenReturn(1);
        testStudent.addCourseGrade(mockCourse, grade);
        testStudent.addCourseGrade(mockCourseTwo, grade);
        assertEquals(mockHistory.size(), testStudent.getCourseHistory().size());
    }
    @Test
    public void addCourseGradeCorrection(){
        testStudent.addCourseGrade(mockCourse,failGrade);
        testStudent.addCourseGrade(mockCourseTwo,grade);
        assertEquals(grade,testStudent.getCourseGrade(mockCourse));
    }
    @Test
    public void addCourseGradeTest(){
        when(mockCourse.getCreditHours()).thenReturn(3);
        testStudent.addCourseGrade(mockCourse,grade);
        assertEquals(4.0, testStudent.getGPA());
    }
    @Test
    public void hasStudentTakenCourseTest(){
        when(mockHistory.containsKey(mockCourse)).thenReturn(true);
        testStudent.addCourseGrade(mockCourse,grade);
        assertTrue(testStudent.hasStudentTakenCourse(mockCourse));
    }
    @Test
    public void hasStudentTakenCourseUpdateTest(){
        testStudent.addCourseGrade(mockCourse, grade);
        //testStudent2.addCourseGrade(mockCourseTwo,grade);
        assertTrue(testStudent.hasStudentTakenCourse(mockCourseTwo));
        //assertEquals(testStudent.hasStudentTakenCourse(mockCourseTwo), testStudent2.hasStudentTakenCourse(mockCourse));
    }
    @Test
    public void getCourseGradeTest(){
        testStudent.addCourseGrade(mockCourse,grade);
        assertEquals(grade, testStudent.getCourseGrade(mockCourse));
    }
    @Test
    public void getCourseGradeExceptionTest(){
        assertThrows(IllegalArgumentException.class, ()-> testStudent.getCourseGrade(mockCourse));
    }
    @Test
    public void meetPreRequisitePassTest(){
        Prerequisite prerequisite = new Prerequisite(mockCourse,minGrade);//C
        testStudent.addCourseGrade(mockCourse,grade); //A
        assertTrue(testStudent.meetsPrerequisite(prerequisite));
    }
    @Test
    public void meetPreRequisiteFailTestBadGrade(){
        Prerequisite prerequisite = new Prerequisite(mockCourse,minGrade);
        testStudent.addCourseGrade(mockCourse,failGrade);
        assertFalse(testStudent.meetsPrerequisite(prerequisite));

    }
    @Test
    public void meetPreRequisitesFailTestDidNotTake(){
        Prerequisite prerequisite = new Prerequisite(mockCourse,minGrade);
        assertFalse(testStudent.meetsPrerequisite(prerequisite));
    }

    @Test
    public void getGPAExceptionTest(){
        assertThrows(IllegalStateException.class, ()-> testStudent.getGPA());
    }

    @Test
    public void getGPATest(){
        mockCourseTwo = mock(Course.class);
        when(mockCourse.getCreditHours()).thenReturn(3);
        when(mockCourseTwo.getCreditHours()).thenReturn(4);
        testStudent.addCourseGrade(mockCourse, grade);
        testStudent.addCourseGrade(mockCourseTwo, minGrade);
        assertEquals(2.857,testStudent.getGPA(),1E-3);
        //expected value calculated by hand
    }
}
