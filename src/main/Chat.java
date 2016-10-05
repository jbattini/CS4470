package main;

import java.util.Scanner;
import java.io.IOException;
import java.net.*;

public class Chat {
	
	//static Thread server;
	static Client client;
	static public int localPort;
	private static Scanner in;
	
	public static void main(String [] args) {
	      localPort = Integer.parseInt(args[0]);	      
	      in = new Scanner(System.in);
	      String input;
	      do {
			input = in.nextLine();
			command(input);
	      } while(!input.equals("8"));
	 }
	
	public static void command(String command){
		String[] args = command.split(" ");
		String cmd1 = args[0];
		String cmd2 = "";
		String cmd3 = "";
		if (args.length > 1) {
			cmd2 = args[1];
		}
		if (args.length > 2) {
			
			for(int i = 2; i < args.length; i++){
				cmd3 += args[i] + " ";
			}
		}
		
		
		
		switch (cmd1) {
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
        	String destination = cmd2;
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
        case "send": 
        	if (cmd3.length() > 100) {
				System.out.println("Your message is more than 100 characters!");
			} else {
//				String[] sendMsgArgs = new String[3];
//				sendMsgArgs[0] = cmd1;
//				sendMsgArgs[1] = cmd2;
//				sendMsgArgs[2] = cmd3;
				client.speakToServer(args);
			}
                 break;
        case "exit":  exit();
                 break;
		}
		
	}
	
	
	
	public static void help(){
		System.out.println();
		System.out.println("====================HELP======================");
		System.out.println("help:  Display information about the available user interface options or command manual.");
		System.out.println("myip:  Display the IP address of this process.");
		System.out.println("myport:  Display the port on which this process is listening for incoming connections.");
		System.out.println("connect <destination> <port no>:  This command establishes a new TCP connection to the specified <destination> at the specified < port no>. The <destination> is the IP address of the computer.");
		System.out.println("list:  Display a numbered list of all the connections this process is part of.");
		System.out.println("terminate <connection id>:  This command will terminate the connection listed under the specified number when LIST is used to display all connections.");
		System.out.println("send <connection id> <message>:  This will send the message to the host on the connection that is designated by the number 3 when command “list” is used. ");
		System.out.println("exit:  Close all connections and terminate this process. The other peers should also update their connection list by removing the peer that exits.");
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
		if (client   != null)  	client.close();
		System.exit(0);
	}
	
}

