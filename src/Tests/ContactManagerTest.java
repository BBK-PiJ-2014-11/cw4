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
        contacts = new HashSet<Contact>();
    }

    @After
    public void tearDown() {
        manager = null;
        contacts = null;
    }

    @Test
    public void testAddNewContact(){
        String newName = "Donald Kimble";
        String newNote = "Lunch meeting at the Four Seasons";
        manager.addNewContact(newName,newNote);
        assertEquals(newName, manager.getContacts(0).getName());
        assertEquals(newNote, manager.getContacts(0).getNotes());
    }

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
}