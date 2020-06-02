//Rachel Leong
//ISP - Jeopardy
//January 14, 2020
//Ms. Krasteva

/*
    Jeopardy is a two-player game that involves answering questions in the form of a question and tests the user's knowledge of random fact. 5 categories will
    be randomly selected, with each category containing 5 increasingly difficult questions. Level 1 will score in increments of $100 and the Daily Double will
    be randomly hidden in 1 location. Level 2 will score in increments of $200 and 2 Daily Double options will be randomly hidden. The final question will be
    randomly selected and only players with a non-negative score will be able to answer it. The game will detect the winning and losing player of each round.
    The game will automatically reset if the users play again. The program begins with a threaded splash screen animation, which brings the user to the main menu,
    where the user can decide whether to go to intructions, level selection, high scores, or exit the program. Instructions contains a thorough description of
    program rules. Level selection allows the user to choose between the 2 levels. Then, each player gets prompted to enter his/her name. High scores displays the
    top ten players by their name and score for both levels and allows the user to clear the file. Each time, after completing a game, the user gets directed to
    a display screen, where the user can see the win/loss result, along with the score. Prior to exiting the program, the user will see a goodbye message.
*/

/*  Instance Variable Dictionary:
    Name            Type                Description
    --------------------------------------------------------------------------------------------------------------------------------
    c               Console             the output console
    choice          String              stores the user's choice of menu selection
    levelChoice     String              stores the user's choice of level selection
    userData        String[][]          store's the players names and scores
    fileName        String              stores the name of the current file to be read/written to
    TOTAL           final int           defines the length of the arrays of length 11, used throughout the program
    used            boolean[]           keeps track of which categories have been chosen for gameplay to ensure there are no repeats
    exit            boolean             stores user's choice of exiting the game
*/

import java.io.*; //gives access to java input/output classes
import java.awt.*; //gives access to java command libraries (colours)
import hsa.Console; //gives access to Console class file
import javax.swing.JOptionPane; //gives access to java class for creating a dialog window

public class Jeopardy //Jeopardy class
{
    //instance variables
    Console c; //output console
    String choice = "";
    String levelChoice = "";
    String[] [] userData = new String [2] [2];
    String fileName = "HighScores";
    final int TOTAL = 11;
    boolean[] used = {false, false, false, false, false, false, false, false, false, false, true};
    boolean exit = false;

    /*  Purpose: Jeopardy class constructor
	Segment Blocks: the try catch statement catches any errors that may occur while importing the fonts
    */
    public Jeopardy ()
    {
	c = new Console (30, 100, "Jeopardy"); //creates an output console that's 800 by 600 pixels and has a "Jeopardy" title
	try
	{ //try catch block catches errors
	    //imports fonts
	    //citation: http://www.java2s.com/Tutorials/Java/Graphics_How_to/Font/Load_font_from_ttf_file.htm
	    Font.createFont (Font.TRUETYPE_FONT, getClass ().getResourceAsStream ("/fonts/impact.ttf")); //imports font from data folder
	    Font.createFont (Font.TRUETYPE_FONT, getClass ().getResourceAsStream ("/fonts/iskpota.ttf")); //imports font from data folder
	}
	catch (Exception e)
	{
	}
    }


    /*  Purpose: private title method outputs the title and clears the screen
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	darkBlue        Color               stores the RGB value for the dark blue colour used for the background
    */
    private void title ()  //calls title method
    {
	Color darkBlue = new Color (18, 11, 71); //dark blue
	c.clear (); //clears the screen
	c.setColor (darkBlue); //sets colour to dark blue
	c.fillRect (10, 0, 800, 600); //fills in the background with colour
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets the font to Impact, size 20
	c.drawString ("Jeopardy!", 370, 30); //prints the title
    }


    //  Purpose: private pauseProgram method temporarily pauses the program until the user to enter any key to acknowledge that information has been read
    private void pauseProgram ()
    {
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 15)); //sets font to Iskoola Pota, size 15
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Enter any character to continue...", 20, 590);
	c.getChar (); //gets character input
    }


    /*  Purpose: public splashScreen method contains a thread class of animations that executes before the user can access mainMenu
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	s               splashScreen        used to execute the splashScreen thread
    */
    public void splashScreen () 
    {
	//creates the thread
	SplashScreen s = new SplashScreen (c);
	//starts the thread
	s.run ();
    }


    /*  Purpose: public mainMenu class presents the players with 4 options: instructions, level selection, high scores, and exit. It is
		 also where players are brought after exiting a game. It throws InterruptedException for the thread.
	Loops: while loop continues looping until the user enters a valid input.
	Conditionals: if statement checks if the menu choice is valid
	Segment Blocks: try catch block displays an error message if the user enters anything other than 'a', 'b', 'c', or 'd'.
    */
    public void mainMenu () throws InterruptedException
    {
	exit = false; //sets exit to false
	title (); //calls title method
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 40)); //sets font to Iskoola Pota, size 40
	c.drawString ("Main Menu", 315, 130);
	c.setFont (new Font ("Impact", Font.PLAIN, 30)); //sets font to Impact, size 30
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (310, 160, 195, 40, 10, 10); //instructions button
	c.fillRoundRect (290, 220, 230, 40, 10, 10); //level selection button
	c.fillRoundRect (308, 280, 195, 40, 10, 10); //high scores button
	c.fillRoundRect (360, 340, 90, 40, 10, 10); //exit button
	c.setColor (Color.ORANGE); //sets colour to orange
	c.drawString ("a) Instructions", 320, 190);
	c.drawString ("b) Level Selection", 300, 250);
	c.drawString ("c) High Scores", 318, 310);
	c.drawString ("d) Exit", 370, 370);
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20)); //sets font to Iskoola Pota, size 20
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Please enter your choice:", 295, 455); //prompts user to enter their choice
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (498, 436, 25, 29, 10, 10); //textbox

	while (true) //loops to get user input; keeps looping until break; is reached
	{
	    try //catches errors
	    {
		choice = c.getChar () + ""; //gets the character input and converts it into a string
		c.setColor (Color.ORANGE); //sets colour to orange
		c.drawString (choice, 505, 455); //displays input
		if (!choice.equalsIgnoreCase ("a") && !choice.equalsIgnoreCase ("b") && !choice.equalsIgnoreCase ("c") && !choice.equalsIgnoreCase ("d")) //checks if choice is valid
		    throw new IllegalArgumentException (); //throws new error
		Thread.sleep (1000); //delay to display input
		break; //exit loop
	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter either 'a', 'b', 'c', or 'd'!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (498, 436, 25, 29, 10, 10); //erases bad input
	    }
	}
    }


    //  Purpose: public instructions method contains a thorough description of program/rules and returns to the main menu.
    public void instructions ()
    {
	title (); //calls title method
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30)); //sets font to Iskoola Pota, size 30
	c.drawString ("Instructions", 345, 80);
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 17)); //sets font to Iskoola Pota, size 17
	//instructions
	c.drawString ("Welcome to this 2-player game of", 20, 125);
	c.drawString ("You will be competing against a friend to answer 25 questions. In level 1, the questions will be in increments of", 20, 155);
	c.drawString ("$100 and in level 2, they will be in increments of $200. A player will be randomly chosen to choose the first", 20, 175);
	c.drawString ("question, and after the clue is displayed, each player will be able to buzz to respond. The player who got the", 20, 195);
	c.drawString ("most recent question correct will be asked to choose the next question. If the remaining players do not know the", 20, 215);
	c.drawString ("answer, they buzz with '?'. Any wrong answers will result in deductions, but correct answers will add to your", 20, 235);
	c.drawString ("score. Don't forget to answer with a question beginning with \"what is\"/\"what are\"/\"who is\"/\"who are\" depending on ", 20, 255);
	c.drawString ("the context, or your answer will be marked as incorrect. To return to the main menu during the game, enter \"<\".", 20, 275);
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (15, 310, 100, 20, 10, 10);
	c.fillRoundRect (15, 430, 108, 20, 10, 10);
	c.fillRoundRect (258, 110, 73, 20, 10, 10);
	c.setColor (Color.ORANGE); //sets colour to orange
	c.drawString ("Daily Double", 20, 325);
	c.drawString ("Final Jeopardy", 20, 445);
	c.drawString ("Jeopardy!", 263, 125);
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("There will be either 1 or 2 randomly placed daily doubles, depending on the selected level. The player who lands", 20, 355);
	c.drawString ("on the daily double will be able to wager any amount from $5 to the greater of their current score or the highest", 20, 375);
	c.drawString ("dollar amount on the board.", 20, 395);
	c.drawString ("At the end of the round, players with a non-negative score will be able to answer a final question. They will wager", 20, 475);
	c.drawString ("any amount of money from $0 to the greater of their current score or the highest dollar amount on the board.", 20, 495);
	c.drawString ("For both, a failure to respond or an incorrect answer will result in a deduction of the wager, and a correct answer", 20, 525);
	c.drawString ("will result in the addition of the wager to your score.", 20, 545);

	pauseProgram (); //calls pauseProgram method
    }


    /*  Purpose: public levelSelection method asks the players which level they'd like to play and proceeds into gameplay.
	Loops: while loop gets the levelChoice and executes until a valid input is obtained
	Conditionals: if statement checks if the input is valid
	Segment Blocks: try catch block displays an error message if invalid input in entered
    */
    public void levelSelection () throws InterruptedException
    {
	title (); //calls title method
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (362, 160, 90, 25, 10, 10); //level 1 button
	c.fillRoundRect (362, 200, 90, 25, 10, 10); //level 2 button
	c.setColor (Color.ORANGE); //sets colour to orange
	c.drawString ("a) Level 1", 370, 180);
	c.drawString ("b) Level 2", 370, 220);
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30));  //sets font to Iskoola Pota, size 30
	c.drawString ("Level Selection", 320, 120);
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20));  //sets font to Iskoola Pota, size 20
	c.drawString ("Please enter your choice:", 295, 325); //prompts user for levelChoice
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (498, 306, 25, 29, 10, 10); //textbox

	while (true) //gets user input; executes until break; statement is reached
	{
	    try //catches errors
	    {
		levelChoice = c.getChar () + ""; //gets user input and converts it into a string
		c.setColor (Color.ORANGE); //sets colour to orange
		c.drawString (levelChoice, 505, 325); //displays input
		if (!levelChoice.equalsIgnoreCase ("a") && !levelChoice.equalsIgnoreCase ("b")) //checks for valid input
		    throw new IllegalArgumentException (); //throws new exception
		Thread.sleep (1000); //delays to display input
		break; //exit loop
	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter either 'a' or 'b'!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (498, 306, 25, 29, 10, 10); //erases bad input
	    }
	}
    }


    /*  Purpose: private askData method gets the names of both players and notifies them of their assigned buzzers
	Loops: both while loops are used to get each character of the players' names and execute until the player enters the 'enter' key
	Conditionals:
	    - checks if the name length exceeds 13 characters (in both while loops)
	    - checks what the user entered and follows the appropriate course of action.
		- If they entered the 'enter' key, the loop is broken out of.
		- If they entered the 'backspace' key, it erases bad input and deletes that character from the string.
		- Otherwise, the character that they entered is added to the string and displayed on the screen
	    - checks if the name length is 0 and throws an exception if so
	Segment Blocks: try catch blocks check that the length of the name is between 1 and 13 inclusive
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	input           char                stores the user's input by character
    */
    private void askData ()
    {
	title (); //calls title method

	//local variable
	char input = ' ';

	//initializes userData array
	userData [0] [0] = "";
	userData [1] [0] = "";
	userData [0] [1] = 0 + "";
	userData [1] [1] = 0 + "";

	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30)); //sets font to Iskoola Pota, size 30
	c.drawString ("User Data", 350, 130);
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20)); //sets font to Iskoola Pota, size 20
	c.drawString ("Enter player 1's name:", 195, 200); //prompts player 1 to enter their name
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (375, 182, 270, 26, 10, 10); //textbox for player 1's name

	while (true) //executes until break; statement is reached
	{
	    try //catches errors
	    {
		input = c.getChar (); //gets the character
		if (userData [0] [0].length () > 13) //checks if the length exceeds 13 characters
		    throw new IllegalArgumentException (); //throws an exception
		if (input == '\n') //checks if they entered the 'enter' key
		{
		    if (userData [0] [0].length () == 0) //checks if the length is 0
			throw new IllegalArgumentException (); //throws an exception
		    break; //exit loop
		}
		else if (input == 8) //checks if the user entered the 'backspace' key
		{
		    if (userData [0] [0].length () == 0) //checks if the length is 0
			throw new IllegalArgumentException (); //throws an exception
		    else
		    {
			userData [0] [0] = userData [0] [0].substring (0, userData [0] [0].length () - 1); //deletes that character from name
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (375, 182, 270, 26, 10, 10); //erases bad input
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (userData [0] [0], 385, 200); //displays the name
		    }
		}
		else
		{
		    userData [0] [0] += input + ""; //adds character to name
		    c.setColor (Color.ORANGE); //sets colour to orange
		    c.drawString (userData [0] [0], 385, 200); //displays name
		}
	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "The name length must not be less than 0 or exceed 13 characters!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (375, 182, 270, 26, 10, 10); //player 1 name textbox
		userData [0] [0] = ""; //clears the name
	    }
	}

	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Enter player 2's name:", 195, 240); //prompts player 2 to enter their name
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (375, 222, 270, 26, 10, 10); //textbox for player 2's name

	while (true) //executes until break; statement is reached
	{
	    try //catches errors
	    {
		input = c.getChar (); //gets the character
		if (userData [1] [0].length () > 13) //checks if the length exceeds 13 characters
		    throw new IllegalArgumentException (); //throws an exception
		if (input == '\n') //checks if they entered the 'enter' key
		{
		    if (userData [1] [0].length () == 0) //checks if the length is 0
			throw new IllegalArgumentException (); //throws an exception
		    break; //exit loop
		}
		else if (input == 8) //checks if the user entered the 'backspace' key
		{
		    if (userData [1] [0].length () == 0) //checks if the length is 0
			throw new IllegalArgumentException (); //throws an exception
		    else
		    {
			userData [1] [0] = userData [1] [0].substring (0, userData [1] [0].length () - 1); //deletes that character
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (375, 222, 270, 26, 10, 10); //erase bad input
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (userData [1] [0], 385, 240); //displays the name
		    }
		}
		else
		{
		    userData [1] [0] += input + ""; //adds the character to the name
		    c.setColor (Color.ORANGE); //sets colour to orange
		    c.drawString (userData [1] [0], 385, 240); //displays the name
		}
	    }
	    catch (IllegalArgumentException e)
	    {
		JOptionPane.showMessageDialog (null, "The name length must not be less than 0 or exceed 13 characters!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (375, 222, 270, 26, 10, 10); //erase bad input
		userData [1] [0] = ""; //clears the name
	    }
	}
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString (userData [0] [0] + ", your buzzer will be the character 'A'!", 200, 320);
	c.drawString (userData [1] [0] + ", your buzzer will be the character 'L'!", 200, 350);
	pauseProgram (); //calles pauseProgram method
    }


    /*  Purpose: private saveData method writes the player names and scores into the appropriate file.
	Loops:
	    - loops through the 1st and 2nd set of indices of the userData array to access every element; starts at 0 and ends at 1
	    - loops through 1 and 2 to access both high scores files
	Segment Blocks: try catch block catches any errors that may occur while writing to a file
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	level           int                 method parameter that stores the value of the level that was played
	output          PrintWriter         used to write the player names and scores to the file
	i               int                 loop variable that loops through the 1st set of indices of the userData array
	x               int                 loop variable that loops through the 2nd set of indices of the userData array
	y               int                 loop variable that loops through 1 and 2 to access both high scores files
    */
    private void saveData (int level)
    {
	PrintWriter output;
	try //catches errors
	{
	    for (int y = 1 ; y < 3 ; y++) //loops through both high scores files
	    {
		// if (!new File ("highScores/HighScores" + y + ".txt").exists ()) //checks if it doesn't exist
		// {
		//     output = new PrintWriter (new FileWriter ("highScores/HighScores" + y + ".txt")); //resets text file
		//     output.println ("");
		//     output.close (); //closes the file
		// }
	    }

	    output = new PrintWriter (new FileWriter ("highScores/" + fileName + level + "" + ".txt", true)); //opens the correct file and appends to it
	    for (int i = 0 ; i < 2 ; i++) //loops through 1st set of indices
	    {
		for (int x = 0 ; x < 2 ; x++) //loops through 2nd set of indices
		{
		    output.println (userData [i] [x]); //writes to the file
		}
	    }
	    output.close (); //closes the file
	}
	catch (IOException e)
	{
	}
    }


    /*  Purpose: private return chooseCategory method randomly chooses a category that hasn't been picked yet and randomly selects 5 questions of increasing difficulty
		 and returns them in a String array.
	Loops:
	    - while loop randomly chooses a category until the category hasn't been chosen before
	    - for loop
	Segment Blocks: try catch block catches any errors that may occur while reading from a file
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	level           int                 method parameter that stores the current level that's being played
	categories      String[][]          stores the names of all categories from both level 1 and 2
	chosen          String[]            stores the chosen questions and category name; returned to the method
	cetegory        int                 stores the randomly selected category
	choice          int                 stores the index of the randomly selected question
	input           BufferedReader      allows me to read from a text file
	i               int                 loop variable that loops from 0 to 4 so that 5 questions can be selected
	y               int                 loop variable that loops from 1 to choice*2 to skip through all of the questions and answers before the desired question
    */
    private String[] chooseCategory (int level)
    {
	//local variables
	String[] [] categories = {{"Golden Globes", "Uncommon", "Colours", "Fashion", "Word Origins", "Sports", "Astrology", "Metaphors", "Mythology", "Adjectives"},
		{"Kids' Books", "Pop Quiz", "Philosophy", "Disney", "Rhyme Time", "Movies", "Communication", "Let's Eat", "Human Body", "Tech Stuff"}};
	String[] chosen = new String [TOTAL];
	int category = 10;
	int choice = 0;
	BufferedReader input;

	while (used [category] == true) //loops until the category hasn't been used
	{
	    category = (int) (10 * Math.random ()); //randomly selects a category within a range of 10
	}
	used [category] = true; //sets the boolean value of the category at its index to true so it can't be used again in this round

	for (int i = 0 ; i < 5 ; i++) //loops 5 times
	{
	    choice = ((int) (3 * Math.random ())) + (i * 3); //randomly selects a value within a range of 3
	    try //catches errors
	    {
		input = new BufferedReader (new FileReader ("level" + level + "Questions/" + categories [level - 1] [category] + ".txt")); //reads from the correct file
		for (int y = 0 ; y < choice * 2 ; y++) //loops until the question is reached in the file
		    input.readLine (); //reads the line
		chosen [i * 2] = input.readLine (); //moves the question into the chosen array
		chosen [i * 2 + 1] = input.readLine (); //moves its answer into the chosen array
	    }
	    catch (IOException e)
	    {
	    }
	}
	chosen [10] = categories [level - 1] [category]; //puts in the category name
	return chosen; //returns the array
    }


    /*  Purpose: private displayBoard method prints the indices and creates the Jeopardy gameplay board.
	Loops:
	    - for loop is used to display both the number and letter indices; starts at 195 and ends at 695, going up by 100, which is its x and y values
	    - for loop is used to get the x-coordinates to display the gameplay board
	    - for loop is used to get the y-coordinates to display the gameplay board
	Conditionals: if statement is used to check if it's currently at the 1st row of boxes; if so, display the category name instead
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	board           String[][]          method parameter that stores the category names, questions and answers in use
	amount          int                 method parameter that stores the amount of money by which the money amounts increase by ($100 or $200)
	i               int                 loop variable that starts at 195 and ends at 695, increasing by 100, used for the x and y-coordinates of the indices
					    and calculating the number/letter to print
	x               int                 loop variable that starts at 150 and ends at 650, increasing by 100, used for the x-coordinates of the game board boxes
	y               int                 loop variable that starts at 85 and ends at 335, increasing by 50, used for the y-coordinates of the game board boxes
    */
    private void displayBoard (String[] [] board, int amount)
    {
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20));  //sets font to Iskoola Pota, size 20

	for (int i = 195 ; i < 695 ; i += 100)
	{
	    c.drawString ((i - 70) / 100 + "", i, 80); //displays the number index
	    c.drawString (Character.toString ((char) ((int) ((i - 195) / 100 + 65))), 130, 165 + (50 * (i - 195) / 100)); //displays the letter index
	}

	for (int x = 150 ; x < 650 ; x += 100) //used for x-coordinates
	{
	    for (int y = 85 ; y <= 335 ; y += 50) //used for y-coordinates
	    {
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (x, y, 98, 48, 10, 10);
		if (y == 85)
		{
		    c.setColor (Color.WHITE); //sets colour to white
		    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 13));  //sets font to Iskoola Pota, size 13
		    c.drawString (board [(x - 150) / 100] [10], x + 8, y + 30); //displays category name
		}
		else
		{
		    c.setColor (Color.ORANGE); //sets colour to orange
		    c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
		    c.drawString ("$" + (amount + (y / 50 - 2) * amount) + "", x + 27, y + 32); //displays money amount
		}
	    }
	}
    }


    /*  Purpose: private gameplay method that contains all of the processing for the main part of the Jeopardy game. It executes the dailyDouble method when necessary
		 and also involves choosing an index, buzzing, and finally answering the chosen question. The player's scores are updated in read time, so they can
		 see how well they are doing. After all questions have been asked, final jeopardy is played if at least 1 player has a non-negative score.
	Loops:
	    - while loop runs 25 times because that's how many questions there are to choose from
	    - while loop for errortrapping and ensuring that only valid input is taken in (i.e. no repeating choices)
	    - while loop runs at most twice when getting buzzes and answers
	    - while loop gets each character of the player's answer and loops until they press the 'enter' key
	Conditionals:
	    - if statement checks what character the user entered and follows the appropriate course of action.
		- If they entered the 'enter' key, the loop is broken out of.
		- If they didn't enter one of the valid index options, an error is thrown.
		- If they entered the 'backspace' key, it erases bad input and deletes that character from the string.
		- If they entered '<', it confirms with them if they want to leave and if they say yes, they are taken back to the main menu.
		- Otherwise, the character is added to the string.
	    - if statement checks if the y choice is greater than 0 and less than 6
	    - if statement that checks if that box has already been chosen
	    - if statement checks if the chosen box contains the randomly selected daily double 1 or 2, and if so, runs the dailyDouble method
	    - if statement checks if exit is true, if the player chose to exit and confirmed
	    - if statement checks if the user confirmed that they would like to exit the game and if so, they are take back to the main menu.
	    - if statement checks which character was used to buzz and the different possible combinations
	    - if statement checks if the player didn't enter '?' for help, and continues with getting the answer if so
	    - if statement checks if the length of answer is 0 and throws an error if so
	    - if statement checks if the length of the answer is less than 44 and only adds the typed character to the answer if so
	    - if statement checks if last character of the answer that isn't a space is '?' and deletes it if so
	    - if statement checks if the answer is correct (no leading or trailing spaces, any capitalization) and adds to their score if so, if not, subtract
	    - if statement checks if it was just player 1's turn and changes turns accordingly
	    - if statement checks is both players have already guessed but both got it wrong
	Segment Blocks: try catch blocks display an error message if the input isn't valid
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	gameBoard       String[][]          method parameter that stores all of the previously selected categories, questions, and answers
	dailyDouble1    String              method parameter that stores the index of the 1st randomly hidden daily double
	dailyDouble2    String              method parameter that stores the index of the 2nd randomly hidden daily double
	maxWager        int                 method parameter that stores the maximum amount of money on the board
	xChoice         String              stores the player's letter index of choice
	yChoice         int                 stores the player's number index of choice
	turn            int                 keeps track of whose turn it is (0 = player 1, 1 = player 2)
	answer          String              stores of the player's answer to the question
	first           char                stores the character pressed to buzz
	board           String[][]          stores all of the previously selected categories, questions, and answers
	count           int                 while loop variable that keeps track of how many questions have been asked
	help            boolean             keeps track of whether the player entered '?' for help
	input           char                stores the user's input by character
	darkBlue        Color               stores the RGB for darkBlue that's used for the background
	counter         int                 while loop variable that keeps track of how many times players have buzzed for the same question
    */
    private void gameplay (String[] [] gameBoard, String dailyDouble1, String dailyDouble2, int maxWager)
    {
	//local variables
	String xChoice;
	int yChoice;
	int turn = (int) (Math.round (Math.random ()));
	String answer = "";
	char first = ' ';
	String board[] [] = gameBoard;
	int count = 0;
	boolean help = false;
	char input = ' ';
	Color darkBlue = new Color (18, 11, 71); //dark blue

	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 10));  //sets font to Iskoola Pota, size 10
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (20, 80, 120, 20, 10, 10); //player 1 box
	c.fillRoundRect (20, 103, 120, 30, 10, 10);
	c.fillRoundRect (660, 80, 120, 20, 10, 10); //player 2 box
	c.fillRoundRect (660, 103, 120, 30, 10, 10);
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 13));  //sets font to Iskoola Pota, size 13
	c.drawString ("Player 1", 23, 77);
	c.drawString ("Player 2", 663, 77);
	c.drawString (userData [0] [0], 23, 96);
	c.drawString (userData [1] [0], 663, 96);
	c.setColor (Color.ORANGE); //sets colour to orange
	c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
	c.drawString ("$" + userData [0] [1], 23, 128);
	c.drawString ("$" + userData [1] [1], 663, 128);

	while (count < 25) //loops 25 times
	{
	    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size 14
	    c.setColor (Color.WHITE); //sets colour to white
	    c.drawString ("Enter '<' to exit.", 700, 590); //lets the players know they can exit
	    while (true) //checks if that box has already been chosen
	    {
		while (true) //gets each character of input
		{
		    try //catches errors
		    {
			c.setColor (Color.WHITE); //sets colour to white
			c.drawString ("Player " + (turn + 1) + ", please select a box. Letter: ", 20, 440);
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (230, 424, 20, 22, 10, 10); //textbox
			xChoice = c.getChar () + ""; //gets the character and converts it into a string
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (xChoice, 235, 440); //displays choice
			if (xChoice.equals ("<")) //checks if they entered '<'
			{
			    int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
			    if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
			    {
				exit = true;
				return; //exits the method
			    }
			    else
				continue; //continues
			}
			else if (!xChoice.equalsIgnoreCase ("a") && !xChoice.equalsIgnoreCase ("b") && !xChoice.equalsIgnoreCase ("c") && !xChoice.equalsIgnoreCase ("d") && !xChoice.equalsIgnoreCase ("e")) //checks if they didn't choose a valid letter
			    throw new IllegalArgumentException (); //throws new exception
			xChoice = xChoice.toUpperCase (); //turns it to uppercase
			break; //exits loop
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a valid letter!", "Error", JOptionPane.ERROR_MESSAGE); //error message
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (230, 424, 20, 22, 10, 10); //erases bad input
		    }
		}

		while (true) //loops to get valid y choice
		{
		    try //catches errors
		    {
			c.setColor (Color.WHITE); //sets colour to white
			c.drawString ("Number: ", 265, 440);
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (322, 424, 20, 22, 10, 10); //textbox
			input = c.getChar (); //gets the character
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (input + "", 327, 440);  //displays choice
			if (input == '<') //checks if they entered '<'
			{
			    int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
			    if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
			    {
				exit = true;
				return; //exits the method
			    }
			    else
				continue; //continues
			}
			yChoice = Integer.parseInt (input + ""); //parses the input into an integer
			if (yChoice < 1 || yChoice > 5) //checks if the y choice is valid
			    throw new IllegalArgumentException (); //throws new exception
			break; //exits the loop
		    }
		    catch (NumberFormatException i)
		    {
			JOptionPane.showMessageDialog (null, "Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE); //error message
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (322, 424, 20, 22, 10, 10); //erases bad input
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE); //error message
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (322, 424, 20, 22, 10, 10); //erases bad input
		    }
		}

		if (board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2] != "") //checks if the question has already been asked
		    break; //exits loop
		else
		{
		    JOptionPane.showMessageDialog (null, "Please enter a box that hasn't been chosen!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		    c.setColor (Color.BLUE); //sets colour to blue
		    c.fillRoundRect (322, 424, 20, 22, 10, 10); //erases bad input
		}
	    }

	    c.setColor (Color.BLUE); //sets colour to blue
	    c.fillRoundRect (150 + 100 * (yChoice - 1), (((int) (xChoice.charAt (0)) - 65) * 50 + 135), 98, 48, 10, 10); //covers the box to indicate it's been chosen

	    if (xChoice.charAt (0) == dailyDouble1.charAt (0) && yChoice == Integer.parseInt (dailyDouble1.substring (1, 2))) //checks if it's daily double 1
	    {
		dailyDouble (xChoice, yChoice, board, turn, maxWager); //executes dailyDouble method
		if (exit) //checks if exit is true
		    return; //exits the method
	    }
	    else if (xChoice.charAt (0) == dailyDouble2.charAt (0) && yChoice == Integer.parseInt (dailyDouble2.substring (1, 2))) //checks if it's daily double 2
	    {
		dailyDouble (xChoice, yChoice, board, turn, maxWager); //executes dailyDouble method
		if (exit) //checks if exit is true
		    return; //exits the method
	    }
	    else
	    {
		c.setColor (Color.WHITE); //sets colour to white
		c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14)); //sets font to Iskoola Pota, size 14
		c.drawString ("Question: " + board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2], 20, 460); //displays the question
		int counter = 0; //local variable for the while loop
		while (counter != 2) //loops no more than twice
		{
		    while (true) //loops to get input
		    {
			try //catches errors
			{
			    first = c.getChar (); //gets the character
			    if (first == '<') //checks if they entered '<'
			    {
				int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
				if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
				{
				    exit = true;
				    return; //exits the method
				}
				else
				    continue; //continues
			    }
			    else if ((first == 'a' || first == 'A') && counter == 1 && turn == 1) //checks if they entered 'a' and it's the 2nd turn but player 1 already went
				throw new Exception (); //throws new exception
			    else if ((first == 'l' || first == 'L') && counter == 1 && turn == 0) //checks if they entered 'l' and it's the 2nd turn but player 2 already went
				throw new Exception (); //throws new exception
			    else if ((first == 'a' || first == 'A')) //checks if they entered 'a'
			    {
				turn = 0;
				help = false;
				break; //exits loop
			    }
			    else if ((first == 'l' || first == 'L')) //checks if they entered 'l'
			    {
				turn = 1;
				help = false;
				break; //exits loop
			    }
			    else if (first == '?') //checks if they entered '?'
			    {
				help = true;
				break; //exits loop
			    }
			    else
				throw new IllegalArgumentException (); //throws new exception
			}
			catch (IllegalArgumentException i)
			{
			    JOptionPane.showMessageDialog (null, "Please buzz with a valid character: either 'A' or 'L'!", "Error", JOptionPane.ERROR_MESSAGE); //error message
			}
			catch (Exception e)
			{
			    JOptionPane.showMessageDialog (null, "You've already buzzed!", "Error", JOptionPane.ERROR_MESSAGE); //error message
			}
		    }

		    if (!help) //checks if they didn't ask for help
		    {
			try //catches errors
			{
			    c.setColor (Color.WHITE); //sets colour to white
			    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size
			    c.drawString ("Player " + (turn + 1) + ", please enter the answer: ", 20, 480);
			    c.setColor (Color.BLUE); //sets colour to blue
			    c.fillRoundRect (220, 464, 500, 22, 10, 10); //textbox

			    while (true) //loops until the 'enter' key is entered
			    {
				input = c.getChar (); //gets the character
				if (input == '\n') //checks if they entered the 'enter' key
				    break; //exits loop
				else if (input == 8) //checks if they entered the 'backspace' key
				{
				    if (answer.length () == 0) //checks if the answer length is 0
					continue; //continues
				    else
				    {
					answer = answer.substring (0, answer.length () - 1); //subtracts a character from the answer
					c.setColor (Color.BLUE); //sets colour to blue
					c.fillRoundRect (220, 464, 500, 22, 10, 10); //erases bad input
					c.setColor (Color.ORANGE); //sets colour to orange
					c.drawString (answer, 225, 480); //displays the answer
				    }
				}
				else if (input == '<') //checks if they entered '<'
				{
				    int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
				    if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
				    {
					exit = true;
					return; //exits method
				    }
				    else
					continue; //continues
				}
				else
				{
				    if (answer.length () < 44) //checks if they've exceeded the maximum answer length
				    {
					answer += input + ""; //adds the character to the answer
					c.setColor (Color.ORANGE); //sets colour to orange
					c.drawString (answer, 225, 480); //displays the answer
				    }
				}
			    }

			    if (answer.trim ().charAt (answer.trim ().length () - 1) == '?') //checks if the last character that isn't a space is '?'
				answer = answer.substring (0, answer.trim ().length () - 1); //deletes the '?'

			    if (!(answer.trim ()).equalsIgnoreCase (board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1])) //checks if the answer is incorrect
			    {
				answer = ""; //resets the answer
				counter++; //adds one to counter
				userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) - (100 + (100 * ((int) (xChoice.charAt (0)) - 65)))) + ""; //subtracts from score
				c.setColor (Color.BLUE); //sets colour to blue
				c.fillRoundRect (20, 103, 120, 30, 10, 10); //clear player 1 score
				c.fillRoundRect (660, 103, 120, 30, 10, 10); //clear player 2 score
				c.setColor (Color.ORANGE); //sets colour to orange
				c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
				c.drawString ("$" + userData [0] [1], 23, 128); //display player 1 score
				c.drawString ("$" + userData [1] [1], 663, 128); //display player 2 score

				if (turn == 0) //checks if it was just player 1's turn
				    turn = 1;
				else
				    turn = 0;

				if (counter == 2) //checks if the counter is 2, both players already guessed
				{
				    c.setColor (Color.WHITE); //sets colour to white
				    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size 14
				    c.drawString ("The answer was: " + board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1], 20, 500);
				    throw new Exception (); //throws new exception
				}
				throw new IllegalArgumentException (); //throws new exception
			    }
			    else
			    {
				answer = ""; //resets answer
				userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) + 100 + (100 * ((int) (xChoice.charAt (0)) - 65))) + ""; //adds to score
				c.setColor (Color.BLUE); //sets colour to blue
				c.fillRoundRect (20, 103, 120, 30, 10, 10); //clear player 1 score
				c.fillRoundRect (660, 103, 120, 30, 10, 10); //clear player 2 score
				c.setColor (Color.ORANGE); //sets colour to orange
				c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
				c.drawString ("$" + userData [0] [1], 23, 128); //display player 1 score
				c.drawString ("$" + userData [1] [1], 663, 128); //display player 2 score
				c.setColor (Color.WHITE); //sets colour to white
				c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size
				c.drawString ("You are correct!", 20, 500);
				counter = 2; //sets counter to 2
			    }
			}
			catch (IllegalArgumentException i)
			{
			    JOptionPane.showMessageDialog (null, "Your answer was wrong! " + userData [turn] [0] + ", you may buzz to answer or enter '?' if you don't know the answer.", "Wrong Answer", JOptionPane.ERROR_MESSAGE); //error message
			    c.setColor (darkBlue); //sets colour to darkBlue
			    c.fillRect (20, 462, 780, 25); //erases bad input
			    answer = ""; //resets answer
			}
			catch (Exception e)
			{
			    JOptionPane.showMessageDialog (null, "Your answer is also wrong!", "Wrong Answer", JOptionPane.ERROR_MESSAGE); //error message
			    c.setColor (darkBlue); //sets colour to darkBlue
			    c.fillRect (20, 462, 780, 25); //erases bad input
			    answer = ""; //resets answer
			}
		    }
		    else
		    {
			c.setColor (Color.WHITE); //sets colour to white
			c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size 14
			c.drawString ("The answer was: " + board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1], 20, 500); //displays answer
			counter = 2; //sets counter to 2
		    }
		}
	    }

	    pauseProgram (); //calls pauseProgram method
	    count++; //adds to count
	    c.setColor (darkBlue); //sets colour to darkBlue
	    c.fillRect (20, 420, 780, 180); //clears bottom of screen
	    board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2] = ""; //makes its value to null to make sure the user can't choose the same question again
	    board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1] = "";
	}
    }


    /*  Purpose: private dailyDouble method contains all of the processing necessary for the daily double part of the game. Players give their wager, view the
		 question, and give their answer. At the end, their score is increased or decreased depending on the validity of their response. Players are taken
		 back to the regular game after this.
	Loops:
	    - while loop for errortrapping and ensuring that only valid input is taken in
	    - while loop gets each character of the player's wager and loops until they press the 'enter' key
	    - while loop gets each character of the player's answer and loops until they press the 'enter' key
	Conditionals:
	    - if statement checks what character the user entered and follows the appropriate course of action.
		- If they entered the 'enter' key, the loop is broken out of.
		- If they entered the 'backspace' key, it erases bad input and deletes that character from the string.
		- If they entered '<', it confirms with them if they want to leave and if they say yes, they are taken back to the main menu.
		- Otherwise, the character is added to the string.
	    - if statement checks if the length of wager is 0 and throws an error if so
	    - if statement checks if the user confirmed that they would like to exit the game and if so, they are take back to the main menu.
	    - if statement checks if the value of the entered wager is less than 5 or greater than the maximum possible wager and throws an error if so.
	    - if statement checks if the length of the answer is less than 44 and only adds the typed character to the answer if so
	    - if statement checks if the length of the wager is less than 44 and only adds the typed character to the wager if so
	    - if statement checks if the length of answer is 0 and throws an error if so
	    - if statement checks if last character of the answer that isn't a space is '?' and deletes it if so
	    - if statement checks if the answer is correct (no leading or trailing spaces, any capitalization) and adds to their score if so, if not, subtract
	Segment Blocks: try catch blocks display an error message if the input isn't valid
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	xChoice         String              method parameter that stores the user's x choice
	yChoice         String              method parameter that stores the user's y choice
	board           String[][]          method parameter that stores all of the categories, questions, and answers that were selected for gameplay
	turn            int                 method parameter that stores whose turn it was when they landed on the daily double
	maxWager        int                 method parameter that stores the greatest amount of money that was on the board
	wager           String              stores the player's wager
	input           char                stores the user's input by character
	answer          String              stores the player's answer to the question
    */
    private void dailyDouble (String xChoice, int yChoice, String[] [] board, int turn, int maxWager)
    {
	//local variables
	String wager = "";
	char input = ' ';
	String answer = "";

	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size 14
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Daily Double! You may wager between $5 to $" + Math.max (maxWager, Integer.parseInt (userData [turn] [1])), 20, 460);
	c.drawString ("Player " + (turn + 1) + ", please enter your wager ($): ", 20, 480); //prompts for a wager

	while (true) //loops for error trapping purposes
	{
	    try //catches errors
	    {
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (255, 464, 500, 22, 10, 10); //textbox

		while (true) //gets each character of their wager and loops until the player presses the 'enter' key
		{
		    input = c.getChar (); //gets the character
		    if (input == '\n') //checks if they entered the 'enter' key
			break; //exits loop
		    else if (input == 8) //checks if they entered the 'backspace' key
		    {
			if (wager.length () == 0) //checks if the wager length is 0
			    continue; //continues
			else
			{
			    wager = wager.substring (0, wager.length () - 1); //subtracts a character from the wager
			    c.setColor (Color.BLUE); //sets colour to blue
			    c.fillRoundRect (255, 464, 500, 22, 10, 10);
			    c.setColor (Color.ORANGE); //sets colour to orange
			    c.drawString (wager, 260, 480); //displays the wager
			}
		    }
		    else if (input == '<') //checks if they entered '<'
		    {
			int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
			if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
			{
			    exit = true;
			    return; //exits the method
			}
			else
			    continue; //continues
		    }
		    else
		    {
			if (wager.length () < 44) //checks if they've exceeded the maximum wager length
			{
			    wager += input + ""; //adds the character to the wager
			    c.setColor (Color.ORANGE); //sets colour to orange
			    c.drawString (wager, 260, 480); //displays the wager
			}
		    }
		}
		if (Integer.parseInt (wager) < 5 || Integer.parseInt (wager) > Math.max (maxWager, Integer.parseInt (userData [turn] [1]))) //checks if the wager is valid
		    throw new IllegalArgumentException (); //throws new exception
		break; //exits loop
	    }
	    catch (NumberFormatException e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter an integer amount!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (255, 464, 500, 22, 10, 10); //erases bad input
		wager = ""; //resets wager
	    }
	    catch (IllegalArgumentException i)
	    {
		JOptionPane.showMessageDialog (null, "Please enter a wager between $5 to the greater of either your total earnings or the highest amount on the board!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (255, 464, 500, 22, 10, 10); //erases bad input
		wager = ""; //resets wager
	    }
	}


	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Question: " + board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2], 20, 500); //displays question
	c.drawString ("Please enter your answer: ", 20, 520); //prompts for answer
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (180, 504, 500, 22, 10, 10); //textbox

	while (true) //gets each character of the answer and loops until the player enter the 'enter' key
	{
	    input = c.getChar (); //gets character
	    if (input == '\n') //checks if they entered the 'enter' key
		break; //exits loop
	    else if (input == 8) //checks if they entered the 'backspace' key
	    {
		if (answer.length () == 0) //checks if the answer length is 0
		    continue; //continues
		else
		{
		    answer = answer.substring (0, answer.length () - 1); //subtracts a character from the answer
		    c.setColor (Color.BLUE); //sets colour to blue
		    c.fillRoundRect (180, 504, 500, 22, 10, 10); //erases bad input
		    c.setColor (Color.ORANGE); //sets colour to orange
		    c.drawString (answer, 185, 520); //displays the answer
		}
	    }
	    else if (input == '<') //checks if they entered '<'
	    {
		int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
		if (n == JOptionPane.YES_OPTION) //checks if they said yes
		{
		    exit = true;
		    return; //exits the method
		}
		else
		    continue; //continues
	    }
	    else
	    {
		if (answer.length () < 44) //checks if they've exceeded the maximum answer length
		{
		    answer += input + ""; //adds the character to the answer
		    c.setColor (Color.BLUE); //sets colour to blue
		    c.fillRoundRect (180, 504, 500, 22, 10, 10); //erases
		    c.setColor (Color.ORANGE); //sets colour to orange
		    c.drawString (answer, 185, 520); //displays the answer
		}
	    }
	}


	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14));  //sets font to Iskoola Pota, size 14

	if (answer.trim ().charAt (answer.trim ().length () - 1) == '?') //checks if the last character that isn't a space is '?'
	    answer = answer.substring (0, answer.trim ().length () - 1); //deletes the '?'

	if (answer.trim ().equalsIgnoreCase (board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1])) //checks if the answer is correct
	{
	    c.drawString ("You are correct!", 20, 540);
	    userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) + Integer.parseInt (wager)) + ""; //adds to score
	}


	else
	{
	    c.drawString ("Sorry, the answer was: " + board [yChoice - 1] [((int) (xChoice.charAt (0)) - 65) * 2 + 1], 20, 540);
	    userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) - Integer.parseInt (wager)) + ""; //subtracts from score
	}


	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (20, 103, 120, 30, 10, 10); //clears player 1's score
	c.fillRoundRect (660, 103, 120, 30, 10, 10); //clears player 2's score
	c.setColor (Color.ORANGE); //sets colour to orange
	c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
	c.drawString ("$" + userData [0] [1], 23, 128); //displays player 1's score
	c.drawString ("$" + userData [1] [1], 663, 128); //displays player 2's score
    }


    /*  Purpose: private finalJeopardy method contains all of the processing necessary for the final jeopardy part of the game. Players give their wager, view the
		 question, and give their answer. At the end, their score is increased or decreased depending on the validity of their response. Players are taken
		 to the display method after this.
	Loops:
	    - while loop loops until all eligible players has gone
	    - while loop gets each character of the player's wager and loops until they press the 'enter' key
	    - do-while loop gets all eligible player's answers and only repeats if player 2 is eligible to play and player 1 just went
	    - do-while loop checks all eligible player's answers and only repeats if player 2 is eligible to play and player 1 just went
	Conditionals:
	    - if statement checks if player 1 has a valid score
	    - if statement checks what character the user entered and follows the appropriate course of action.
		- If they entered the 'enter' key, the loop is broken out of.
		- If they entered the 'backspace' key, it erases bad input and deletes that character from the string.
		- If they entered '<', it confirms with them if they want to leave and if they say yes, they are taken back to the main menu.
		- Otherwise, the character is added to the string.
	    - if statement checks if the length of wager[turn] is 0 and throws an error if so
	    - if statement checks if the user confirmed that they would like to exit the game and if so, they are take back to the main menu.
	    - if statement checks if the value of the entered wager is less than 0 or greater than the maximum possible wager and throws an error if so.
	    - if statement checks if the length of the answer is less than 44 and only adds the typed character to the answer if so
	    - if statement checks if the length of the wager is less than 44 and only adds the typed character to the wager if so
	    - if statement checks if player 1 just went but player 2 is also eligible to play final jeopardy
	    - if statement checks if the length of answer[turn] is 0 and throws an error if so
	    - if statement checks if last character of the answer that isn't a space is '?' and deletes it if so
	    - if statement checks if the answer is correct (no leading or trailing spaces, any capitalization) and adds to their score if so, if not, subtract
	Segment Blocks: try catch blocks display an error message if the input isn't valid
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	finalCategory   String              method parameter that stores the final category
	finalQuestion   String              method parameter that stores the final question
	finalAnswer     String              method parameter that stores the final answer
	player1         boolean             method parameter that stores the eligibility of player 1 to play final jeopardy
	player2         boolean             method parameter that stores the eligibility of player 2 to play final jeopardy
	maxWager        int                 method parameter that stores the highest amount of money on the gameplay board
	turn            int                 keeps track of which player's turn it is
	answer          String[]            stores the answers of all eligible players
	wager           String[]            stores the wager of all eligible players
	input           char                stores the user's input by character
	darkBlue        Color               stores the RGB for darkBlue that's used for the background
    */
    private void finalJeopardy (String finalCategory, String finalQuestion, String finalAnswer, boolean player1, boolean player2, int maxWager)
    {
	//local variables
	int turn = 0;
	String[] answer = {"", ""};
	String[] wager = {"", ""};
	char input = ' ';
	Color darkBlue = new Color (18, 11, 71);

	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14)); //sets font to Iskoola Pota, size 14
	c.drawString ("Final Jeopardy!", 20, 440);
	c.drawString ("You may wager between $0 to the greater of $500 or your current score.", 20, 460);
	c.drawString ("Category: " + finalCategory, 20, 480); //displays the final category
	c.drawString ("Enter '<' to exit.", 700, 590); //lets the players know they can exit

	if (player1) //executes if player 1 has a valid score
	    turn = 0;
	else
	    turn = 1;

	while (true) //loops until all eligible players have gone
	{
	    try //catches errors
	    {
		c.setColor (Color.WHITE); //sets colour to white
		c.drawString ("Player " + (turn + 1) + ", please enter your wager ($): ", 20, 500);
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (245, 484, 500, 22, 10, 10); //textbox
		while (true) //loops until the player presses the 'enter' key
		{
		    input = c.getChar (); //gets the character
		    if (input == '\n') //checks if they entered the 'enter' key
			break; //exit loop
		    else if (input == 8) //checks if they entered the 'backspace' key
		    {
			if (wager [turn].length () == 0) //checks if its length is 0
			    continue; //continues
			else
			{
			    wager [turn] = wager [turn].substring (0, wager [turn].length () - 1); //subtracts a character from the wager
			    c.setColor (Color.BLUE); //sets colour to blue
			    c.fillRoundRect (245, 484, 500, 22, 10, 10); //erases bad input
			    c.setColor (Color.ORANGE); //sets colour to orange
			    c.drawString (wager [turn], 250, 500); //displays the wager
			}
		    }
		    else if (input == '<') //checks if they entered '<'
		    {
			int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
			if (n == JOptionPane.YES_OPTION) //checks if they click yes
			{
			    exit = true;
			    return; //exits the method
			}
			else
			    continue; //continues
		    }
		    else
		    {
			if (wager [turn].length () < 44) //checks if they've exceeded the maximum wager length
			{
			    wager [turn] += input + ""; //adds the character to the wager
			    c.setColor (Color.ORANGE); //sets colour to orange
			    c.drawString (wager [turn], 250, 500); //displays the wager
			}
		    }
		}

		if (Integer.parseInt (wager [turn]) < 0 || Integer.parseInt (wager [turn]) > Math.max (maxWager, Integer.parseInt (userData [turn] [1]))) //checks value of wager
		    throw new IllegalArgumentException (); //throw new exception

		if (turn == 0 && player2) //checks if player 1 just went but player 2 is also eligible to play final jeopardy
		{
		    c.setColor (darkBlue); //sets colour to darkBlue
		    c.fillRect (20, 484, 780, 22); //clears portion of screen
		    turn = 1; //changes turns
		}
		else
		    break; //exits loop
	    }
	    catch (NumberFormatException e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter an integer amount!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (245, 484, 500, 22, 10, 10); //erases bad input
		wager [turn] = ""; //resets wager
	    }
	    catch (IllegalArgumentException i)
	    {
		JOptionPane.showMessageDialog (null, "Please enter a wager between $0 to the greater of either your total earnings or the highest amount on the board!", "Error", JOptionPane.ERROR_MESSAGE); //error message
		c.setColor (Color.BLUE); //sets colour to blue
		c.fillRoundRect (245, 484, 500, 22, 10, 10); //erases bad input
		wager [turn] = ""; //resets wager
	    }
	}


	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("Question: " + finalQuestion, 20, 520);

	if (player1) //checks if player 1 is eligible to play final jeopardy
	    turn = 0;
	else
	    turn = 1;

	do //gets the answer to the question
	{
	    c.setColor (Color.WHITE); //sets colour to white
	    c.drawString ("Player " + (turn + 1) + ", please enter the answer: ", 20, 540);
	    c.setColor (Color.BLUE); //sets colour to blue
	    c.fillRoundRect (220, 524, 500, 22, 10, 10); //textboz

	    while (true) //loops to get input
	    {
		input = c.getChar (); //gets character
		if (input == '\n') //checks if they entered the 'enter' key
		    break; //exits loop
		else if (input == 8) //checks if they entered the 'backspace' key
		{
		    if (answer [turn].length () == 0) //checks if the answer length is 0
			continue; //continues
		    else
		    {
			answer [turn] = answer [turn].substring (0, answer [turn].length () - 1); //subtracts a character from the answer
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (220, 524, 500, 22, 10, 10); //erases bad input
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (answer [turn], 225, 540); //displays the answer
		    }
		}
		else if (input == '<') //checks if they entered '<'
		{
		    int n = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit? All progress will be lost.", "", JOptionPane.YES_NO_OPTION); //confirmation message
		    if (n == JOptionPane.YES_OPTION) //checks if they clicked yes
		    {
			exit = true;
			return; //exits the method
		    }
		    else
			continue; //continues
		}
		else
		{
		    if (answer [turn].length () < 44) //checks if they've exceeded the maximum answer length
		    {
			answer [turn] += input + ""; //adds the character to the answer
			c.setColor (Color.BLUE); //sets colour to blue
			c.fillRoundRect (220, 524, 500, 22, 10, 10); //erases
			c.setColor (Color.ORANGE); //sets colour to orange
			c.drawString (answer [turn], 225, 540); //displays the answer
		    }
		}
	    }

	    if (answer [turn].trim ().charAt (answer [turn].trim ().length () - 1) == '?') //checks if last character that isn't a space is '?'
		answer [turn] = answer [turn].substring (0, answer [turn].trim ().length () - 1); //deletes the '?'

	    if (turn == 0 && player2) //checks if player 2 is eligible to play final jeopardy and player 1 just went
	    {
		turn = 1;
		c.setColor (darkBlue); //sets colour to darkBlue
		c.fillRect (20, 522, 780, 25); //clears portion of screen
	    }
	    else
		break; //exit loop

	}


	while (player2);

	if (player1) //checks if player 2 is eligible to play final jeopardy
	    turn = 0;
	else
	    turn = 1;

	do //checks all eligible player's answers
	{
	    if ((answer [turn].trim ()).equalsIgnoreCase (finalAnswer)) //checks if the answer is correct
		userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) + Integer.parseInt (wager [turn])) + ""; //add to score
	    else
		userData [turn] [1] = (Integer.parseInt (userData [turn] [1]) - Integer.parseInt (wager [turn])) + ""; //subtract from score
	    c.setColor (Color.BLUE); //sets colour to blue
	    c.fillRoundRect (20, 103, 100, 30, 10, 10); //clears player 1's score
	    c.fillRoundRect (660, 103, 100, 30, 10, 10); //clears player 2's score
	    c.setColor (Color.ORANGE); //sets colour to orange
	    c.setFont (new Font ("Impact", Font.PLAIN, 20)); //sets font to Impact, size 20
	    c.drawString ("$" + userData [0] [1], 23, 128); //displays player 1's score
	    c.drawString ("$" + userData [1] [1], 663, 128); //displays player 2's score

	    if (turn == 0 && player2) //checks if player 2 is eligible to play final jeopardy and player 1 just went
		turn = 1;
	    else
		break; //exits loop
	}


	while (player2);

	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 14)); //sets font to Iskoola Pota, size 14
	c.drawString ("The answer was: " + finalAnswer, 20, 560); //displays answer
    }


    /*  Purpose: public levelOne method calls all of the appropriate methods in the right order for game play.
	Loops: for loop loops past all of the final questions until the chosen one is reached
	Conditionals:
	    - if statements check if exit are false and only execute if so
	    - if statement checks both player's scores to see which are eligible to play final jeopardy
	Segment Blocks: try catch block catches any errors that may occur while reading to a file
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	dailyDouble1    String              stores the index of the randomly selected daily double
	index           int                 stores the randomly selected index of the final question that is to be asked
	reader          BufferedReader      allows me to read from a file
	finalCategory   String              stores the chosen final question
	finalQuestion   String              stores the answer to the chosen final question
	finalAnswer     String              stores the category of the chosen final question
	board1          String[][]          stores all of the randomly selected questions, answers, and category names
	i               int                 loop variable that starts at 1 and ends right before the line of the chosen question in the file
    */
    public void levelOne () throws InterruptedException
    {
	//local variables
	String dailyDouble1 = Character.toString ((char) ((int) (5 * Math.random () + 65)));
	dailyDouble1 += (int) (5 * Math.random () + 1) + "";
	int index = (int) (1 + (10 * Math.random ()));
	BufferedReader reader;
	String finalCategory = "";
	String finalQuestion = "";
	String finalAnswer = "";
	used = new boolean[] {false, false, false, false, false, false, false, false, false, false, true}; //resets values
	String[] [] board1 = {chooseCategory (1), chooseCategory (1), chooseCategory (1), chooseCategory (1), chooseCategory (1) };

	askData (); //calls askData method
	title (); //calls title method
	displayBoard (board1, 100); //displays the board

	try //catches errors
	{
	    reader = new BufferedReader (new FileReader ("finalQuestions/level1.txt")); //reads from the level1 file
	    for (int i = 1 ; i < (index - 1) * 3 ; i += 3) //loops past other lines in the file
		reader.readLine (); //reads the line
	    finalCategory = reader.readLine (); //stores the current line of text as the final category
	    finalQuestion = reader.readLine (); //stores the current line of text as the final question
	    finalAnswer = reader.readLine (); //stores the current line of text as the final answer
	}


	catch (IOException e)
	{
	}


	gameplay (board1, dailyDouble1, "F6", 500); //calls the gameplay method; "F6" is written because there is no daily double 2 in level 1

	if (!exit) //executes if the user doesn't wish to exit
	{
	    if (Integer.parseInt (userData [0] [1]) > -1 && Integer.parseInt (userData [1] [1]) > -1) //if both have valid scores
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, true, true, 500); //calls finalJeopardy method
	    else if (Integer.parseInt (userData [0] [1]) > -1 && Integer.parseInt (userData [1] [1]) < 0) //if player 1 has a valid score only
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, true, false, 500); //calls finalJeopardy method
	    else if (Integer.parseInt (userData [0] [1]) < 0 && Integer.parseInt (userData [1] [1]) > -1) //if player 2 has a valid score only
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, false, true, 500); //calls finalJeopardy method
	    else
	    {
		c.setColor (Color.WHITE); //sets colour to white
		c.drawString ("The game is over.", 20, 400);
	    }
	}


	if (!exit) //executes if the user doesn't wish to exit
	{
	    saveData (1); //saves the data
	    pauseProgram (); //calls pauseProgram method
	}
    }


    /*  Purpose: public levelTwo method calls all of the appropriate methods in the right order for game play.
	Loops: for loop loops past all of the final questions until the chosen one is reached
	Conditionals:
	    - if statements check if exit are false and only execute if so
	    - if statement checks both player's scores to see which are eligible to play final jeopardy
	Segment Blocks: try catch block catches any errors that may occur while reading to a file
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	dailyDouble1    String              stores the index of the 1st randomly selected daily double
	dailyDouble2    String              stores the index of the 2nd randomly selected daily double
	index           int                 stores the randomly selected index of the final question that is to be asked
	reader          BufferedReader      allows me to read from a file
	finalCategory   String              stores the chosen final question
	finalQuestion   String              stores the answer to the chosen final question
	finalAnswer     String              stores the category of the chosen final question
	board2          String[][]          stores all of the randomly selected questions, answers, and category names
	i               int                 loop variable that starts at 1 and ends right before the line of the chosen question in the file
    */
    public void levelTwo () throws InterruptedException
    {
	//local variables
	String dailyDouble1 = Character.toString ((char) ((int) (5 * Math.random () + 65)));
	dailyDouble1 += (int) (3 * Math.random () + 1) + "";
	String dailyDouble2 = Character.toString ((char) ((int) (5 * Math.random () + 65)));
	dailyDouble2 += (int) (2 * Math.random () + 4) + "";
	int index = (int) (10 * Math.random ()) - 1;
	BufferedReader reader;
	String finalCategory = "";
	String finalQuestion = "";
	String finalAnswer = "";
	used = new boolean[] {false, false, false, false, false, false, false, false, false, false, true}; //resets values
	String[] [] board2 = {chooseCategory (2), chooseCategory (2), chooseCategory (2), chooseCategory (2), chooseCategory (2) };

	askData (); //calls askData method
	title (); //calls title method
	displayBoard (board2, 200); //displays the board

	try //catches errors
	{
	    reader = new BufferedReader (new FileReader ("finalQuestions/level2.txt")); //reads from the level2 file
	    for (int i = 1 ; i < (index - 1) * 3 ; i += 3) //loops past other lines in the file
		reader.readLine (); //reads the line
	    finalCategory = reader.readLine (); //stores the current line of text as the final category
	    finalQuestion = reader.readLine (); //stores the current line of text as the final question
	    finalAnswer = reader.readLine (); //stores the current line of text as the final answer
	}


	catch (IOException e)
	{
	}


	gameplay (board2, dailyDouble1, dailyDouble2, 1000); //calls the gameplay method

	if (!exit) //executes if the user doesn't wish to exit
	{
	    if (Integer.parseInt (userData [0] [1]) > 0 && Integer.parseInt (userData [1] [1]) > 0) //if both scores are valid
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, true, true, 1000); //calls finalJeopardy method
	    else if (Integer.parseInt (userData [0] [1]) > 0 && Integer.parseInt (userData [1] [1]) < 0) //if player 1 has a valid score only
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, true, false, 1000); //calls finalJeopardy method
	    else if (Integer.parseInt (userData [0] [1]) < 0 && Integer.parseInt (userData [1] [1]) > 0) //if player 2 has a valid score only
		finalJeopardy (finalCategory, finalQuestion, finalAnswer, false, true, 1000); //calls finalJeopardy method
	    else
	    {
		c.setColor (Color.WHITE); //sets colour to white
		c.drawString ("The game is over.", 20, 400);
	    }
	}


	if (!exit) //executes if the user doesn't wish to exit
	{
	    saveData (2); //saves the data
	    pauseProgram (); //calls pauseProgram method
	}
    }


    /*  Purpose: public display method displays the results of the game: the winning and losing player and their respective scores. It leads the users back to the
		 main menu.
	Conditionals:
	    - if statement checks if the players didn't choose to exit earlier on in the game
	    - if statement checks which player has the higher score
    */
    public void display ()
    {
	title (); //calls title method

	if (!exit) //executes if exit is false
	{
	    c.setColor (Color.WHITE); //sets colour to white
	    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30));  //sets font to Iskoola Pota, size 30
	    c.drawString ("Results", 370, 100);
	    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20));  //sets font to Iskoola Pota, size 20

	    //displays results
	    if (Integer.parseInt (userData [0] [1]) > Integer.parseInt (userData [1] [1])) //executes if player 1 has a greater score
	    {
		c.drawString ("1st place: " + userData [0] [0] + " with a score of $" + userData [0] [1], 220, 170);
		c.drawString ("2nd place: " + userData [1] [0] + " with a score of $" + userData [1] [1], 220, 200);
	    }
	    else if (Integer.parseInt (userData [0] [1]) < Integer.parseInt (userData [1] [1])) //executes if player 2 has a greater score
	    {
		c.drawString ("1st place: " + userData [1] [0] + " with a score of $" + userData [1] [1], 220, 170);
		c.drawString ("2nd place: " + userData [0] [0] + " with a score of $" + userData [0] [1], 220, 200);
	    }
	    else //executes if there's a tie
	    {
		c.drawString ("There was a tie!", 220, 170);
		c.drawString (userData [0] [0] + " ended with a score of $" + userData [0] [1], 220, 200);
		c.drawString (userData [0] [0] + " ended with a score of $" + userData [0] [1], 220, 230);
	    }

	    pauseProgram (); //calls pauseProgram method
	}
    }


    /*  Purpose: public highScores method auto stores top 10 sorted winners/highest scoring players by their name and score, separated by level.
	Loops:
	    - for loop starts at 1 and stops at 2, increasing by 1, it's used to repeat the entire block of code for the second set of high scores
	    - while loop is used to count the number of lines in the text file and only stops when the line is null
	    - for loop is used to loop through the length of highScores
	    - for loop is used to loop through the length of highScores[0]
	    - for loop is used to loop through the length of highScores - 1
	    - for loop is used to loop through the length of highScores - k - 1
	    - for loop is used to loop through the minimum of either 10 or the length of highScores
	    - for loop is used to loop from 1 to 2, alternating between name and score
	Conditionals:
	    - if statement checks if the "HighScores1.txt" and "HighScores2.txt" files don't exist
	    - if statement checks if the current line of text is null
	    - if statement checks if the current high score is less than the next high score
	    - if statement checks if z == 1, which means if I'm currently displaying the score, and adds "$" to the start of the string
	    - if statement checks which files the players chose to clear, if any, and clears the specified files
	Segment Blocks: try catch blocks catches any errors that may occur while reading and writing to a file.
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	input           BufferedReader      allows me to read from files
	output          PrintWriter         allows me to write to files
	line            String              stores the value of the current line of text in the file
	counter         int                 stores the number of lines in the current file
	tempScore       String              stores the score temporarily for bubble sort
	tempName        String              stores the name temporarily for bubble sort
	highScores      String[][]          stores the names and scores from the file which is used for bubble sort and display
	clearChoice     char                stores the user's choice for choosing which files to clear, if any at all
	t               int                 loop variable that's used to repeat the entire block of code for the second set of high scores
	i               int                 loop variable used to loop through the length of highScores
	x               int                 loop variable used to loop through the length of highScores[0]
	k               int                 loop variable used to loop through the length of highScores - 1
	j               int                 loop variable used to loop through the length of highScores - k - 1
	y               int                 loop variable used to loop through the minimum of either 10 or the length of highScores
	z               int                 loop variable used to loop from 1 to 2, alternating between name and score
    */
    public void highScores ()
    {
	//local variables
	BufferedReader input;
	PrintWriter output;
	String line = "";
	int counter = 0;
	String tempScore = "";
	String tempName = "";
	String[] [] highScores = new String [1] [1];
	char clearChoice = ' ';

	title (); //calls title method

	for (int t = 1 ; t < 3 ; t++) //loops twice
	{
	    counter = 0; //resets counter
	    try //catches errors
	    {
		//counts number of lines
		input = new BufferedReader (new FileReader ("highScores/HighScores" + t + ".txt")); //resets text file
		while (true) //loops until the line is null
		{
		    line = input.readLine (); //reads the line
		    if (line == null) //checks if it's null
			break; //exits loop
		    else
			counter++; //adds to counter
		}

		//put data into the array
		input = new BufferedReader (new FileReader ("highScores/HighScores" + t + ".txt")); //resets text file
		highScores = new String [counter / 2] [2]; //resizes highScores as needed
		for (int i = 0 ; i < highScores.length ; i++) //loops through highScores
		{
		    for (int x = 0 ; x < highScores [0].length ; x++) //loops through highScores[0]
		    {
			highScores [i] [x] = input.readLine (); //stores the line of text into the array
		    }
		}
	    }
	    catch (IOException e)
	    {
	    }

	    //sort the array in descending order using bubble sort
	    for (int k = 0 ; k < (highScores.length - 1) ; k++) //loops through highScores-1
	    {
		for (int j = 0 ; j < highScores.length - k - 1 ; j++) //loops through highScores-k-1
		{
		    if (Integer.parseInt (highScores [j] [1]) < Integer.parseInt (highScores [j + 1] [1])) //checks if the current high score is less than the next one
		    {
			tempName = highScores [j] [0];
			highScores [j] [0] = highScores [j + 1] [0];
			highScores [j + 1] [0] = tempName;
			tempScore = highScores [j] [1];
			highScores [j] [1] = highScores [j + 1] [1];
			highScores [j + 1] [1] = tempScore;
		    }
		}
	    }

	    //prints the top 10 winners
	    c.setColor (Color.WHITE); //sets colour to white
	    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30));  //sets font to Iskoola Pota, size 30
	    c.drawString ("High Scores", 335, 70);
	    c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20));  //sets font to Iskoola Pota, size 20
	    c.drawString ("Name", 20, 145);
	    c.drawString ("Score", 290, 145);
	    c.drawString ("Name", 410, 145);
	    c.drawString ("Score", 680, 145);

	    for (int y = 0 ; y < Math.min (10, highScores.length) ; y++) //loops through the minimum of either 10 or the length of highScores
	    {
		for (int z = 0 ; z < 2 ; z++) //loops from 1 to 2
		{
		    if (z == 1) //checks if z is 1
			c.drawString ("$" + highScores [y] [z], (20 + (t - 1) * 390) + 270 * z, 170 + y * 25); //prints the score with "$"
		    else
			c.drawString (highScores [y] [z], (20 + (t - 1) * 390) + 270 * z, 170 + y * 25); //prints the name
		}
	    }
	}


	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (15, 100, 70, 25, 10, 10); //level 1 button
	c.fillRoundRect (405, 100, 70, 25, 10, 10); //level 2 button
	c.setColor (Color.ORANGE); //sets colour to orange
	c.drawString ("Level 1", 20, 120);
	c.drawString ("Level 2", 410, 120);
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 15));  //sets font to Iskoola Pota, size  15
	c.drawString ("Enter '1' to clear the Level 1 file, '2' to clear the Level 2 file, '3' to clear both, or any other character to continue...", 20, 590);
	clearChoice = c.getChar (); //gets character

	try //catches errors
	{
	    if (clearChoice == '1' || clearChoice == '3')
	    {
		output = new PrintWriter (new FileWriter ("highScores/HighScores1.txt")); //resets text file
		output.close (); //closes file
	    }
	    else if (clearChoice == '2' || clearChoice == '3')
	    {
		output = new PrintWriter (new FileWriter ("highScores/HighScores2.txt")); //resets text file
		output.close (); //closes file
	    }
	}


	catch (IOException e)
	{
	}
    }


    //  Purpose: public goodbye method displays the programmer's name, program info, and closes the program without error.
    public void goodbye ()
    {
	title (); //calls title method
	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (220, 220, 375, 55, 10, 10); //textbox
	c.setColor (Color.ORANGE); //sets colour to orange
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20)); //sets font to Iskoola Pota, size 20
	c.drawString ("Thank you for playing my Jeopardy program!", 230, 240);
	c.drawString ("Created by: Rachel Leong", 310, 265);
	pauseProgram (); //calls pauseProgram method
	c.close (); //closes program
    }


    /*  Purpose: main method of a java application correctly executes and controls all program flow through the methods; throws InterruptedException
	Loops: while loop loops until the players enter 'd'.
	Conditionals:
	    - if statement used to check the value of choice and execute the appropriate methods.
	    - if statement used to check the value of levelChoice and execute the correct level of play.
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	j               Jeopardy            used to call different methods
    */
    public static void main (String[] args) throws InterruptedException
    {
	Jeopardy j = new Jeopardy (); //creates instance variable of Jeopardy & constructs a new Jeopardy object
	j.splashScreen ();
	while (true) //loops until the players choose to exit
	{
	    j.mainMenu (); //executes mainMenu method
	    if (j.choice.equalsIgnoreCase ("a")) //checks if choice is 'a'
		j.instructions (); //executes instructions method
	    else if (j.choice.equalsIgnoreCase ("b")) //checks if choice is 'b'
	    {
		j.levelSelection (); //executes levelSelection method
		if (j.levelChoice.equalsIgnoreCase ("a")) //checks if levelChoice is 'a'
		    j.levelOne (); //executes levelOne method
		else if (j.choice.equalsIgnoreCase ("b")) //checks if levelChoice is 'b'
		    j.levelTwo (); //executes levelTwo method
		j.display (); //executes display method
	    }
	    else if (j.choice.equalsIgnoreCase ("c")) //checks if choice is 'c'
		j.highScores (); //executes highScores method
	    else if (j.choice.equalsIgnoreCase ("d")) //checks if choice is 'd'
		break; //exits loop
	}
	j.goodbye (); //executes goodbye method
    }
}


