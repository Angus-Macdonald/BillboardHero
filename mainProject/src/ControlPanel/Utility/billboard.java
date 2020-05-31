package ControlPanel.Utility;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class billboard {
    private static String filePath;

    private static DocumentBuilder documentBuilder;
    private static Document document;
    private static TransformerFactory transformerFactory;
    private static Transformer transformer;
    private static DOMSource domSource;
    private static StreamResult streamResult;

    public billboard() {

    }

    //creates a new XML file to the file path with only a billboard tag with no attributes
    public static void createXML(String name) {
        filePath = "./" + name + ".xml";

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            //root element (billboard tag)
            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);

            //create the xml file
            //transform the DOM Object to an XML File
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File at (" + filePath + ")");

        } catch (ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        }
    }

    public static void importXML(File xmlFile, String fileOrServer) {
        if (fileOrServer == "file") {
            filePath = xmlFile.getAbsolutePath();
        } else if (fileOrServer == "server") {
            filePath = xmlFile.getName();
        }

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            documentBuilder = documentFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);

            System.out.println("Done importing (" + xmlFile + ").");

        } catch (ParserConfigurationException | SAXException | IOException pce) {
            pce.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds a new message tag along with the input as the message
    public static void addMsg(String msg) {
        try {
            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Checks if there's an existing message element and edits that if there is
            NodeList elementList = billboard.getChildNodes();
            for (int i = 0; i < elementList.getLength(); i++) {
                Node element = elementList.item(i);
                if ("message".equals(element.getNodeName())) {
                    element.setTextContent(msg);

                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();
                    domSource = new DOMSource(document);
                    streamResult = new StreamResult(new File(filePath));
                    transformer.transform(domSource, streamResult);

                    System.out.println("Added (" + msg + ") as a message to the XML file at (" + filePath + ")");
                    return;
                }
            }

            //Add msg text
            Element msgTag = document.createElement("message");
            msgTag.appendChild(document.createTextNode(msg));
            billboard.appendChild(msgTag);

            // write the DOM object to the file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + msg + ") as a message to the XML file at (" + filePath + ")");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds a picture tag along with the source
    public static void addImg(String source, String input) {
        try {
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

                        transformerFactory = TransformerFactory.newInstance();
                        transformer = transformerFactory.newTransformer();
                        domSource = new DOMSource(document);
                        streamResult = new StreamResult(new File(filePath));
                        transformer.transform(domSource, streamResult);

                        System.out.println("Added (" + input + ") as the image source to the XML file at (" + filePath + ")");
                        return;
                    }
                }
            }

            //Add img source
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

            // write the DOM object to the file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + input + ") as the image source to the XML file at (" + filePath + ")");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds an information tag along with the input
    public static void addInfo(String info) {
        try {
            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Checks if there's an existing message element and edits that if there is
            NodeList elements = billboard.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node element = elements.item(i);
                if ("information".equals(element.getNodeName())) {
                    element.setTextContent(info);

                    transformerFactory = TransformerFactory.newInstance();
                    transformer = transformerFactory.newTransformer();
                    domSource = new DOMSource(document);
                    streamResult = new StreamResult(new File(filePath));
                    transformer.transform(domSource, streamResult);

                    System.out.println("Added (" + info + ") as a message to the XML file at (" + filePath + ")");
                    return;
                }
            }

            //Add info text
            Element infoTag = document.createElement("information");
            infoTag.appendChild(document.createTextNode(info));
            billboard.appendChild(infoTag);

            // write the DOM object to the file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + info + ") as information to the XML file at ("  + filePath + ")");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and searches for the
    //specific tag then adds a color attribute along with the color
    public static void addColor(String changeColorOf, String color) {
        try {
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

                transformerFactory = TransformerFactory.newInstance();
                transformer = transformerFactory.newTransformer();
                domSource = new DOMSource(document);
                streamResult = new StreamResult(new File(filePath));
                transformer.transform(domSource, streamResult);

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

                        transformerFactory = TransformerFactory.newInstance();
                        transformer = transformerFactory.newTransformer();
                        domSource = new DOMSource(document);
                        streamResult = new StreamResult(new File(filePath));
                        transformer.transform(domSource, streamResult);

                        System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");
                        return;
                    }
                }
            }

            // write the DOM object to the file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");

        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

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

    public static String[] getImg() {
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
                        String[] imgProp = new String[2];
                        if (element.hasAttribute("url")) {
                            imgProp[0] = "url";
                            imgProp[1] = element.getAttribute("url");
                        } else if (element.hasAttribute("data")) {
                            imgProp[0] = "data";
                            imgProp[1] = element.getAttribute("data");
                        }

                        System.out.println("Got (" + imgProp[1] + ") as the image source to the XML file at (" + filePath + ")");
                        return imgProp;
                    }
                }
            }
        }
        return null;
    }

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

    public static String xmlToString() {
        try {
            StringWriter writer = new StringWriter();
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
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

    public static void previewBillboard() {

    }
}