// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated:

package core;

import java.util.ArrayList;
import java.util.Random;

public class Die 
{
    private static final int NUMBER_OF_SIDES = 6;
    private ArrayList <String> dieSide = new ArrayList<>();
    private String letter;
    
    // Obtains a letter from a random side of the die
    private void randomLetter()
    {
        Random random = new Random();
        letter = dieSide.get(random.nextInt(NUMBER_OF_SIDES));
    }
    
    // Returns a latter after calling the randomLetter class 
    public String getLetter()
    {
        randomLetter();
        return letter;
    }
    
    // Adds the letter that was passed in
    public void addLetter(String someLetter)
    {
        dieSide.add(someLetter);
    }
    
    // Prints out all letters on the die
    public void displayAllLetters()
    {
        // Travels through all sides of the die
        for(int i = 0; i < NUMBER_OF_SIDES; i ++)
        {
            System.out.print(dieSide.get(i) + " ");
        }
        
        // Creates a new line after all letters are printed
        System.out.println();
    }
}
