package Implementations;

import Interfaces.Contact;
import Interfaces.FutureMeeting;

import java.util.Calendar;
import java.util.Set;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
    /**
     * FutureMeetingImpl class constructor
     *
     * Creates a new future meeting
     *
     * @param id the meeting's (unique) id number
     * @param date the date of the meeting
     * @param contacts the set of contacts attending the meeting
     */
    public FutureMeetingImpl(int id, Calendar date,Set<Contact> contacts) {
        super(id,date,contacts);
    }
}
