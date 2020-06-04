package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import ControlPanel.Utility.billboard;
import Server.ServerBillboard;

public class billboardEditGUI {
    private static File selectedFile;

    public static void main(String[] args) {
//    public billboardEditGUI() {
        JFrame frame = new JFrame("Edit an Existing Billboard");
        JPanel top = new JPanel();
        JLabel title = new JLabel("Where would you like to edit from", SwingConstants.CENTER);
        JPanel mid = new JPanel();
        JButton fromFile = new JButton("Computer");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML file", "xml");
        fileChooser.addChoosableFileFilter(xmlFilter);
        JPanel bot = new JPanel();
        JTextField xmlName = new JTextField();
        JButton fromServer = new JButton("Server");

        frame.add(title);
        frame.add(fromFile);
        frame.add(xmlName);
        frame.add(fromServer);

        fromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {    //opens a file browser to select a file and imports it to the billboard class
                int confirmation = fileChooser.showOpenDialog(frame);
                if (confirmation == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Importing file...");
                    selectedFile = fileChooser.getSelectedFile();
                    editFromFile(selectedFile.getAbsoluteFile());
                    frame.dispose();
                }
            }
        });
        fromServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ServerBillboard serverConn = new ServerBillboard();
                    String billboard = serverConn.getBBInfo(xmlName.getText());
                    if (billboard == null) {
                        return;
                    }
                    editFromServer(xmlName.getText());
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
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

        newBillboard.importXML(xmlFile, null, "file");
        msgBox.setText(newBillboard.getMsg());
        msgBox.setForeground(Color.decode(newBillboard.getColor("message")));
        msgColorPicker.setBackground(Color.decode(newBillboard.getColor("message")));
        HashMap<String, String> imgProps = newBillboard.getImg();
        if (imgProps != null) {
            typePicBox.setSelectedItem(imgProps.get("type"));
            sourcePicBox.setText(imgProps.get("source"));
        }
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
                //upload the created XML file to the server
                ServerBillboard serverConn = new ServerBillboard();
                try {
                    serverConn.createBB(xmlFile.getName(), 1, newBillboard.xmlToString());
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
                frame.dispose();
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(13, 1));
        frame.setVisible(true);
    }

    public static void editFromServer(String fileName) throws SQLException, IOException, ClassNotFoundException {
        ServerBillboard serverConn = new ServerBillboard();
        String billboard = serverConn.getBBInfo(fileName);

        JFrame frame = new JFrame("Edit an Existing Billboard");
        JLabel title = new JLabel("Editing " + fileName, SwingConstants.CENTER);
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

        newBillboard.importXML(null, billboard, "server");
        msgBox.setText(newBillboard.getMsg());
        msgBox.setForeground(Color.decode(newBillboard.getColor("message")));
        msgColorPicker.setBackground(Color.decode(newBillboard.getColor("message")));
        HashMap<String, String> imgProps = newBillboard.getImg();
        typePicBox.setSelectedItem(imgProps.get("type"));
        sourcePicBox.setText(imgProps.get("source"));
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
                //upload the created XML file to the server
                ServerBillboard serverConn = new ServerBillboard();
                try {
                    serverConn.createBB(fileName, 1, newBillboard.xmlToString());
                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                }
                frame.dispose();
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(13, 1));
        frame.setVisible(true);
    }
}
