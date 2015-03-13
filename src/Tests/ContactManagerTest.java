package Tests;

import Implementations.ContactImpl;
import Implementations.ContactManagerImpl;
import Interfaces.Contact;
import Interfaces.ContactManager;
import Interfaces.Meeting;
import Interfaces.PastMeeting;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        assertEquals(newNote, findContact(newContacts,"Donald Kimble").getNotes());
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
    //NEED TO REVIEW - CAN A PAST MEETING BE CREATED IN THE FUTURE
//    /**
//     * Testing adding a past meeting with a future date
//     *
//     * Should @throw a IllegalArgumentException
//     */
//    @Test(expected = IllegalArgumentException.class)
//    public void testAddNewPastMeetingFutureDate() {
//        String meetingNote = "We had the peanut butter soup...with smoked duck and mashed squash.";
//        manager.addNewPastMeeting(contacts, futureDate, meetingNote);
//    }
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
//    /**
//     * Testing adding a past meeting with a empty notes
//     *
//     * Should @throw a NullPointerException
//     */
//    @Test(expected = NullPointerException.class)
//    public void testAddNewPastMeetingEmptyNotes() {
//        String meetingNote = "";
//        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
//    }
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
    /*
    * BREAK HERE
    *
    *
    * NEW TEST BATCH
    *
    *
    */
    /**
     * Testing getting the future meetings of a given contact
     *
     * Should @return a list containing meeting id, date and contact set for
     * both meetings
     */
    @Test
    public void getFutureMeetingListWithContact() {
        Calendar futureDate2 = new GregorianCalendar(2016, 11, 11);
        newContacts = manager.getContacts(1);

        //create meetings
        manager.addFutureMeeting(contacts, futureDate);
        manager.addFutureMeeting(newContacts, futureDate2);

        //creating contact object
        Contact bateman = findContact(contacts,"Patrick Bateman");

        //getting list with contacts meetings
        List<Meeting> meetings = manager.getFutureMeetingList(bateman);

        //tests
        assertEquals(2, meetings.size());

        //checking first meeting is in list
        assertEquals(1, meetings.get(0).getId());
        assertEquals(futureDate, meetings.get(0).getDate());
        Set<Contact> firstMeetingContacts = meetings.get(0).getContacts();
        assertTrue(contactFound(firstMeetingContacts,"Patrick Bateman"));

        //checking second meeting is in list
        assertEquals(2, meetings.get(1).getId());
        assertEquals(futureDate2, meetings.get(1).getDate());
        Set<Contact> secondMeetingContacts = meetings.get(1).getContacts();
        assertTrue(contactFound(secondMeetingContacts,"Patrick Bateman"));
    }
    /**
     * Testing getting the future meetings of a given contact
     *
     * Should @return a list containing meetings chronologically sorted
     */
    @Test
    public void getFutureMeetingListWithContactSortedList() {
        newContacts = manager.getContacts(1);

        //create dates
        Calendar futureDate2 = new GregorianCalendar(2016, 11, 11);
        Calendar futureDate3 = new GregorianCalendar(2017, 11, 11);
        Calendar futureDate4 = new GregorianCalendar(2018, 11, 11);
        Calendar futureDate5 = new GregorianCalendar(2019, 11, 11);

        //create meetings(not added in order)
        manager.addFutureMeeting(newContacts, futureDate5);
        manager.addFutureMeeting(contacts, futureDate3);
        manager.addFutureMeeting(newContacts, futureDate4);
        manager.addFutureMeeting(contacts, futureDate);
        manager.addFutureMeeting(newContacts, futureDate2);

        //creating contact object
        Contact bateman = findContact(contacts,"Patrick Bateman");

        //getting list with contacts meetings
        List<Meeting> meetings = manager.getFutureMeetingList(bateman);

        //tests
        assertEquals(5, meetings.size());
        assertEquals(futureDate, meetings.get(0).getDate());
        assertEquals(futureDate2, meetings.get(1).getDate());
        assertEquals(futureDate3, meetings.get(2).getDate());
        assertEquals(futureDate4, meetings.get(3).getDate());
        assertEquals(futureDate5, meetings.get(4).getDate());
    }
    /**
     * Testing getting the future meetings of contact with no scheduled meetings
     *
     * Should @return an empty list
     */
    @Test
    public void getFutureMeetingListWithContactNoMeetings() {
        Contact bateman = findContact(contacts,"Patrick Bateman");
        List<Meeting> meetings = manager.getFutureMeetingList(bateman);
        assertTrue(meetings.isEmpty());
    }
    /**
     * Testing getting the future meetings of an unknown contact
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void getFutureMeetingListWithUnknownContact() {
        Contact halberstram = new ContactImpl(4, "Marcus Halberstram");
        manager.getFutureMeetingList(halberstram);
    }
    /**
     * Testing getting the past meetings of a given contact
     *
     * Should @return a list containing meeting id, date, notes and contact set for
     * both meetings
     */
    @Test
    public void getPastMeetingList() {
        Calendar pastDate1 = new GregorianCalendar(1985, 11, 11);
        newContacts = manager.getContacts(1);
        String meetingNote = "We, inexplicably could not get reservations at Crayons, so Price suggested the new Tony McManus restaurant ";
        String meetingNote1 = "I was wearing a two button wool suit, cotton shirt and silk tie, all by Armani";

        //create meetings
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
        manager.addNewPastMeeting(newContacts, pastDate1, meetingNote1);

        //creating contact object
        Contact bateman = findContact(contacts,"Patrick Bateman");

        //getting list with contacts meetings
        List<PastMeeting> meetings = manager.getPastMeetingList(bateman);

        //tests
        assertEquals(2, meetings.size());

        //checking first meeting is in list
        assertEquals(2, meetings.get(0).getId());
        assertEquals(pastDate1, meetings.get(0).getDate());
        assertEquals(meetingNote1, meetings.get(0).getNotes());
        Set<Contact> firstMeetingContacts = meetings.get(0).getContacts();
        assertTrue(contactFound(firstMeetingContacts,"Patrick Bateman"));

        //checking second meeting is in list
        assertEquals(1, meetings.get(1).getId());
        assertEquals(pastDate, meetings.get(1).getDate());
        assertEquals(meetingNote, meetings.get(1).getNotes());
        Set<Contact> secondMeetingContacts = meetings.get(1).getContacts();
        assertTrue(contactFound(secondMeetingContacts,"Patrick Bateman"));
    }
    /**
     * Testing getting the future meetings of a given contact
     *
     * Should @return a list containing meetings chronologically sorted
     */
    @Test
    public void getPastMeetingListWithContactSortedList() {
        newContacts = manager.getContacts(1);
        String meetingNote = "Decent table in Espace, the relief washed over me in an awesome wave ";
        String meetingNote2 = "Squash";
        String meetingNote3 = "The menu was in braille";
        String meetingNote4 = "I can believe that Price prefers Van Patten's card to mine";

        //create dates
        Calendar pastDate2 = new GregorianCalendar(2012, 11, 11);
        Calendar pastDate3 = new GregorianCalendar(2013, 11, 11);
        Calendar pastDate4 = new GregorianCalendar(2014, 11, 11);

        //create meetings(not added in order)
        manager.addNewPastMeeting(newContacts, pastDate3, meetingNote3);
        manager.addNewPastMeeting(contacts, pastDate, meetingNote);
        manager.addNewPastMeeting(newContacts, pastDate4, meetingNote4);
        manager.addNewPastMeeting(newContacts, pastDate2, meetingNote2);

        //creating contact object
        Contact bateman = findContact(contacts,"Patrick Bateman");

        //getting list with contacts meetings
        List<PastMeeting> meetings = manager.getPastMeetingList(bateman);

        //tests
        assertEquals(4, meetings.size());
        assertEquals(pastDate, meetings.get(0).getDate());
        assertEquals(pastDate2, meetings.get(1).getDate());
        assertEquals(pastDate3, meetings.get(2).getDate());
        assertEquals(pastDate4, meetings.get(3).getDate());
    }
    /**
     * Testing getting the past meetings of contact with no previous meeting
     *
     * Should @return an empty list
     */
    @Test
    public void getPastMeetingListNoMeetings() {
        Contact bateman = findContact(contacts,"Patrick Bateman");
        List<PastMeeting> meetings = manager.getPastMeetingList(bateman);
        assertTrue(meetings.isEmpty());
    }
    /**
     * Testing getting the past meetings of an unknown contact
     *
     * Should @throw a IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void getPastMeetingListUnknownContact() {
        Contact halberstram = new ContactImpl(4, "Marcus Halberstram");
        manager.getPastMeetingList(halberstram);
    }
    /**
     * Testing getting the future meetings on a given date
     *
     * Should @return a list containing meetings chronologically sorted
     */
    @Test
    public void testGetFutureMeetingListWithDate() {
        manager.addNewContact("Craig McDermott","Knows the maitre'd at Canal Bar");
        newContacts = manager.getContacts(4);

        //create dates (futureDate with times added) n.b. 0-based for month
        Calendar futureDate1 = new GregorianCalendar(2015, 11, 11, 11, 00);
        Calendar futureDate2 = new GregorianCalendar(2015, 11, 11, 11, 30);
        Calendar futureDate3 = new GregorianCalendar(2015, 11, 11, 14, 45);
        Calendar futureDate4 = new GregorianCalendar(2015, 11, 11, 17, 15);
        Calendar futureDate5 = new GregorianCalendar(2016, 11, 11, 11, 00);


        //create meetings (not added in order)
        manager.addFutureMeeting(contacts, futureDate4);
        manager.addFutureMeeting(newContacts, futureDate1);
        manager.addFutureMeeting(contacts, futureDate5);
        manager.addFutureMeeting(newContacts, futureDate2);
        manager.addFutureMeeting(contacts, futureDate3);


        //getting list with meetings on futureDate
        List<Meeting> meetings = manager.getFutureMeetingList(futureDate);

        //tests
        assertEquals(4, meetings.size());

        //check list is chronologically sorted
        assertEquals(futureDate1, meetings.get(0).getDate());
        assertEquals(futureDate2, meetings.get(1).getDate());
        assertEquals(futureDate3, meetings.get(2).getDate());
        assertEquals(futureDate4, meetings.get(3).getDate());
    }
    /**
     * Testing getting the past meetings on a given date
     *
     * Should @return a list containing meetings chronologically sorted
     */
    @Test
    public void testGetFutureMeetingListWithPastDate() {
        String meetingNote1 = "Huey Lewis and the News's early work was a little too new wave for my taste";
        String meetingNote2 = "I was wearing a double-breasted wool cavalry twill suit by Givenchy";
        String meetingNote3 = "Table at Camols - non-smoking section";
        String meetingNote4 = "Went to a Matinee of Les Miz";
        String meetingNote5 = "Black-tie party at the Puck building for a new brand of computerised rowing machines";
        manager.addNewContact("Marcus Halberstram","Marcus and I go to the same barber, although I have a slightly better haircut");
        newContacts = manager.getContacts(4);

        //create dates (pastDate with times added) n.b. 0-based for month
        Calendar pastDate1 = new GregorianCalendar(2010, 11, 11, 11, 00);
        Calendar pastDate2 = new GregorianCalendar(2010, 11, 11, 17, 30);
        Calendar pastDate3 = new GregorianCalendar(2011, 11, 11, 11, 00);
        Calendar pastDate4 = new GregorianCalendar(2011, 11, 11, 12, 15);
        Calendar pastDate5 = new GregorianCalendar(2011, 11, 11, 17, 30);

        //create meetings (not added in order)
        manager.addNewPastMeeting(contacts, pastDate2, meetingNote2);
        manager.addNewPastMeeting(newContacts, pastDate1, meetingNote1);
        manager.addNewPastMeeting(contacts, pastDate5, meetingNote5);
        manager.addNewPastMeeting(newContacts, pastDate3, meetingNote3);
        manager.addNewPastMeeting(contacts, pastDate4, meetingNote4);

        //getting list with meetings on pastDate
        List<Meeting> meetings = manager.getFutureMeetingList(pastDate);

        //tests
        assertEquals(3, meetings.size());

        //check list is chronologically sorted
        assertEquals(pastDate3, meetings.get(0).getDate());
        assertEquals(pastDate4, meetings.get(1).getDate());
        assertEquals(pastDate5, meetings.get(2).getDate());
    }
    /**
     * Testing getting the future meetings on a given date with no scheduled meetings
     *
     * Should @return empty lists
     */
    @Test
    public void testGetFutureMeetingListWithDateNoMeetings() {
        manager.addNewContact("David Van Patten","Card is Eggshell with Romalian type");
        newContacts = manager.getContacts(4);
        String meetingNote = "Went to Nell's with three models from Elite";

        //create dates
        Calendar pastDate1= new GregorianCalendar(2013, 11, 11, 11, 11);
        Calendar pastDate2= new GregorianCalendar(2014, 11, 11, 11, 11);
        Calendar futureDate1 = new GregorianCalendar(2015, 11, 11, 11, 11);
        Calendar futureDate2 = new GregorianCalendar(2016, 11, 11, 11, 11);

        //create meetings
        manager.addNewPastMeeting(contacts, pastDate1, meetingNote);
        manager.addFutureMeeting(newContacts, futureDate1);

        //getting list with meetings
        List<Meeting> pastMeetings = manager.getFutureMeetingList(pastDate2);
        List<Meeting> futureMeetings = manager.getFutureMeetingList(futureDate2);

        //tests
        assertTrue(pastMeetings.isEmpty());
        assertTrue(futureMeetings.isEmpty());
    }
     /*
    * BREAK HERE
    *
    *
    * NEW TEST BATCH
    *
    *
    */
    /**
     * Testing adding meeting notes to past meeting with notes.
     *
     * Should @return a concatenation of Strings meetingNote1+space+meetingNote2
     */
    @Test
    public void testAddMeetingNotesPastMeetingWithNotes() {
        String meetingNote1 = "Reservation under the name Marcus Halberstram";
        String meetingNote2 = "The mud soup and charcoal arugula were outrageous";
        String space = ". ";
        manager.addNewPastMeeting(contacts, pastDate, meetingNote1);
        manager.addMeetingNotes(1, meetingNote2);

        assertEquals(meetingNote1+space+meetingNote2, manager.getPastMeeting(1).getNotes());
    }
    /**
     * Testing adding meeting notes to past meeting with notes.
     *
     * Should @return the String meetingNote
     */
    @Test
    public void testAddMeetingNotesPastMeetingWithoutNotes() {
        String meetingNote = "We should have gone to Dorsia,I could have gotten us a table";
        //n.b. need to edit constructor in MeetingImpl
        manager.addNewPastMeeting(contacts, pastDate,"");
        manager.addMeetingNotes(1, meetingNote);

        assertEquals(meetingNote, manager.getPastMeeting(1).getNotes());
    }
    /**
     * Testing adding meeting notes to an already held future meeting.
     *
     * Should @return the String meetingNote (also checks for instance of PastMeeting)
     */
    @Test
    public void testAddMeetingNotesFutureMeeting() {
        Calendar thisPreciseMoment = new GregorianCalendar();
        manager.addFutureMeeting(contacts, thisPreciseMoment);
        String meetingNote = "We're going to Nell's. Gwendolyn's father is buying it";
        manager.addMeetingNotes(1, meetingNote);

        //test not vital due to getPastMeeting call below - added for clarity
        assertTrue(manager.getPastMeeting(1) instanceof PastMeeting);
        assertEquals(meetingNote, manager.getPastMeeting(1).getNotes());
    }
    /**
     * Testing adding meeting notes a non existing meeting
     *
     * Should @throw an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddMeetingNotesNonExistentMeeting() {
        String meetingNote = "A bold-striped shirt calls for solid-colored or discreetly patterned suits and ties";
        manager.addMeetingNotes(11, meetingNote);
    }
    /**
     * Testing adding meeting notes to a yet to be held future meeting.
     *
     * Should @throw an IllegalStateException
     */
    @Test(expected = IllegalStateException.class)
    public void testAddMeetingNotesNotYetHeldFutureMeeting() {
        String meetingNote = "mulling over business problems, examining opportunities, exchanging rumors, spreading gossip";
        manager.addFutureMeeting(contacts, futureDate);
        manager.addMeetingNotes(1, meetingNote);
    }
    /**
     * Testing adding null meeting notes.
     *
     * Should @throw an NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void testAddNullMeetingNotes() {
        manager.addNewPastMeeting(contacts, pastDate,"");
        manager.addMeetingNotes(1, null);
    }
//    /**
//     * Testing adding empty meeting notes.
//     *
//     * Should @throw an NullPointerException
//     */
//    @Test(expected = NullPointerException.class)
//    public void testAddEmptyMeetingNotes() {
//        String emptyNotes = "";
//        manager.addNewPastMeeting(contacts, pastDate,"");
//        manager.addMeetingNotes(1, emptyNotes);
//    }
    /**
     * Testing set contact id.
     *
     * Should @return the id number 26 (after adding 26 contacts - A-Z)
     */
    @Test
    public void testContactMeetingId() {
        ContactManager newManager = new ContactManagerImpl();
        //looping through alphabet
        for(char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            //converts each char to a string - to add to CM
            String str = new String(new char[] {alphabet});
            newManager.addNewContact(str , str.toLowerCase() );
        }
        newContacts = newManager.getContacts(26);
        assertTrue(contactFound(newContacts, "Z"));
        assertEquals(26, findContact(newContacts, "Z").getId());
        assertEquals("z", findContact(newContacts, "Z").getNotes());
    }
    /**
     * Testing set meeting id.
     *
     * Should @return the id number 1000 (after adding 1000 meetings)
     */
    @Test
    public void testSetMeetingId() {
        Calendar today = new GregorianCalendar();
        for(int i=0; i<1000; i++){
            //increment the day by 1 for a 1000 days
            today.add(Calendar.DAY_OF_YEAR,1);
            manager.addFutureMeeting(contacts,today);
        }
        Contact bateman = findContact(contacts,"Patrick Bateman");
        List<Meeting> meetings = manager.getFutureMeetingList(bateman);

        assertEquals(1000,meetings.get(999).getId());
    }
    /**
     * Testing that programme data can be saved and retrieved from disk
     *
     * Should @return all data from the original Manager
     */
    @Test
    public void testFlush() {
        String note = "I had to return some videotapes";
        //adds two meets to current manager
        manager.addNewPastMeeting(contacts, pastDate, note);
        manager.addFutureMeeting(contacts, futureDate);

        //saves the date of current manager to disk
        manager.flush();

        //new manager session
        ContactManager restoredManager = new ContactManagerImpl();

        //get the resorted contacts
        newContacts = restoredManager.getContacts(1,2,3);

        //checking all contacts present
        assertTrue(contactFound(newContacts,"Patrick Bateman"));
        assertTrue(contactFound(newContacts, "Paul Owen"));
        assertTrue(contactFound(newContacts, "Timothy Price"));

        //checking past meeting data
        assertEquals(pastDate, restoredManager.getPastMeeting(1).getDate());
        assertEquals(note, restoredManager.getPastMeeting(1).getNotes());
        Set<Contact> pastMeetingContacts = manager.getPastMeeting(1).getContacts();
        assertTrue(contactFound(pastMeetingContacts,"Patrick Bateman"));
        assertTrue(contactFound(pastMeetingContacts, "Paul Owen"));
        assertTrue(contactFound(pastMeetingContacts, "Timothy Price"));

        //checking future meeting data
        assertEquals(futureDate, restoredManager.getFutureMeeting(2).getDate());
        Set<Contact> futureMeetingContacts = manager.getPastMeeting(2).getContacts();
        assertTrue(contactFound(futureMeetingContacts,"Patrick Bateman"));
        assertTrue(contactFound(futureMeetingContacts, "Paul Owen"));
        assertTrue(contactFound(futureMeetingContacts, "Timothy Price"));
    }
}