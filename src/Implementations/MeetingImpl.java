package Implementations;

import Interfaces.Contact;
import Interfaces.Meeting;

import java.util.Calendar;
import java.util.Set;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class MeetingImpl implements Meeting {

    private int id;
    private Calendar date;
    private Set<Contact> contacts;
    /**
     * MeetingImpl class constructor
     *
     * Creates a new meeting
     *
     * @param id the meeting's (unique) id number
     * @param date the date of the meeting
     * @param contacts the set of contacts attending the meeting
     */
    public MeetingImpl(int id, Calendar date, Set<Contact>contacts ) {
        this.id = id;
        this.date = date;
        this.contacts = contacts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return id;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Calendar getDate() {
        return date;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts() {
        return contacts;
    }
}
