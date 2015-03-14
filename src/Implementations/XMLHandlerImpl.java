package Implementations;

import Interfaces.Contact;
import Interfaces.Meeting;
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
     * Creates a Document Object Model element to store text data
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
}
