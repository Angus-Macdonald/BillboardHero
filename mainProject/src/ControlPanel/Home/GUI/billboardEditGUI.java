package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import ControlPanel.Utility.billboard;

public class billboardEditGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Edit an Existing Billboard");
        JLabel title = new JLabel("Pick an option.", SwingConstants.CENTER);
        JButton fromFile = new JButton("Import billboard from a file.");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML file", "xml");
        fileChooser.addChoosableFileFilter(xmlFilter);
        JButton fromServer = new JButton("Import billboard from the server.");

        frame.add(title);
        frame.add(fromFile);
        frame.add(fromServer);

        fromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmation = fileChooser.showOpenDialog(frame);
                if (confirmation == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFile().getName().endsWith(".xml")) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Importing file...");
                    editFromFile(selectedFile.getAbsoluteFile());
                    frame.dispose();
                }
            }
        });
        fromServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFromServer();
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(3, 1));
        frame.setVisible(true);
    }

    public static void editFromFile(File xmlFile) {
        JFrame frame = new JFrame("Edit an Existing Billboard");
        JLabel title = new JLabel("Editing " + xmlFile.getName(), SwingConstants.CENTER);
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
        JButton button = new JButton("Save and Exit");

        billboard newBillboard = new billboard();
        newBillboard.importXML(xmlFile, "file");
        msgBox.setText(newBillboard.getMsg());
        msgBox.setForeground(Color.decode(newBillboard.getColor("message")));
        msgColorPicker.setBackground(Color.decode(newBillboard.getColor("message")));
        String[] imgProps = newBillboard.getImg();
        typePicBox.setSelectedItem(imgProps[0]);
        sourcePicBox.setText(imgProps[1]);
        infoBox.setText(newBillboard.getInfo());
        infoBox.setForeground(Color.decode(newBillboard.getColor("information")));
        infoColorPicker.setBackground(Color.decode(newBillboard.getColor("information")));

        frame.add(title);
        frame.add(bgColorPicker);
        frame.add(msgLabel);
        frame.add(msgBox);
        frame.add(msgColorPicker);
        frame.add(sourcePicLabel);
        frame.add(typePicBox);
        frame.add(sourcePicBox);
        frame.add(infoLabel);
        frame.add(infoBox);
        frame.add(infoColorPicker);
        frame.add(button);
        bgColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color bgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.white);
                bgColorPicker.setBackground(bgColor);
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
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(13, 1));
        frame.setVisible(true);
    }

    public static void editFromServer() {

    }
}
