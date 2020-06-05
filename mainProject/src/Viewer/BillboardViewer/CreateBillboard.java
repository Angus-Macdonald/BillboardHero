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

// Third testing class - can DELETE
public class CreateBillboard {
    public static String defaultMessage = "There are currently no billboards scheduled.";
    public static Color background;
    public static Color messageColour, infoColour = Color.BLACK;
    public static int messageSize = 70;
    public static int informationSize = 30;
    public static String messageText;
    public static String informationText;
    private static Font messageFont = new Font("Helvetica", Font.BOLD, messageSize);
    private static Font infoFont = new Font("Helvetica", Font.BOLD, informationSize);
    private static URL url;
    private static BufferedImage urlImage;
    private static String imageStream;
    public static boolean serverResponse = true;
    private static boolean isDataImage;
    private static List<String> elementOrder = new ArrayList<>();
    private static byte[] byteImg;

    public static void serverConnect() throws SQLException, IOException, ClassNotFoundException, ParserConfigurationException, SAXException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        try {
            Client connection = new Client();
            Object data = connection.currentBBS(timestamp);
            String dataString = data.toString();

            ByteArrayInputStream bais = new ByteArrayInputStream(dataString.getBytes());
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
                    Element element = (Element) node;
                    String tagName = element.getTagName();
                    System.out.println("Child: " + tagName);
                    elementOrder.add(tagName);

                    if (tagName == "message") {
                        System.out.println("Content: " + element.getTextContent());
                        messageText = element.getTextContent();
//                        resizeText(messageText);
                        messageColour = Color.decode(element.getAttribute("color"));
                        System.out.println("Colour: " + element.getAttribute("color"));
                    } else if (tagName == "information") {
                        infoColour = Color.decode(element.getAttribute("color"));
                        informationText = element.getTextContent();
                        System.out.println("Colour: " + element.getAttribute("color"));
                    } else if (tagName == "picture") {
                        // NEW Set of ifs for url or data
                        if (element.hasAttribute("url")) {
                            System.out.println(element.getAttribute("url"));
                            isDataImage = false;
                            url = new URL(element.getAttribute("url"));

                            System.out.println("Should see this in bool check");
                        } else {
                            System.out.println(element.getAttribute("data"));
                            isDataImage = true;
                            byteImg = Base64.getMimeDecoder().decode(element.getAttribute("data"));

                            System.out.println("Should not see this is bool check");
                        }

                    }
                }
            }
            serverResponse = true;
            System.out.println(elementOrder);

            //System.out.println(elementOrder.get(1));
        } catch(Exception e) {
            serverResponse = false;
            System.out.println("Connection failed");
//            System.out.println(e);
            //return;
        }
    }

    // Need to have something to check how many elements are in billboard
    // 1 uses centre panel, 2 uses top and bottom, 3 uses all three
    public static void createBillboard() throws IOException, ClassNotFoundException, SQLException, ParserConfigurationException, SAXException {
        serverConnect();

        // Testing frame disposal
//        ControlPanel.Utility.FrameDispose.disposeFrames();

        JFrame frame = new JFrame();
        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        if (!serverResponse) {
//            JLabel message = new JLabel();
            JTextArea defaultText = new JTextArea(defaultMessage, 1,20);
            defaultText.setLineWrap(true);
            defaultText.setWrapStyleWord(true);
            defaultText.setOpaque(false);
            defaultText.setEditable(false);


            defaultText.addMouseListener(clickCheck);
            defaultText.addKeyListener(escListener);
            defaultText.setFont(new Font("Helvetica", Font.BOLD,80));

            middlePanel.setBorder(new EmptyBorder(420,10,10,10));
            middlePanel.add(defaultText);
        }
        else {
            if (elementOrder.size() == 1) {
                // Code for single element
                String billboardElement = elementOrder.get(0);

                if (billboardElement == "information") {
                    JTextArea element = packedInfo(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setBorder(new EmptyBorder(420, 10, 10, 10));
                } else if (billboardElement == "picture") {
                    JLabel element = packedElement(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setLayout(new GridBagLayout());
                }
                else {
                    JLabel element = packedElement(billboardElement);
                    middlePanel.add(element);
                    middlePanel.setBorder(new EmptyBorder(420, 10, 10, 10));
                }


            } else if (elementOrder.size() == 2) {
                String firstItem = elementOrder.get(0);
                String secondItem = elementOrder.get(1);

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
            } else {
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
//                middlePanel.setBorder(new EmptyBorder(60, 10, 10, 10));
                middlePanel.setLayout(new GridBagLayout());
                bottomPanel.setBorder(new EmptyBorder(10, 10, 100, 10));
            }

            elementOrder.clear();

            topPanel.setBackground(background);
//            topPanel.setBorder(new EmptyBorder(120, 10, 10, 10));

            bottomPanel.setBackground(background);
//            bottomPanel.setBorder(new EmptyBorder(10, 10, 100, 10));

//            middlePanel.setBorder(new EmptyBorder(100, 10, 10, 10));
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

//        serverConnect();

    }

//    /**
//     * Simple method to resize billboard font size based on message length
//     *
//     * @param input the message string to be checked
//     */
//    public static void resizeText(String input) {
//        if (input.length() > 35) {
//            messageSize = 60;
////            informationSize = 40;
//        } else {
//            messageSize = 100;
////            informationSize = 80;
//        }
////        System.out.println(input.length());
//    }

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

        if (imageWidth > maxWidth || imageHeight > maxHeight) {
            return image;
        } else {
            int newHeight, newWidth;
            int widthDiff = maxWidth / imageWidth;
            int heightDiff = maxHeight / imageHeight;

            if (widthDiff == 0 || heightDiff == 0) {
                scaledImage = resizeImage(image, 1);
            }
            else if (widthDiff <= heightDiff) {
                scale = widthDiff;

                scaledImage = resizeImage(image, scale);

//                newHeight = imageHeight * scale;
//                newWidth = imageWidth * scale;
            } else {
                scale = heightDiff;

                scaledImage = resizeImage(image, scale);
//                newHeight = imageHeight * scale;
//                newWidth = imageWidth * scale;
            }



            return scaledImage;
        }


    }


    public static JLabel packedElement(String element) throws IOException {
        JLabel output = new JLabel();

        if (element == "message") {
//            resizeText(messageText);
            output = new JLabel(messageText);
            output.setFont(messageFont);
            output.setForeground(messageColour);

        } else if (element.equals("picture")) {
            if (!isDataImage) {
                BufferedImage image = ImageIO.read(url);
                BufferedImage resizedImg = scaleImage(image);
                output = new JLabel(new ImageIcon(resizedImg));

                System.out.println("This is where we need to be");

            } else if (isDataImage) {

                System.out.println("Why are we in here?");

                BufferedImage dataImage = ImageIO.read(new ByteArrayInputStream(byteImg));
                BufferedImage resizedImg = scaleImage(dataImage); // CHECK SCALE
                output = new JLabel(new ImageIcon(resizedImg));
            }
        }


        return output;
    }

    public static JTextArea packedInfo(String element) {
        JTextArea information = new JTextArea(informationText, 4, 60);

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
