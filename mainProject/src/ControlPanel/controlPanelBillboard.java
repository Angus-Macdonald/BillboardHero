package ControlPanel;
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
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class controlPanelBillboard {
    private static String billboardName;
    private static String filePath;

    //creates a new XML file to the file path with only a billboard tag with no attributes
    public controlPanelBillboard(String billboardName) {
        this.billboardName = billboardName;
        filePath = "./" + billboardName + ".xml";

        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //root element (billboard tag)
            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);

            //create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds a new message tag along with the input as the message
    public static void addMsg(String msg) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Add msg text
            Element msgTag = document.createElement("message");
            msgTag.appendChild(document.createTextNode(msg));
            billboard.appendChild(msgTag);

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + msg + ") as a message to the XML file.");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds a picture tag along with the source
    public static void addImage(String source, String input) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Add img source
            Element img = document.createElement("picture");
            billboard.appendChild(img);
            if (source == "url") {
                Attr imgURL = document.createAttribute("url");
                imgURL.setValue(input);
                img.setAttributeNode(imgURL);
            } else if (source == "data") {
                Attr imgData = document.createAttribute("data");
                imgData.setValue(input);
                img.setAttributeNode(imgData);
            } else {
                Attr imgURL = document.createAttribute("error");
                imgURL.setValue("true");
                img.setAttributeNode(imgURL);
            }

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + input + ") as the image source to the XML file.");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and adds an information tag along with the input
    public static void addInfo(String info) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Add info text
            Element infoTag = document.createElement("information");
            infoTag.appendChild(document.createTextNode(info));
            billboard.appendChild(infoTag);

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + info + ") as information to the XML file.");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    //opens the existing XML file at the file path and searches for the
    //specific tag then adds a color attribute along with the color
    public static void addColor(String changeColorOf, String color) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

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
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("Added (" + color + ") as the color of (" + changeColorOf + ") to the XML file.");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    public static void previewBillboard() {

    }
}
