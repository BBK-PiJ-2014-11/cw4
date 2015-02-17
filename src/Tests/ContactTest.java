package Tests;

import Interfaces.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    private static final int newId = 11;
    private static final String newName = "Marcus Halberstram";
    private static final String newNote = "Marcus and I go to the same barber, although I have a slightly better haircut";
    private Contact newContact;



    @Before
    public void setUp() {
        newContact = new ContactImpl(newId , newName, newNote);
    }

    @After
    public void tearDown() {
        newContact = null;
    }

    @Test
    public void testGetId() {
        assertEquals(newId, newContact.getId());
    }

    @Test
    public void testGetName() {
        assertEquals(newName, newContact.getName());
    }

    @Test
    public void testGetNotes() {
        assertEquals(newNote, newContact.getNotes());
    }

    @Test
    public void testAddNotes() {
        newContact.addNotes("He also has a penchant for Valentino suits and Oliver Peoples glasses");
        assertEquals(newContact.getNotes(), "Marcus and I go to the same barber, although I have a slightly better haircut. He also has a penchant for Valentino suits and Oliver Peoples glasses");
    }
}