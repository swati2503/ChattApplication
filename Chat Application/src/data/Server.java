package data;
import java.net.*;
import java.io.*;

public class Server {
    //Constructor
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Server()
    {
        try {
            server=new ServerSocket(7777);
            System.out.println("Server Is Ready To Make Connection.");
            System.out.println("Waiting ....................");
            socket=server.accept();
            
            //to read data from BuggerReader
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            //to write data
            out=new PrintWriter(socket.getOutputStream());
            
            //
            startReading();
            startWriting();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //we have read and wirte simoultaneously thats why we create thread
    public void startReading(){
        //thread for read data
        Runnable r1=()->{
            System.out.println("Reader Is Ready");
            try {
                while(true){
                
                    String msg=br.readLine();
                    if(msg.equals("exit")){
                        System.out.println("Client Has Terminated The Chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : "+msg);
                
            }
            } catch (Exception ex) {
                    ex.printStackTrace();
                }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        //thread will take data from user and send the data to client
        Runnable r2=()->{
            System.out.println("Writer Started");
           try {
            while(true){
                
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();
           
            }
             } catch (Exception e) {
                 e.printStackTrace();
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Server Is Going To Start ..........................");
        new Server();
    }
}
