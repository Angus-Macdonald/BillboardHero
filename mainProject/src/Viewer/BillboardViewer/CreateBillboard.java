package Viewer.BillboardViewer;

import ControlPanel.Utility.billboard;
import Server.Client;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CreateBillboard {
    private static String defaultMessage = "There are currently no billboards scheduled.";
    private static Color background;
    private static Color messageColour, infoColour = Color.BLACK;
    private static int messageSize = 70;
    private static int informationSize = 30;
    private static String messageText;
    private static String informationText;
    private static Font messageFont = new Font("Helvetica", Font.BOLD, messageSize);
    private static Font infoFont = new Font("Helvetica", Font.BOLD, informationSize);
    private static URL url;
    private static BufferedImage urlImage;
    private static String imageStream;
    private static boolean serverResponse = true;
    private static boolean isDataImage;
    private static List<String> elementOrder = new ArrayList<>();
    private static byte[] byteImg;

    /**
     * This method handles interaction with the server and extracts information from xml files returned
     * from the billboard schedule. If the server is down, or there are currently no billboards
     * scheduled, this changes the serverResponse boolean.
     *
     * @throws SQLException handles any errors from server interaction
     * @throws IOException handles any failed or interrupted I/O operations
     * @throws ClassNotFoundException handles any errors if class interaction between packages fails
     * @throws ParserConfigurationException handles any configuration errors
     * @throws SAXException handles any errors from the parsing process for xml
     */
    public static void serverConnect() throws SQLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        // Grabbing the current timestamp for interaction with the scheduled billboards
        // table
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // Build document for xml parsing
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Initialises connection to the server and scheduled billboards
        try {

            // Calls to server methods to start up a new instance of Client
            Client connection = new Client();
            // Returns the scheduled billboard as an object then parses to string
            Object data = connection.currentBBS(timestamp);
            String dataString = data.toString();

            // Transfers the String to bytes for parsing to the document
            ByteArrayInputStream bais = new ByteArrayInputStream(dataString.getBytes());
            Document document = builder.parse(bais);
            bais.close();

            // retrieves the "background" tag of XML and checks if it has a chosen colour
            // makes it the default white if not
            Element documentElement = document.getDocumentElement();
            String attributeValue = documentElement.getAttribute("background");
            if (attributeValue.isEmpty()) {
                background = Color.WHITE;
            } else {
                background = Color.decode(attributeValue);
            }

            // Creates a list of the xml tags for proper retrieval of elements
            NodeList xmlTags = documentElement.getChildNodes();
            for (int i = 0; i < xmlTags.getLength(); i++) {
                Node node = xmlTags.item(i);
                if (node instanceof Element) {
                    Element element = (Element) node;
                    String tagName = element.getTagName();

                    // One by one adds the elements to a string list for order of panel placement
                    // later on
                    elementOrder.add(tagName);

                    if (tagName == "message") {
                        System.out.println("Content: " + element.getTextContent());
                        messageText = element.getTextContent();
                        if (element.hasAttribute("color")) {
                            messageColour = Color.decode(element.getAttribute("color"));
                        }
                    }
                    else if (tagName == "information") {
                        if (element.hasAttribute("color")) {
                            infoColour = Color.decode(element.getAttribute("color"));
                        }
                        informationText = element.getTextContent();
                    }
                    else if (tagName == "picture") {
                        // Checks the type of image in the xml, and performs the correct generating
                        if (element.hasAttribute("url")) {
                            isDataImage = false;
                            url = new URL(element.getAttribute("url"));
                        }
                        else {
                            isDataImage = true;
                            byteImg = Base64.getMimeDecoder().decode(element.getAttribute("data"));
                        }
                    }
                }
            }
            // Sets the server response boolean to true for the createBillboard() method
            serverResponse = true;
        } catch(Exception e) {
            // If any of the server interaction, or above I/O operations fail, the response boolean
            // is set to false for createBillboard()
            serverResponse = false;
        }
    }

    /**
     * Method to create fullscreen GUI that is the billboard viewer. Takes it's data from variables set up by
     * serverConnect(). This formats and sets up all the JSwing elements.
     *
     * @throws IOException handles any failed or interrupted I/O operations
     * @throws ClassNotFoundException handles any errors if class interaction between packages fails
     * @throws SQLException handles any errors from server interaction
     * @throws ParserConfigurationException handles any configuration errors
     * @throws SAXException handles any errors from the parsing process for xml
     */
    public static void createBillboard() throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException {
        // Calls the above method to set up all the variables to be checked and used throughout
        serverConnect();

        JFrame frame = new JFrame();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // If there's no server connection, or no billboards currently scheduled, a default billboard is displayed
        if (!serverResponse) {
            JTextArea defaultText = new JTextArea(defaultMessage, 1,20);
            defaultText.setLineWrap(true);
            defaultText.setWrapStyleWord(true);
            defaultText.setOpaque(false);
            defaultText.setEditable(false);

            // Adds mouse listener and key listener to JTextArea to keep the same exit functionality
            defaultText.addMouseListener(clickCheck);
            defaultText.addKeyListener(escListener);
            defaultText.setFont(new Font("Helvetica", Font.BOLD,80));

            middlePanel.setBorder(new EmptyBorder(420,10,10,10));
            middlePanel.add(defaultText);
        }

        // This runs if server connection is successful
        else {
            // If there is one element in the billboard
            if (elementOrder.size() == 1) {
                // Checks element names inside String list
                String billboardElement = elementOrder.get(0);

                // Formats JLabel or JTextArea for relevant panel and main frame
                if (billboardElement == "information") {
                    JTextArea element = packedInfo(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setBorder(new EmptyBorder(420, 10, 10, 10));
                }
                else if (billboardElement == "picture") {
                    JLabel element = packedElement(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setLayout(new GridBagLayout());
                }
                else {
                    JLabel element = packedElement(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setBorder(new EmptyBorder(420, 10, 10, 10));
                }


            }
            // If there are two elements to the billboard
            else if (elementOrder.size() == 2) {
                String firstItem = elementOrder.get(0);
                String secondItem = elementOrder.get(1);

                // Formats correctly if second element is a String
                if (secondItem == "information") {
                    JLabel topElement = packedElement(firstItem);
                    JTextArea bottomElement = packedInfo(secondItem);

                    topPanel.add(topElement);
                    bottomPanel.add(bottomElement);
                } else {
                    JLabel topElement = packedElement(firstItem);
                    JLabel bottomElement = packedElement(secondItem);

                    topPanel.add(topElement);
                    bottomPanel.add(bottomElement);
                }
                topPanel.setBorder(new EmptyBorder(160, 10, 10, 10));
                bottomPanel.setBorder(new EmptyBorder(10, 10, 160, 10));
            }
            // If there are three elements to the billboard
            else {
                String firstItem = elementOrder.get(0);
                String secondItem = elementOrder.get(1);
                String thirdItem = elementOrder.get(2);

                JLabel topElement = packedElement(firstItem);
                JLabel middleElement = packedElement(secondItem);
                JTextArea bottomElement = packedInfo(thirdItem);

                topPanel.add(topElement);
                middlePanel.add(middleElement);
                bottomPanel.add(bottomElement);

                topPanel.setBorder(new EmptyBorder(120, 10, 10, 10));
                middlePanel.setLayout(new GridBagLayout());
                bottomPanel.setBorder(new EmptyBorder(10, 10, 100, 10));
            }

            // Need to clear the String list
            elementOrder.clear();

            topPanel.setBackground(background);
            bottomPanel.setBackground(background);
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
        frame.setVisible(true);
    }

    /**
     * Taken from lecture/tutorial demos, has main operating on separate
     * thread.
     */
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        SwingUtilities.invokeLater(() -> {
            try {
                createBillboard();
                System.out.println(serverResponse);
            } catch (IOException | ClassNotFoundException | SQLException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates a new instance of MouseListener for implementation of viewer closing on click
     */
    public static MouseListener clickCheck = new MouseListener() {
        // Simply counts the number of clicks and closes the program when mouse is clicked
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

    /**
     * Creates a new instance of KeyListener, used to listen only for user pressing 'esc'.
     * If user has pressed said key, the viewer closes.
     */
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

    /**
     * Method to resize a BufferedImage based on what scale a user input. Method multiplies the
     * both width and height ratio by said scale.
     *
     * @param image Takes BufferedImage to be scaled up.
     * @param scale Takes an int that sets what scale the BufferedImage is to be scaled by
     * @return Returns a new BufferedImage to the new scale of the output
     */
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

    /**
     * Method to check the current dimensions of an input BufferedImage. If both the width
     * and height of the image is smaller than a third of the user's screen resolution, this
     * method initialises a resize.
     *
     * @param image BufferedImage to be checked and resized
     * @return Returns the input BufferedImage to the new resolution
     */
    public static BufferedImage scaleImage(BufferedImage image) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scale;
        int screenWidth = (int)screenSize.getWidth();
        int screenHeight = (int)screenSize.getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int maxWidth = screenWidth / 3;
        int maxHeight = screenHeight / 3;
        BufferedImage scaledImage;

        // Checking if current dimensions are larger than the allowed 1/3 of the screen
        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            return image;
        } else {
//            int newHeight, newWidth;
            int widthDiff = maxWidth / imageWidth;
            int heightDiff = maxHeight / imageHeight;

            if (widthDiff == 0 || heightDiff == 0) {
                scaledImage = resizeImage(image, 1);
            }
            else if (widthDiff <= heightDiff) {
                scale = widthDiff;
                scaledImage = resizeImage(image, scale);
            } else {
                scale = heightDiff;
                scaledImage = resizeImage(image, scale);
            }
            return scaledImage;
        }
    }

    /**
     * This method takes what data is stored in the message and image variables based on
     * the user input. Then, depending on the element type, configures them to a JLabel for
     * the createBillboard method.
     *
     * @param element takes which element of the billboard to be packed
     * @return returns a JLabel containing the passed element
     * @throws IOException
     */
    public static JLabel packedElement(String element) throws IOException {
        JLabel output = new JLabel();

        if (element.equals("message")) {
            output = new JLabel(messageText);
            output.setFont(messageFont);
            output.setForeground(messageColour);
        }
        else if (element.equals("picture")) {
            if (!isDataImage) {
                BufferedImage image = ImageIO.read(url);
                BufferedImage resizedImg = scaleImage(image);
                output = new JLabel(new ImageIcon(resizedImg));
            }
            else if (isDataImage) {
                BufferedImage dataImage = ImageIO.read(new ByteArrayInputStream(byteImg));
                BufferedImage resizedImg = scaleImage(dataImage);
                output = new JLabel(new ImageIcon(resizedImg));
            }
        }
        return output;
    }

    /**
     * Method to take any data stored in the information variables. This then formats
     * and packs the text into a JTextArea to allow for word wrap.
     *
     * @param element takes information text to be packed
     * @return returns a JTextArea containing the passed information text
     */
    public static JTextArea packedInfo(String element) {
        int textLength;
        if (element.length() < 60) {
            textLength = element.length();
        } else {
            textLength = 60;
        }

        JTextArea information = new JTextArea(informationText, 4, textLength);

        information.setLineWrap(true);
        information.setWrapStyleWord(true);
        information.setOpaque(false);
        information.setEditable(false);

        information.addMouseListener(clickCheck);
        information.addKeyListener(escListener);
        information.setFont(infoFont);
        information.setForeground(infoColour);

        return information;
    }
}
