package Implementations;

import Interfaces.Contact;
import Interfaces.Meeting;
import Interfaces.PastMeeting;
import Interfaces.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.util.Set;
/**
 * {@inheritDoc}
 *
 * @author Ehshan Veerabangsa
 */
public class XMLHandlerImpl implements XMLHandler {
    private DocumentBuilder builder;
    private Document doc;

    public XMLHandlerImpl(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //builder = factory.newDocumentBuilder();
        doc = builder.newDocument();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void readFile() {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void parseData(File file) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void createDocument(Set<Contact> contacts, List<Meeting> meetings, int currentContactId, int currentMeetingId) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFile(File file) {

    }
    /**
     * Creates a Document Object Model(DOM) element to store text data
     *
     * @param name name of element to be created
     * @param text the text to be added to the element
     * @return an element with text information
     */
    private Element createTextElement(String name, String text){
        Text txt = doc.createTextNode(text);
        Element ele = doc.createElement(name);
        ele.appendChild(txt);
        return ele;
    }
    /**
     * Creates a DOM element for a contact.
     *
     * @param contact the contact to be stored
     * @return an element with text information about the contact
     */
    private Element createContactElement(Contact contact){
        Element ele = doc.createElement("contact");
        ele.appendChild(createTextElement("id", ""+ contact.getId()));
        ele.appendChild(createTextElement("name",""+ contact.getName()));
        ele.appendChild(createTextElement("notes",""+ contact.getNotes()));
        return ele;
    }
    /**
     * Creates a DOM element for a contact set.
     *
     * @param contacts the set to be created
     * @return a DOM element listing contacts
     */
    private Element createContactListElement(Set<Contact> contacts){
        Element ele = doc.createElement("contacts");
        for(Contact contact : contacts){
            ele.appendChild(createContactElement(contact));
        }
        return ele;
    }
    /**
     * Creates a DOM element for a meeting.
     *
     * @param meeting the meeting to be created
     * @return an element with text information about the meeting
     */
    private Element createMeetingElement(Meeting meeting){
        Element ele = doc.createElement("meeting");
        ele.appendChild(createTextElement("id", ""+meeting.getId()));
        ele.appendChild(createTextElement("date", ""+meeting.getDate()));
        ele.appendChild(doc.createElement("contacts"));
        for(Contact contact : meeting.getContacts()){
            ele.appendChild(createTextElement("contacts", ""+contact.getName()));
        }
        return ele;
    }
    /**
     * Creates a DOM element for a past meeting.
     *
     * @param meeting the past meeting to be created
     * @return an element with text information about the meeting
     */
    private Element createPastMeetingElement(PastMeeting meeting){
        Element ele = doc.createElement("meeting");
        ele.appendChild(createTextElement("id", ""+meeting.getId()));
        ele.appendChild(createTextElement("date", ""+meeting.getDate()));
        ele.appendChild(createTextElement("notes", ""+meeting.getNotes()));
        ele.appendChild(doc.createElement("contacts"));
        for(Contact contact : meeting.getContacts()){
            ele.appendChild(createTextElement("contacts", ""+contact.getName()));
        }
        return ele;
    }
}
