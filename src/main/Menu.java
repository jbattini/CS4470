package main;

import java.util.Scanner;
import java.io.IOException;
import java.net.*;

public class Menu {

	public void start(int port){
		Scanner in = new Scanner(System.in);
		
		String input;
		do {
			display();
			input = in.nextLine();
			try {
				choice(input);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		} while(!input.equals("8"));
	}

	// DISPLAY MENU
	public void display(){
		
	}
	
	public void choice(String choice) throws UnknownHostException{
		switch (choice) {
        case "1":  help();
                 break;
        //case "2":  myip();
                 //break;
        case "3":  ;
                 break;
        case "4":  ;
                 break;
        case "5":  ;
                 break;
        case "6":  ;
                 break;
        case "7":  ;
                 break;
        case "8":  ;
                 break;
		}
	}
	
	// MENU ITEMS
	public void help(){
		System.out.println("Help: Here is information about the user interface options or command manual");
	}
	
	
	
	
}
