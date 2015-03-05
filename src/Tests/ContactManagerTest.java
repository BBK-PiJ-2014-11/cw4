package Tests;

import Implementations.ContactImpl;
import Implementations.ContactManagerImpl;
import Interfaces.Contact;
import Interfaces.ContactManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
/**
 *  @author Ehshan Veerabangsa
 */
public class ContactManagerTest {

    private ContactManager manager;
    private Set<Contact> contacts;
    private Set<Contact> newContacts;
    private final Calendar pastDate = new GregorianCalendar(2011,11,11);
    private final Calendar futureDate = new GregorianCalendar(2015,11,11);
    /**
     * TestContact Manager constructor
     */
    @Before
    public void setUp() {
        manager = new ContactManagerImpl();
        manager.addNewContact("Patrick Bateman", "A big Genesis fan ever since the release of their 1980 album 'Duke'");
        manager.addNewContact("Paul Owen","Handling the Fisher account...lucky b******");
        manager.addNewContact("Timothy Price","He presents himself as a harmless old codger. But inside…");
        contacts = manager.getContacts(1,2,3);
        newContacts = new HashSet<Contact>();
    }
    /**
     * Removing manager and contact set
     */
    @After
    public void tearDown() {
        manager = null;
        contacts = null;
        newContacts = null;
    }
    /**
     * Testing adding a new contact to new manager
     *
     * Should @return the first contacts name and notes
     */
    @Test
    public void testAddNewContact(){
        ContactManager newManager = new ContactManagerImpl();
        String newName = "Donald Kimble";
        String newNote = "Lunch meeting at the Four Seasons";
        newManager.addNewContact(newName,newNote);
        //assertEquals(newName, newManager.getContacts(1).getName());
        //assertEquals(newNote, newManager.getContacts(1).getNotes());
        newContacts = newManager.getContacts(1);
        assertEquals(1, newContacts.size());
        assertTrue(contactFound(newContacts, "Donald Kimble"));
    }
    /**
     * Testing adding a new contact without a name
     *
     * Should @throw a NullPointerException
     */
    @Test (expected = NullPointerException.class)
    public void testAddContactWithoutName(){
        String newName = null;
        String newNote = "A guy who looks a lot like Luis Carruthers";
        manager.addNewContact(newName,newNote);
    }
    /**
     * Testing adding a new contact without notes
     *
     * Should @throw a NullPointerException
     */
    @Test (expected = NullPointerException.class)
    public void testAddContactWithoutNotes(){
        String newName = "Luis Carruthers";
        String newNote = null;
        manager.addNewContact(newName, newNote);
    }
    /**
     * Testing getting contact by id
     *
     * Should @return the size of the contact set
     */
    @Test
    public void testGetContactsId(){
        newContacts = manager.getContacts(1,2,3);
        assertEquals(3, newContacts.size());
    }
    /**
     * Testing getting contact with an invalid id
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsInvalidId(){
        manager.getContacts(-1);
    }
    /**
     * Testing getting contact with an unused id
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsUnusedId(){
        manager.getContacts(4);
    }
    /**
     * Method to check whether a given set contains a contact depending on name
     *
     * @param contactSet the set of contacts to be searched
     * @param name the string to search for
     * @return true if contact found, false if not
     */
    private boolean contactFound(Set<Contact> contactSet, String name) {
        boolean found = false;
        for (Contact person : contactSet) {
            if (person.getName().contains(name)) {
                found = true;
            }
        }
        return found;
    }
    /**
     * Method to retrieve a contact from a given set depending on name
     *
     * @param contactSet the set of contacts to be searched
     * @param name the string to search for
     * @return person the contact if present in set, null if not
     */
    private Contact findContact(Set<Contact> contactSet, String name) {
        for (Contact person : contactSet) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        return null;
    }
    /**
     * Testing getting contact by string
     *
     * Should @return the size of the contact set and the boolean found
     */

    @Test
    public void testGetContactsString() {
        newContacts = manager.getContacts("Patrick Bateman");
        assertEquals(1, newContacts.size());
        assertTrue(contactFound(newContacts,"Patrick Bateman"));
    }
    /**
     * Testing getting and unknown contact by string
     *
     * Should @return the size of the contact set and the boolean found
     */
    @Test
    public void testGetContactsInvalidString() {
        newContacts = manager.getContacts("Harold Carnes");
        assertTrue(newContacts.isEmpty());
        assertFalse(contactFound(newContacts, "Harold Carnes"));
    }
    /**
     * Testing getting contact with an empty string
     *
     * Should @throw a NullPointerException
     */
    @Test (expected = NullPointerException.class)
    public void testGetContactsEmptyString() {
        manager.getContacts("");
    }
    /*
    IGNORE FOR NOW
    @Test
    public void testAddContactWithoutName(){
        String newName = null;
        String newNote = "A guy who looks a lot like Luis Carruthers";
        manager.addNewContact(newName,newNote);
        assertSame(manager.getContacts(0),NullPointerException.class);
    }

    @Test
    public void testAddContactWithoutNotes(){
        String newName = "Luis Carruthers";
        String newNote = null;
        manager.addNewContact(newName,newNote);
        assertSame(manager.getContacts(0),NullPointerException.class);
    }
    */
    /**
     * Testing adding a future meeting to manager
     *
     * Should @return the Calender object futureDate
     */
    @Test
    public void testAddFutureMeeting() {
        manager.addFutureMeeting(contacts, futureDate);
        assertEquals(futureDate, manager.getMeeting(1).getDate());
    }
    /**
     * Testing adding a future meeting to manager by checking contacts
     *
     * Should @return the boolean found for each of the tested contacts
     */
    @Test
    public void testAddFutureMeetingCheckContacts() {
        manager.addFutureMeeting(contacts, futureDate);
        Set<Contact> testContacts = new HashSet<Contact>();
        testContacts = manager.getMeeting(1).getContacts();
        assertTrue(contactFound(testContacts,"Patrick Bateman"));
        assertTrue(contactFound(testContacts, "Paul Owen"));
        assertTrue(contactFound(testContacts, "Timothy Price"));
    }
    /**
     * Testing adding a future meeting with a past date
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddFutureMeetingPastDate() {
        manager.addFutureMeeting(contacts, pastDate);
    }
    /**
     * Testing adding a future meeting with an empty set of contacts
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddFutureMeetingNoContacts() {
        Set<Contact> noContacts = new HashSet<Contact>();
        manager.addFutureMeeting(noContacts, futureDate);
    }
    /**
     * Testing adding a future meeting with a set containing an unknown contact
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddFutureMeetingWithUnknownContact() {
        contacts.add(new ContactImpl(4, "Marcus Halberstram"));
        manager.addFutureMeeting(contacts, futureDate);
    }
    /**
     * Testing getting a non existent meeting
     *
     * Should @return null
     */
    @Test
    public void testGetMeetingNonExistent() {
        assertNull(manager.getMeeting(11));
    }
    /**
     * Testing adding a new past meeting
     *
     * Should @return the Calender object pastDate
     */
    @Test
    public void testAddNewPastMeeting() {
        String meetingNote = "I was wearing a wool tweed suit and a striped cotton shirt, both by Yves Saint Laurent";
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
        assertEquals(pastDate, manager.getMeeting(1).getDate());
    }
    /**
     * Testing adding a past meeting to manager by checking contacts
     *
     * Should @return the boolean found for each of the tested contacts
     */
    @Test
    public void testAddNewPastMeetingCheckContacts() {
        String meetingNote = "I was wearing a wool tweed suit and a striped cotton shirt, both by Yves Saint Laurent";
        manager.addNewPastMeeting(contacts, futureDate, meetingNote);
        Set<Contact> testContacts = new HashSet<Contact>();
        testContacts = manager.getMeeting(1).getContacts();
        assertTrue(contactFound(testContacts,"Patrick Bateman"));
        assertTrue(contactFound(testContacts, "Paul Owen"));
        assertTrue(contactFound(testContacts, "Timothy Price"));
    }
    /**
     * Testing adding a past meeting with a future date
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingFutureDate() {
        String meetingNote = "We had the peanut butter soup...with smoked duck and mashed squash.";
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
    }
    /**
     * Testing adding a past meeting with a null notes
     *
     * Should @throw a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullNotes() {
        String meetingNote = null;
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
    }
    /**
     * Testing adding a past meeting with a null date
     *
     * Should @throw a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullDate() {
        String meetingNote = "It's, uh, all right";
        Calendar elapsedDate = null;
        manager.addNewPastMeeting(contacts, elapsedDate, meetingNote);
    }
    /**
     * Testing adding a past meeting with a null set of contacts
     *
     * Should @throw a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testAddNewPastMeetingNullContacts() {
        String meetingNote = "Nobody goes to Dorsia anymore";
        Set<Contact> nonExistentContacts = null;
        manager.addNewPastMeeting(nonExistentContacts, pastDate, meetingNote);
    }
    /**
     * Testing adding a a past meeting with an empty set of contacts
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingNoContacts() {
        String meetingNote = "Nobody goes to Dorsia anymore";
        Set<Contact> noContacts = new HashSet<Contact>();
        manager.addNewPastMeeting(noContacts, futureDate, meetingNote);
    }
    /**
     * Testing adding a past meeting with a set containing an unknown contact
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingWithUnknownContact() {
        String meetingNote = "Had an 8.30 rez at Dorsia...great Sea Urchin Ceviche";
        contacts.add(new ContactImpl(4, "Marcus Halberstram"));
        manager.addNewPastMeeting(contacts, futureDate, meetingNote);
    }
    /**
     * Testing getting past meeting
     *
     * Should @return the calender object pastDate, the String meetingNote and
     * the boolean found for each of the tested contact for first meeting (ID = 1)
     *
     * Should @return the calender object oldDate, the String newNote and
     * the boolean found for each of the tested contact for second meeting (ID = 2)
     */
    @Test
    public void testGetPastMeeting() {
        //first past meeting
        String meetingNote = "Went to see the new musical...called Oh, Africa, Brave Africa";
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);

        //second past meeting
        manager.addNewContact("Craig McDermott","Knows the maitre'd at Canal Bar");
        newContacts = manager.getContacts(4);
        Calendar oldDate = new GregorianCalendar(1985,11,11);
        String newNote = "Signed a peace treaty with Gorbachev";
        manager.addNewPastMeeting(newContacts, oldDate, newNote);

        //tests for first past meeting
        assertEquals(pastDate, manager.getPastMeeting(1).getDate());
        assertEquals(meetingNote, manager.getPastMeeting(1).getNotes());
        Set<Contact> firstMeetingContacts = manager.getPastMeeting(1).getContacts();
        assertTrue(contactFound(firstMeetingContacts,"Patrick Bateman"));
        assertTrue(contactFound(firstMeetingContacts, "Paul Owen"));
        assertTrue(contactFound(firstMeetingContacts, "Timothy Price"));

        //tests for second past meeting
        assertEquals(oldDate, manager.getPastMeeting(2).getDate());
        assertEquals(newNote, manager.getPastMeeting(2).getNotes());
        Set<Contact> SecondMeetingContacts = manager.getPastMeeting(2).getContacts();
        assertTrue(contactFound(SecondMeetingContacts ,"Craig McDermott"));
    }
    /**
     * Testing getting a past meeting held at a future date
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetPastMeetingWithFutureDate() {
        manager.addFutureMeeting(contacts, futureDate);
        manager.getPastMeeting(1);
    }
    /**
     * Testing getting a non existent past meeting
     *
     * Should @return null
     */
    @Test
    public void testGetPastMeetingNonExistent() {
        assertNull(manager.getPastMeeting(11));
    }
    /**
     * Testing getting future meeting
     *
     * Should @return the calender object futureDate an the boolean
     * found for each of the tested contact for first meeting (ID = 1)
     *
     * Should @return the calender object newDate the boolean
     * found for each of the tested contact for  the second meeting (ID = 2)
     */
    @Test
    public void testGetFutureMeeting() {
        //first future meeting
        manager.addFutureMeeting(contacts, futureDate);

        //second future meeting
        manager.addNewContact("Craig McDermott","Knows the maitre'd at Canal Bar");
        newContacts = manager.getContacts(4);
        Calendar newDate = new GregorianCalendar(2025,11,11);
        manager.addFutureMeeting(newContacts, newDate);

        //tests for first future meeting
        assertEquals(futureDate, manager.getFutureMeeting(1).getDate());
        Set<Contact> firstMeetingContacts = manager.getFutureMeeting(1).getContacts();
        assertTrue(contactFound(firstMeetingContacts,"Patrick Bateman"));
        assertTrue(contactFound(firstMeetingContacts, "Paul Owen"));
        assertTrue(contactFound(firstMeetingContacts, "Timothy Price"));

        //tests for second future meeting
        assertEquals(newDate, manager.getFutureMeeting(2).getDate());
        Set<Contact> SecondMeetingContacts = manager.getFutureMeeting(2).getContacts();
        assertTrue(contactFound(SecondMeetingContacts ,"Craig McDermott"));
    }
    /**
     * Testing getting a future meeting held on a past date
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void testGetFutureMeetingWithPastDate() {
        String pastNote = "The pasta served was squid ravioli in a lemon grass broth";
        manager.addNewPastMeeting(contacts, pastDate, pastNote);
        manager.getFutureMeeting(1);
    }
    /**
     * Testing getting a non existent future meeting
     *
     * Should @return null
     */
    @Test
    public void testGetFutureMeetingNonExistent() {
        assertNull(manager.getFutureMeeting(11));
    }
}