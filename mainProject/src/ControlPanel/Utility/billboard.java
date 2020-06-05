/*This class controls everything to do with the billboard
* which includes creating the base structure of the XML file
* as a doocument, and then converting it to an xml file for user to
* export. The class also controls editing existing xml files along
* with grabbing specific text from the xml file for each access.*/

package ControlPanel.Utility;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;

public class billboard {
    private static String filePath; //stores the path to the xml file
    private static Document document;   //stores the actual document before conversion

    /**
     * creates a default document as the base for
     * the XML file to be edited or saved to the
     * server
     *
     * @param name the name of the billboard and the file if exported
     */
    public void createXML(String name) {
        filePath = "./" + name + ".xml";

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            //root element (billboard tag)
            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    /**
     * imports the XML that is passed into the method, which works
     * for both from the server or from a local storage, and creates
     * a document to be edited and saved/exported
     *
     * @param xmlFile if the user imports the file, it is passed into this parameter
     * @param xmlString if the user imports from the server, the server output is passed in this parameter
     * @param fileOrServer determines if the import is from a file or the server
     */
    public void importXML(File xmlFile, String xmlString, String fileOrServer) {
        if (fileOrServer.equals("file")) {
            //stores the xml file
            filePath = xmlFile.getAbsolutePath();
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                document = documentBuilder.parse(xmlFile);
            } catch (ParserConfigurationException | SAXException | IOException pce) {
                pce.printStackTrace();
            }
        } else if (fileOrServer.equals("server")) {
            filePath = "./";
            stringToXml(xmlString);
        }
    }

    /**
     * adds/edits a message element to the current document
     *
     * @param msg the user input to be placed into the message element
     */
    public void addMsg(String msg) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node element = elementList.item(i);
            if ("message".equals(element.getNodeName())) {
                if (msg.equals("")) {
                    //deletes message element if the user enters nothing when editing
                    billboard.removeChild(element);
                } else {
                    element.setTextContent(msg);
                }

                return;
            }
        }

        if (msg.equals("")) { return; } //ignore empty inputs if the element doesn't already exist

        //Add msg text
        Element msgTag = document.createElement("message");
        msgTag.appendChild(document.createTextNode(msg));
        if (billboard.getFirstChild() != null) {
            billboard.insertBefore(msgTag, billboard.getFirstChild());
        } else {
            billboard.appendChild(msgTag);
        }

    }

    /**
     * adds/edits a picture element to the current document
     *
     * @param source determines if the source of the picture is from a data or url
     * @param input the source of the picture inputted by the user
     */
    public void addImg(String source, String input) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if ("picture".equals(element.getNodeName())) {
                    if (source.equals("None")) {
                        //deletes picture element if the user enters None when editing
                        billboard.removeChild(element);
                    } else {
                        if (element.getAttributes() != null) {
                            //remove all existing attributes in picture element
                            element.removeAttribute("url");
                            element.removeAttribute("data");
                        }

                        //adds the correct attribute specified by the user
                        if (source.equals("url")) {
                            Attr imgURL = document.createAttribute("url");
                            imgURL.setValue(input);
                            element.setAttributeNode(imgURL);
                        } else if (source.equals("data")) {
                            Attr imgData = document.createAttribute("data");
                            imgData.setValue(input);
                            element.setAttributeNode(imgData);
                        }
                    }

                    return;
                }
            }
        }

        if (source.equals("None")) { return; }  //ignore empty inputs if the element doesn't already exist

        //checks if there are any existing nodes already and places the picture node in the correct order
        Element img = document.createElement("picture");
        if (billboard.getLastChild() != null && billboard.getLastChild().getNodeName().equals("information")) {
            billboard.insertBefore(img, billboard.getLastChild());
        } else {
            billboard.appendChild(img);
        }
        //adds the correct attribute to picture element
        if (source.equals("url")) {
            Attr imgURL = document.createAttribute("url");
            imgURL.setValue(input);
            img.setAttributeNode(imgURL);
        } else if (source.equals("data")) {
            Attr imgData = document.createAttribute("data");
            imgData.setValue(input);
            img.setAttributeNode(imgData);
        }
    }

    /**
     * adds/edits a information element in the current document
     *
     * @param info the input from the user to be placed into the information element
     */
    public void addInfo(String info) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing information element and edits that if there is
        NodeList elements = billboard.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if ("information".equals(element.getNodeName())) {
                if (info.equals("")) {
                    billboard.removeChild(element);
                } else {
                    element.setTextContent(info);
                }

                return;
            }
        }

        if (info.equals("")) { return; }    //ignore empty inputs if the element doesn't already exist

        //Add info text
        Element infoTag = document.createElement("information");
        infoTag.appendChild(document.createTextNode(info));
        billboard.appendChild(infoTag);
    }

    /**
     * adds/edits a color attribute of an existing element in the document
     *
     * @param changeColorOf determines which element color attribute to add/edit
     * @param color the color to be inputted into the attribute
     */
    public void addColor(String changeColorOf, String color) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);
        if (changeColorOf.equals("billboard")) {
            Element element = (Element) billboard;
            if (element.getAttributes() != null) {
                element.removeAttribute("background");
            }
            //adds the correct attribute specified by the user
            Attr colorTag = document.createAttribute("background");
            colorTag.setValue(color);
            element.setAttributeNode(colorTag);
            return;
        }

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (changeColorOf.equals(element.getNodeName())) {
                    if (element.getAttributes() != null) {
                        //remove all existing attributes in picture element
                        element.removeAttribute("color");
                    }
                    //adds the correct attribute specified by the user
                    Attr colorTag = document.createAttribute("color");
                    colorTag.setValue(color);
                    element.setAttributeNode(colorTag);
                    return;
                }
            }
        }
    }

    /**
     * Converts the current document to an XML file to be exported to a local storage
     */
    public void writeToFile() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);  //stores DOM from the document
            StreamResult streamResult = new StreamResult(new File(filePath));   //stores the output of writing the DOM to a file
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets all the current document's propeties and packs it into an array
     *
     * @return an array containing the number of existing elements and the element names
     */
    public Object[] getBillboardProperties() {
        Node billboard = document.getElementsByTagName("billboard").item(0);
        NodeList elements = billboard.getChildNodes();
        Object[] returnVal = new Object[elements.getLength()];

        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            returnVal[i] = element.getNodeName();
        }

        return returnVal;
    }

    /**
     * gets the message from the message element from the current document
     *
     * @return returns the message as a String
     */
    public static String getMsg() {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node element = elementList.item(i);
            if ("message".equals(element.getNodeName())) {
                String msg = element.getTextContent();
                return msg;
            }
        }

        return "";
    }

    /**
     * gets the color attribute of a specific element
     *
     * @param colorOf determines which element attribute is needed
     * @return returns the color code from the attribute as a String
     */
    public static String getColor(String colorOf) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);
        if (colorOf.equals("billboard")) {
            Element element = (Element) billboard;
            if (element.getAttributes() != null) {
                return element.getAttribute("background");
            }

            return "#ffffff";
        }

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (colorOf.equals(element.getNodeName())) {
                    if (element.getAttributes() != null) {
                        String color = element.getAttribute("color");
                        return color;
                    }
                }
            }
        }

        return "#000000";
    }

    /**
     * gets the picture properties and adds it to a HashMap for easy reference
     *
     * @return returns a HashMap containing the type of picture source and its source
     */
    public static HashMap<String, String> getImg() {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if ("picture".equals(element.getNodeName())) {
                    if (element.getAttributes() != null) {
                        HashMap<String, String> imgProp = new HashMap<>();
                        if (element.hasAttribute("url")) {
                            imgProp.put("type", "url");
                            imgProp.put("source", element.getAttribute("url"));
                        } else if (element.hasAttribute("data")) {
                            imgProp.put("type", "data");
                            imgProp.put("source", element.getAttribute("data"));
                        }

                        return imgProp;
                    }
                }
            }
        }

        return new HashMap<>();
    }

    /**
     * gets the information from the information element from the current document
     *
     * @return returns the information as a String
     */
    public static String getInfo() {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elements = billboard.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if ("information".equals(element.getNodeName())) {
                String info = element.getTextContent();
                return info;
            }
        }

        return "";
    }

    /**
     * converts the current document into a String for storing
     *
     * @return returns a String version of the current document
     */
    public String xmlToString() {
        try {
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    /**
     * converts a String input into a document for editing
     *
     * @param xmlString a String version of a document which can be obtained from xmlToString()
     */
    public void stringToXml(String xmlString) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}