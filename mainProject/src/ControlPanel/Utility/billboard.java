package ControlPanel.Utility;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    //creates a new XML file to the file path with only a billboard tag with no attributes
    public billboard(String name) {
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

    //opens the existing XML file at the file path and adds a new message tag along with the input as the message
    public static void addMsg(String msg) {
        try {
            document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Checks if there's an existing message element
            NodeList elements = billboard.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node element = elements.item(i);
                if ("message".equals(element.getNodeName())) {
                    //removes existing tag
                    billboard.removeChild(element);
                    break;
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

        } catch (TransformerException | IOException | SAXException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds a picture tag along with the source
    public static void addImg(String source, String input) {
        try {
            document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

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

        } catch (TransformerException | IOException | SAXException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds an information tag along with the input
    public static void addInfo(String info) {
        try {
            document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Checks if there's an existing information element
            NodeList elements = billboard.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node element = elements.item(i);
                if ("information".equals(element.getNodeName())) {
                    //removes existing tag
                    billboard.removeChild(element);
                    break;
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

        } catch (TransformerException | IOException | SAXException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and searches for the
    //specific tag then adds a color attribute along with the color
    public static void addColor(String changeColorOf, String color) {
        try {
            document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);
            NodeList elements = billboard.getChildNodes();
            for (int i = 0; i < elements.getLength(); i++) {
                Node element = elements.item(i);
                if (changeColorOf.equals(element.getNodeName())) {
                    //removes existing tag
                    billboard.removeChild(element);
                    //creates a new replacement with color attribute
                    Element replace = document.createElement(changeColorOf);
                    billboard.appendChild(replace);
                    Attr colorAttribute = document.createAttribute("color");
                    colorAttribute.setValue(color);
                    replace.setAttributeNode(colorAttribute);
                    break;
                }
            }

            // write the DOM object to the file
            transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            domSource = new DOMSource(document);
            streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file at (" + filePath + ")");

        } catch (TransformerException | IOException | SAXException tfe) {
            tfe.printStackTrace();
        }
    }

    public static void previewBillboard() {

    }
}
