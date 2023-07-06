package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseCatalogTest {
    private CourseCatalog testCatalog;
    private Course mockCourseMatch, mockCourseNonMatch;

    @BeforeEach
    public void setUp() {
        mockCourseMatch = mock(Course.class);
        mockCourseNonMatch = mock(Course.class);
        testCatalog = new CourseCatalog(Set.of(mockCourseMatch, mockCourseNonMatch));
    }

    @Test
    public void testGetAllCourse() {
        List<Course> courseList = testCatalog.getAllCourses();
        assertTrue(courseList.contains(mockCourseMatch));
        assertTrue(courseList.contains(mockCourseNonMatch));
        assertEquals(2, courseList.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void addCourse() {
        Set<Course> mockSet = (Set<Course>) mock(Set.class);
        testCatalog = new CourseCatalog(mockSet);
        testCatalog.addCourse(mockCourseMatch);
        verify(mockSet).add(mockCourseMatch);
    }

    @Test
    public void testGetCoursesEnrolledIn() {
        Student mockStudent = mock(Student.class);
        when(mockCourseMatch.isStudentEnrolled(mockStudent)).thenReturn(true);
        when(mockCourseNonMatch.isStudentEnrolled(mockStudent)).thenReturn(false);
        List<Course> courseList = testCatalog.getCoursesEnrolledIn(mockStudent);
        assertIterableEquals(List.of(mockCourseMatch), courseList);
    }

    @Test
    public void testGetCoursesWaitListedIn() {
        Student mockStudent = mock(Student.class);
        when(mockCourseMatch.isStudentWaitListed(mockStudent)).thenReturn(true);
        when(mockCourseNonMatch.isStudentWaitListed(mockStudent)).thenReturn(false);
        List<Course> courseList = testCatalog.getCoursesWaitListedIn(mockStudent);
        assertIterableEquals(List.of(mockCourseMatch), courseList);
    }

    @Test
    public void testGetCourseByRegistrationNumber() {
        int crn = 18802;
        when(mockCourseMatch.getCourseRegistrationNumber()).thenReturn(crn);
        when(mockCourseNonMatch.getCourseRegistrationNumber()).thenReturn(12345);
        assertEquals(mockCourseMatch, testCatalog.getCourseByRegistrationNumber(crn));
    }

    @Test
    public void testGetCourseByDepartment() {
        when(mockCourseMatch.getDepartment()).thenReturn("CS");
        when(mockCourseNonMatch.getDepartment()).thenReturn("APMA");
        assertEquals(List.of(mockCourseMatch), testCatalog.getCoursesByDepartment("CS"));
    }

    @Test
    public void testGetCourseByDepartmentAndNumber() {
        when(mockCourseMatch.getDepartment()).thenReturn("CS");
        when(mockCourseNonMatch.getDepartment()).thenReturn("CS");
        when(mockCourseMatch.getCatalogNumber()).thenReturn(3140);
        when(mockCourseNonMatch.getCatalogNumber()).thenReturn(1110);
        assertEquals(List.of(mockCourseMatch),
                testCatalog.getCoursesByDepartmentAndNumber("CS", 3140));
    }
    @Test
    public void testGetCourseByInstructorName() {
        when(mockCourseMatch.getInstructorName()).thenReturn("Paul McBurney");
        when(mockCourseNonMatch.getInstructorName()).thenReturn("Rich Nguyen");
        assertEquals(List.of(mockCourseMatch), testCatalog.getCoursesByInstructorName("Paul McBurney"));
    }
}
