package Viewer.BillboardViewer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest extends TimerTask {
    private boolean isResponse = false;

    public void run() {
        System.out.println("oi");
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTest(),0,15000);
    }
}
