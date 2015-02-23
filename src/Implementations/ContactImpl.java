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
     * Creates a new contact with initial notes
     *
     * @param id the contact's (unique) id number
     * @param name the contact's name
     * @param notes notes about the contact (any level of abstraction)
     */
    // n.b. need to review whether notes should be used as a parameter
    public ContactImpl(int id, String name, String notes ) {
        this.id = id;
        this.name = name;
        this.notes = notes;
    }
    /**
     * ContactImpl class constructor
     *
     * Creates a new contact without initial notes
     *
     * @param id the contact's (unique) id number
     * @param name the contact's name
     */
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
        if(this.notes.equals("")){
            this.notes = note;
        }else {
            String space = " ";
            this.notes = this.notes + space + note;
        }
    }
}
