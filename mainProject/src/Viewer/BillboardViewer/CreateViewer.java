package Viewer.BillboardViewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreateViewer extends JFrame implements KeyListener, MouseListener {


    /**
     * Taken from lecture/tutorial demos, has main operating on separate
     * thread.
     */
    public static void main(String[] args) {
        CreateViewer viewer = new CreateViewer();

        //SwingUtilities.invokeLater(() -> createViewer());
        //createViewer();
    }

    public CreateViewer() {
        JLabel defaultMessage = new JLabel("There are currently no billboards scheduled.",
                SwingConstants.CENTER);
        defaultMessage.setFont(new Font("Helvetica", Font.BOLD, 60));
        defaultMessage.setForeground(Color.WHITE);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        defaultMessage.addKeyListener(this);
        defaultMessage.setFocusable(true);
        this.addMouseListener(this);
        this.add(defaultMessage);
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.pack();
        this.setVisible(true);
    }

    public void keyTyped(KeyEvent input) {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        System.out.println(key);
        if (key == 27) System.exit(0);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Do nothing
    }

//    private static void createViewer() {
//        JFrame frame = new JFrame();
//        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setUndecorated(true);
//        frame.getContentPane().addKeyListener(this);
//        frame.getContentPane().setBackground(Color.YELLOW);
//        frame.setVisible(true);
//
//    }


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
}
