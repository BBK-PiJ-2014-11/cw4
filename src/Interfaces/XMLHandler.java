package Interfaces;

import javax.xml.xpath.XPathExpressionException;
import java.util.List;
import java.util.Set;

/**
 * A class read and write data to and from an XML file
 */
public interface XMLHandler {
    /**
     * Creates a Document Object from contact manager data
     *
     * @param contacts the set of contacts from CM
     * @param meetings the list of meetings from CM
     * @param currentContactId the last assigned contact id
     * @param currentMeetingId the last assigned meeting id
     */
    public void createDocument(Set<Contact> contacts, List<Meeting> meetings, int currentContactId, int currentMeetingId);
    /**
     * Writes a Document Object to an XML file
     *
     * @param file the file to be written to
     */
    public void writeFile(String file);
    /**
     * Parses the current stored value of contact Id
     *
     * @return the last assigned contact Id
     */
    public int parseContactId();
    /**
     * Parses the current stored value of meeting Id
     *
     * @return the last assigned meeting Id
     */
    public int parseMeetingId();
    /**
     * Parses each stored contact and place them into a contact set
     *
     * @return a set of contacts.
     */
    public Set<Contact> parseContacts();
    /**
     * Parses each stored meeting and place them into a meeting list
     *
     * @return the List of meetings.
     */
    public List<Meeting> parseMeetings(Set<Contact> contacts) throws XPathExpressionException;
}
