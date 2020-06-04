package Server;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeUTF("BBinfo");/////write
        oos.writeUTF("the44");
        oos.flush();
        String result = ois.readUTF();
        System.out.println(result);

        ois.close();
        oos.close();
        socket.close();
    }
    public void createUserS(int userID,
                            String password,
                            boolean admin,
                            boolean createBB,
                            boolean editAllBB,
                            boolean scheduleBB,
                            boolean editUsers) throws SQLException, IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("createUser");
        //callparams
        oos.writeInt(userID);
        oos.writeUTF(password);
        oos.writeBoolean(admin);
        oos.writeBoolean(createBB);
        oos.writeBoolean(editAllBB);
        oos.writeBoolean(scheduleBB);
        oos.writeBoolean(editUsers);

        //get result
        oos.flush();
        //String result = ois.readUTF();

        ois.close();
        oos.close();
        socket.close();
    }
    public void deleteUserS(int userID) throws  IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("deleteUser");

        //callparams
        oos.writeInt(userID);
        //get results
        oos.flush();

        ois.close();
        oos.close();
        socket.close();
    }
//    public ArrayList<Integer> listUsersS() throws IOException, ClassNotFoundException {
//        Socket socket = new Socket("localhost",12345);
//        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//
//        //call method
//        oos.writeUTF("listUsers");
//
//        //get results
//        oos.flush();
//
//
//        System.out.println(userslist);
//
//
//        ois.close();
//        oos.close();
//        socket.close();
//        return userslist;
//    };
public void setPasswordS(String password, int userID ) throws IOException {
    Socket socket = new Socket("localhost",12345);
    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

    //call method
    oos.writeUTF("setPassword");

    //callparams
    oos.writeUTF(password);
    oos.writeInt(userID);
    //get results
    oos.flush();

    ois.close();
    oos.close();
    socket.close();
}
    public static int loginS(int userID, String password) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("login");

        //callparams
        oos.writeInt(userID);
        oos.writeUTF(password);
        //get results
        oos.flush();
        int result = ois.readInt();

        ois.close();
        oos.close();
        socket.close();
        return result;
    }
    public void logoutS(int userID) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("logout");

        //callparams
        oos.writeInt(userID);
        //get results
        oos.flush();

        ois.close();
        oos.close();
        socket.close();
    }
    public void setPermissionS(int userID,
                              boolean setAdmin,
                              boolean setCreateBB,
                              boolean setEditAllBB,
                              boolean setScheduleBB,
                              boolean editUsers) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("setPermission");
        //callparams
        oos.writeInt(userID);
        oos.writeBoolean(setAdmin);
        oos.writeBoolean(setCreateBB);
        oos.writeBoolean(setEditAllBB);
        oos.writeBoolean(setScheduleBB);
        oos.writeBoolean(editUsers);

        //get result
        oos.flush();
        //String result = ois.readUTF();

        ois.close();
        oos.close();
        socket.close();

    }
    public static String getBBInfoS(String bbName) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("BBinfo");

        //callparams
        oos.writeUTF(bbName);
        //get results
        oos.flush();
        String result = ois.readUTF();

        ois.close();
        oos.close();
        socket.close();
        return result;
    }
}
