package Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerBillboard {
    public void createBB(String BBname,int creator,Object billboard) throws SQLException, IOException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO Billboards Values(?,?,?)");

        ByteArrayOutputStream bbIn = new ByteArrayOutputStream();
        ObjectOutputStream bbOut = new ObjectOutputStream(bbIn);
        bbOut.writeObject(billboard);
        byte[] bbData = bbIn.toByteArray();

        insertStatement.clearParameters();
        insertStatement.setString(1, BBname);
        insertStatement.setInt(2, creator);
        insertStatement.setBinaryStream(3,new ByteArrayInputStream(bbData),bbData.length);

        insertStatement.executeUpdate();
        insertStatement.close();
        connection.close();
    };
    public void deleteBB(String BBname) throws SQLException {
        Connection connection = DBConnection.getInstance();
        PreparedStatement statement = connection.prepareStatement(" DELETE FROM Billboards WHERE BBName=?");
        statement.clearParameters();
        statement.setString(1,BBname);
        statement.executeUpdate();
        statement.close();
        connection.close();
    };
    public void getBBInfo(){}
    public void currentBB(){};
}
