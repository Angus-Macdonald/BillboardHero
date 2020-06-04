package Viewer.BillboardViewer;

import ControlPanel.Utility.billboard;
import Server.ServerBillboard;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Base64;

// Third testing class - can DELETE
public class CreateBillboard {
    public static String defaultMessage = "There are currently no billboards scheduled.";
    public static Color background;
    public static String testMessage;
    public static String informationText;
    public static int messageSize;
    public static int informationSize;
    public static URL url;
    public static boolean serverResponse = false;
    public static int xmlElements = 0;

    static {
        try {
            url = new URL("https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public static String test = "iVBORw0KGgoAAAANSUhEUgAAACAAAAAQCAIAAAD4YuoOAAAAKXRFWHRDcmVhdGlvb" +
            "iBUaW1lAJCFIDI1IDMgMjAyMCAwOTowMjoxNyArMDkwMHlQ1XMAAAAHdElNRQfkAxkAAyQ8nibjAAAACXBIWX" +
            "MAAAsSAAALEgHS3X78AAAABGdBTUEAALGPC/xhBQAAAS5JREFUeNq1kb9KxEAQxmcgcGhhJ4cnFwP6CIIiPoZ" +
            "wD+ALXGFxj6BgYeU7BO4tToSDFHYWZxFipeksbMf5s26WnAkJki2+/c03OzPZDRJNYcgVwfsU42cmKi5YjS1s" +
            "4p4DCrkBPc0wTlkdX6bsG4hZQOj3HRDLHqh08U4Adb/zgEMtq5RuH3Axd45PbftdB2wO5OsWc7pOYaOeOk63w" +
            "YfdFtL5qldB34W094ZfJ+4RlFldTrmW/ZNbn2g0of1vLHdZq77qSDCaSAsLf9kXh9w44PNoR/YSPHycEmbIOs" +
            "5QzBJsmDHrWLPeF24ZkCe6ZxDCOqHcmxmsr+hsicahss+n8vYb8NHZPTJxi/RGC5IqbRwqH6uxVTX+5LvHtvT" +
            "/V/R6PGh/iF4GHoBAwz7RD26spwq6Amh/AAAAAElFTkSuQmCC";

    public static String test2 = "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";

    public static billboard testBillboard = new billboard();

    public static void serverConnect() throws SQLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ServerBillboard connection = new ServerBillboard();

        String data = connection.getBBInfo("Bob");

        ByteArrayInputStream bais = new ByteArrayInputStream(data.getBytes());
        Document document = builder.parse(bais);
        bais.close();

        Element documentElement = document.getDocumentElement();
        String attributeValue = documentElement.getAttribute("background");
        if (attributeValue.isEmpty()) {
            background = Color.WHITE;
        } else {
            background = Color.decode(attributeValue);
        }

        NodeList xmlTags = documentElement.getChildNodes();

        for (int i = 0; i < xmlTags.getLength(); i++) {
            Node node = xmlTags.item(i);
            if (node instanceof Element) {
                xmlElements++;
                Element element = (Element) node;
                System.out.println("Child: " + element.getTagName());
            }
        }
//        System.out.println(xmlElements);
    }

    public static void formBillboard() {
        Object[] contents = new Object[] {};
        contents = testBillboard.getBillboardProperties();
        System.out.println(contents);


    }

    // Need to have something to check how many elements are in billboard
    // 1 uses centre panel, 2 uses top and bottom, 3 uses all three
    private static void createBillboard() throws IOException {

        JFrame frame = new JFrame();
        // Constant
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        if (serverResponse) {
            JLabel message = new JLabel();
            JTextArea defaultText = new JTextArea(defaultMessage, 1,20);
            defaultText.setLineWrap(true);
            defaultText.setWrapStyleWord(true);
            defaultText.setOpaque(false);
            defaultText.setEditable(false);


            defaultText.addMouseListener(clickCheck);
            defaultText.addKeyListener(escListener);
            defaultText.setFont(new Font("Helvetica", Font.BOLD,80));

//            message.setFont(new Font("Helvetica", Font.BOLD, 80));
//            message.setForeground(Color.BLACK);
//            middlePanel.add(message);
            middlePanel.setBorder(new EmptyBorder(100,10,10,10));
            middlePanel.add(defaultText);
        }
        else {
            // 25 character limit before downsizing font
            // 50 character max for message
            JLabel message = new JLabel(testMessage);
            JLabel information = new JLabel(informationText);

            // Testing image data
            byte[] byteTest = Base64.getMimeDecoder().decode(test2);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(byteTest));

            // Scaling iff image needs it???
            BufferedImage resizedImg = resizeImage(img, 1);

            BufferedImage image = ImageIO.read(url);
            JLabel imageInput = new JLabel(new ImageIcon(resizedImg));


            resizeText(testMessage);

            message.setFont(new Font("Helvetica", Font.BOLD, messageSize));
            message.setForeground(Color.decode("#FF9E3F"));
            information.setFont(new Font("Helvetica", Font.BOLD, informationSize));
            information.setForeground(Color.decode("#3FFFC7"));

            topPanel.add(message);
            topPanel.setBackground(background);
            topPanel.setBorder(new EmptyBorder(150, 10, 10, 10));

            bottomPanel.add(information);
            bottomPanel.setBackground(background);
            bottomPanel.setBorder(new EmptyBorder(10, 10, 150, 10));

            middlePanel.add(imageInput);
            middlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
            middlePanel.setBackground(background);
        }

        frame.addKeyListener(escListener);
        frame.addMouseListener(clickCheck);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }

    /**
     * Taken from lecture/tutorial demos, has main operating on separate
     * thread.
     */
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
//        SwingUtilities.invokeLater(() -> {
//            try {
//                createBillboard();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        serverConnect();

    }

    /**
     * Simple method to resize billboard font size based on message length
     *
     * @param input the message string to be checked
     */
    public static void resizeText(String input) {
        if (input.length() >= 25) {
            messageSize = 60;
            informationSize = 40;
        } else {
            messageSize = 100;
            informationSize = 80;
        }
        System.out.println(input.length());
    }

    public static MouseListener clickCheck = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            int clicked = e.getClickCount();
            if (clicked == 1) System.exit(0);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    public static KeyListener escListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == 27) System.exit(0);
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    };

    public static BufferedImage resizeImage(BufferedImage image, int scale) {
        int width = scale * image.getWidth();
        int height = scale * image.getHeight();

        BufferedImage enlargedImage = new BufferedImage(width, height, image.getType());

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                enlargedImage.setRGB(j, i, image.getRGB(j / scale, i / scale));
            }
        }

        return enlargedImage;
    }
}
