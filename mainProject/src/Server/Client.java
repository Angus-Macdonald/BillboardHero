package Server;

import java.io.*;
import java.net.Socket;
import java.sql.*;
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
    public static void createUserS(int userID,
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
    public static void deleteUserS(int userID) throws  IOException {
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
    public static ArrayList<Integer> listUsersS() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("listUsers");

        //get results
        oos.flush();
        ArrayList<Integer> al = new ArrayList<Integer>();
        al = (ArrayList<Integer>) ois.readObject();


        ois.close();
        oos.close();
        socket.close();
        return al;
    };
public static void setPasswordS(String password, int userID) throws IOException {
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
    public static ArrayList<Boolean> ChkPermsS(int userID) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("ChkPerms");

        //callparams
        oos.writeInt(userID);
        //get results
        oos.flush();
        ArrayList<Boolean> al = new ArrayList<Boolean>();
        al = (ArrayList<Boolean>) ois.readObject();


        ois.close();
        oos.close();
        socket.close();
        return al;
    }
    public static void setPermissionS(int userID,
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
    public void scheduleBBS(String bbName, int creator, Timestamp dateStart, int duration, int repD, int repH, int repM) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("scheduleBB");
        //callparams
        oos.writeUTF(bbName);
        oos.writeInt(creator);
        oos.writeObject(dateStart);
        oos.writeInt(duration);
        oos.writeInt(repD);
        oos.writeInt(repH);
        oos.writeInt(repM);

        //get result
        oos.flush();
        //String result = ois.readUTF();

        ois.close();
        oos.close();
        socket.close();

    }
    public ArrayList viewScheduleS() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("viewSchedule");

        //get results
        oos.flush();
        ArrayList al = new ArrayList();
        al = (ArrayList) ois.readObject();


        ois.close();
        oos.close();
        socket.close();
        return al;
    }
    public void rmvFromSchS(String bbname,Timestamp dateStart) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("rmvFromSch");

        //callparams
        oos.writeUTF(bbname);
        oos.writeObject(dateStart);
        //get results
        oos.flush();

        ois.close();
        oos.close();
        socket.close();
    }
    public Object currentBBS(Timestamp currentStamp) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("currentBB");

        //callparams
        oos.writeObject(currentStamp);
        //get results
        Object bb = ois.readObject();
        oos.flush();

        ois.close();
        oos.close();
        socket.close();
        return bb;
    }
    public boolean sessionCheckS(int token,int userID) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("sessionCheck");

        //callparams
        oos.writeInt(token);
        oos.writeInt(userID);
        //get results
        oos.flush();
        boolean answer = ois.readBoolean();


        ois.close();
        oos.close();
        socket.close();
        return answer;

    }
    public void createBBS(String BBname,int creator,Object billboard) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("createBB");

        //callparams
        oos.writeUTF(BBname);
        oos.writeInt(creator);
        oos.writeObject(billboard);
        //get results
        oos.flush();

        ois.close();
        oos.close();
        socket.close();
    }
    public void deleteBBS(String BBname) throws IOException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("deleteBB");

        //callparams
        oos.writeUTF(BBname);
        //get results
        oos.flush();

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
    public ArrayList ListBillboardsS() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",12345);
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //call method
        oos.writeUTF("ListBillboards");

        //get results
        oos.flush();
        ArrayList al = new ArrayList();
        al = (ArrayList) ois.readObject();


        ois.close();
        oos.close();
        socket.close();
        return al;

    }
}
