package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import ControlPanel.Utility.billboard;
import Server.ServerBillboard;

public class billboardCreateGUI {
    public static void main(String[] args) {
//    public billboardCreateGUI() {
        JFrame frame = new JFrame("Create a New Billboard");
        JLabel nameLabel = new JLabel("Name:* ");
        JTextField nameBox = new JTextField();
        JButton bgColorPicker = new JButton("Background Color");
        JLabel msgLabel = new JLabel("Message (required to pick a color): ");
        JTextField msgBox = new JTextField();
        JButton msgColorPicker = new JButton("Message Color");
        JLabel sourcePicLabel = new JLabel("Picture Source: ");
        JComboBox typePicBox = new JComboBox(new String[]{"None", "url", "data"});
        JTextField sourcePicBox = new JTextField();
        JLabel infoLabel = new JLabel("Information (required to pick a color): ");
        JTextField infoBox = new JTextField();
        JButton infoColorPicker = new JButton("Information Color");
        JButton button = new JButton("Create");

        frame.add(nameLabel);
        frame.add(nameBox);
        frame.add(bgColorPicker);
        frame.add(msgLabel);
        msgBox.setToolTipText("Max 50 characters.");
        frame.add(msgBox);
        frame.add(msgColorPicker);
        frame.add(sourcePicLabel);
        frame.add(typePicBox);
        frame.add(sourcePicBox);
        frame.add(infoLabel);
        infoBox.setToolTipText("Max 200 characters.");
        frame.add(infoBox);
        frame.add(infoColorPicker);
        frame.add(button);
        bgColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameBox.getText().isEmpty()) {
                    Color bgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.white);
                    bgColorPicker.setBackground(bgColor);
                }
            }
        });
        msgColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!msgBox.getText().isEmpty()) {
                    Color msgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                    msgBox.setForeground(msgColor);
                    msgColorPicker.setBackground(msgColor);
                }
            }
        });
        infoColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!infoBox.getText().isEmpty()) {
                    Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                    infoBox.setForeground(infoColor);
                    infoColorPicker.setBackground(infoColor);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nameBox.getText().isEmpty() && (!msgBox.getText().isEmpty() && !infoBox.getText().isEmpty() && !sourcePicBox.getText().isEmpty())) {
                    billboard newBillboard = new billboard();
                    newBillboard.createXML(nameBox.getText());
                    newBillboard.addColor("billboard", String.format("#%02X%02X%02X",
                            bgColorPicker.getBackground().getRed(),
                            bgColorPicker.getBackground().getGreen(),
                            bgColorPicker.getBackground().getBlue())
                    );
                    if (!msgBox.getText().isEmpty() && msgBox.getText().length() <= 50) {
                        newBillboard.addMsg(msgBox.getText());
                        newBillboard.addColor("message", String.format("#%02X%02X%02X",
                                msgColorPicker.getBackground().getRed(),
                                msgColorPicker.getBackground().getGreen(),
                                msgColorPicker.getBackground().getBlue())
                        );
                    } else if (!msgBox.getText().isEmpty() && msgBox.getText().length() > 50) {
                        return;
                    }
                    if (!typePicBox.getSelectedItem().toString().equals("None") && !sourcePicBox.getText().isEmpty()) {
                        newBillboard.addImg(typePicBox.getSelectedItem().toString(), sourcePicBox.getText());
                    }
                    if (!infoBox.getText().isEmpty() && infoBox.getText().length() <= 200) {
                        newBillboard.addInfo(infoBox.getText());
                        newBillboard.addColor("information", String.format("#%02X%02X%02X",
                                infoColorPicker.getBackground().getRed(),
                                infoColorPicker.getBackground().getGreen(),
                                infoColorPicker.getBackground().getBlue())
                        );
                    } else if (!infoBox.getText().isEmpty() && infoBox.getText().length() > 200) {
                        return;
                    }
                    System.out.println(msgBox.getText().length());
                    System.out.println(newBillboard);
                    //upload the created XML file to the server
                    ServerBillboard serverConn = new ServerBillboard();
                    try {
                        serverConn.createBB(nameBox.getText(), 1, newBillboard.xmlToString());
                        System.out.println(serverConn.getBBInfo(nameBox.getText()));
                    } catch (SQLException | IOException | ClassNotFoundException throwables) {
                        throwables.printStackTrace();
                    }
                    frame.dispose();
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(14, 1));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
