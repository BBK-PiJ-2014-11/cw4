package Interfaces;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * A class read and write data to and from an XML file
 */
public interface XMLHandler {
    /**
     * Checks if a file for Contact Manager exists and reads contents
     * to a Document Object
     *
     * If no file exists a new one is created
     */
    public void readFile();
    /**
     * Parses an XML file containing Contact Manager data
     *
     * @param file the file to be read and parsed
     */
    public void parseData(File file);
    /**
     * Creates a Document Object from contact manager data
     */
    public void createDocument(Set<Contact> contacts, List<Meeting> meetings, Calendar today, int currentContactId, int currentMeetingId);
    /**
     * Writes a Document Object to an XML file
     *
     * @param file the file to be written to
     */
    public void writeFile(String file);
}
