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

    public controlPanelBillboard(String billboardName) {
        this.billboardName = billboardName;

        createXML();
    }

    public static void createXML() {
        filePath = "./" + billboardName;
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //root element (billboard tag)
            Element billboard = document.createElement("billboard");
            document.appendChild(billboard);
            //attribute for root element (background color)
            Attr bgColor = document.createAttribute("background");
            bgColor.setValue("");
            billboard.setAttributeNode(bgColor);

//            //message element
//            Element message = document.createElement("message");
//            billboard.appendChild(message);
//            //attribute for message element (message color)
//            Attr msgColor = document.createAttribute("color");
//            msgColor.setValue("");
//            message.setAttributeNode(msgColor);
//
//            //image element
//            Element img = document.createElement("picture");
//            billboard.appendChild(img);
//            //attribute for img element (img url/data)
//            Attr imgURL = document.createAttribute("url");
//            Attr imgData = document.createAttribute("data");
//            imgURL.setValue("");
//            imgData.setValue("");
//            img.setAttributeNode(imgURL);
//
//            //information element
//            Element info = document.createElement("information");
//            billboard.appendChild(info);
//            //attribute for infomation element (info color)
//            Attr infoColor = document.createAttribute("color");
//            infoColor.setValue("");
//            info.setAttributeNode(infoColor);

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

    public static void addMsg(String input) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Add msg text
            Element msg = document.createElement("message");
            msg.appendChild(document.createTextNode(input));
            billboard.appendChild(msg);

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was changed");

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
            }

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was ");

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

    public static void addInfo(String input) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(filePath);

            //Target existing root element
            Node billboard = document.getElementsByTagName("billboard").item(0);

            //Add info text
            Element info = document.createElement("information");
            info.appendChild(document.createTextNode(input));
            billboard.appendChild(info);

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was changed");

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

    public static void addColor(String changeColorOf, String input) {
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
                    Attr imgURL = document.createAttribute("url");
                    imgURL.setValue(input);
                    break;
                } else if (changeColorOf.equals(element.getNodeName())) {
                    break;
                } else if (changeColorOf.equals(element.getNodeName())) {
                    break;
                }
            }

            //Add img source
            if (changeColorOf == "background") {
                Attr color = document.createAttribute("color");
                color.setValue(input);
            } else if (changeColorOf == "message") {
                Attr imgURL = document.createAttribute("url");
                imgURL.setValue(input);
            } else if (changeColorOf == "information") {
                Attr imgData = document.createAttribute("data");
                imgData.setValue(input);
            }

            // write the DOM object to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(filePath));
            transformer.transform(domSource, streamResult);

            System.out.println("The XML File was ");

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
