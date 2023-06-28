//Student Name(Student ID:):
//Akshit Negi(n01532132)
//Malika Gupta(n01581424)
//Oluwamayokun Badejo(n01520574)
//Sagar Nagesh Karkera(n01582945)
//
//Section: IGA
//java class for the game logic - instance creation
package hangman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class logic_code{

	public String gameWord; // The word to be guessed
    public StringBuilder guessedWord; // The word with guessed letters filled in
    public StringBuilder guessedList; // Letters already guessed by the player
    public int lives; // Counter for incorrect guesses
	
    //constructor for the new game object instance
    public logic_code() throws IOException 
    {
    	//selecting a random word from the text file using this function
    	gameWord = randomwordgenerator();
    	//comment for debugging
    	System.out.println(gameWord);
    	//using StringBuilder for the guessed word declaration for easy conversion to string and easy character access
        guessedWord = new StringBuilder();
    	//using StringBuilder for the list of guessed letters declaration for easy conversion to string and easy character access
        guessedList = new StringBuilder();
        //setting the lives as 8
        lives=8;
        //Initializing the guessed word as * for each letter in the game word
        for (int i = 0; i < gameWord.length(); i++) {
            guessedWord.append("*");
        }
    }
    
	//userGuess function that is triggered when the user makes a guess.
    //It takes the character entered in the guess text box
	//This function checks if the character guessed is a letter and then checks
	//if the guessed letter is in the game word. If it is in the word, 
	//it changes the guessed word characters to the guessed letters.
    public int userGuess(char letter) 
    {
		//boolean letterFound = false;
    	if(!guessedWord.toString().equals(gameWord) && lives != 0)//game is continuing
    	{
    		char guessedLetter = Character.toUpperCase(letter);
    		if(!guessedList.toString().contains(Character.toString(guessedLetter))) {
    			guessedList.append(guessedLetter);
    			System.out.println("GL: "+guessedList);
    			if(gameWord.contains(Character.toString(guessedLetter))) 
    			{
    				for(int i =0; i< gameWord.length();i++) 
    				{
    					if(gameWord.charAt(i)==guessedLetter)
    					{
    						guessedWord.setCharAt(i,guessedLetter);
    					}
    				}
    				return 1;
    				//set label to say 'good guess! try another.'
    			}
    			else 
    			{
    				lives--;
    				return 0;
    				//set label bad guess. life lost.
    			}
    		}
    		else 
    		{
    			//set label You've tried this letter already. Try another.
    			return 2;
    		}
    	}
    	else return 3;
    	//game over
    }
	
    
    //randomwordgenerator function reads the text file using buffered reader.
	//the text file has single space seperated capitalized words used in the game.
	//the next word is selected on random and returned by this function
	private static String randomwordgenerator() throws IOException
	{
		// Reading the file that contains the words and saving the words in an array
		String filePath = "C:\\Users\\malik\\eclipse-workspace\\Assignments-DBwJava\\src\\wordFile.txt";
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
	    String line = reader.readLine();
	    String[] words = line.split(" ");
		int listSize = words.length;
		
		//finding the number of words in the file to select a random word
		//System.out.println(listSize);
		Random rand = new Random();
		int int_random = rand.nextInt(listSize);
		reader.close();
		//selecting a random word
		//System.out.println(words[int_random]);
		return(words[int_random]);
	}
	
	
	//This function adds the word entered by the user after the game ends.
	//It takes the string entered by the user, checks if the string is an actual word made of only letters
	//it capitalizes the word and adds the word to the text file after a space
	public boolean addWord(String newWord) 
	{
		String filePath = "C:\\Users\\malik\\eclipse-workspace\\Assignments-DBwJava\\src\\wordFile.txt";
		if(newWord.matches("[a-zA-Z]+")) 
		{
	        try (FileWriter fileWriter = new FileWriter(filePath, true);
	            	BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) 
	            { 
	        		newWord = newWord.toUpperCase();
	            	bufferedWriter.write(" " + newWord);
	            	return true;
	                //System.out.println("Word added successfully.");
	            } 
	            catch (IOException e) 
	            {
	            	System.out.println("An error occurred while adding the word to the file: " + e.getMessage());
	            	return false;
	            }
		}
		else 
		{
			return false;
		}

	}
	
	
	//This method checks the game status and returns different integer values depending on the check conditions
	//If the word is guessed and there are lives remaining, the user wins and 1 is returned
	//Similarly, 2 is returned if all lives are lost
	//returns -1 if the game can continue
	public int checkGameStatus() {
		// TODO Auto-generated method stub
		if(guessedWord.toString().equals(gameWord) && lives != 0) {
			//game over-word guessed - lives remaining
			return 1;
		}
		if(lives == 0) {
			//game over-word not guessed
			return 2;
		}
		//game can continue
		return -1;
	}

}
