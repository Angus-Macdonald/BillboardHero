package Viewer.BillboardViewer;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CreateViewer extends JFrame implements KeyListener {
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        escListener.addKeyListener(this);
        escListener.setFocusable(true);

        add(escListener);
        pack();
        setVisible(true);
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


}
