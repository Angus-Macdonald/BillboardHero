package Viewer.BillboardViewer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ViewerBillboard extends JFrame {
    public static Color background = Color.decode("#6800C0");
    public static String testMessage = "This is a test message!";
    public static int messageSize;
    public static int informationSize;

    private static void createBillboard() {
        JFrame frame = new JFrame();

        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        // 25 character limit before downsizing font
        // 50 character max for message
        JLabel message = new JLabel(testMessage);
        JLabel information = new JLabel("Information text");

        resizeText(testMessage);

        message.setFont(new Font("Helvetica", Font.BOLD, messageSize));
        message.setForeground(Color.decode("#FF9E3F"));
        information.setFont(new Font("Helvetica", Font.BOLD, informationSize));
        information.setForeground(Color.decode("#3FFFC7"));

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().setBackground(background);

        topPanel.add(message);
        topPanel.setBackground(background);
        topPanel.setBorder(new EmptyBorder(150, 10, 10, 10));

        bottomPanel.add(information);
        bottomPanel.setBackground(background);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 150, 10));

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

        SwingUtilities.invokeLater(() -> createBillboard());
        //createViewer();
    }

    /**
     * Simple method to resize billboard font size based on message length
     *
     * @param input
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