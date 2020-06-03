package Viewer.BillboardViewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ViewerBillboard extends JFrame {
    public static Color background = Color.decode("#6800C0");
    public static String testMessage = "This is a test message!";
    public static int messageSize;
    public static int informationSize;
    public static URL url;

    static {
        try {
            url = new URL("https://cloudstor.aarnet.edu.au/plus/s/X79GyWIbLEWG4Us/download");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // Need to have something to check how many elements are in billboard
    // 1 uses centre panel, 2 uses top and bottom, 3 uses all three
    private static void createBillboard() throws IOException {

        JFrame frame = new JFrame();

        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // 25 character limit before downsizing font
        // 50 character max for message
        JLabel message = new JLabel(testMessage);
        JLabel information = new JLabel("Information text");

        BufferedImage image = ImageIO.read(url);
        JLabel imageInput = new JLabel(new ImageIcon(image));

        resizeText(testMessage);

        message.setFont(new Font("Helvetica", Font.BOLD, messageSize));
        message.setForeground(Color.decode("#FF9E3F"));
        information.setFont(new Font("Helvetica", Font.BOLD, informationSize));
        information.setForeground(Color.decode("#3FFFC7"));

        frame.addKeyListener(new KeyListener() {
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
        });
        frame.addMouseListener(new MouseListener() {
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
        });

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);



        frame.add(topPanel, BorderLayout.NORTH);

        frame.add(middlePanel, BorderLayout.CENTER);

        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(background);

        topPanel.add(message);
        topPanel.setBackground(background);
        topPanel.setBorder(new EmptyBorder(150, 10, 10, 10));

        bottomPanel.add(information);
        bottomPanel.setBackground(background);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 150, 10));

        middlePanel.add(imageInput);
        middlePanel.setBorder(new EmptyBorder(100, 10, 10, 10));
        middlePanel.setBackground(background);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
    }

    /**
     * Taken from lecture/tutorial demos, has main operating on separate
     * thread.
     */
    public static void main(String[] args) {
//        new ViewerBillboard();

        SwingUtilities.invokeLater(() -> {
            try {
                createBillboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //createViewer();
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


}