package main;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Server extends Thread {
	
   private static ServerSocket serverSocket;
   private static ArrayList<Socket> clients;
   private boolean listenForConnections;
   private boolean listenToClients;
   private static Thread clientListen;
   private DataInputStream  in   = null;
   private DataOutputStream out = null;
   private static Scanner inScan;
   
   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clients = new ArrayList<Socket>();
      this.listenForConnections = true;
      this.listenToClients = false;
      
   }

   public void run() {
      while(true) {
    	 if (listenForConnections){
				try {
					
					System.out.println("Waiting for client on host " + this.getMyIP() + " on port "
							+ serverSocket.getLocalPort() + "...");

					Socket clientSocket = serverSocket.accept();
					clients.add(clientSocket);

					System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
					in = new DataInputStream(clientSocket.getInputStream());

					System.out.println(in.readUTF());
					out = new DataOutputStream(clientSocket.getOutputStream());
					out.writeUTF("Thank you for connecting to " + clientSocket.getLocalSocketAddress());

					
				} catch (SocketTimeoutException s) {
					System.out.println("Socket timed out!");
					//break;
				} catch (SocketException se) {
					System.out.println("Server Socket Closed");
					//se.printStackTrace();
					//break;
				} catch (IOException e) {
					//e.printStackTrace();
					//break;
				} 
    	 }
    	 
    	 clientListen = new Thread() {
  		    public void run() {
  		      while(true) {
  		    	for(int i = 0; i < clients.size(); i++){
  					try {
  						// Checks if current client sent a command
  						if (clients.get(i).getInputStream().available() != 0){
  							DataInputStream in = new DataInputStream(clients.get(i).getInputStream());
  							String input = in.readUTF();
  							String[] args = input.split(" ");
  							if (args[0].equals("list")){
  						        sendClientsListToClient(i);
  							}
  							if (args[0].equals("send")){
  								int toID = Integer.parseInt(args[1]);
  								String msg = "";
  								
  								if (args.length > 2) {
  									for(int j = 2; j < args.length; j++){
  										if (j == args.length-1){
  	  										msg += args[j];
  										} else {
  	  										msg += args[j] + " ";
  										}
  									}
  								}
  								
  						        sendMsgToClient(i, toID, msg);
  							}
  						} 
  					} catch (SocketException se) {
  						System.out.println("Socket Closed");
  						//se.printStackTrace();
  					} catch (EOFException e) {
  						//e.printStackTrace();
  					} catch (IOException e) {
  						//e.printStackTrace();
  					}
  					
  					
  	    		 }
  		      }
  		    }
    	  };
    	  clientListen.start();
    	 
    	 if(listenToClients){
    	 }
      }
   }
   
   
   
   public InetAddress getMyIP() throws UnknownHostException{
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return address;
	}
   
   public static void getClients(){
	   System.out.println(clients.size()+" clients connected");
	   System.out.println("id: IP address			Port No.");
	   for(int i = 0; i < clients.size(); i++) {   
		    System.out.println(" "+i+"  "+clients.get(i).getInetAddress().getHostAddress()+"			"+clients.get(i).getPort());
		}  
   }
   
   private void sendClientsListToClient(int clientID){
	   	System.out.println("Sending client id:"+clientID+" the list of clients...");
		try {
			out = new DataOutputStream(clients.get(clientID).getOutputStream());
			out.writeUTF(clients.size()+" client(s) connected");
			out.writeUTF("id: IP address			Port No.");
			for(int i = 0; i < clients.size(); i++) {   
				out.writeUTF(" "+i+"  "+clients.get(i).getInetAddress().getHostAddress()+"			"+clients.get(i).getPort());
			}  
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
   }
   
   private void sendMsgToClient(int fromID, int toID, String msg){
	   	System.out.println("Sending to <"+toID+"> a message from <"+fromID+">");
		try {
			out = new DataOutputStream(clients.get(toID).getOutputStream());
			out.writeUTF("Message received from "+clients.get(fromID).getInetAddress().getHostAddress());
			out.writeUTF("Sender's Port: "+clients.get(fromID).getPort());
			out.writeUTF("Message: '"+msg+"'");
			out.flush();
		} catch (IOException e) {
			System.out.println("IO Exception has occured when sending a message!");
		}
		try {
			out = new DataOutputStream(clients.get(fromID).getOutputStream());
			out.writeUTF("Message sent to "+toID);
			out.flush();
		} catch (IOException e) {
			System.out.println("IO Exception has occured when sending a message!");
		}
		
  }
   
   public static void close() {
	   try {
			for(int i = 0; i < clients.size(); i++) {   
				clients.get(i).close();
			}
			serverSocket.close();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("IO Exception has occured when closing!");
		}
		
   }
 
   
   public static void command(String command){
		String[] args = command.split(" ");
		switch (args[0]) {
		case "close":  
			close();
			break;
		case "clients": 
			getClients();
			break;
		}
   }
   
   
   public static void main(String [] args) {
	      int port = Integer.parseInt(args[0]);
	      try {
	    	  
	    	  Thread t = new Server(port);
	    	  t.start();

	      }catch(IOException e) {
	         e.printStackTrace();
	      }
	      inScan = new Scanner(System.in);
	      String input;
	      do {
			input = inScan.nextLine();
			command(input);
	      } while(!input.equals("close"));
   }
   
}