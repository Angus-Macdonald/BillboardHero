package ControlPanel.Home.GUI.Billboard;

import ControlPanel.Utility.billboard;
import Server.Client;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class billboardEditGUI {
    private static File selectedFile;

    public static void main(String[] args) {
        new billboardEditGUI();
    }

    public billboardEditGUI() {
        JFrame frame = new JFrame("Edit an Existing Billboard");
        JLabel title = new JLabel("Where would you like to edit from", SwingConstants.CENTER);
        JButton fromFile = new JButton("Computer");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("XML file", "xml");
        fileChooser.addChoosableFileFilter(xmlFilter);
        JTextField xmlName = new JTextField();
        JButton fromServer = new JButton("Server");

        frame.add(title);
        frame.add(fromFile);
        frame.add(xmlName);
        frame.add(fromServer);

        fromFile.addActionListener(e -> {    //opens a file browser to select a file and imports it to the billboard class
            int confirmation = fileChooser.showOpenDialog(frame);
            if (confirmation == JFileChooser.APPROVE_OPTION) {
                System.out.println("Importing file...");
                selectedFile = fileChooser.getSelectedFile();
                try {
                    edit(selectedFile.getAbsoluteFile(), null, "file");
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                //editFromFile(selectedFile.getAbsoluteFile());
                frame.dispose();
            }
        });
        fromServer.addActionListener(e -> {
            try {
                Client serverConn = new Client();
                if (serverConn.getBBInfoS(xmlName.getText()).equals("nothing")) {
                    JOptionPane.showMessageDialog(frame, "Invalid billboard name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                edit(null, xmlName.getText(), "server");
                frame.dispose();
                //editFromServer(xmlName.getText());
            } catch (SQLException | IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        frame.setVisible(true);
    }

    public static void edit(File xmlFile, String billboardName, String fileOrServer) throws SQLException, IOException, ClassNotFoundException {
        JFrame frame = new JFrame("Edit an Existing Billboard");
        JLabel title = new JLabel("Editing Billboard", SwingConstants.CENTER);
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
        JCheckBox exportBillboard = new JCheckBox("Export Billboard", false);
        JButton deleteButton = new JButton("Delete Billboard");
        JButton saveButton = new JButton("Save and Exit");
        billboard newBillboard = new billboard();

        if (fileOrServer.equals("file")) {
            newBillboard.importXML(xmlFile, null, "file");
        } else if (fileOrServer.equals("server")) {
            Client serverConn = new Client();
            String xmlString = serverConn.getBBInfoS(billboardName);
            newBillboard.importXML(null, xmlString, "server");
        }
        bgColorPicker.setBackground(Color.decode(newBillboard.getColor("billboard")));
        msgBox.setText(newBillboard.getMsg());
        msgBox.setForeground(Color.decode(newBillboard.getColor("message")));
        HashMap<String, String> imgProps = newBillboard.getImg();
        if (!imgProps.isEmpty()) {
            typePicBox.setSelectedItem(imgProps.get("type"));
            sourcePicBox.setText(imgProps.get("source"));
        } else {
            typePicBox.setSelectedItem("None");
        }
        infoBox.setText(newBillboard.getInfo());
        infoBox.setForeground(Color.decode(newBillboard.getColor("information")));

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
        frame.add(exportBillboard);
        if (fileOrServer.equals("server")) { frame.add(deleteButton); } //user can only delete when editing billboards from server
        frame.add(saveButton);
        bgColorPicker.addActionListener(e -> {
            Color bgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.decode(newBillboard.getColor("billboard")));
            bgColorPicker.setBackground(bgColor);
        });
        msgColorPicker.addActionListener(e -> {
            if (!msgBox.getText().isEmpty()) {
                Color msgColor = JColorChooser.showDialog(frame, "Pick a Color", Color.decode(newBillboard.getColor("message")));
                msgBox.setForeground(msgColor);
            }
        });
        infoColorPicker.addActionListener(e -> {
            if (!infoBox.getText().isEmpty()) {
                Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.decode(newBillboard.getColor("information")));
                infoBox.setForeground(infoColor);
            }
        });
        deleteButton.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this billboard?", "Warning", JOptionPane.YES_NO_OPTION);
            if (confirmation == 0) {
                Client serverConn = new Client();
                try {
                    serverConn.deleteBBS(billboardName);
                    System.out.println("Successfully deleted (" + billboardName + ") from server.");
                } catch (IOException throwables) {
                    throwables.printStackTrace();
                }
                new billboardEditGUI();
                frame.dispose();
            }
        });
        saveButton.addActionListener(e -> {
            if (!msgBox.getText().isEmpty() || !typePicBox.getSelectedItem().toString().equals("None") || !infoBox.getText().isEmpty()) {
                newBillboard.addColor("billboard", String.format("#%02X%02X%02X",
                        bgColorPicker.getBackground().getRed(),
                        bgColorPicker.getBackground().getGreen(),
                        bgColorPicker.getBackground().getBlue())
                );
                if (msgBox.getText().length() <= 50) {
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
                if (!typePicBox.getSelectedItem().toString().equals("None") && sourcePicBox.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in the picture source or pick none.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    newBillboard.addImg(typePicBox.getSelectedItem().toString(), sourcePicBox.getText());
                }
                if (infoBox.getText().length() <= 350) {
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
                    if (fileOrServer.equals("file")) {
                        serverConn.createBBS(xmlFile.getName(), 1, newBillboard.xmlToString());
                    } else if (fileOrServer.equals("server")) {
                        serverConn.createBBS(billboardName, 1, newBillboard.xmlToString());
                    }
                } catch (IOException throwables) {
                    throwables.printStackTrace();
                }
                new billboardEditGUI();
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill out at least one field.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(14, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /*
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
        JCheckBox exportBillboard = new JCheckBox("Export Billboard", false);
        JButton button = new JButton("Save and Exit");
        billboard newBillboard = new billboard();

        newBillboard.importXML(xmlFile, null, "file");
        msgBox.setText(newBillboard.getMsg());
        msgBox.setForeground(Color.decode(newBillboard.getColor("message")));
        HashMap<String, String> imgProps = newBillboard.getImg();
        typePicBox.setSelectedItem(imgProps.get("type"));
        sourcePicBox.setText(imgProps.get("source"));
        infoBox.setText(newBillboard.getInfo());
        infoBox.setForeground(Color.decode(newBillboard.getColor("information")));

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
        frame.add(exportBillboard);
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
                }
            }
        });
        infoColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!infoBox.getText().isEmpty()) {
                    Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                    infoBox.setForeground(infoColor);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!msgBox.getText().isEmpty() || !typePicBox.getSelectedItem().toString().equals("None") || !infoBox.getText().isEmpty()) {
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
                    } else if (msgBox.getText().length() > 350) {
                        JOptionPane.showMessageDialog(frame, "Exceeded 50 character limit for information.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (exportBillboard.isSelected()) {
                        newBillboard.writeToFile();
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
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill out at least one field.", "Error", JOptionPane.ERROR_MESSAGE);
                }
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
        JCheckBox exportBillboard = new JCheckBox("Export Billboard", false);
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
        frame.add(exportBillboard);
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
                }
            }
        });
        infoColorPicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!infoBox.getText().isEmpty()) {
                    Color infoColor = JColorChooser.showDialog(frame, "Pick a Color", Color.black);
                    infoBox.setForeground(infoColor);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!msgBox.getText().isEmpty() || !typePicBox.getSelectedItem().toString().equals("None") || !infoBox.getText().isEmpty()) {
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
                    } else if (msgBox.getText().length() > 350) {
                        JOptionPane.showMessageDialog(frame, "Exceeded 50 character limit for information.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (exportBillboard.isSelected()) {
                        newBillboard.writeToFile();
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
                } else {
                    JOptionPane.showMessageDialog(frame, "Please fill out at least one field.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(13, 1));
        frame.setVisible(true);
    }
     */
}