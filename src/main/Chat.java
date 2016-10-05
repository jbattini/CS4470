package main;

import java.util.Scanner;
import java.io.IOException;
import java.net.*;

public class Chat {
	
	//static Thread server;
	static Client client;
	static public int localPort;
	
	public static void main(String [] args) {
	      localPort = Integer.parseInt(args[0]);	      
	      
	      Scanner in = new Scanner(System.in);
			
	      String input;
	      do {
			input = in.nextLine();
			command(input);
	      } while(!input.equals("8"));
	      
	      
	 }
	
	public static void command(String command){
		String[] args = command.split(" ");
		switch (args[0]) {
        case "help":  help();
                 break;
                 
        case "myip":  try {
        		myip();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
                 break;
        case "myport":  try {
				myport();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
                 break;
        case "connect": 
        	String destination = args[1];
        	int port = Integer.parseInt(args[2]);
        	try {
				client = new Client(destination, port, localPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
            break;
        case "list": client.speakToServer(args);
                 break;
        case "terminate": 
                 break;
        case "send": client.speakToServer(args);
                 break;
        case "exit":  exit();
                 break;
		}
		
	}
	
	
	
	public static void help(){
		System.out.println();
		System.out.println("====================Menu======================");
		System.out.println("1) help");
		System.out.println("2) myip");
		System.out.println("3) myport");
		System.out.println("4) connect <destination> <port no>");
		System.out.println("5) list");
		System.out.println("6) terminate <connection id>");
		System.out.println("7) send <connection id> <message>");
		System.out.println("8) exit");
		System.out.println("==============================================");
		System.out.println();
	}
	
	public static void myip() throws UnknownHostException{
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		System.out.println("My IP Address: " + address);
	}
	
	public static void myport() throws UnknownHostException{
		System.out.println("My Port: "+client.getClientPort());
	}
	
	public static void exit(){
		client.close();
		System.exit(0);
	}
	
}

