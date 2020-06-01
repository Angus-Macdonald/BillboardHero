package Server;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Schedule {
    public void scheduleBB(String bbName, int creator, Timestamp dateStart, int duration, int repD, int repH, int repM) throws ParseException, SQLException {
        //get date start timestamp and add minutes to get end timestamp
        long curTS=dateStart.getTime();
        long min=duration*60*1000;
        java.sql.Timestamp endStamp= new java.sql.Timestamp(curTS+min);
        Connection createUserStatementConnection = DBConnection.getInstance();
        PreparedStatement Statement = createUserStatementConnection.prepareStatement(" INSERT INTO mpdb.schedule (" +
                "bbName," +
                "Creator," +
                "STime," +
                "ETime," +
                "duration," +
                "repeatDay," +
                "repeatHour," +
                "repeatMin) " +
                "Values(?,?,?,?,?,?,?,?)");
        Statement.clearParameters();
        Statement.setString(1,bbName);
        Statement.setInt(2,creator);
        Statement.setTimestamp(3,dateStart);
        Statement.setTimestamp(4,endStamp);
        Statement.setInt(5,duration);
        Statement.setInt(6,repD);
        Statement.setInt(7,repH);
        Statement.setInt(8,repM);
        Statement.executeUpdate();
        Statement.close();

    };
    public ArrayList viewSchedule() throws SQLException {
        ArrayList schList = new ArrayList();
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT BBname, Creator, STime, duration, repeatDay, repeatHour, repeatMin FROM schedule");
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
            String listName = rs.getString("BBname");
            int creator = rs.getInt("Creator");
            Timestamp startTime = rs.getTimestamp("STime");
            int dur = rs.getInt("duration");
            int repeatD = rs.getInt("repeatDay");
            int repeatH = rs.getInt("repeatHour");
            int repeatM = rs.getInt("repeatHour");

            schList.add(listName);
            schList.add(creator);
            schList.add(startTime);
            schList.add(dur);
            schList.add(repeatD);
            schList.add(repeatH);
            schList.add(repeatM);
        }
        statement.close();
        rs.close();

        return(schList);
    };
    public void rmvFromSch(String bbname,Timestamp dateStart) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(" DELETE FROM schedule WHERE BBname=? And STime=?");
        statement.clearParameters();
        statement.setString(1,bbname);
        statement.setTimestamp(2,dateStart);
        statement.executeUpdate();
        statement.close();
    };
    public Object currentBB(Timestamp currentStamp) throws SQLException {
        String currentBB = null;
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT BBname, STime, ETime, createNum FROM schedule");
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String billboardName = rs.getNString("BBname");
            Timestamp startTime = rs.getTimestamp("STime");
            Timestamp endTime = rs.getTimestamp("ETime");
            int result1 = currentStamp.compareTo(startTime);
            int result2 = currentStamp.compareTo(endTime);
            if((result1>0)&&(result2<0)){
                currentBB=billboardName;
            }

        }
        statement.close();
        rs.close();
        ServerBillboard getBB = new ServerBillboard();
        return getBB.getBBInfo(currentBB);
    };
    public void editRepeats(Timestamp curTime) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT BBname, STime, ETime, repeatDay, repeatHour, repeatMin createNum FROM schedule");
        ResultSet rs = statement.executeQuery();

        while(rs.next()) {
            String billboardName = rs.getNString("BBname");
            Timestamp startTime = rs.getTimestamp("STime");
            Timestamp endTime = rs.getTimestamp("ETime");
            int reD = rs.getInt("repeatDay");
            int reH = rs.getInt("repeatHour");
            int reM = rs.getInt("repeatMin");
            long crtNum = rs.getLong("createNum");
            int ispass = curTime.compareTo(endTime);
            if(ispass>0){
                System.out.println(crtNum+" has passed the current time");
                System.out.println("repeats per day "+reD);
                System.out.println("repeats per hour "+reH);
                System.out.println("repeats per Minutes "+reM);
                System.out.println("start time was "+startTime);
                System.out.println("end time was "+endTime);
                long currentStMS=startTime.getTime();
                long currentEtMS=endTime.getTime();
                if(reD>0){
                    long reDay=reD*24*60*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reDay);
                    System.out.println("start time now is "+startTime);
                    endTime = new java.sql.Timestamp(currentEtMS+reDay);
                    System.out.println("start time now is "+endTime);

                }
                if(reH>0){
                    long reHour=reH*60*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reHour);
                    System.out.println("start time now is "+startTime);
                    endTime = new java.sql.Timestamp(currentEtMS+reHour);
                    System.out.println("start time now is "+endTime);
                }
                if(reM>0){
                    long reMin=reH*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reMin);
                    System.out.println("start time now is "+startTime);
                    endTime = new java.sql.Timestamp(currentEtMS+reMin);
                    System.out.println("start time now is "+endTime);

                }
                System.out.println();


            }

        }

    }

}
