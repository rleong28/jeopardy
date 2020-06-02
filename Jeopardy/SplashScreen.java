//Rachel Leong
//ISP - Jeopardy
//January 10, 2020
//Ms. Krasteva

//This class animates the words "This is... JEOPARDY" appearing on the screen and a cursor coming in to click on the 'YES' button.

import java.awt.*;  //gives access to java command libraries
import hsa.Console; //gives access to Console class file
import java.lang.*; //gives access to Thread class

public class SplashScreen implements Runnable
{
    private Console c; //The output console

    /*  Purpose: splashscreen method contains all the animations
	Loops:
	    - for loop loops from 0 to 300, controls the movement of the Jeopardy letter
	    - for loop loops form 0 to 145, controls the diagonal movement of the cursor
	Segment Blocks: try and catch blocks catch errors that may occur while using Threads
	Variable Dictionary:
	Name            Type                Description
	--------------------------------------------------------------------------------------------------------------------------------
	darkBlue        Color               stores the RGB value for dark blue
	brown           Color               stores the RGB value for brown
	lightBlue       Color               stores the RGB value for light blue
	cursorX         int[]               stores the x-coordinates for the moving cursor
	cursorY         int[]               stores the y-coordinates for the moving cursor
	newCursorX      int[]               stores the x-coordinates for the stationary cursor
	newCursorY      int[]               stores the y-coordinates for the stationary cursor
	cursorEraseX    int[]               stores the x-coordinates for the erase for the moving cursor
	cursorEraseY    int[]               stores the y-coordinates for the erase for the moving cursor
	x               int                 loop variable that controls the up and down movement of the word "JEOPARDY"
	i               int                 loop variable that controls the diagonal movement of the cursor
    */
    public void splashscreen ()
    {
	//local variables
	Color darkBlue = new Color (18, 11, 71); //dark blue
	Color brown = new Color (150, 98, 26); //brown
	Color lightBlue = new Color (94, 94, 255); //light blue

	c.setColor (darkBlue); //sets colour to dark blue
	c.fillRect (10, 0, 800, 600); //background
	c.setColor (Color.ORANGE); //sets colour to orange
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30)); //sets font to Iskoola Pota, size 30
	c.drawString ("This", 240, 200);

	try //catches errors
	{
	    Thread.sleep (500); //delays the animation
	}
	catch (Exception e)
	{
	}
	c.drawString ("is", 300, 200);

	try //catches errors
	{
	    Thread.sleep (500); //delays the animation
	}
	catch (Exception e)
	{
	}

	c.drawString ("...", 318, 200);
	c.setFont (new Font ("Impact", Font.PLAIN, 70)); //sets font to Impact, size 70

	for (int x = 0 ; x < 300 ; x++) //loops 300 times
	{
	    synchronized (c) //learned from: https://docs.google.com/document/d/1mgR09oneNIJhgefgkHK2vpaSQ5gZ8xDqB7rtPDQ7YHM/edit By: Andy Pham
	    {
		c.setColor (darkBlue); //sets colour to dark blue
		c.drawString ("J   O    A    D    !", 240, -20 + x - 1);
		c.drawString ("E    P    R    Y", 265, 578 - x + 1);
		c.drawString ("J   O    A    D    !", 245, -15 + x - 1); //shadow
		c.drawString ("E    P    R    Y", 270, 583 - x + 1);
	    }
	    synchronized (c)
	    {
		c.setColor (Color.ORANGE); //sets colour to orange
		c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 30)); //sets font to Iskoola Pota, size 30
		c.drawString ("This is...", 240, 200);
		c.setFont (new Font ("Impact", Font.PLAIN, 70)); //sets font to Impact, size 70
		c.setColor (brown); //sets colour to brown
		c.drawString ("J   O    A    D    !", 245, -15 + x);
		c.drawString ("E    P    R    Y", 270, 583 - x);
		c.setColor (Color.ORANGE); //sets colour to orange
		c.drawString ("J   O    A    D    !", 240, -20 + x);
		c.drawString ("E    P    R    Y", 265, 578 - x);
	    }

	    try //catches errors
	    {
		Thread.sleep (10); //delays the animation
	    }
	    catch (Exception e)
	    {
	    }
	}


	c.setColor (Color.BLUE); //sets colour to blue
	c.fillRoundRect (320, 375, 75, 30, 10, 10); //buttons
	c.fillRoundRect (420, 375, 75, 30, 10, 10);
	c.setColor (Color.WHITE); //sets colour to white
	c.setFont (new Font ("Iskoola Pota", Font.PLAIN, 20)); //sets font to Iskoola Pota, size 20
	c.drawString ("Shall we begin?", 350, 350);
	c.drawString ("YES", 340, 396);
	c.drawString ("NO", 445, 396);

	for (int i = 0 ; i < 145 ; i++) //loops 145 times
	{
	    c.setColor (darkBlue); //sets colour to dark blue
	    int[] cursorEraseX = {534 - i + 1, 541 - i + 1, 539 - i + 1, 541 - i + 1, 535 - i + 1, 533 - i + 1, 531 - i + 1};
	    int[] cursorEraseY = {540 - i + 1, 547 - i + 1, 549 - i + 1, 551 - i + 1, 553 - i + 1, 549 - i + 1, 551 - i + 1};
	    c.fillPolygon (cursorEraseX, cursorEraseY, 7);
	    c.setColor (Color.GRAY); //sets colour to grey
	    int[] cursorX = {534 - i, 541 - i, 539 - i, 541 - i, 535 - i, 533 - i, 531 - i};
	    int[] cursorY = {540 - i, 547 - i, 549 - i, 551 - i, 553 - i, 549 - i, 551 - i};
	    c.fillPolygon (cursorX, cursorY, 7); //creates a polygon in the shape of a cursor

	    try //catches errors
	    {
		Thread.sleep (13); //delays the animation
	    }
	    catch (Exception e)
	    {
	    }
	}


	c.setColor (lightBlue); //sets colour to light blue
	c.fillRoundRect (320, 375, 75, 30, 10, 10); //button
	c.setColor (Color.WHITE); //sets colour to white
	c.drawString ("YES", 340, 396);
	c.setColor (Color.GRAY); //sets colour to grey
	int[] newCursorX = {389, 396, 394, 392, 392, 390, 386};
	int[] newCursorY = {395, 402, 404, 406, 408, 404, 406};
	c.fillPolygon (newCursorX, newCursorY, 7); //creates the cursor on top of the button

	try //catches errors
	{
	    Thread.sleep (500); //delays the animation
	}


	catch (Exception e)
	{
	}
    }


    //SplashScreen class constructor
    public SplashScreen (Console con)
    {
	c = con;
    }


    //run method to be started in Jeopardy class
    public void run ()
    {
	splashscreen ();    //execute splashscreen method
    }
}
