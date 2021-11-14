/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gautam
 */
import java.net.*;
import java.io.*;
public class Server {
    ServerSocket server;
    Socket socket;
     BufferedReader br; //reading
     PrintWriter out; //writing
    
    public Server(){
        try{
    server=new ServerSocket(7777);
    System.out.println("Server is ready for connection");
    System.out.println("Waiting");
    socket=server.accept();
    
    br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out=new PrintWriter(socket.getOutputStream());
    
    startReading();
    startWriting();
     }
    catch(Exception e){
    System.out.println("exc"+e);
    }
    }
    
    public void startReading(){
    //thread read karke deta rahega
    
    Runnable r1=()->{
        try{
        System.out.println("Reader Started");
    while(true){
    String msg=br.readLine();
    if(msg.equals("exit")){
        System.out.println("Client terinated the chat");
        socket.close();
        break;
     }
    System.out.println("Client :"+msg);
    }
        }catch(Exception e){
        System.out.println("connection closed");
        }
    
    };
       new Thread(r1).start();
    }
    public void startWriting(){
        
        //thread-data user lega and the send karega client tak
    Runnable r2=()->{
         System.out.println("Writer Started");
    try{
         while(!socket.isClosed()){
    
    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
    String content=br1.readLine();
    out.println(content);
    out.flush();
    if(content.equals("exit")){
    socket.close();
    break;
    }
    
         }
    }catch(Exception e){
    
    }
    
    };
    new Thread(r2).start();
    }
    public static void main(String args[]){
    new Server();
    }
}
