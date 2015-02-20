package Implementations;

import Interfaces.Contact;
import Interfaces.Meeting;

import java.util.Calendar;
import java.util.Set;

public class MeetingImpl implements Meeting {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Calendar getDate() {
        return null;
    }

    @Override
    public Set<Contact> getContacts() {
        return null;
    }
}
