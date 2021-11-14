
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gautam
 */
public class Client {
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    
    
    public Client(){
    try{
    socket=new Socket("127.0.0.1",7777);
   
    System.out.println("Connection done");
    br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out=new PrintWriter(socket.getOutputStream());
    
    startReading();
    startWriting();
        }
    catch(Exception e){
   System.out.println("Server not started");
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
        System.out.println("server terinated the chat");
       socket.close();
        break;
     }
    System.out.println("server :"+msg);
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
     System.out.println("connection closed");
    }
    
    };
    new Thread(r2).start();
    }
    public static void main(String args[]){
    new Client();
    }
}
