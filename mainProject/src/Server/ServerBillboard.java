package Server;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerBillboard {
    public void createBB(String BBname,int creator,Object billboard) throws SQLException, IOException {
        Connection connection = DBConnection.getInstance();
        //check if it exists
        int found = 0;
        PreparedStatement findstatement = connection.prepareStatement("SElECT BBName FROM Billboards WHERE BBName=?");
        findstatement.clearParameters();
        findstatement.setString(1, BBname);
        ResultSet rs = findstatement.executeQuery();
        while(rs.next()) {
            String someName = rs.getString("BBName");
            found++;
        }
        findstatement.close();
        //turn object into byte array
        ByteArrayOutputStream bbIn = new ByteArrayOutputStream();
        ObjectOutputStream bbOut = new ObjectOutputStream(bbIn);
        bbOut.writeObject(billboard);
        byte[] bbData = bbIn.toByteArray();

        //update if exists
        if(found>0){
            PreparedStatement statementUpdateBB = connection.prepareStatement("UPDATE Billboards SET billboard=? WHERE BBname=?");
            statementUpdateBB.clearParameters();
            statementUpdateBB.setBinaryStream(1, new ByteArrayInputStream(bbData), bbData.length);
            statementUpdateBB.setString(2,BBname);
            statementUpdateBB.executeUpdate();
            statementUpdateBB.close();
        }
        //insert into billboards
        else {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Billboards Values(?,?,?)");
            insertStatement.clearParameters();
            insertStatement.setString(1, BBname);
            insertStatement.setInt(2, creator);
            insertStatement.setBinaryStream(3, new ByteArrayInputStream(bbData), bbData.length);

            insertStatement.executeUpdate();
            insertStatement.close();
        }
    };


    public void deleteBB(String BBname) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(" DELETE FROM Billboards WHERE BBName=?");
        statement.clearParameters();
        statement.setString(1,BBname);
        statement.executeUpdate();
        statement.close();
    };
    public String getBBInfo(String bbName) throws SQLException, IOException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement("SElECT Billboard FROM billboards WHERE BBName=? ");
        statement.clearParameters();
        statement.setString(1, bbName);
        ResultSet rs = statement.executeQuery();
        String bbInfo = "nothing";
        while(rs.next()) {
            byte[] data = rs.getBytes("billboard");
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            Object objectBB = ois.readObject();
            bbInfo = objectBB.toString();
        }



        statement.close();
        //connection.close();
        return bbInfo;
    }
    public ArrayList ListBillboards() throws SQLException {
        Connection connection = DBConnection.getInstance();
        ArrayList billboardList = new ArrayList();
        PreparedStatement statement = connection.prepareStatement("SElECT BBname, Creator FROM billboards");
        statement.clearParameters();
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            String listname = rs.getString("BBname");
            int listCreator = rs.getInt("Creator");
            billboardList.add(listname);
            billboardList.add(listCreator);
        }
        statement.close();
        return billboardList;
    }

}
