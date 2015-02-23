package Tests;

import Implementations.ContactImpl;
import Interfaces.Contact;
import Interfaces.PastMeeting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class PastMeetingTest {
    private Set<Contact> testContacts;
    private PastMeeting pastMeeting;
    private final int testId = 1;
    private final Calendar testDate = new GregorianCalendar(2011,11,11);
    private final String pastNotes = " Agreed on a reservations for four at Arcadia at 8:00 on Thursday";

    @Before
    public void setUp(){
        testContacts = new HashSet<Contact>();
        Contact bateman = new ContactImpl(1, "Patrick Bateman");
        testContacts.add(bateman);
        Contact owen = new ContactImpl(2, "Paul Owen", "He was part of that whole 'Yale thing'");
        testContacts.add(owen);
        Contact vanPatten = new ContactImpl(3,"David Van Patten","Van Patten won't go anywhere without a reservation");
        testContacts.add(vanPatten);
        pastMeeting = new PastMeetingImpl(testId, testDate, testContacts,pastNotes);
    }

    @After
    public void tearDown() {
        testContacts = null;
        pastMeeting = null;
    }

    @Test
    public void testGetNotes() {
        assertEquals(,pastMeeting.getNotes());
    }
}