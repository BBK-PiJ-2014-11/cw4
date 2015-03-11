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
    private Calendar today;
    private int currentContactId;
    private int currentMeetingId;
    /**
     * ContactManagerImpl class constructor
     *
     * Create a new manager.
     */
    public ContactManagerImpl(){
        contacts = new HashSet<Contact>();
        meetings = new ArrayList<Meeting>();
        today = new GregorianCalendar();
        currentContactId = 0;
        currentMeetingId = 0;
    }
    /**
     * Methods to assign a contact id, increments value every time called
     */
    private int setContactId() {
        return ++this.currentContactId;
    }
    /**
     * Methods to assign a meeting id, increments value every time called
     */
    private int setMeetingId() {
        return ++this.currentMeetingId;
    }
    /**
     * {@inheritDoc}
     */
    //bugs fixed
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (date.before(today)){
            throw new IllegalArgumentException();
        }
        if (!this.contacts.containsAll(contacts) || contacts.isEmpty()){
            throw new IllegalArgumentException();
        }
        MeetingImpl newMeeting = new FutureMeetingImpl(setMeetingId(), date, contacts);
        meetings.add(newMeeting);
        return newMeeting.getId();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        Meeting meeting = getMeeting(id);
        if (meeting == null) {
            return null;
        }
        if (meeting.getDate().after(today)){
            throw new IllegalArgumentException();
        }
        return (PastMeeting)meeting;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        Meeting meeting = getMeeting(id);
        if (meeting == null) {
            return null;
        }
        if (meeting.getDate().before(today)){
            throw new IllegalArgumentException();
        }
        return (FutureMeeting)meeting;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Meeting getMeeting(int id) {
        for(Meeting meeting : meetings){
            if(meeting.getId()== id){
                return meeting;
            }
        }
        return null;
    }
    /**
     * {@inheritDoc}
     */
    //N.B. remember to add sorting function
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        if (!contacts.contains(contact)) {
            throw new IllegalArgumentException();
        }
        List<Meeting> contactMeetings = new ArrayList<Meeting>();
        for(Meeting meeting : meetings) {
            if (meeting.getDate().after(today) && meeting.getContacts().contains(contact)) {
                contactMeetings.add(meeting);
            }
        }
        Collections.sort(contactMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return contactMeetings;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        List<Meeting> dateMeetings = new ArrayList<Meeting>();
        for (Meeting meeting : meetings) {
            Boolean datesEquals = meeting.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) &&
                    meeting.getDate().get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR);
            if(datesEquals){
                dateMeetings.add(meeting);
            }
        }
        Collections.sort(dateMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return dateMeetings;
    }
    /**
     * {@inheritDoc}
     */
    //N.B. remember to add sorting function
    //remember to test converting future to past meeting
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        if (!contacts.contains(contact)) {
            throw new IllegalArgumentException();
        }
        List<PastMeeting> contactMeetings = new ArrayList<PastMeeting>();
        for(Meeting meeting : meetings) {
            if (meeting.getDate().before(today) && meeting.getContacts().contains(contact)) {
                contactMeetings.add((PastMeeting)meeting);
            }
        }
        Collections.sort(contactMeetings, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return contactMeetings;
    }
    /**
     * {@inheritDoc}
     */
    //no checking for dates?
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (!this.contacts.containsAll(contacts) || contacts.isEmpty()){
            throw new IllegalArgumentException();
        }
        //n.b. not sure if contact check is require - review
        if (contacts == null || date == null || text == null){  //|| text.equals("")) {
            throw new NullPointerException();
        }
        meetings.add(new PastMeetingImpl(setMeetingId(), date, contacts, text));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        Meeting meeting = getMeeting(id);
        if (meeting == null) {
            throw new IllegalArgumentException();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewContact(String name, String notes) {
        if (name.equals(null) || notes.equals(null)) {
            throw new NullPointerException();
        }
        Contact newContact = new ContactImpl(setContactId(),name,notes);
        contacts.add(newContact);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    // n.b need to decide where invalid ids (i.e < 1) will be checked
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
        if (name.equals(null) || name.equals("") ) {
            throw new NullPointerException();
        }
        Set<Contact> foundContacts = new HashSet<>();
        for (Contact person : contacts){
            if(person.getName().toLowerCase().contains(name.toLowerCase())){
                foundContacts.add(person);
            }
        }
        return foundContacts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() {
        //TODO
    }
}