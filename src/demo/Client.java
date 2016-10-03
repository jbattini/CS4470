package demo;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
//"help", "myip", "myport", "connect", "list", "terminate", "send", "exit"
	static boolean live = true;
	
	public static void main(String[] args) throws UnknownHostException {
		String[] menu = {"help", "myip", "myport", "connect", "list", "terminate", "send", "exit"};
		
		System.out.println("Chat ---- CS4470 ");
		System.out.println("type help for more info");
		Scanner input = new Scanner(System.in);

		while(live) {
			Client c = new Client();
			String in = input.next();
			
			if(in.equals(menu[0])) 
				c.help();
			else if(in.equals(menu[1])) 
				System.out.println("my IP > " + c.myIP());
			else if(in.equals(menu[7])) 
				live = false;
		
		}
		
		input.close();
	}
		//c.exit();
	public void help() {
		System.out.println("myip - displays the IP address of this process.");
		System.out.println("myport - display the port on which this process is listening for incoming connections");
		System.out.println("connect <destination> <port no> - establishes a new TCP connection to the destination at the specified port number");
		System.out.println("list - displays a numbered list of all the connections this process is a part of");
		System.out.println("terminate <connection ID> - terminates the connection listed under the specified number when LIST is used to display all connections");
		System.out.println("send <connection ID> <message> - sends the message to the host on the connection that is designated by the connection ID");
		System.out.println("exit - close all connections and terminates this process");
		
	}//help
	
	public String myIP() throws UnknownHostException {
		InetAddress IP = InetAddress.getLocalHost();
		return IP.toString();
		
	}
}
