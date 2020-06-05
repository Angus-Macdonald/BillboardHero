package ControlPanel.Home.GUI.Billboard;

import Server.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class billboardScheduleGUI {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new billboardScheduleGUI();
    }

    public billboardScheduleGUI() throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame("Schedule a Billboard");
        JLabel title = new JLabel("Pick a Date and a Billboard");
        Client serverConn = new Client();
        String[][] data = updateData(serverConn);
        String[] headings = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        JTable calender = new JTable(data, headings);
        JScrollPane scrollPane = new JScrollPane(calender);
        JPanel inputs = new JPanel();
        JPanel inputFields = new JPanel();
        JLabel yearLabel = new JLabel("Year: ", SwingConstants.RIGHT);
        SpinnerModel yearModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR),
                1970, 3000, 1);
        JSpinner yearBox = new JSpinner(yearModel);
        JLabel monthLabel = new JLabel("Month: ", SwingConstants.RIGHT);
        SpinnerModel monthModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.MONTH) + 1,
                1, 12, 1);
        JSpinner monthBox = new JSpinner(monthModel);
        JLabel dayLabel = new JLabel("Day: ", SwingConstants.RIGHT);
        SpinnerModel dayModel = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                1, 31, 1);
        JSpinner dayBox = new JSpinner(dayModel);
        JLabel hourLabel = new JLabel("Hour: ", SwingConstants.RIGHT);
        SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 24, 1);
        JSpinner hourBox = new JSpinner(hourModel);
        JLabel minLabel = new JLabel("Minute: ", SwingConstants.RIGHT);
        SpinnerModel minModel = new SpinnerNumberModel(1, 1, 60, 1);
        JSpinner minBox = new JSpinner(minModel);
        JLabel durationLabel = new JLabel("Duration: ", SwingConstants.RIGHT);
        SpinnerModel durationModel = new SpinnerNumberModel(1, 1, 999, 1);
        JSpinner durationBox = new JSpinner(durationModel);
        JLabel repeatDayLabel = new JLabel("Every _ day: ", SwingConstants.RIGHT);
        SpinnerModel repeatDayModel = new SpinnerNumberModel(0, 0, 999, 1);
        JSpinner repeatDay = new JSpinner(repeatDayModel);
        JLabel repeatHourLabel = new JLabel("Every _ hour: ", SwingConstants.RIGHT);
        SpinnerModel repeatHourModel = new SpinnerNumberModel(0, 0, 999, 1);
        JSpinner repeatHour = new JSpinner(repeatHourModel);
        JLabel repeatMinLabel = new JLabel("Every _ min: ", SwingConstants.RIGHT);
        SpinnerModel repeatMinModel = new SpinnerNumberModel(0, 0, 999, 1);
        JSpinner repeatMin = new JSpinner(repeatMinModel);
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
        repeatDay.addChangeListener(e -> {
            if (repeatDay.getValue().equals(0)) {
                repeatHour.setEnabled(true);
                repeatMin.setEnabled(true);
            } else {
                repeatHour.setEnabled(false);
                repeatMin.setEnabled(false);
            }
        });
        repeatHour.addChangeListener(e -> {
            if (repeatHour.getValue().equals(0)) {
                repeatDay.setEnabled(true);
                repeatMin.setEnabled(true);
            } else {
                repeatDay.setEnabled(false);
                repeatMin.setEnabled(false);
            }
        });
        repeatMin.addChangeListener(e -> {
            if (repeatMin.getValue().equals(0)) {
                repeatDay.setEnabled(true);
                repeatHour.setEnabled(true);
            } else {
                repeatDay.setEnabled(false);
                repeatHour.setEnabled(false);
            }
        });
        button.addActionListener(e -> {
            try {
                if (serverConn.getBBInfoS(billboardName.getText()).equals("nothing")) {
                    JOptionPane.showMessageDialog(frame, "Invalid billboard name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            if (!repeatDay.getValue().equals(0) && !repeatHour.getValue().equals(0) && !repeatMin.getValue().equals(0)) {
                JOptionPane.showMessageDialog(frame, "Please only fill in one repeat field.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String input = yearBox.getValue() + "-" +
                    monthBox.getValue() + "-" +
                    dayBox.getValue() + " " +
                    hourBox.getValue() + ":" +
                    minBox.getValue() + ":" +
                    "00.00";
            Timestamp timestamp = java.sql.Timestamp.valueOf(input);

            try {
                serverConn.scheduleBBS(
                        billboardName.getText(),
                        2000,
                        timestamp,
                        (int) durationBox.getValue(),
                        (int) repeatDay.getValue(),
                        (int) repeatHour.getValue(),
                        (int) repeatMin.getValue()
                );
                System.out.println("Successfully added billboard (" + billboardName.getText() + ") to the schedule.");
                JOptionPane.showMessageDialog(frame, "Successfully added billboard (" + billboardName.getText() + ") to the schedule.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        frame.setSize(500, 400);
        frame.setLayout(new GridLayout(2, 1));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public String[][] updateData(Client serverConn) throws IOException, ClassNotFoundException {
        ArrayList scheduledBillboards = serverConn.viewScheduleS();
        String[][] returnVal = new String[scheduledBillboards.size()][7];

        for (int i = 0; i < scheduledBillboards.size(); i+=7) {
            long timestamp = java.sql.Timestamp.valueOf(scheduledBillboards.get(i+2).toString()).getTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(timestamp);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int weekInYear = calendar.get(Calendar.WEEK_OF_YEAR);
            int today = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

            if (today == weekInYear) {
                for (int k = 0; k < 7; k++) {
                    System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
                    if (dayInWeek == k+1) {
                        returnVal[0][k] = returnVal[0][k] + " | " + (String) scheduledBillboards.get(i);
                    }
                }
            }
        }

        return returnVal;
    }
}
