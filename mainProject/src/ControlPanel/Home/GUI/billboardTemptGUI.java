package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import ControlPanel.Utility.billboard;

public class billboardTemptGUI {
    public static void billboardTemptGUI() {
        JFrame frame = new JFrame("Create a New Billboard");
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameBox = new JTextField();
        JLabel msgLabel = new JLabel("Message: ");
        JTextField msgBox = new JTextField();
        JLabel typePicLabel = new JLabel("Picture Source Type: ");
        JTextField typePicBox = new JTextField();
        JLabel sourcePicLabel = new JLabel("Picture Source: ");
        JTextField sourcePicBox = new JTextField();
        JLabel infoLabel = new JLabel("Information: ");
        JTextField infoBox = new JTextField();
        JButton button = new JButton("Create");

//        nameLabel.setBounds();
//        nameBox.setBounds(50, 0, 300, 50);
//        msgBox.setBounds(50, 50, 300, 50);
//        typePicBox.setBounds(50, 200, 300, 50);
//        sourcePicBox.setBounds(50, 200, 300, 50);
//        infoBox.setBounds(50, 450, 300, 50);
//        button.setBounds(50, 200, 10, 10);
        frame.add(nameLabel);
        frame.add(nameBox);
        frame.add(msgLabel);
        frame.add(msgBox);
        frame.add(typePicLabel);
        frame.add(typePicBox);
        frame.add(sourcePicLabel);
        frame.add(sourcePicBox);
        frame.add(infoLabel);
        frame.add(infoBox);
        frame.add(button);
        button.addActionListener(new ActionListener() {
        billboard billboard1 = new billboard("New Billboard");
            @Override
            public void actionPerformed(ActionEvent e) {
//                billboard billboard1 = new billboard(nameBox.getText());
                if (!msgBox.getText().isEmpty()) {
                    billboard1.addMsg(msgBox.getText());
                }
                if (!typePicBox.getText().isEmpty() && !sourcePicBox.getText().isEmpty()) {
                    billboard1.addImg(typePicBox.getText(), sourcePicBox.getText());
                }
                if (!infoBox.getText().isEmpty()) {
                    billboard1.addInfo(infoBox.getText());
                }
                System.out.println("Successfully edited the XML files with changes.");
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(8, 1));
        frame.setVisible(true);
    }
}
