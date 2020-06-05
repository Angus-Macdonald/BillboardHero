package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

public class Server {
    public static void server() throws IOException, SQLException, ClassNotFoundException, ParseException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Users user = new Users();
        logIO log = new logIO();
        Schedule schedule = new Schedule();
        session session = new session();
        Permission permission = new Permission();
        ServerBillboard billboard = new ServerBillboard();

        CreateDB init = new CreateDB();
        init.createDB();

        for(;;){
            Socket socket = serverSocket.accept();
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String methodRun = ois.readUTF();//////read
            if(methodRun.equals("createUser")){
                user.createUser(ois.readInt(),ois.readUTF(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean());
            }
            else if(methodRun.equals("deleteUser")){
                user.deleteUser(ois.readInt());
            }
            else if(methodRun.equals("listUsers")){
                ArrayList al = user.listUsers();
                oos.writeObject(al);
                oos.flush();
            }
            else if(methodRun.equals("setPassword")){
                user.setPassword(ois.readUTF(),ois.readInt());
            }
            else if(methodRun.equals("login")){
                int token = log.login(ois.readInt(),ois.readUTF());
                oos.writeInt(token);
                oos.flush();
            }
            else if(methodRun.equals("logout")){
                log.logout(ois.readInt());
            }
            else if(methodRun.equals("ChkPerms")){
                ArrayList al = permission.ChkPerms(ois.readInt());
                oos.writeObject(al);
            }
            else if(methodRun.equals("setPermission")){
                permission.setPermission(ois.readInt(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean());
            }
            else if(methodRun.equals("scheduleBB")){
                //Timestamp timestamp =ois.available();
                schedule.scheduleBB(ois.readUTF(),ois.readInt(), (Timestamp) ois.readObject(),ois.readInt(),ois.readInt(),ois.readInt(),ois.readInt());
            }
            else if(methodRun.equals("viewSchedule")) {
                ArrayList al = schedule.viewSchedule();
                oos.writeObject(al);
                oos.flush();
            }
            else if(methodRun.equals("rmvFromSch")){
                schedule.rmvFromSch(ois.readUTF(), (Timestamp) ois.readObject());
            }
            else if(methodRun.equals("currentBB")){
                Object bb = schedule.currentBB((Timestamp) ois.readObject());
                oos.writeObject(bb);
                oos.flush();
            }
            else if(methodRun.equals("sessionCheck")){
                boolean bool = session.sessionCheck(ois.readInt(),ois.readInt());
                oos.writeBoolean(bool);
                oos.flush();
            }
            else if(methodRun.equals("createBB")){
                billboard.createBB(ois.readUTF(),ois.readInt(),ois.readObject());
            }
            else if(methodRun.equals("deleteBB")){
                billboard.deleteBB(ois.readUTF());
            }
            else if(methodRun.equals("BBinfo")){
                //String methIn = ois.readUTF();
                String result =billboard.getBBInfo(ois.readUTF());
                oos.writeUTF(result);
                oos.flush();
            }
            else if(methodRun.equals("ListBillboards")){
                ArrayList al = billboard.ListBillboards();
                oos.writeObject(al);
                oos.flush();
            }

            ois.close();
            oos.close();
            socket.close();
        }
    }
}
