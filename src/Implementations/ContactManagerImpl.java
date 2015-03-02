package Implementations;

import Interfaces.*;

import java.util.*;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class ContactManagerImpl implements ContactManager {

    private Set<Contact> contacts;
    private List<Meeting> meetings;
    private int currentContactId;
    private int currentdMeetingId;
    /**
     * ContactManagerImpl class constructor
     *
     * Create a new manager.
     */
    public ContactManagerImpl(){
        contacts = new HashSet<Contact>();
        meetings = new ArrayList<Meeting>();
        currentContactId = 0;
        currentdMeetingId = 0;
    }
    /**
     * Methods to assign a contact id, increments value every time called
     */
    private int setContactId() {
        return ++this.currentContactId;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
       //TODO
        return 0;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeeting(int id) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        //TODO
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        //TODO
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewContact(String name, String notes) {
        if (name.equals(null)|| notes.equals(null)) {
            throw new NullPointerException();
        }
        Contact newContact = new ContactImpl(setContactId(),name,notes);
        contacts.add(newContact);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(int... ids) {
        Set<Contact> foundContacts = new HashSet<>();
        for (int id : ids){
            for (Contact person : contacts) {
                if (person.getId() == id) {
                    foundContacts.add(person);
                }
            }
        }
        if(foundContacts.isEmpty()){
            throw new IllegalArgumentException();
        }
        return foundContacts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        //TODO
        return null;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        //TODO
    }
}
