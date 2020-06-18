/**
  * User data of the current session
  * 
  * Last edit: 6/18/2020
  * @author 	Celeste, Eric
  * @version 	1.0
  * @since 		1.0
  */

public final class User {
	/**
	  * The user's selected gender
	  */
	public static char gender;

	/**
	  * The user's selected name
	  */
	public static String name;

	/**
	  * Whether the user has trained
	  */
	public static boolean hasTrained;

	/**
	  * Whether it is the first time that the user entered the main menu 
	  */
	public static boolean firstMainMenu;

	static {
		gender = 'M';
		name = "fellow";
		hasTrained = false;
		firstMainMenu = true;
	}

}
