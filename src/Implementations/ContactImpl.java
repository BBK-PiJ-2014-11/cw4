package Implementations;

import Interfaces.Contact;
/**
 * {@inheritDoc}
 * 
 * @author Ehshan Veerabangsa
 */
public class ContactImpl implements Contact {

    private int id;
    private String name;
    private String notes;
    /**
     * ContactImpl class constructor
     *
     * Creates a new contact
     *
     * @param id the contact's (unique) id number
     * @param name the contacts name
     * @param notes notes about the contact (any level of abstraction)
     */
    // n.b. need to review whether notes should be used as a parameter
    public ContactImpl(int id, String name, String notes ) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }

    public ContactImpl(int id, String name ) {
        this.id = id;
        this.name = name;
        this.notes = "";
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
    public String getName() {
        return name;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getNotes() {
        return notes;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void addNotes(String note) {
        String space = " ";
        this.notes = this.notes+space+note;
    }
}
