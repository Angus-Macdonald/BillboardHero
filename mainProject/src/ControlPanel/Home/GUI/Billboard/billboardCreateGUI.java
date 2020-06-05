/*This class controls everything to do with the billboard creation. The user can
* input a message, a picture, a information, and colors for specific parts and
* the class will convert it to a document and export it to a file or upload it
* to the server based on what the user chooses.*/

package ControlPanel.Home.GUI;

import ControlPanel.Utility.billboard;
import Server.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class billboardCreateGUI {
    public billboardCreateGUI() {
        JFrame frame = new JFrame("Create a New Billboard");
        JLabel nameLabel = new JLabel("Name:* ");
        JTextField nameBox = new JTextField();
        JButton bgColorPicker = new JButton("Choose Background Color");
        JLabel msgLabel = new JLabel("Message (required to pick a color): ");
        JTextField msgBox = new JTextField();
        JButton msgColorPicker = new JButton("Choose Message Color");
        JLabel sourcePicLabel = new JLabel("Picture Source: ");
        JComboBox typePicBox = new JComboBox(new String[]{"None", "url", "data"});
        JTextField sourcePicBox = new JTextField();
        JLabel infoLabel = new JLabel("Information (required to pick a color): ");
        JTextField infoBox = new JTextField();
        JButton infoColorPicker = new JButton("Choose Information Color");
        JCheckBox exportBillboard = new JCheckBox("Export Billboard", false);
        JButton button = new JButton("Create");

        frame.add(nameLabel);
        frame.add(nameBox);
        bgColorPicker.setBackground(Color.WHITE);
        frame.add(bgColorPicker);
        frame.add(msgLabel);
        msgBox.setToolTipText("Max 50 characters.");
        frame.add(msgBox);
        frame.add(msgColorPicker);
        frame.add(sourcePicLabel);
        frame.add(typePicBox);
        sourcePicBox.setEnabled(false);
        sourcePicBox.setBackground(Color.lightGray);
        frame.add(sourcePicBox);
        frame.add(infoLabel);
        infoBox.setToolTipText("Max 200 characters.");
        frame.add(infoBox);
        frame.add(infoColorPicker);
        frame.add(exportBillboard);
        frame.add(button);
        bgColorPicker.addActionListener(e -> {
            if (!nameBox.getText().isEmpty()) {
                Color bgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.white);
                bgColorPicker.setBackground(bgColor);
            }
        });
        msgColorPicker.addActionListener(e -> {
            if (!msgBox.getText().isEmpty()) {
                Color msgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                msgBox.setForeground(msgColor);
            }
        });
        typePicBox.addItemListener(e -> {
            if (!typePicBox.getSelectedItem().equals("None")) {
                sourcePicBox.setEnabled(true);
                sourcePicBox.setBackground(Color.white);
            } else {
                sourcePicBox.setEnabled(false);
                sourcePicBox.setBackground(Color.lightGray);
            }
        });
        infoColorPicker.addActionListener(e -> {
            if (!infoBox.getText().isEmpty()) {
                Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                infoBox.setForeground(infoColor);
            }
        });
        button.addActionListener(e -> {
            if (!nameBox.getText().isEmpty()) {
                if (!msgBox.getText().isEmpty() || !typePicBox.getSelectedItem().toString().equals("None") || !infoBox.getText().isEmpty()) {
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
                                msgBox.getForeground().getRed(),
                                msgBox.getForeground().getGreen(),
                                msgBox.getForeground().getBlue())
                        );
                    } else if (msgBox.getText().length() > 50) {
                        JOptionPane.showMessageDialog(frame, "Exceeded 50 character limit for message.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!typePicBox.getSelectedItem().toString().equals("None") && !sourcePicBox.getText().isEmpty()) {
                        newBillboard.addImg(typePicBox.getSelectedItem().toString(), sourcePicBox.getText());
                    } else if (!typePicBox.getSelectedItem().toString().equals("None") && sourcePicBox.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in the picture source or pick none.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!infoBox.getText().isEmpty() && infoBox.getText().length() <= 350) {
                        newBillboard.addInfo(infoBox.getText());
                        newBillboard.addColor("information", String.format("#%02X%02X%02X",
                                infoBox.getForeground().getRed(),
                                infoBox.getForeground().getGreen(),
                                infoBox.getForeground().getBlue())
                        );
                    } else if (infoBox.getText().length() > 350) {
                        JOptionPane.showMessageDialog(frame, "Exceeded 50 character limit for information.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (exportBillboard.isSelected()) {
                        newBillboard.writeToFile();
                    }
                    System.out.println(newBillboard.xmlToString());
                    //upload the created XML file to the server
                    Client serverConn = new Client();
                    try {
                        serverConn.createBBS(nameBox.getText(), 1, newBillboard.xmlToString());
                        System.out.println("Billboard (" + serverConn.getBBInfoS(nameBox.getText()) + ") successfully added to database.");
                    } catch (IOException throwables) {
                        throwables.printStackTrace();
                    }
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill out at least one field.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a billboard name.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(14, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}