package main;

import java.net.*;
import java.io.*;

public class Client extends Thread {
	public static Socket socket;
	public String destination;
	public int port;
	public InetAddress localAddress;
	public int localPort;
	private DataInputStream  in   = null;
	private DataOutputStream out = null;
	
	
	public Client(String destination, int port, int localPort) throws IOException {
		this.destination = destination;
		this.port = port;
		this.localAddress = InetAddress.getLocalHost();
		this.localPort = localPort;

		try {
	         System.out.println("Connecting to " + destination + " on port " + port);
	         socket = new Socket(destination, port, this.localAddress, localPort);
	         
	         System.out.println("Just connected to " + socket.getRemoteSocketAddress());
	         
	         OutputStream outToServer = socket.getOutputStream();
	         out = new DataOutputStream(outToServer);
	         out.writeUTF("Hello from " + socket.getLocalSocketAddress());
	         out.flush();
	         
	         InputStream inFromServer = socket.getInputStream();
	         in = new DataInputStream(inFromServer);
	         
	         System.out.println("Server says " + in.readUTF());
	         start();
	      } catch(IOException e) {
	         e.printStackTrace();
	      } 
		
		
		
	}
	
	
	public void run() {  
		while (true) {  
		   try {  
			   InputStream inFromServer = socket.getInputStream();
		       in = new DataInputStream(inFromServer);
		       System.out.println(in.readUTF());
	         }
	         catch(IOException ioe)
	         {  System.out.println("Listening error: " + ioe.getMessage());
	            close();
	         }
	      }
	}
	
	public void speakToServer(String[] args){
		// appends the String array (args) together to create one command string (cmd) to send to the server
		String cmd = "";
		for(int i = 0; i < args.length; i++){
			cmd += args[i] +" ";
		}
		try {
			// write command to the server
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(cmd);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void close(){
		try {
	        if (in != null)  		in.close();	
	        if (out    != null)  	out.close();
	        if (socket   != null)  	socket.close();
  	  	} catch (IOException e) {
  		  e.printStackTrace();
  	  	}
	}
	
	public int getClientPort(){
		return socket.getLocalPort();
	}


}