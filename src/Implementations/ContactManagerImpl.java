package Implementations;

import Interfaces.*;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
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
     * Contact ManagerImpl class constructor
     *
     * Create a new manager. If file contacts.xml exists, object data is loaded from it.
     * If no file exists, new objects are created
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
     *
     * Contact id numbers are specific to Contact Manager
     */
    private int setContactId() {
        return ++this.currentContactId;
    }
    /**
     * Methods to assign a meeting id, increments value every time called
     *
     * Meeting id numbers are specific to Contact Manager
     */
    private int setMeetingId() {
        return ++this.currentMeetingId;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        if (date.before(today)){
            throw new IllegalArgumentException("Date cannot be in the past");
        }
        if (!this.contacts.containsAll(contacts) || contacts.isEmpty()){
            throw new IllegalArgumentException("Those contacts are unknown");
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
            throw new IllegalArgumentException("That id corresponds to a future meeting");
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
            throw new IllegalArgumentException("That id corresponds to a past meeting");
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
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        if (!contacts.contains(contact)) {
            throw new IllegalArgumentException(contact.getName()+" is unknown");
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
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        if (!contacts.contains(contact)) {
            throw new IllegalArgumentException(contact.getName()+" is unknown");
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
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
        if (!this.contacts.containsAll(contacts) || contacts.isEmpty()){
            throw new IllegalArgumentException("Those contacts are unknown");
        }
        //removed check for empty string, past meetings do not now implicitly need notes when created
        if (contacts == null || date == null || text == null){  //|| text.equals("")) {
            throw new NullPointerException("Fields cannot be 'null'");
        }
       //not requested in the interface, but seem logical to have this check here...no?
        if (date.after(today)){
            throw new IllegalArgumentException("Date cannot be in the future");
        }
        meetings.add(new PastMeetingImpl(setMeetingId(), date, contacts, text));
    }
    /**
     * {@inheritDoc}
     *
     * N.B. text will be appended to existing notes (if any)
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        Meeting meeting = getMeeting(id);
        String meetingNote = text;
        if (meeting == null) {
            throw new IllegalArgumentException("That Meeting does not exist");
        }
        if (meeting.getDate().after(today)) {
            throw new IllegalStateException("That is a future meeting, hence is yet to be held");
        }
        if(text == null){
            throw new NullPointerException("You must enter some notes");
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
        if (name == null || notes == null) {
            throw new NullPointerException("Name or notes cannot be null");
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
            //id's start at 1 and increment by 1 for each new contact. Hence last added will == size
            if ( id < 1 || id > contacts.size()){
                throw new IllegalArgumentException("There is no contact with the id "+id);
            }
            for (Contact person : contacts) {
                if (person.getId() == id) {
                    foundContacts.add(person);
                }
            }
        }
        //will work if all id's are invalid
        if(foundContacts.isEmpty()){
            throw new IllegalArgumentException("There are no contacts with those ids");
        }
        return foundContacts;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Contact> getContacts(String name) {
        if (name == null || name.equals("") ) {
            throw new NullPointerException("Who are you looking for?");
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
            try{
                newFile.delete();
            }catch(Exception e){
                e.printStackTrace();
            }
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
    private Meeting updateMeeting(Meeting meeting, String note){
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