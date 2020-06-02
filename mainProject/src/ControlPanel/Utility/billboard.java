//This class controls everything to do with the billboard
//which includes creating the base structure of the XML file
//as a doocument, and then converting it to an xml file for user to
//export. The class also controls editing existing xml files along
//with grabbing specific text from the xml file for each access.

package ControlPanel.Utility;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class billboard {
    private static File file;   //stores the xml file
    private static String filePath; //stores the path to the xml file

    private static Document document;   //stores the actual document before conversion

    //creates a new XML file to the file path with only a billboard tag with no attributes
    public static void createXML(String name) {
        filePath = "./" + name + ".xml";

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            //root element (billboard tag)
            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);
            //writeToFile();
            System.out.println("Done creating XML File at (" + filePath + ")");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
    }

    //imports an existing xml file to this class for easy access
    //handles input from a file or server
    public static void importXML(File xmlFile, String xmlString, String fileOrServer) {
        if (fileOrServer.equals("file")) {
            file = xmlFile;
            filePath = file.getAbsolutePath();
            try {
                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                document = documentBuilder.parse(file);
            } catch (ParserConfigurationException | SAXException | IOException pce) {
                pce.printStackTrace();
            }
        } else if (fileOrServer.equals("server")) {
            filePath = "./";
            stringToXml(xmlString);
        }

        System.out.println("Done importing (" + xmlFile + ").");
    }

    //either creates or modifies a message element with the input msg from the user, then adds it to the document
    public static void addMsg(String msg) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node element = elementList.item(i);
            if ("message".equals(element.getNodeName())) {
                element.setTextContent(msg);
                System.out.println("Added (" + msg + ") as a message to the XML file at (" + filePath + ")");
                return;
            }
        }

        //Add msg text
        Element msgTag = document.createElement("message");
        msgTag.appendChild(document.createTextNode(msg));
        billboard.appendChild(msgTag);

        System.out.println("Added (" + msg + ") as a message to the XML file at (" + filePath + ")");
    }

    //either creates or modifies a image element with the input from the user, then adds it to the document
    public static void addImg(String source, String input) {
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
                        //remove all existing attributes in picture element
                        element.removeAttribute("url");
                        element.removeAttribute("data");
                        element.removeAttribute("error");
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
                    } else {
                        Attr imgURL = document.createAttribute("error");
                        imgURL.setValue("true");
                        element.setAttributeNode(imgURL);
                    }

                    System.out.println("Added (" + input + ") as the image source to the XML file at (" + filePath + ")");
                    return;
                }
            }
        }

        //Add img type and source
        Element img = document.createElement("picture");
        billboard.appendChild(img);
        if (source.equals("url")) {
            Attr imgURL = document.createAttribute("url");
            imgURL.setValue(input);
            img.setAttributeNode(imgURL);
        } else if (source.equals("data")) {
            Attr imgData = document.createAttribute("data");
            imgData.setValue(input);
            img.setAttributeNode(imgData);
        } else {
            Attr imgURL = document.createAttribute("error");
            imgURL.setValue("true");
            img.setAttributeNode(imgURL);
        }

        System.out.println("Added (" + input + ") as the image source to the XML file at (" + filePath + ")");
    }

    //either creates or modifies a information element with the input info from the user, then adds it to the document
    public static void addInfo(String info) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing information element and edits that if there is
        NodeList elements = billboard.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if ("information".equals(element.getNodeName())) {
                element.setTextContent(info);
                System.out.println("Added (" + info + ") as a message to the XML file at (" + filePath + ")");
                return;
            }
        }

        //Add info text
        Element infoTag = document.createElement("information");
        infoTag.appendChild(document.createTextNode(info));
        billboard.appendChild(infoTag);

        System.out.println("Added (" + info + ") as information to the XML file at ("  + filePath + ")");
    }

    //either creates or modifies a picture element with the input from the user, then adds it to the document
    public static void addColor(String changeColorOf, String color) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);
        if (changeColorOf == "billboard") {
            Element element = (Element) billboard;
            if (element.getAttributes() != null) {
                element.removeAttribute("color");
            }
            //adds the correct attribute specified by the user
            Attr colorTag = document.createAttribute("color");
            colorTag.setValue(color);
            element.setAttributeNode(colorTag);
            System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");
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
                    System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");
                    return;
                }
            }
        }
        System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");
    }

    //create the xml file
    //convert the DOM Object to an xml File
    public static void writeToFile() {
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

    //return the msg of the document
    public static String getMsg() {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node element = elementList.item(i);
            if ("message".equals(element.getNodeName())) {
                String msg = element.getTextContent();
                System.out.println("Got (" + msg + ") as a message to the XML file at (" + filePath + ")");
                return msg;
            }
        }

        return "";
    }

    //return the color of the requested element of the document
    public static String getColor(String colorOf) {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elementList = billboard.getChildNodes();
        for (int i = 0; i < elementList.getLength(); i++) {
            Node node = elementList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (colorOf.equals(element.getNodeName())) {
                    if (element.getAttributes() != null) {
                        //remove all existing attributes in picture element
                        String color = element.getAttribute("color");
                        System.out.println("Got (" + color + ") as the color of (" + colorOf + ") to the XML file at (" + filePath + ")");
                        return color;
                    }
                }
            }
        }

        return "";
    }

    //return the img and its properties of the document
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
                        HashMap<String, String> imgProp = new HashMap<String, String>();
                        if (element.hasAttribute("url")) {
                            imgProp.put("type", "url");
                            imgProp.put("source", element.getAttribute("url"));
                        } else if (element.hasAttribute("data")) {
                            imgProp.put("type", "data");
                            imgProp.put("source", element.getAttribute("data"));
                        }

                        System.out.println("Got (" + imgProp.get("source") + ") as the image source to the XML file at (" + filePath + ")");
                        return imgProp;
                    }
                }
            }
        }

        return null;
    }

    //return the information of the document
    public static String getInfo() {
        //Target existing root element
        Node billboard = document.getElementsByTagName("billboard").item(0);

        //Checks if there's an existing message element and edits that if there is
        NodeList elements = billboard.getChildNodes();
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if ("information".equals(element.getNodeName())) {
                String info = element.getTextContent();
                System.out.println("Got (" + info + ") as a message to the XML file at (" + filePath + ")");
                return info;
            }
        }

        return "";
    }

    //converts the document to a string for database entry
    public static String xmlToString() {
        try {
            StringWriter writer = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    //converts the string to a document for easy access to elements
    public static void stringToXml(String xmlString) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlString)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void previewBillboard() {

    }
}