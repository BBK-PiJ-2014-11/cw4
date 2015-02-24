package Implementations;

import Interfaces.Contact;
import Interfaces.PastMeeting;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

    private String notes;

    // n.b. need to review whether notes should be used as a parameter - or possibly use multiple constructors
    public PastMeetingImpl(int id, Calendar date,Set<Contact> contacts, String notes) {
        super(id,date,contacts);
        this.notes = notes;
    }

    @Override
    public String getNotes() {
        return notes;
    }

}
