package ie.gmit.sw;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Cory Odonoghue- The class Menu is used to display a menu to the users
 *         , take in their response and perform a function depending on the
 *         users input
 */
public class Menu {
	/**
	 * creating varibales
	 */
	private int option;
	private boolean leave = false;
	private Scanner sc = new Scanner(System.in);

	/**
	 * creating a new instance of Filter
	 */
	private Filter parser = new Filter();

	/**
	 * Menu function calls init , then starts the thread, 
	 * else there is an error
	 */
	public Menu() {
		try {
			init();
			parser.start();
		} catch (Exception e) {
			System.out.println("error");
		}
	}

	/**
	 * @throws NumberFormatException
	 * @throws IOException
	 * Initialise the menu
	 * displays 3 options for user to choose from,
	 * Reads in user input
	 *  and carries out another function based on the input
	 */
	public void init() throws NumberFormatException, IOException {
		/** 
		 * a do while loop that keep running until leave is set to true, 
		 * if option 1 is selected perform addImage function,
		 * if option 2 id=s selected perform add filter function
		 * if 3 is selected leave the program
		 */
		
		do {
			System.out.println("***************************************************");
			System.out.println("* GMIT - Dept. Computer Science & Applied Physics *");
			System.out.println("*                                                 *");
			System.out.println("*    A Multithreaded Image Filtering System       *");
			System.out.println("*                                                 *");
			System.out.println("***************************************************");
			System.out.println("Options:");
			System.out.println("1) Enter your image directory");
			System.out.println("2) Choose your custom filter");
			System.out.println("3) Quit");
			System.out.println("Choose Option:");
			option = sc.nextInt();
			switch(option) {
			case 1:
				parser.addImage();
				break;
			case 2:
				parser.chooseFilter();
				break;
			case 3:
				System.out.println("Leaving program");
				leave = true;
				break;
			}

		} while (leave != true);
	}
	
}
