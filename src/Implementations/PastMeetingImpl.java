package Implementations;

import Interfaces.Contact;
import Interfaces.PastMeeting;

import java.util.Calendar;
import java.util.Set;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;
    /**
     * PastMeetingImpl class constructor
     *
     * Creates a new past meeting
     *
     * @param id the meeting's (unique) id number
     * @param date the date of the meeting
     * @param contacts the set of contacts attending the meeting
     * @param notes notes about what happened and what was agreed
     */
    // n.b. need to review whether notes should be used as a parameter - or possibly use multiple constructors
    public PastMeetingImpl(int id, Calendar date,Set<Contact> contacts, String notes) {
        super(id,date,contacts);
        this.notes = notes;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return notes;
    }

}
