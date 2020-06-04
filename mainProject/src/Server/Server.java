package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(12345);
        Users user = new Users();
        logIO log = new logIO();
        Schedule schedule = new Schedule();
        session session = new session();
        Permission permission = new Permission();
        ServerBillboard billboard = new ServerBillboard();

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
//            else if(methodRun.equals("listUsers")){
//
//                ArrayList lU = user.listUsers();
//                //oos.writeUnshared(lU);
//
//                FileOutputStream fos = new FileOutputStream("listData");
//                oos = new ObjectOutputStream(fos);
//                oos.writeObject(lU);
//                fos.close();
//
//            }
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
            else if(methodRun.equals("setPermission")){
                permission.setPermission(ois.readInt(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean(),ois.readBoolean());
            }
            else if(methodRun.equals("BBinfo")){
                //String methIn = ois.readUTF();
                String result =billboard.getBBInfo(ois.readUTF());
                oos.writeUTF(result);
                oos.flush();
            }


            ois.close();
            oos.close();
            socket.close();
        }
    }
}
