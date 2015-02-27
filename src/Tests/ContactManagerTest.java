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

public class ContactManagerTest {

    private ContactManager manager;
    private Set<Contact> contacts;
    private final Calendar pastDate = new GregorianCalendar(2011,11,11);
    private final Calendar futureDate = new GregorianCalendar(2015,11,11);

    @Before
    public void setUp() {
        manager = new ContactManagerImpl();
        manager.addNewContact("Patrick Bateman", "A big Genesis fan ever since the release of their 1980 album 'Duke'");
        manager.addNewContact("Paul Owen","Handling the Fisher account...lucky b******");
        manager.addNewContact("Timothy Price","He presents himself as a harmless old codger. But inside…");
        contacts = new HashSet<Contact>();
    }

    @After
    public void tearDown() {
        manager = null;
        contacts = null;
    }

    @Test
    public void testAddNewContact(){
        ContactManager newManager = new ContactManagerImpl();
        String newName = "Donald Kimble";
        String newNote = "Lunch meeting at the Four Seasons";
        newManager.addNewContact(newName,newNote);
        assertEquals(newName, newManager.getContacts(0).getName());
        assertEquals(newNote, newManager.getContacts(0).getNotes());
    }


    @Test (expected = NullPointerException.class)
    public void testAddContactWithoutName(){
        String newName = null;
        String newNote = "A guy who looks a lot like Luis Carruthers";
        manager.addNewContact(newName,newNote);
    }

    @Test (expected = NullPointerException.class)
    public void testAddContactWithoutNotes(){
        String newName = "Luis Carruthers";
        String newNote = null;
        manager.addNewContact(newName,newNote);
    }

    @Test
    public void testGetContactsId(){
        contacts = manager.getContacts(0,1,2);
        assertEquals(3,contacts.size());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testGetContactsInvalidId(){
       manager.getContacts(-1);
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