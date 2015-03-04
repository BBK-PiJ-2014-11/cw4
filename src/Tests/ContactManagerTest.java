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
        assertTrue(contactFound(newContacts,"Donald Kimble"));
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
        manager.addNewContact(newName,newNote);
    }
    /**
     * Testing getting contact by id
     *
     * Should @return the size of the contact set
     */
    @Test
    public void testGetContactsId(){
        newContacts = manager.getContacts(1,2,3);
        assertEquals(3,newContacts.size());
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
        assertTrue(contactFound(testContacts,"Paul Owen"));
        assertTrue(contactFound(testContacts,"Timothy Price"));
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
        contacts = manager.getContacts(1);
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
        assertTrue(contactFound(testContacts,"Paul Owen"));
        assertTrue(contactFound(testContacts,"Timothy Price"));
    }
    /**
     * Testing adding a pass meeting with a future date
     *
     * Should @throw a IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddNewPastMeetingFutureDate() {
        String meetingNote = "We had the peanut butter soup...with smoked duck and mashed squash.";
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
    }
}