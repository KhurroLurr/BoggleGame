// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated: 

package inputOutput;

import java.io.File;
import java.net.URL;
// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated: 

import java.util.Scanner;
import java.util.ArrayList;

public class ReadDataFile 
{
    Scanner input;
    String fileName;
    ArrayList data = new ArrayList();
    
    public ReadDataFile(String inputFile)
    {
        fileName =  inputFile;
    }
    
    public void populateData()
    {
        try // Attempt to read in data from file
        {
            // Ensures file input is platform independent
            URL url = getClass().getResource(fileName);
            File file = new File(url.toURI());
            input = new Scanner(file);
            
            // Gets data from file until it reaches the end
            while(input.hasNext())
            {
                data.add(input.next());
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            input.close();
        }
    }
    
    public ArrayList getData()
    {
        return data;
    }
    
}
