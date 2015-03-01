package Tests;

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
        contacts = new HashSet<Contact>();
    }
    /**
     * Removing manager and contact set
     */
    @After
    public void tearDown() {
        manager = null;
        contacts = null;
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
        assertEquals(newName, newManager.getContacts(1).getName());
        assertEquals(newNote, newManager.getContacts(1).getNotes());
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
        contacts = manager.getContacts(1,2,3);
        assertEquals(3,contacts.size());

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
     * Testing getting contact by string
     *
     * Should @return the size of the contact set and the boolean found
     */
    @Test
    public void testGetContactsString() {
        boolean found = false;
        contacts = manager.getContacts("Patrick Bateman");
        for (Contact person : contacts) {
            if (person.getName().contains("Patrick Bateman")) {
                found = true;
            }
        }
        assertEquals(1, contacts.size());
        assertTrue(found);
    }
    /**
     * Testing getting and unknown contact by string
     *
     * Should @return the size of the contact set and the boolean found
     */
    @Test
    public void testGetContactsInvalidString() {
        boolean found = false;
        contacts = manager.getContacts("Harold Carnes");
        assertTrue(contacts.isEmpty());
        for (Contact person : contacts) {
            if (person.getName().contains("Harold Carnes")) {
                found = true;
            }
        }
        assertTrue(contacts.isEmpty());
        assertFalse(found);
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
}