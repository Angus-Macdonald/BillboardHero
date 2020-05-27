package Server;

import java.sql.*;

public class TestDB {
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mpdb","root","");

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM test");

        //first row
        resultSet.next();
        int personId = resultSet.getInt(1);
        String peronName = resultSet.getString("personName");
        float someNumber = resultSet.getFloat(3);
        System.out.println("personID: "+ personId);
        System.out.println("peronName: "+ peronName);
        System.out.println("someNumber: "+ someNumber);

        //second row
        resultSet.next();
        personId = resultSet.getInt(1);
        peronName = resultSet.getString("personName");
        someNumber = resultSet.getFloat(3);
        System.out.println("personID: "+ personId);
        System.out.println("peronName: "+ peronName);
        System.out.println("someNumber: "+ someNumber);

        statement.close();

        connection.close();
    }
}
