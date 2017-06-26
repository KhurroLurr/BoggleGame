// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated: 

package boggle;

import java.util.ArrayList;
import core.Board;
import inputOutput.ReadDataFile;
import userInterface.BoggleUi;

public class Boggle 
{
    // Boggle Game Data
    public static String boggleFile = "BoggleData.txt";
    public static ArrayList boggleData = new ArrayList();
    public static String dictFile = "TemporaryDictionary.txt";
    public static ArrayList<String> dictData = new ArrayList<>();
    
    public static void main(String[] args)
    {
        // Reading in boggle data
        ReadDataFile readBoggle = new ReadDataFile(boggleFile);
        readBoggle.populateData();
        boggleData = readBoggle.getData();
        
        // Reading in dictionary data
        ReadDataFile readDict = new ReadDataFile(dictFile);
        readDict.populateData();
        dictData = readDict.getData();
        
        // Passing in data to a new board
        Board boggleBoard = new Board(boggleData);
        
        // Randomizes the dice 
        boggleBoard.shakeDice();
        
        BoggleUi boggleUI = new BoggleUi(boggleBoard, dictData);
    }
    
}
