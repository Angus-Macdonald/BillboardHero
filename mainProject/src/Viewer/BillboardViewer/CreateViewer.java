package Viewer.BillboardViewer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreateViewer extends JFrame implements KeyListener, MouseListener {
    JLabel escListener = new JLabel("");

    /**
     * Taken from lecture/tutorial demos, has main operating on separate
     * thread.
     */
    public static void main(String[] args) {
        CreateViewer viewer = new CreateViewer();

//        SwingUtilities.invokeLater(() -> createViewer());
        //createViewer();
    }

    public CreateViewer() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        escListener.addKeyListener(this);
        escListener.setFocusable(true);
        this.addMouseListener(this);
        this.add(escListener);
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

    public void createViewer() {
        JFrame frame = new JFrame();

//        KeyAdapter keyListener = new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                super.keyTyped(e);
//            }
//        };

        // on ESC key close frame


        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
//        frame.pack();
        frame.setVisible(true);

    }


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
