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
    private Contact newContact;
    private final int testId = 1;
    private final int newId = 2;
    private final String testName = "Marcus Halberstram";
    private final String newName = "Timothy Price";
    private final String testNote = "Marcus and I go to the same barber, although I have a slightly better haircut.";
    /**
     * Test contact constructor
     */
    @Before
    public void setUp() {
        testContact = new ContactImpl(testId , testName, testNote);
        newContact = new ContactImpl(newId, newName);
    }
    /**
     * Removing Test contact
     */
    @After
    public void tearDown() {
        testContact = null;
        newContact = null;
    }
    /**
     * Testing get Id
     *
     * Should @return the int testId
     */
    @Test
    public void testGetId() {
        assertEquals(testId, testContact.getId());
    }
    /**
     * Testing get name
     *
     * Should @return the String testName
     */
    @Test
    public void testGetName() {
        assertEquals(testName, testContact.getName());
    }
    /**
     * Testing get notes
     *
     * Should @return the String testNote
     */
    @Test
    public void testGetNotes() {
        assertEquals(testNote, testContact.getNotes());
    }
    /**
     * Testing add notes
     *
     * Should @return concatenation of Strings testNote+space+newNote
     */
    @Test
    public void testAddNotes() {
        String newNote = "He also has a penchant for Valentino suits and Oliver Peoples glasses.";
        String space = " ";
        testContact.addNotes(newNote);
        assertEquals(testNote+space+newNote, testContact.getNotes());
    }
    /**
     * Testing get note for contact without initial notes
     *
     * should @return an empty String
     */
    @Test
    public void testNoNotes() {
        String emptyNote = "";
        assertEquals(emptyNote, newContact.getNotes());
    }
}