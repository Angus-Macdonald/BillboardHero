package ControlPanel.Home.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import ControlPanel.Utility.billboard;

public class billboardTemptGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Create a New Billboard");
        JTextField msgBox = new JTextField();
        JTextField picBox1 = new JTextField();
        JTextField picBox2 = new JTextField();
        JTextField infoBox = new JTextField();
        JButton button = new JButton("Create");

        msgBox.setBounds(50, 50, 300, 50);
        picBox1.setBounds(50, 200, 300, 50);
        picBox2.setBounds(50, 200, 300, 50);
        infoBox.setBounds(50, 450, 300, 50);
        button.setBounds(50, 200, 10, 10);
        frame.add(msgBox);
        frame.add(picBox1);
        frame.add(picBox2);
        frame.add(infoBox);
        frame.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                billboard billboard1 = new billboard("billboard1");
                if (!msgBox.getText().isEmpty()) {
                    billboard1.addMsg(msgBox.getText());
                }
                if (!picBox1.getText().isEmpty() && !picBox2.getText().isEmpty()) {
                    billboard1.addImg(picBox1.getText(), picBox2.getText());
                }
                if (!infoBox.getText().isEmpty()) {
                    billboard1.addInfo(infoBox.getText());
                }
            }
        });

        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(8, 1));
        frame.setVisible(true);
    }
}
