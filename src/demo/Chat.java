package demo;

import java.util.Scanner;

public class Chat {
	static boolean live = true;
	
	public static void main(String[] args) {
		String[] menu = {"help", "myip", "myport", "connect", "list", "terminate", "send", "exit"};
		
		System.out.println("Chat ---- CS4470 ");
		System.out.println("type help for more info");
		Scanner input = new Scanner(System.in);

		while(live) {
			Client c = new Client();
			String in = input.next();
			
			if(in.equals(menu[0])) 
				c.help();
			else if(in.equals(menu[7])) {
				live = false;
			}
		}
		
		input.close();
		//c.exit();
	}
}
