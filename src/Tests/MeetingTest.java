package Tests;

import Implementations.ContactImpl;
import Interfaces.Contact;
import Implementations.MeetingImpl;
import Interfaces.Meeting;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 *@author Ehshan Veerabangsa
 */
public class MeetingTest {

    private Set<Contact> testContacts;
    private Meeting testMeeting;
    private final int testId = 1;
    private final Calendar testDate = new GregorianCalendar(2011,11,11);
    /**
     * Test meeting constructor
     */
    @Before
    public void setUp() {
        testContacts = new HashSet<Contact>();
        Contact bateman = new ContactImpl(1, "Patrick Bateman");
        testContacts.add(bateman);
        Contact owen = new ContactImpl(2, "Paul Owen", "He was part of that whole 'Yale thing'");
        testContacts.add(owen);
        Contact vanPatten = new ContactImpl(3,"David Van Patten","Van Patten won't go anywhere without a reservation");
        testContacts.add(vanPatten);
        testMeeting = new MeetingImpl(testId, testDate, testContacts);
    }
    /**
     * Removing Test meeting
     */
    @After
    public void tearDown() {
        testContacts = null;
        testMeeting = null;
    }
    /**
     * Testing get meeting Id
     *
     * Should @return the int testId
     */
    @Test
    public void testGetId() {
        assertEquals(testId, testMeeting.getId());
    }
    /**
     * Testing get meeting date
     *
     * Should @return the Calender object testDate
     */
    @Test
    public void testGetDate() {
        assertEquals(testDate, testMeeting.getDate());
    }
    /**
     * Testing get meeting contacts
     *
     * Should return the Set testContacts
     */
    @Test
    public void testGetContacts() {
        assertTrue(testMeeting.getContacts().containsAll(testContacts));
    }
}