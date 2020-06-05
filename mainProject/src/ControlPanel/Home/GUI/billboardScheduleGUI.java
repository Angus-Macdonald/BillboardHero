package ControlPanel.Home.GUI;

import javax.swing.*;

import Server.Client;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class billboardScheduleGUI {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new billboardScheduleGUI();
    }

    public billboardScheduleGUI() throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("Schedule a Billboard");
        JLabel title = new JLabel("Pick a Data and a Billboard");
        Client serverConn = new Client();
        ArrayList scheduledBillboards = serverConn.viewScheduleS();
        String[][] data = new String[scheduledBillboards.size() + 1][7];
        for (int i = 0; i < 7; i++) {
            data[0][i] = "";
//            billboardNames[0][i] = scheduledBillboards.get(i).toString();
        }
        String[] headings = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        JTable calender = new JTable(data, headings);
        JScrollPane scrollPane = new JScrollPane(calender);
        JPanel inputs = new JPanel();
        JPanel inputFields = new JPanel();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss.SS");
        Date currentDate = new Date();
        JLabel yearLabel = new JLabel("Year: ", SwingConstants.RIGHT);
        JSpinner yearBox = new JSpinner();
        JLabel monthLabel = new JLabel("Month: ", SwingConstants.RIGHT);
        JSpinner monthBox = new JSpinner();
        JLabel dayLabel = new JLabel("Day: ", SwingConstants.RIGHT);
        JSpinner dayBox = new JSpinner();
        JLabel hourLabel = new JLabel("Hour: ", SwingConstants.RIGHT);
        JSpinner hourBox = new JSpinner();
        JLabel minLabel = new JLabel("Minute: ", SwingConstants.RIGHT);
        JSpinner minBox = new JSpinner();
        JLabel durationLabel = new JLabel("Duration: ", SwingConstants.RIGHT);
        JSpinner durationBox = new JSpinner();
        JLabel repeatDayLabel = new JLabel("Every day: ", SwingConstants.RIGHT);
        JCheckBox repeatDay = new JCheckBox("", false);
        JLabel repeatHourLabel = new JLabel("Every hour: ", SwingConstants.RIGHT);
        JCheckBox repeatHour = new JCheckBox("", false);
        JLabel repeatMinLabel = new JLabel("Every x min: ", SwingConstants.RIGHT);
        JSpinner repeatMin = new JSpinner();
        JPanel namePanel = new JPanel();
        JLabel billboardNameLabel = new JLabel("Billboard Name: ", SwingConstants.CENTER);
        JTextField billboardName = new JTextField();
        JButton button = new JButton("Schedule");

        frame.add(scrollPane);
        inputs.add(yearLabel);
        inputs.add(yearBox);
        inputs.add(monthLabel);
        inputs.add(monthBox);
        inputs.add(dayLabel);
        inputs.add(dayBox);
        inputs.add(hourLabel);
        inputs.add(hourBox);
        inputs.add(minLabel);
        inputs.add(minBox);
        inputs.add(durationLabel);
        inputs.add(durationBox);
        inputs.add(repeatDayLabel);
        inputs.add(repeatDay);
        inputs.add(repeatHourLabel);
        inputs.add(repeatHour);
        inputs.add(repeatMinLabel);
        inputs.add(repeatMin);
        inputs.setLayout(new GridLayout(3, 6));
        inputFields.add(inputs);
        namePanel.add(billboardNameLabel);
        namePanel.add(billboardName);
        namePanel.setLayout(new GridLayout(1, 2));
        inputFields.add(namePanel);
        inputFields.add(button);
        inputFields.setLayout(new GridLayout(3, 1));
        frame.add(inputFields);
        button.addActionListener(e -> {
            String input = yearBox.getValue() + "/" +
                    monthBox.getValue() + "/" +
                    dayBox.getValue() + " " +
                    hourBox.getValue() + ":" +
                    minBox.getValue() + ":" +
                    "00.00";
            Date scheduleDate = null;
            try {
                scheduleDate = dateFormat.parse(input);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            System.out.println(billboardName.getText());
            System.out.println(2000);
            System.out.println(scheduleDate);
        });

        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(2, 1));
        frame.setVisible(true);
    }
}
