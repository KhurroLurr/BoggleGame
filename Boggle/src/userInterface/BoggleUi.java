// Nicholas Espinosa
// COP 3330-C001
// Boggle Project
// Last Updated: 

package userInterface;

import core.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;



public class BoggleUi implements ActionListener
{
    // Game board
    Board board;
    
    // Frame, Layout, and Panels
    JFrame frame;
    BorderLayout borderLayout;
    JPanel buttonPanel;
    JPanel gameInfo;
    JPanel userInput;
    
    // JMenu components
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem newGame;
    JMenuItem exit;
    
    // Game Information Items
    JScrollPane scrollPane;
    JTextArea wordsEntered;
    JTextPane wordPane;
    Font font;
    JLabel letters;
    JLabel gameClock;
    JButton shakeDice;
    
    // User Input Items
    JLabel currentWord;
    JButton submit;
    JLabel currentScore;
    int gameScore;
    
    // Timer Items
    Timer timer;
    int timeLeft;
    int timeMinutes;
    int timeSeconds;
    
    // Flag variable set to determine if word was entered twice
    boolean flag;
    
    boolean[] isGuessed;
    
    // ArrayLists to hold data
    private ArrayList<Die> die = new ArrayList<>();
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ArrayList dictionary = new ArrayList();
    private ArrayList<String> validAnswers = new ArrayList<>();

    // Constructor for the BoggleUi class
    public BoggleUi(Board gameBoard, ArrayList dictData)
    {
        board = gameBoard;
        dictionary = dictData;
        initComponents();
    }
    
    // Initializes the the user interface
    private void initComponents()
    {
        // Creating the user interface
        createJFrame();
        createJPanels();
        createMenu();
        createButtonPanel();
        createGameInfoPanel();
        createUserInputPanel();
        
        // Setting the frame to visible
        frame.setVisible(true);
    }
    
    private void createJFrame()
    {
        // Creating the JFrame
        frame = new JFrame("Boggle");
        borderLayout = new BorderLayout();
        frame.setLayout(borderLayout);
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void createJPanels()
    {
        // Creating the Button JPanel
        buttonPanel = new JPanel(new GridLayout(4,4));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
        
        // Creating the Game Information JPanel
        gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));
        gameInfo.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        
        // Creating the User Input JPanel
        userInput = new JPanel(new FlowLayout());
        userInput.setBorder(BorderFactory.createTitledBorder("Current Word"));
    }
    
    private void createMenu()
    {
        // Initializing teh menu items
        menuBar = new JMenuBar();
        menu = new JMenu("Boggle");
        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");
     
        //Putting the menu together
        menu.add(newGame);
        menu.add(exit);
        menuBar.add(menu);
        
        // Adding the ActionListeners
        newGame.addActionListener(this);
        exit.addActionListener(this);
        
        //Adding the menu to the fame
        frame.setJMenuBar(menuBar);
    }
    
    private void createButtonPanel()
    {
        // Initializing necessary variables
        int k = 0;
        
        // Randomizing the die
        die = board.shakeDice();
        
        // Creates the buttons in the correct format
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                // Create a new button and sets its size
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(80, 80));
                
                // Retrieve a letter from the ArrayList and add it to the button
                String letter = die.get(k).getLetter();
                button.setText(letter);
                
                // Adding ActionListener and Client Properties
                button.addActionListener(new ButtonListener());
                button.putClientProperty("row", i);
                button.putClientProperty("col", j);
                button.putClientProperty("clicked", false);
                
                // Adds the button to the ArrayList and the Panel then increments
                buttons.add(button);
                buttonPanel.add(buttons.get(k));
                k++;
            }
        }
        
        // Adding this JPanel to the frame
        frame.add(buttonPanel, borderLayout.WEST);
    }
    
    private void createGameInfoPanel()
    {
        // Creating the text area that holds the words entered
        wordsEntered = new JTextArea();
        font = new Font("Times Roman", Font.PLAIN, 12);
        wordsEntered.setFont(font);
        wordsEntered.setLineWrap(true);
        scrollPane = new JScrollPane(wordsEntered);
        
        // Setting up the clock for the game
        gameClock = new JLabel();
        Font timeFont = new Font("Time Roman", Font.PLAIN, 45);
        TitledBorder clockBorder = BorderFactory.createTitledBorder("Time Left");
        gameClock.setText("      " + "3:00" + "      ");
        gameClock.setFont(timeFont);
        gameClock.setBorder(clockBorder);
        gameClock.setPreferredSize(new Dimension(300, 100));
        
        // Creating the Shake Dice button
        shakeDice = new JButton("Shake Dice");
        
        // Adding to the JPanel
        gameInfo.add(scrollPane);
        gameInfo.add(gameClock);
        gameInfo.add(shakeDice);
        
        // Adding ActionListener
        shakeDice.addActionListener(this);
        
        // Adding this JPanel to the frame
        frame.add(gameInfo, borderLayout.CENTER);
    }
    
    private void createUserInputPanel()
    {
        // Creating current word JLabel
        currentWord = new JLabel("");
        TitledBorder wordBorder = BorderFactory.createTitledBorder("Current Word");
        currentWord.setBorder(wordBorder);
        currentWord.setPreferredSize(new Dimension(300, 50));
        
        // Creating the submit word button
        submit = new JButton("Submit");
        
        
        // Creating the score JLabel
        currentScore = new JLabel("    " + gameScore);
        TitledBorder scoreBorder = BorderFactory.createTitledBorder("Score");
        currentScore.setBorder(scoreBorder);
        currentScore.setPreferredSize(new Dimension(150, 50));
        
        // Adding the labels to the User Input JPanel
        userInput.add(currentWord);
        userInput.add(submit);
        userInput.add(currentScore); 
        
        //Disable submit on start
        submit.setEnabled(false);
        
        // Adding ActionListener
        submit.addActionListener(this);
        
        // Adding this JPanel to the frame
        frame.add(userInput, borderLayout.SOUTH);
    }
    
    private int addScore(int wordLength)
    {
        int points = 0;
        
        if(wordLength < 3)
            points = 0;
        else if(wordLength == 3)
            points = 1;
        else if(wordLength == 4)
            points = 2;
        else if(wordLength == 5)
            points = 3;
        else if(wordLength == 6)
            points = 4;
        else if(wordLength == 7)
            points = 5;
        else if(wordLength >= 8)
            points = 6;
        
        return points;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == newGame)
        {
            // Letter buttons re-enabled
            for(int i = 0; i < buttons.size(); i++)
                buttons.get(i).setEnabled(true);

            // Clean the JTextArea
            wordsEntered.setText("");
            scrollPane.setViewportView(wordsEntered);
            
            // Resetting the score
            gameScore = 0;
            
            // Resetting the clock
            stopTimer();
            gameClock.setText("      " + "3:00" + "      ");
            
            // Enabling shake dice
            shakeDice.setEnabled(true);
            
            // Clearing valid answers
            validAnswers.clear();
            
            // Score is reset
            gameScore = 0;
            currentScore.setText("" + gameScore);
            
        }
        
        else if(e.getSource() == exit)
        {
            System.exit(0);
        }
        else if(e.getSource() == shakeDice)
        {
            // Create a new button panel
            buttonPanel.removeAll();
            buttons.clear();
            createButtonPanel();
            
            // Enable submit
            submit.setEnabled(true);
            
            // Clean current word JLabel
            currentWord.setText("");
            
            // Clean the JTextArea
            wordsEntered.setText("");
            
            // Resetting the score
            gameScore = 0;
            
            // Starting the clock
            createTimer();
            
            // Disabling shake dice button
            shakeDice.setEnabled(false);
            
            // Clearing valid answers
            validAnswers.clear();
            
            // Score is reset
            gameScore = 0;
            currentScore.setText("" + gameScore);
        }
        else if(e.getSource() == submit)
        {
            // String entry is set equal to word being submitted
            String entry = currentWord.getText();
            
            //Flag is set equal to false
            flag = false;
            
            if(entry.length() < 3)
            {
                JOptionPane.showMessageDialog(null, "Word is too short to receive score.", "Invalid Word", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                // Searches through the dictionary
                for(int i = 0; i < dictionary.size(); i++)
                {
                    // If the word is found
                    if(entry.toUpperCase().equals(dictionary.get(i).toString().toUpperCase()))
                    { 
                        // If there are no 
                        if(validAnswers.isEmpty())
                        {
                            // entry is added to validAnswers array
                            validAnswers.add(entry);

                            // Score is added based upon word length 
                            gameScore += addScore(entry.length());
                            currentScore.setText("" + gameScore);

                            // Word is added to the list of words
                            wordsEntered.append(entry + "\n"); 
                        }
                        else
                        {
                            WordValidation(entry);
                        }

                        // Exit the loop
                        break;
                    }
                }
            }
            
            
            // Flag is reset to false
            flag = false;
            
            // Current word is reset
            currentWord.setText("");
            
            // Buttons are re-enabled
            for(JButton button : buttons)
            {
                button.setEnabled(true);
                button.putClientProperty("clicked", false);
            }
        }
    }
    
    private void WordValidation(String word)
    {
        for(String string : validAnswers)
        {
            if(word.equals(string))
                flag = true;
        }
                        
        if(flag == true)
            JOptionPane.showMessageDialog(null, "Word has been used and is no longer valid.", "Invalid Word", JOptionPane.ERROR_MESSAGE);
        else
        {
             // entry is added to validAnswers array
            validAnswers.add(word);

            // Score is added based upon word length 
            gameScore += addScore(word.length());
            currentScore.setText("" + gameScore);

             // Word is added to the list of words
            wordsEntered.append(word + "\n"); 
        }
    }
    
    private void EndGame()
    {
        // Letting player know the computer is comparing
        JOptionPane.showMessageDialog(null, "The computer comparing its guesses.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        
        // Clean the JTextArea
        wordsEntered.setText("");
        
        // ArrayList of words that are removed
        ArrayList<String> wordsRemoved = new ArrayList<>();
        String wordList = "";
        
        // Determining the number of words guessed
        Random random = new Random();
        int numberGuessed = random.nextInt(validAnswers.size());
        
        //Flag variables
        int count = 0;
        isGuessed = new boolean[validAnswers.size()];
        
        // Determining which words are guessed
        while(count < numberGuessed)
        {
            int wordToStrike = random.nextInt(validAnswers.size());
            
            if(isGuessed[wordToStrike] == false)
            {
                isGuessed[wordToStrike] = true;
                count++;
            }
        }
        
        // Creating style document
        wordPane = new JTextPane();
        StyledDocument doc = wordPane.getStyledDocument();
        Style marked = doc.addStyle("marked", null);
        Style unmarked = doc.addStyle("unmarked", marked);
        StyleConstants.setFontFamily(marked, "Times Roman");
        StyleConstants.setFontSize(marked, 12);
        StyleConstants.setForeground(marked, Color.BLACK);
        StyleConstants.setStrikeThrough(marked, true);
        StyleConstants.setStrikeThrough(unmarked, false);
        
        
        // Creating JTextPane that displays marked and unmarked words
        for(int i = 0; i < validAnswers.size(); i ++)
        {
            if(isGuessed[i])
            {
                try
                {
                    doc.insertString(doc.getLength(), validAnswers.get(i) + "\n", marked);
                }
                catch(Exception e)
                {
                    System.out.print(e.toString());
                }
                
                gameScore -= addScore(validAnswers.get(i).length());
                wordsRemoved.add(validAnswers.get(i));
            }
            else 
            {
                try
                {
                    doc.insertString(doc.getLength(), validAnswers.get(i) + "\n", unmarked);
                }
                catch(Exception e)
                {
                    System.out.print(e.toString());
                }
            }
        }
        
        // Updating viewable area
        scrollPane.setViewportView(wordPane);
        
        // Create a String containing the struck through words
        for(int i = 0; i < wordsRemoved.size(); i++)
        {
            if(i == (wordsRemoved.size() - 1))
                wordList = wordList + wordsRemoved.get(i);
            else
                wordList = wordList + wordsRemoved.get(i) + ", ";
        }
        
        //Updating score
        currentScore.setText("" + gameScore);
        
        // Current word is reset
        currentWord.setText("");
        
        // Disabling submit
        submit.setEnabled(false);
        
        // JOptionPane showing the result
        if(wordsRemoved.isEmpty())
            JOptionPane.showMessageDialog(null, "The computer did not match any words.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(null, "The computer guessed " + wordList + ".", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    } 
    
    private void createTimer()
    {
        // Time Limit in seconds
        timeLeft = 180;
        
        // Creating the new timer and starting
        timer = new Timer(1000, new TimerListener());
        timer.start();
    }
    
    private void stopTimer()
    {
        timer.stop();
    }
    
    private class TimerListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent t)
        {
            // Time keeping variables
            timeMinutes = timeLeft / 60;
            timeSeconds = timeLeft % 60;
            
            // Keep reducing time if timeLeft is greater than zero
            if(timeLeft > 0) 
                timeLeft = timeLeft - 1;
            else // Otherwise
            {
                // Stop the timer
                timer.stop();
                
                // All buttons are disabled
                for(JButton button : buttons)
                    button.setEnabled(false);
                
                // EndGame method is called
                EndGame();
            }
            
            // Formatting the time display
            if(timeSeconds < 10)
                gameClock.setText("      " + timeMinutes + ":0" + timeSeconds + "      "); 
            else
                gameClock.setText("      " + timeMinutes + ":" + timeSeconds + "      "); 
        }
    }
    
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent b)
        {
            // Word Variables
            JButton button = (JButton)b.getSource();
            String letter = button.getText();
            String word = currentWord.getText();
            
            // letter is added to word and currentWord is updated
            word += letter;
            currentWord.setText(word);
            currentWord.repaint();
            
            // Row and Column comparrisson variables
            int k = 0, compRow, compCol, distance;
            int row = (int)button.getClientProperty("row");
            int col = (int)button.getClientProperty("col");
            
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    // Determining the distance between letter clicked and the others
                    compRow = (int)buttons.get(k).getClientProperty("row");
                    compCol = (int)buttons.get(k).getClientProperty("col");
                    distance = (int)Math.sqrt(Math.pow(row - compRow, 2) + Math.pow(col - compCol, 2));
                    
                    // Button is set enabled based upon distance from button clicked
                    if(distance == 0)
                    {
                        button.setEnabled(false);
                        buttons.get(k).putClientProperty("clicked", true);
                    }
                    else if(distance == 1)
                        buttons.get(k).setEnabled(true);
                    else 
                        buttons.get(k).setEnabled(false);
                        
                    // Checks if the button has been clicked
                    if((boolean)buttons.get(k).getClientProperty("clicked") == true)
                        buttons.get(k).setEnabled(false);
                    
                    k++;
                }  
            }
        }
    }
}
