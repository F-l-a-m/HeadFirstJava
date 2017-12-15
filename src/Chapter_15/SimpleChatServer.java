package Chapter_15;

import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
    ArrayList<ObjectOutputStream> clientOutputStreams;
    
    public class ClientHandler implements Runnable {
        ObjectInputStream in;
        Socket clientSocket;
        
        public ClientHandler(Socket socket){
            try{
                clientSocket = socket;
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        
        public void run(){
            Object obj2 = null;
            Object obj1 = null;
            try{
                while((obj1 = in.readObject()) != null){
                    obj2 = in.readObject();
                    System.out.println("read two objects");
                    tellEveryone(obj1, obj2);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void go(){
        clientOutputStreams = new ArrayList<ObjectOutputStream>();
        
        try{
            ServerSocket serverSock = new ServerSocket(4242);
            while(true){
                Socket clientSocket = serverSock.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(out);
                
                Thread t = new Thread(new ClientHandler(clientSocket));
                t.start();
                
                System.out.println("got a connection");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void tellEveryone(Object one, Object two){
        Iterator it = clientOutputStreams.iterator();
        while(it.hasNext()){
            try{
                ObjectOutputStream out = (ObjectOutputStream) it.next();
                out.writeObject(one);
                out.writeObject(two);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
