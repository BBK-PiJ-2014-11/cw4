package Tests;

import Interfaces.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    private Contact testContact;
    private final int testId = 1;
    private final String testName = "Marcus Halberstram";
    private final String testNote = "Marcus and I go to the same barber, although I have a slightly better haircut.";


    @Before
    public void setUp() {
        testContact = new ContactImpl(testId , testName, testNote);
    }

    @After
    public void tearDown() {
        testContact = null;
    }

    @Test
    public void testGetId() {
        assertEquals(testId, testContact.getId());
    }

    @Test
    public void testGetName() {
        assertEquals(testName, testContact.getName());
    }

    @Test
    public void testGetNotes() {
        assertEquals(testNote, testContact.getNotes());
    }

    @Test
    public void testAddNotes() {
        String newNote = "He also has a penchant for Valentino suits and Oliver Peoples glasses.";
        testContact.addNotes(newNote);
        assertEquals(testContact.getNotes(),testNote+newNote);
    }

    @Test
    public void testNoNotes() {
        Contact newContact = new ContactImpl(2, "Craig McDermott");
        String emptyNote = "";
        assertEquals(newContact.getNotes(),emptyNote);
    }
}