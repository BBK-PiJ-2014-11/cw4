package Implementations;

import Interfaces.*;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
    private int currentMeetingId;
    private Calendar today = new GregorianCalendar();
    private String file = "contacts.xml";
    private  XMLHandler handle;
    /**
     * ContactManagerImpl class constructor
     *
     * Create a new manager.
     */
    public ContactManagerImpl(){
        File newFile = new File(file);
        if(newFile.exists()){
            try {
                handle = new XMLHandlerImpl(file);
                contacts = handle.parseContacts();
                meetings = handle.parseMeetings(contacts);
                currentContactId = handle.parseContactId();
                currentMeetingId = handle.parseMeetingId();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            }
        }else{
            contacts = new HashSet<Contact>();
            meetings = new ArrayList<Meeting>();
            currentContactId = 0;
            currentMeetingId = 0;
        }
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
        sortList(contactMeetings);
        return contactMeetings;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        List<Meeting> dateMeetings = new ArrayList<Meeting>();
        for (Meeting meeting : meetings) {
            if(datesEqual(meeting.getDate(),date)){
                dateMeetings.add(meeting);
            }
        }
        sortList(dateMeetings);
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
        sortList(contactMeetings);
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
       //not requested in the interface, but seem logical to have this check here...no?
        if (date.after(today)){
            throw new IllegalArgumentException();
        }
        meetings.add(new PastMeetingImpl(setMeetingId(), date, contacts, text));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        Meeting meeting = getMeeting(id);
        String meetingNote = text;
        if (meeting == null) {
            throw new IllegalArgumentException();
        }
        if (meeting.getDate().after(today)) {
            throw new IllegalStateException();
        }
        if(text == null){
            throw new NullPointerException();
        }
        if(meeting instanceof PastMeeting) {
            String space = ". ";
            PastMeeting pastMeeting = (PastMeeting) meeting;
            if(!pastMeeting.getNotes().equals("")){
                meetingNote = pastMeeting.getNotes() + space + text;
            }
        }
        meetings.remove(meeting);
        meetings.add(updateMeeting(meeting, meetingNote));
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
        File newFile = new File(file);
        if (newFile.exists()) {
            newFile.delete();
        }
        try {
            handle = new XMLHandlerImpl(file);
            handle.createDocument(contacts, meetings, currentContactId, currentMeetingId);
            handle.writeFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns a list of meeting sorted chronologically by meeting date
     *
     * @param meetingList a list of meetings
     * @return the meetingList arranged by date and time
     */
    private List sortList(List<? extends Meeting> meetingList) {
        Collections.sort(meetingList, new Comparator<Meeting>() {
            public int compare(Meeting o1, Meeting o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return meetingList;
    }
    /**
     * Returns a new past meeting converted from existing meeting
     * with notes added (can be either past or future)
     *
     * @param meeting the future meeting to be converted
     * @param note the note to be added to the meeting
     * @return a new past meeting with same id, date and contacts as before
     */
    public Meeting updateMeeting(Meeting meeting, String note){
        PastMeeting pastMeeting = new PastMeetingImpl(meeting.getId(),meeting.getDate(), meeting.getContacts(),note);
        return pastMeeting;
    }
    /**
     * A method to checks whether two dates are the same
     *
     * @param date1 the first calendar object
     * @param date2 the second calendar object
     * @return true if the dates are the same, false if not
     */
    private boolean datesEqual(Calendar date1, Calendar date2) {
        if (date1.get(Calendar.YEAR) != date2.get(Calendar.YEAR)) {
            return false;
        } else if (date1.get(Calendar.DAY_OF_YEAR) != date2.get(Calendar.DAY_OF_YEAR)) {
            return false;
        }
        return true;
    }
}