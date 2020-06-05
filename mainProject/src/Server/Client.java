package Server;

import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;

public class Client {
    /**
     *
     * @param userID the users ID to send over the server
     * @param password the password to send over the server
     * @param admin a boolean for the admin privilege to send over the server
     * @param createBB a boolean for the createBB to send over the server
     * @param editAllBB a boolean for the editAllBB to send over the server
     * @param scheduleBB a boolean for the scheduleBB to send over the server
     * @param editUsers a boolean for the editUsers to send over the server
     * @throws SQLException
     * @throws IOException
     */
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

    /**
     *
     * @param userID the users ID to send over the server
     * @throws IOException
     */
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

    /**
     *
     * @return return an integer array list of userID's
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *
     * @param password the password to send over the server
     * @param userID the users ID to send over the server
     * @throws IOException
     */
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

    /**
     *
     * @param userID the users ID to send over the server
     * @param password the password to send over the server
     * @return session token for authentication
     * @throws IOException
     */
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

    /**
     *
     * @param userID the users ID to send over the server
     * @throws IOException
     */
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

    /**
     *
     * @param userID the users ID to send over the server
     * @return A boolean array list of permissions for a user
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *
     * @param userID the users ID to send over the server and set as a permission
     * @param setAdmin a boolean for the admin privilege to send over the server and set as a permission
     * @param setCreateBB a boolean for the createBB to send over the server and set as a permission
     * @param setEditAllBB a boolean for the editAllBB to send over the server and set as a permission
     * @param setScheduleBB a boolean for the scheduleBB to send over the server and set as a permission
     * @param editUsers a boolean for the editUsers to send over the server and set as a permission
     * @throws IOException
     */
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

    /**
     *
     * @param bbName the name of the billboard to scheduling
     * @param creator the creator ID of the billboard scheduling
     * @param dateStart the start Date of the billboard scheduling
     * @param duration the duration of the billboard scheduling
     * @param repD the amount of repeat days the billboard will have
     * @param repH the amount of repeat hours the billboard will have
     * @param repM the amount of repeat minutes the billboard will have
     * @throws IOException
     */
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

    /**
     *
     * @return an array list of the schedules
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *
     * @param bbname the name of the billboard too remove from schedule
     * @param dateStart the start date of the billboard that will be removed
     * @throws IOException
     */
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

    /**
     *
     * @param currentStamp the date and time that will be used to get the current billboard to display
     * @return the current billboard that needs to be displayed
     * @throws IOException
     * @throws ClassNotFoundException
     */
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

    /**
     *
     * @param token the session token we want to check to decide if a user is allowed to do a task
     * @param userID the ID of the user who wants to perform an action
     * @return a boolean with the result of weather the session is in the session tables
     * @throws IOException
     */
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

    /**
     *
     * @param BBname the name of the billboard to be created
     * @param creator the creator of the billboard
     * @param billboard the object that holds the xml that will be sent over server
     * @throws IOException
     */
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

    /**
     *
     * @param BBname the name of the billboard the method is attempting to delete
     * @throws IOException
     */
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

    /**
     *
     * @param bbName the name of the billboard that's contents wants to recovered and sends it over the server
     * @return the billboards xml stored as a string and
     * @throws IOException
     */
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

    /**
     *
     * @return an arraylist that returns all billboard's names and creators
     * @throws IOException
     * @throws ClassNotFoundException
     */
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
