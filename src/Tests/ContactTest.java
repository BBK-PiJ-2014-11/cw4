package Tests;

import Implementations.ContactImpl;
import Interfaces.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 *@author Ehshan Veerabangsa
 */
public class ContactTest {

    private Contact testContact;
    private final int testId = 1;
    private final String testName = "Marcus Halberstram";
    private final String testNote = "Marcus and I go to the same barber, although I have a slightly better haircut.";
    /**
     * Test contact constructor
     */
    @Before
    public void setUp() {
        testContact = new ContactImpl(testId , testName, testNote);
    }
    /**
     * Removing Test contact
     */
    @After
    public void tearDown() {
        testContact = null;
    }
    /**
     * Testing get Id
     */
    @Test
    public void testGetId() {
        assertEquals(testId, testContact.getId());
    }
    /**
     * Testing get name
     */
    @Test
    public void testGetName() {
        assertEquals(testName, testContact.getName());
    }
    /**
     * Testing get notes
     */
    @Test
    public void testGetNotes() {
        assertEquals(testNote, testContact.getNotes());
    }
    /**
     * Testing add notes
     */
    @Test
    public void testAddNotes() {
        String newNote = "He also has a penchant for Valentino suits and Oliver Peoples glasses.";
        String space = " ";
        testContact.addNotes(newNote);
        assertEquals(testNote+space+newNote, testContact.getNotes());
    }
    /**
     * Testing get empty notes
     *
     * should @return an empty String
     */
    @Test
    public void testNoNotes() {
        String emptyNote = "";
        Contact newContact = new ContactImpl(2, "Craig McDermott",emptyNote );
        assertEquals(emptyNote, newContact.getNotes());
    }
}