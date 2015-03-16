package Implementations;

import Interfaces.Contact;
import Interfaces.Meeting;
import Interfaces.PastMeeting;
import Interfaces.XMLHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
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
    private XPath path;

    public XMLHandlerImpl(String file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
            File f = new File(file);
            doc = builder.parse(f);
            XPathFactory xpFactory = XPathFactory.newInstance();
            path = xpFactory.newXPath();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void createDocument(Set<Contact> contacts, List<Meeting> meetings, int currentContactId, int currentMeetingId) {
        Element rootElement = doc.createElement("contactManager");
        doc.appendChild(rootElement);
        rootElement.appendChild(createContactListElement(contacts));
        rootElement.appendChild(createMeetingListElement(meetings));
        rootElement.appendChild(createTextElement("currentContactId", ""+currentContactId));
        rootElement.appendChild(createTextElement("currentMeetingId", ""+currentMeetingId));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeFile(String file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD,"xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "5");
            //transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int parseContactId() {
        int currentContactId = 0;
        try {
            currentContactId = Integer.parseInt(path.evaluate("/contactManager/currentContactId", doc));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return currentContactId;
    }

    @Override
    public int parseMeetingId() {
        int currentMeetingId = 0;
        try {
            currentMeetingId  = Integer.parseInt(path.evaluate("/contactManager/currentMeetingId", doc));
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return currentMeetingId ;
    }

    @Override
    public Set<Contact> parseContacts() {
        return null;
    }

    @Override
    public List<Meeting> parseMeetings(Set<Contact> contacts) throws XPathExpressionException {
        return null;
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
        Element ele = doc.createElement("contactList");
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
        int year = meeting.getDate().get(Calendar.YEAR);
        int month = meeting.getDate().get(Calendar.MONTH);
        int day = meeting.getDate().get(Calendar.DAY_OF_MONTH);
        int hour = meeting.getDate().get(Calendar.HOUR_OF_DAY);
        int minute = meeting.getDate().get(Calendar.MINUTE);
        ele.appendChild(createTextElement("date", ""+year+"-"+month+"-"+day));
        //if no time set - omit field from doc (no meetings at midnight)
        if(!(hour == 0 && minute == 0)){
            ele.appendChild(createTextElement("time",""+hour+":"+minute));
        }
        ele.appendChild(doc.createElement("contacts"));
        for(Contact contact : meeting.getContacts()){
            ele.appendChild(createTextElement("contacts", ""+contact.getName()));
        }
        if(meeting instanceof PastMeeting){
            PastMeeting pastMeeting = (PastMeeting)meeting;
            ele.appendChild(createTextElement("notes",""+ pastMeeting.getNotes()));
        }
        return ele;
    }
    /**
     * Builds a DOM element for a List of meetings.
     *
     * @param meetings the list of meetings.
     * @return a DOC element describing the meetings.
     */
    private Element createMeetingListElement(List<? extends Meeting> meetings){
        Element ele = doc.createElement("meetingList");
        for(Meeting meeting : meetings){
            ele.appendChild(createMeetingElement(meeting));
        }
        return ele;
    }
}
