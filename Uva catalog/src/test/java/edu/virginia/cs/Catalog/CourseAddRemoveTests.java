package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseAddRemoveTests {
    private Course testCourse;
    private List<Student> mockEnrollment, mockWaitList;
    private Student mockStudent;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        testCourse = getTestCourse();
        mockEnrollment = (List<Student>) mock(List.class);
        mockWaitList = (List<Student>) mock(List.class);
        testCourse.setEnrolledStudents(mockEnrollment);
        testCourse.setWaitListedStudents(mockWaitList);

        mockStudent = mock(Student.class);
    }

    private Course getTestCourse() {
        return new Course(18802,
                "Software Development Essentials", "CS",
                3140, 1, 3, "Paul McBurney",
                "pm8fc@virginia.edu", 175, 99,
                List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                12, 30, 75,"Gilmer 301",
                new ArrayList<>());
    }

    @Test
    public void testAddStudentToEnrollment_EmptyClass() {
        when(mockEnrollment.size()).thenReturn(0);
        testCourse.addStudentToEnrolled(mockStudent);
        verify(mockEnrollment).add(mockStudent);
    }

    @Test
    public void testAddStudentToEnrollment_FullClass() {
        when(mockEnrollment.size()).thenReturn(testCourse.getEnrollmentCap());
        assertThrows(IllegalStateException.class, () ->
                testCourse.addStudentToEnrolled(mockStudent));
        verify(mockEnrollment, never()).add(mockStudent);
    }

    @Test
    public void testAddStudentToWaitList_EmptyWaitList() {
        when(mockWaitList.size()).thenReturn(0);
        testCourse.addStudentToWaitList(mockStudent);
        verify(mockWaitList).add(mockStudent);
    }

    @Test
    public void testAddStudentToWaitList_FullWaitList() {
        when(mockWaitList.size()).thenReturn(testCourse.getWaitListCap());
        assertThrows(IllegalStateException.class, () ->
                testCourse.addStudentToWaitList(mockStudent));
        verify(mockWaitList, never()).add(mockStudent);
    }
    @Test
    public void getFirstStudentOnWaitList() {
        when(mockWaitList.isEmpty()).thenReturn(false);
        when(mockWaitList.get(0)).thenReturn(mockStudent);
        assertEquals(mockStudent, testCourse.getFirstStudentOnWaitList());
    }

    @Test
    public void getFirstStudentOnWaitList_EmptyWaitList() {
        when(mockWaitList.isEmpty()).thenReturn(true);
        assertThrows(IllegalStateException.class, () ->
                testCourse.getFirstStudentOnWaitList());
        verify(mockWaitList, never()).get(anyInt());
    }

    @Test
    public void removeStudentFromEnrolled() {
        when(mockEnrollment.contains(mockStudent)).thenReturn(true);
        testCourse.removeStudentFromEnrolled(mockStudent);
        verify(mockEnrollment).remove(mockStudent);
    }

    @Test
    public void removeStudentFromEnrolled_NotPresent() {
        when(mockEnrollment.contains(mockStudent)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () ->
                testCourse.removeStudentFromEnrolled(mockStudent));
    }

    @Test
    public void removeStudentFromWaitList() {
        when(mockWaitList.contains(mockStudent)).thenReturn(true);
        testCourse.removeStudentFromWaitList(mockStudent);
        verify(mockWaitList).remove(mockStudent);
    }

    @Test
    public void removeStudentFromWaitList_NotPresent() {
        when(mockWaitList.contains(mockStudent)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () ->
                testCourse.removeStudentFromEnrolled(mockStudent));
    }
}
