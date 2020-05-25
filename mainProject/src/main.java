
import javax.swing.*;

class main{
    public static void main(String[] args) {

        boolean adminPermission = true;

        JFrame frame = new JFrame("Welcome Panel");
        JPanel panel1 = new JPanel();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Open");

        menuBar.add(menu);
        menu.add(item);
        frame.setJMenuBar(menuBar);

        JButton createNewBillboard = new JButton("Create New Billboard");
        JButton userManagement = new JButton("User Management");


        panel1.add(createNewBillboard);
        if(adminPermission) {
            panel1.add(userManagement);
        }

        frame.getContentPane().add(panel1);


        frame.pack();
        frame.setVisible(true);


    }
}