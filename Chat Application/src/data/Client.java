/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;


import java.io.*;
import java.net.Socket;

/**
 *
 * @author dell
 */
public class Client {
    
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    public Client(){
        try {
            System.out.println("Sending Request To Server.");
            socket=new Socket("127.0.0.1",7777);
            System.out.println("Connection Done.");
            
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
                        System.out.println("Server Has Terminated The Chat.");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : "+msg);
                }
            }catch (Exception ex) {
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
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("Client Is Ready ..........................");
        new Client();
    }
}
