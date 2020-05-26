package ControlPanel.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controlPanelExitAlert {
    public static void alterWindow(){

        JFrame frame = new JFrame("Alert");
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JLabel alert = new JLabel("Do you wish to exit? All unsaved progress will be lost.");
        JButton exitButton = new JButton("Exit");
        JButton cancelButton = new JButton("Cancel");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((dim.width/5)*2, (dim.height/5)*2);
        frame.setLayout(new GridLayout(2,1));

        panel1.add(alert);
        panel2.add(exitButton);
        panel2.add(cancelButton);

        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
