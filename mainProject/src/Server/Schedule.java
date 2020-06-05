package Server;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Schedule {
    /**
     *
     * @param bbName the name of the billboard to scheduling
     * @param creator the creator ID of the billboard scheduling
     * @param dateStart the start Date of the billboard scheduling
     * @param duration the duration of the billboard scheduling
     * @param repD the amount of repeat days the billboard will have
     * @param repH the amount of repeat hours the billboard will have
     * @param repM the amount of repeat minutes the billboard will have
     * @throws ParseException
     * @throws SQLException
     */
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

    /**
     *
     * @return  an array list of the schedules
     * @throws SQLException
     */
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
            int repeatM = rs.getInt("repeatMin");

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

    /**
     *
     * @param bbname the name of the billboard too remove from schedule
     * @param dateStart the start date of the billboard that will be removed
     * @throws SQLException
     */
    public void rmvFromSch(String bbname,Timestamp dateStart) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(" DELETE FROM schedule WHERE BBname=? And STime=?");
        statement.clearParameters();
        statement.setString(1,bbname);
        statement.setTimestamp(2,dateStart);
        statement.executeUpdate();
        statement.close();
    };

    /**
     *
     * @param currentStamp the date and time that will be used to get the current billboard to display
     * @return the current billboard that needs to be displayed
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String currentBB(Timestamp currentStamp) throws SQLException, IOException, ClassNotFoundException {
        Schedule updateTB = new Schedule();
        updateTB.editRepeats(currentStamp);
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
        String answer =getBB.getBBInfo(currentBB);
        return answer;
    };

    /**
     *
     * @param curTime checks the time and updates all billboards with repeats that are prior to it
     * @throws SQLException
     */
    public void editRepeats(Timestamp curTime) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT BBname, STime, ETime, repeatDay, repeatHour, repeatMin, createNum FROM schedule");
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
                long currentStMS=startTime.getTime();
                long currentEtMS=endTime.getTime();
                PreparedStatement statementUpdateTime = connection.prepareStatement("UPDATE schedule SET STime=?, ETime=? WHERE createNum=?");
                statementUpdateTime.clearParameters();
                if(reD>0){
                    long reDay=reD*24*60*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reDay);
                    endTime = new java.sql.Timestamp(currentEtMS+reDay);

                }
                else if(reH>0){
                    long reHour=reH*60*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reHour);
                    endTime = new java.sql.Timestamp(currentEtMS+reHour);
                }
                else if(reM>0){
                    long reMin=reM*60*1000;
                    startTime = new java.sql.Timestamp(currentStMS+reMin);
                    endTime = new java.sql.Timestamp(currentEtMS+reMin);

                }
                statementUpdateTime.setTimestamp(1,startTime);
                statementUpdateTime.setTimestamp(2,endTime);
                statementUpdateTime.setLong(3,crtNum);
                statementUpdateTime.executeUpdate();


            }

        }

    }

}
