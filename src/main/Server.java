package main;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Server extends Thread {
	
   private static ServerSocket serverSocket;
   private static ArrayList<Socket> clients;
   
   
   public Server(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      clients = new ArrayList<Socket>();
      //serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on host "+ this.getMyIP() +" on port " + 
               serverSocket.getLocalPort() + "...");
            
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
                 
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF("Thank you for connecting to " + clientSocket.getLocalSocketAddress());
            
         }catch(SocketTimeoutException s) {
        	 System.out.println("Socket timed out!");
        	 break;
         }catch(SocketException se) {
        	 System.out.println("Server Socket Closed");
        	 
             se.printStackTrace();
             break;
         }catch(IOException e) {
        	 e.printStackTrace();
        	 break;
         } 
      }
   }
   
   public void close() {
	   try {
		   serverSocket.close();
	   } catch (IOException e) {
		   e.printStackTrace();
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
 
   
   public static void command(String command){
		String[] args = command.split(" ");
		switch (args[0]) {
		case "close":  
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
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
	      Scanner in = new Scanner(System.in);
	      String input;
	      do {
	    	System.out.print(">>");
			input = in.nextLine();
			command(input);
	      } while(!input.equals("close"));
   }
   
}