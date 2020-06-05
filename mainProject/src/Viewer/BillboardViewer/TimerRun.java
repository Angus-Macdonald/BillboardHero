package Viewer.BillboardViewer;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerRun extends TimerTask {
    private boolean isResponse = false;

    CreateBillboard viewer = new CreateBillboard();

    public void run() {
        System.out.println("New Connection.");

        try {
            viewer.createBillboard();
        } catch (IOException | ParserConfigurationException | SAXException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerRun(),0,15000);
    }
}
