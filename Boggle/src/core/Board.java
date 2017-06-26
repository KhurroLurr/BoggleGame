// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated: 

package core;

import java.util.ArrayList;

public class Board 
{
    // Constant variables used for loops
    private static final int NUMBER_OF_DICE = 16;
    private static final int NUMBER_OF_SIDES = 6;
    private static final int GRID = 4;
    
    //ArrayLists to obtain, gather, and store data
    private ArrayList boggleData = new ArrayList();
    private ArrayList<Die> dieData = new ArrayList<>();
    
    // Constructor for the Board class
    public Board(ArrayList<Board> gameData)
    {
        boggleData = gameData;
    }
    
    private void populateDice()
    {
        // Initializing the necessary variables
        int k = 0;
        Die die;
        
        // For every die
        for(int i = 0; i < NUMBER_OF_DICE; i++)
        {
            // Creating a new instance of die
            die = new Die();
            
            // For every side
            for(int j = 0; j < NUMBER_OF_SIDES; j++)
            {
                // Add that letter a side of that instance of die
                die.addLetter(boggleData.get(k).toString());
                k++;
            }
            // Display all letters on the die, disabled as not needed
            //System.out.print("Die " + i + ":\t");
            //die.displayAllLetters();
            
            // Die is added to the array
            dieData.add(die);
        }
    }
    
    // Randomizes the display of the dice
    public ArrayList shakeDice()
    {
        int i = 0;
        
        // Creates an instance of the die
        populateDice();
        
        /* Used for testing purposes, currently disabled
        // Displays a letter from each die
        for (Die die : dieData)
        {
            // Creates an instance of a string to store a letter
            String letter = die.getLetter();
            
            // Print out the letter retrieved
            System.out.print(letter + " ");
            
            // Increment i by 1
            i++;
            
            // Allows for the die to be displayed in a 4x4 grid
            if(i % GRID == 0)
                System.out.println();
            
        }*/
        
        return dieData;
    }
}
