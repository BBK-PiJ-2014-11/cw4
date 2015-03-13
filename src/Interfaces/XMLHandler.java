package Interfaces;

import java.io.File;
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
}
