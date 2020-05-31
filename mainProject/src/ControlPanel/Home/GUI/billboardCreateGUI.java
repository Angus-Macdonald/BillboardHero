package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import ControlPanel.Utility.billboard;

public class billboardCreateGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create a New Billboard");
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameBox = new JTextField();
        JButton bgColorPicker = new JButton("Background Color");
        JLabel msgLabel = new JLabel("Message (required to pick a color): ");
        JTextField msgBox = new JTextField();
        JButton msgColorPicker = new JButton("Message Color");
        JLabel typePicLabel = new JLabel("Picture Source Type: ");
        JComboBox typePicBox = new JComboBox(new String[]{"None", "url", "data"});
        JLabel sourcePicLabel = new JLabel("Picture Source: ");
        JTextField sourcePicBox = new JTextField();
        JLabel infoLabel = new JLabel("Information (required to pick a color): ");
        JTextField infoBox = new JTextField();
        JButton infoColorPicker = new JButton("Information Color");
        JButton button = new JButton("Create");

        frame.add(nameLabel);
        frame.add(nameBox);
        frame.add(bgColorPicker);
        frame.add(msgLabel);
        frame.add(msgBox);
        frame.add(msgColorPicker);
        frame.add(typePicLabel);
        frame.add(typePicBox);
        frame.add(sourcePicLabel);
        frame.add(sourcePicBox);
        frame.add(infoLabel);
        frame.add(infoBox);
        frame.add(infoColorPicker);
        frame.add(button);
        bgColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color bgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                bgColorPicker.setBackground(bgColor);
            }
        });
        msgColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color msgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                msgBox.setForeground(msgColor);
                msgColorPicker.setBackground(msgColor);
            }
        });
        infoColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                infoBox.setForeground(infoColor);
                infoColorPicker.setBackground(infoColor);
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameBox.getText().isEmpty()) {
                    billboard newBillboard = new billboard(nameBox.getText());
                    newBillboard.addColor("billboard", String.format("#%02X%02X%02X",
                        bgColorPicker.getBackground().getRed(),
                        bgColorPicker.getBackground().getGreen(),
                        bgColorPicker.getBackground().getBlue())
                    );
                    if (!msgBox.getText().isEmpty()) {
                        newBillboard.addMsg(msgBox.getText());
                        newBillboard.addColor("message", String.format("#%02X%02X%02X",
                            msgColorPicker.getBackground().getRed(),
                            msgColorPicker.getBackground().getGreen(),
                            msgColorPicker.getBackground().getBlue())
                        );
                    }
                    if (!typePicBox.getSelectedItem().toString().equals("None") && !sourcePicBox.getText().isEmpty()) {
                        newBillboard.addImg(typePicBox.getSelectedItem().toString(), sourcePicBox.getText());
                    }
                    if (!infoBox.getText().isEmpty()) {
                        newBillboard.addInfo(infoBox.getText());
                        newBillboard.addColor("information", String.format("#%02X%02X%02X",
                            infoColorPicker.getBackground().getRed(),
                            infoColorPicker.getBackground().getGreen(),
                            infoColorPicker.getBackground().getBlue())
                        );
                    }
                    System.out.println(newBillboard.xmlToString());
                    frame.dispose();
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(14, 1));
        frame.setVisible(true);
    }
}
