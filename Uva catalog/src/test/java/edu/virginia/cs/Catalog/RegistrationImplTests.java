package edu.virginia.cs.hw4;

import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*; //mock Course and Student objects

public class RegistrationImplTests {
    //fields
    private CourseCatalog catalog, mockCatalog, catalog2;
    private List<Course> mockGetAllCourse;
    private Set<Course> courseList;
    private List<Prerequisite> prerequisiteListMock, prereqList;
    private Student mockStudent;
    private Course mockCourse, mockCourse2;
    private RegistrationImpl regImpl, regImplMock, regImplMockMock;
    private RegistrationImpl mockReg;
    private Prerequisite mockPrerequisite;
    private Prerequisite prereq;
    private List<Course> list;

    @BeforeEach
    public void setup() {
        list = new ArrayList<Course>();
        prerequisiteListMock = mock(List.class);
        mockReg = mock(RegistrationImpl.class);
        mockStudent = mock(Student.class);
        mockCourse2 = mock(Course.class);
        mockGetAllCourse = mock(ArrayList.class);
        mockCourse = mock(Course.class);
        Prerequisite prerequisite = new Prerequisite(mockCourse, Grade.C);
        prereq = new Prerequisite(mockCourse, Grade.C);
        mockCatalog = mock(CourseCatalog.class);
        catalog2 = new CourseCatalog();
        catalog = new CourseCatalog();
        regImplMock = new RegistrationImpl(mockCatalog);
        regImplMockMock = mock(RegistrationImpl.class);
        regImpl = new RegistrationImpl(catalog);
        prereqList = new ArrayList<Prerequisite>();

    }

    //Tests
    @Test
    public void getCourseCatalogTest() {
        when(mockGetAllCourse.size()).thenReturn(1);
        catalog.addCourse(mockCourse);
        assertEquals(mockGetAllCourse.size(), regImpl.getCourseCatalog().courseList.size());
        //assertEquals(null, mockRegImp.getCourseCatalog());

    }

    @Test
    public void setCourseCatalogTest() {
        catalog.courseList.add(mockCourse);
        regImpl.setCourseCatalog(catalog);
        assertTrue(regImpl.getCourseCatalog().courseList.contains(mockCourse));
    }

    @Test
    public void isEnrollmentFullFalseTest() {
        when(mockCourse.getEnrollmentCap()).thenReturn(1);
        assertFalse(regImpl.isEnrollmentFull(mockCourse));

    }

    @Test
    public void isEnrollmentFullTrueCap0Test() {
        when(mockCourse.getEnrollmentCap()).thenReturn(0);
        assertTrue(regImpl.isEnrollmentFull(mockCourse));
    }

    @Test
    public void isEnrollmentFullTrueAtCapTest() {
        when(mockCourse.getEnrollmentCap()).thenReturn(1);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(1);
        assertTrue(regImpl.isEnrollmentFull(mockCourse));
    }

    @Test
    public void isEnrollmentFullTrueOverCapTest() {
        when(mockCourse.getEnrollmentCap()).thenReturn(1);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(2);
        assertTrue(regImpl.isEnrollmentFull(mockCourse));
    }

    @Test
    public void isWaitListFullFalseTest() {
        when(mockCourse.getWaitListCap()).thenReturn(5);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(1);
        assertFalse(regImpl.isWaitListFull(mockCourse));
    }

    @Test
    public void isWaitListFullCapTest() {
        when(mockCourse.getWaitListCap()).thenReturn(5);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(5);
        assertTrue(regImpl.isWaitListFull(mockCourse));
    }

    @Test
    public void isWaitListFullOverCapTest() {
        when(mockCourse.getWaitListCap()).thenReturn(5);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        assertTrue(regImpl.isWaitListFull(mockCourse));
    }

    @Test
    public void enrollmentOpenTest() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.OPEN);
        assertEquals(Course.EnrollmentStatus.OPEN, regImpl.getEnrollmentStatus(mockCourse));
    }

    @Test
    public void enrollmentWaitListTest() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.WAIT_LIST);
        assertEquals(Course.EnrollmentStatus.WAIT_LIST, regImpl.getEnrollmentStatus(mockCourse));
    }

    @Test
    public void enrollmentClosedTest() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        assertEquals(Course.EnrollmentStatus.CLOSED, regImpl.getEnrollmentStatus(mockCourse));
    }

    @Test
    public void coursesOverlapConflictTest() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        //1000-1050
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        //1030-1120
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        assertTrue(regImpl.areCoursesConflicted(mockCourse, mockCourse2));
    }

    @Test
    public void coursesConflictTestNoOverlapDifferentDays() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY));
        //1000-1050
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        //1030-1120
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        assertFalse(regImpl.areCoursesConflicted(mockCourse, mockCourse2));
    }

    @Test
    public void coursesConflictTestSameDayNoOverlapTime() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        //1000-1050
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);

        //1100-1150
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(0);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        //System.out.println(regImpl.areCoursesConflicted(mockCourse,mockCourse2));
        assertFalse(regImpl.areCoursesConflicted(mockCourse, mockCourse2));
    }

    @Test
    public void coursesConflictTestSameDaySameTime() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        assertTrue(regImpl.areCoursesConflicted(mockCourse, mockCourse2));
    }

    @Test
    public void coursesConflictTestFirstDayStartsLaterTime() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        assertTrue(regImpl.areCoursesConflicted(mockCourse, mockCourse2));
    }

    @Test
    public void DoesNotHaveConflictWithStudentScheduleTest() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        //when(mockStudent.getCourseHistory().keySet()).thenReturn(List.of(mockCourse)));
        when(mockCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse));
        assertFalse(regImpl.hasConflictWithStudentSchedule(mockCourse2, mockStudent));
    }

    @Test
    public void hasConflictWithStudentScheduleDifferentTimeTest() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse));
        assertTrue(regImplMock.hasConflictWithStudentSchedule(mockCourse2, mockStudent));
    }

    @Test
    public void hasConflictWithStudentScheduleDifferentDayTest() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.TUESDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(10);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(30);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse));
        assertFalse(regImplMock.hasConflictWithStudentSchedule(mockCourse2, mockStudent));
    }

    @Test
    public void hasConflictWithStudentScheduleStartSameTimeTest() {
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse));
        assertTrue(regImplMock.hasConflictWithStudentSchedule(mockCourse2, mockStudent));
    }

    @Test
    public void hasStudentMeetsPreRequisiteTrue() {
        prereqList.add(prereq);
        when(mockStudent.meetsPrerequisite(prereq)).thenReturn(true);
        assertTrue(regImpl.hasStudentMeetsPrerequisites(mockStudent, prereqList));
    }

    @Test
    public void hasStudentMeetsPreRequisiteFalse() {
        prereqList.add(prereq);
        when(mockStudent.meetsPrerequisite(prereq)).thenReturn(false);
        assertFalse(regImpl.hasStudentMeetsPrerequisites(mockStudent, prereqList));
    }

    @Test
    public void attemptToEnrollInClosedCourse() {
        //doReturn(Course.EnrollmentStatus.CLOSED).when(mockCourse).getEnrollmentStatus();
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        assertEquals(RegistrationResult.COURSE_CLOSED, regImpl.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void attemptToEnrollInFullCourse() {
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.OPEN);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(100);
        when(mockCourse.getWaitListCap()).thenReturn(100);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(100);
        when(mockCourse.getEnrollmentCap()).thenReturn(100);
        assertEquals(RegistrationResult.COURSE_FULL, regImpl.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void attemptToEnrollScheduleConflict() {
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(0);
        //regImpl.hasConflictWithStudentSchedule(mockCourse, mockStudent);
        regImpl.setCourseCatalog(mockCatalog);
        when(mockCourse.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse2.getMeetingDays()).thenReturn(List.of(DayOfWeek.MONDAY));
        when(mockCourse.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCourse2.getMeetingStartTimeHour()).thenReturn(11);
        when(mockCourse2.getMeetingStartTimeMinute()).thenReturn(00);
        when(mockCourse2.getMeetingDurationMinutes()).thenReturn(50);
        when(mockCatalog.getCoursesEnrolledIn(mockStudent)).thenReturn(List.of(mockCourse2));
        assertEquals(RegistrationResult.SCHEDULE_CONFLICT, regImpl.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void attemptToEnrollPrereqNotMet() {
        prereqList.add(prereq);
        when(mockCourse2.getPrerequisites()).thenReturn(prereqList);
        when(mockStudent.getCourseGrade(mockCourse)).thenReturn(Grade.F);
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(0);
        when(mockCourse2.getEnrollmentCap()).thenReturn(10);
        when(mockCourse2.getCurrentEnrollmentSize()).thenReturn(0);
        assertEquals(RegistrationResult.PREREQUISITE_NOT_MET, regImpl.registerStudentForCourse(mockStudent, mockCourse2));
    }

    @Test
    public void attemptWaitListTest() {
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(10);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(0);

        assertEquals(RegistrationResult.WAIT_LISTED, regImpl.registerStudentForCourse(mockStudent, mockCourse));
    }

    @Test
    public void attemptWaitListTestSideEffect() {
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(10);
        when(mockCourse.getWaitListCap()).thenReturn(10);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(9);
        regImpl.registerStudentForCourse(mockStudent,mockCourse);
        verify(mockCourse).addStudentToWaitList(mockStudent);

    }
    @Test
    public void attemptEnrollingStudents(){
        when(mockCourse.getEnrollmentCap()).thenReturn(10);
        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(9);
        assertEquals(RegistrationResult.ENROLLED,regImpl.registerStudentForCourse(mockStudent,mockCourse));
    }
    //    @Test
//    public void attemptEnrollingStudentsSideEffect(){
//        when(mockCourse.getEnrollmentCap()).thenReturn(10);
//        when(mockCourse.getCurrentEnrollmentSize()).thenReturn(10);
//        when(mockCourse.getWaitListCap()).thenReturn(10);
//        when(mockCourse.getCurrentWaitListSize()).thenReturn(9);
//        regImpl.registerStudentForCourse(mockStudent,mockCourse);
//        verify(regImplMockMock).isWaitListFull(mockCourse);
//        //when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
//        //verify(mockCourse).setEnrollmentStatus(Course.EnrollmentStatus.CLOSED);
//        //verify(mockCourse).addStudentToWaitList(mockStudent);
//
//    }
    @Test
    public void dropCourseException(){
        assertThrows(IllegalArgumentException.class, ()-> regImpl.dropCourse(mockStudent, mockCourse));
    }
    @Test
    public void dropCourseStudentEnrolled(){
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(true);
        regImpl.dropCourse(mockStudent, mockCourse);
        verify(mockCourse).removeStudentFromEnrolled(mockStudent);
    }
    @Test
    public void dropCourseStudentEnrolledSideEffectChangeCourseEnrollmentStatus(){
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(true);
        regImpl.dropCourse(mockStudent, mockCourse);
        verify(mockCourse).setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);
    }
    @Test
    public void dropCourseStudentEnrolledSideEffectRemoveStudentFromWaitList(){
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(true);
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.WAIT_LIST);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        when(mockCourse.getFirstStudentOnWaitList()).thenReturn(mockStudent);
        regImpl.dropCourse(mockStudent,mockCourse);
        verify(mockCourse).addStudentToEnrolled(mockCourse.getFirstStudentOnWaitList());
        verify(mockCourse).removeStudentFromWaitList(mockCourse.getFirstStudentOnWaitList());
    }
    @Test
    public void dropCourseStudentWaitListedWaitListStillNotEmpty(){
        when(mockCourse.isStudentWaitListed(mockStudent)).thenReturn(true);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(10);
        regImpl.dropCourse(mockStudent, mockCourse);
        verify(mockCourse).removeStudentFromWaitList(mockStudent);
    }
    @Test
    public void dropCourseStudentEnrolledSideEffectRemoveStudentFromWaitListSizeZero(){
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(true);
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.WAIT_LIST);
        when(mockCourse.getCurrentWaitListSize()).thenReturn(0);
        regImpl.dropCourse(mockStudent,mockCourse);
        verify(mockCourse).setEnrollmentStatus(Course.EnrollmentStatus.OPEN);
    }
    @Test
    public void dropCourseStudentWaitListed(){
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(false);
        when(mockCourse.isStudentWaitListed(mockStudent)).thenReturn(true);
        regImpl.dropCourse(mockStudent,mockCourse);
        verify(mockCourse).removeStudentFromWaitList(mockStudent);

    }
    @Test
    public void dropCourseStudentWaitListedSideEffect(){
        when(mockCourse.isStudentEnrolled(mockStudent)).thenReturn(false);
        when(mockCourse.isStudentWaitListed(mockStudent)).thenReturn(true);
        when(mockCourse.getEnrollmentStatus()).thenReturn(Course.EnrollmentStatus.CLOSED);
        regImpl.dropCourse(mockStudent,mockCourse);
        verify(mockCourse).setEnrollmentStatus(Course.EnrollmentStatus.WAIT_LIST);

    }

}
