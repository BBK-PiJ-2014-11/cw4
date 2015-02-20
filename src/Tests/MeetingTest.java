package Tests;

import Implementations.ContactImpl;
import Interfaces.Contact;
import Interfaces.Meeting;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeetingTest {

    private Set<Contact> testContacts;
    private Meeting testMeeting;
    private final int testId = 1;
    private final Calendar testDate = new GregorianCalendar(2011,11,11);


    @Before
    public void setUp() {
        testContacts = new HashSet<Contact>();
        Contact bateman = new ContactImpl(1, "Patrick Bateman", "");
        testContacts.add(bateman);
        Contact owen = new ContactImpl(2, "Paul Owen", "He was part of that whole 'Yale thing'");
        testContacts.add(owen);
        Contact vanPatten = new ContactImpl(3,"David Van Patten","He won't go anywhere without a reservation");
        testContacts.add(vanPatten);
        testMeeting = new MeetingImpl(testId, testDate, testContacts);
    }

    @After
    public void tearDown() {
        testContacts = null;
        testMeeting = null;
    }

    @Test
    public void testGetId() {

    }

    @Test
    public void testGetDate() {

    }

    @Test
    public void testGetContacts() {

    }
}