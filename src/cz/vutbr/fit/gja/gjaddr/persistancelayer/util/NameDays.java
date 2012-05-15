
package cz.vutbr.fit.gja.gjaddr.persistancelayer.util;

import cz.vutbr.fit.gja.gjaddr.persistancelayer.Settings;
import cz.vutbr.fit.gja.gjaddr.util.LangUtil;
import cz.vutbr.fit.gja.gjaddr.util.LoggerUtil;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * File storing name days.
 *
 * @author Bc. Drahomira Herrmannova <xherrm01@stud.fit.vutbr.cz>
 */
public class NameDays {
    
    /**
     * Instance of the class.
     */
    private static NameDays instance = null;
    
    /**
     * HashMap of name days.
     */
    private static HashMap<String, Calendar> nameDays = new HashMap<String, Calendar>();
    
    /**
     * Name days without diacritic marks.
     */
    private static HashMap<String, Calendar> noDiacritics = new HashMap<String, Calendar>();
    
    /**
     * Says if name days were successfully loaded from XML.
     */
    private static boolean loaded = false;
    
    /**
     * Constructor. Loads name days from XML.
     */
    private NameDays() {
        try {
            // load name days file path from settings
            String nameDaysFile = Settings.instance().getNameDaysFile();
            this.parseNameDaysFile(nameDaysFile);
            NameDays.loaded = true;
        } catch (SAXException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
        } catch (IOException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
        } catch (ParserConfigurationException ex) {
            LoggerFactory.getLogger(this.getClass()).error(LoggerUtil.getStackTrace(ex));
        }
    }

    /**
     * Load name days from XML file.
     * 
     * @param nameDaysFile XML file with name days
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException 
     */
    private void parseNameDaysFile(String nameDaysFile) throws IOException, SAXException, ParserConfigurationException {
        File fXmlFile = new File(nameDaysFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        // each name day is in a ROW node
        NodeList nList = doc.getElementsByTagName("ROW"); 
        for (int temp = 0; temp < nList.getLength(); temp++) {
           Node nNode = nList.item(temp);
           if (nNode.getNodeType() == Node.ELEMENT_NODE) {
              Element eElement = (Element) nNode;
              Integer day = Integer.parseInt(this.getTagValue("den", eElement));
              Integer month = Integer.parseInt(this.getTagValue("mesic", eElement));
              String name = this.getTagValue("jmeno", eElement);
              Calendar date = Calendar.getInstance();
              date.set(Calendar.MONTH, month - 1);
              date.set(Calendar.DAY_OF_MONTH, day);
              NameDays.nameDays.put(name, date);
              NameDays.noDiacritics.put(LangUtil.removeDiacritics(name), date);
           }
        }
    }
    
    /**
     * Get child node from node.
     * 
     * @param sTag child node name
     * @param eElement node
     * @return 
     */
    private String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        return nValue.getNodeValue();
    }
    
    /**
     * Get name day for a name.
     * 
     * @param name
     * @return 
     */
    public Calendar getNameDay(String name) {
        if (name == null) {
            return null;
        }
        if (!NameDays.loaded) {
            return null;
        }
        Calendar nameDay = NameDays.nameDays.get(name);
        if (nameDay == null) {
            return NameDays.noDiacritics.get(LangUtil.removeDiacritics(name));
        } else {
            return nameDay;
        }
    }
    
    /**
     * Get instance of NameDays class.
     * 
     * @return 
     */
    public static NameDays getInstance() {
        if (NameDays.instance == null) {
            NameDays.instance = new NameDays();
        }
        return NameDays.instance;
    }
    
    /**
     * Test the class.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        Calendar nameDay = NameDays.getInstance().getNameDay("Drahomíra");
        System.out.println(nameDay.toString());
    }
    
}
