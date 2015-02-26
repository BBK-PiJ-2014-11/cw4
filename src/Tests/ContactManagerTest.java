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
}