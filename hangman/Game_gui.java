//Student Name(Student ID:):
//Akshit Negi(n01532132)
//Malika Gupta(n01581424)
//Oluwamayokun Badejo(n01520574)
//Sagar Nagesh Karkera(n01582945)
//
//Section: IGA
//java class for the game gui
package hangman;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class Game_gui extends JFrame{
	
	//Variables and JFrame Object declarations
    private JTextField txt_name; // TEXT FIELD for name accepted 
    private JLabel lbl_name; // label for user's name
    private JButton btn_name; // button to enter user's name
	//North
    private JLabel lbl_welcome; // label for name display and welcome msg
    //East
    private JLabel lbl_guess; // label for guess
    private JTextField txt_guess;// text field for typing guess
    private JButton btn_guess;// button to enter guess
    //South
    private JLabel lbl_display_message;// labels for game info messages
    //Center
    private JLabel lbl_word_guess;// label for the word that is being guessed
    private JLabel lbl_guessed_list;// label to display the list of already guessed letters
    private JLabel lbl_lives;// label for lives remaining

    //game messages that have to be displayed when the game ends
    private String msg_won="Congratulations! You guessed the word correctly!\n It was: ";
    private String msg_lost="You couldn't guess the word. Better luck next time!\n It was: ";
    private String wordToAdd;
    //Creating an instance of the game's logic code
    private logic_code newGame;
    
    //[4]
	//This function is called each time a guess is made to refresh the status of the game
	//Based on the guess made by the user, different integer values are passed through this function
	//These values determine the lives remaining and if the word has been guessed
	private void refreshGame(int check) {
		// TODO Auto-generated method stub
		
		//String values that are used for display messages
		String word_add_success = "Word added successfully!!\n";
		String word_add_fail = "Word addition unsuccessful. You did not type a word.\n";
		
		//declaring a local option integer used for checking if the user wants to add a new word or not after game finishes
		int option;
		//set value of the guessing word
		lbl_word_guess.setText(newGame.guessedWord.toString());
		//set value of the lives remaining
		lbl_lives.setText("Remaining Lives or Incorrect Guesses: "+String.valueOf(newGame.lives));
		//set value of the list of letters already guessed
		lbl_guessed_list.setText("List of Letters guessed already: "+newGame.guessedList.toString());
		//correct letter guessed
		if(check==1) {
			lbl_display_message.setText("Letter Found. Good guess!! Try another one.");

		}
		//letter guessed not in the word
		if(check==0) {
			lbl_display_message.setText("Letter not Found. Missed guess!! One life lost.");
		}
		//letter guessed has already been guessed before
		if(check==2) {
			lbl_display_message.setText("You've tried this letter already. Try another.");
		}
		//if checkGameStatus returns a positive value, it means that the game is over and Option panes are displayed
		//else the game continues
        if (newGame.checkGameStatus() >0) {
        	//if the value returned by checkGameStatus is 1, the player won so success msg is displayed
        	//and new word addition option is displayed using JOptionPane input type dialog box
        	if(newGame.checkGameStatus() == 1)
        	{
        		wordToAdd = JOptionPane.showInputDialog(null, msg_won+newGame.gameWord+"\nEnter a new word to add to the game:");
        	}
        	//if the value returned by checkGameStatus is 2, the player lost so lost msg is displayed
        	//and new word addition option is displayed using JOptionPane input type dialog box
        	if(newGame.checkGameStatus() == 2)
        	{
        		wordToAdd = JOptionPane.showInputDialog(null, msg_lost+newGame.gameWord+"\nEnter a new word to add to the game:");
        	}
        	//if user enters a word and clicks on OK, 
//        	another dialog box appears on the JOptionPane confirm dialog box asking if the want to play again
//        	addWord function takes the user input and returns true if the word is added, 
//        	else returns false if the user enters garbage
        	if(newGame.addWord(wordToAdd)) 
        	{option = JOptionPane.showConfirmDialog(null, word_add_success+"Do you want to play again?", "Confirmation", JOptionPane.YES_NO_OPTION);}
        	else {option = JOptionPane.showConfirmDialog(null, word_add_fail+"Do you want to play again?", "Confirmation", JOptionPane.YES_NO_OPTION);}
        	
        	//if user wants to play again, all current resources are freed and
        	//new game instance is created for a new fresh game
        	if (option == JOptionPane.YES_OPTION) 
        	{
		        // User clicked "Yes"
		      	dispose();
		      	new Game_gui();
        	}
        	else 
        	{
        		// User clicked "No" or closed the dialog
        		dispose();
        		System.exit(0);
        	}
        	
        }
		
		
	}

	
	//[3]
	//This function is called after the user enters their name in the first GUI frame and the game begins
	public void startGame(String name) throws IOException 
	{
		setTitle("Hangman Game - Welcome "+name);
		getContentPane().removeAll();
		setLayout(new BorderLayout());
		//North content
		lbl_welcome = new JLabel("Welcome! Player Name: "+name);
		lbl_welcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_welcome.setFont(new Font("Arial", Font.BOLD, 35));
		add(lbl_welcome, BorderLayout.NORTH);
		
		//SOUTH content
		lbl_display_message =new JLabel("Messages from the game display here!");
		lbl_display_message.setFont(new Font("Arial", Font.BOLD, 24));
		lbl_display_message.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbl_display_message,BorderLayout.SOUTH);
		
		//EAST content
		JPanel east_guessPanel = new JPanel(new GridLayout(4,1));
		lbl_guess = new JLabel("Enter new guess letter: ");
		lbl_guess.setFont(new Font("Arial", Font.PLAIN, 24));
		
		txt_guess = new JTextField(10);
		
		btn_guess = new JButton("GUESS");
		btn_guess.setFont(new Font("Arial", Font.BOLD, 24));
        //assign the 'GUESS' button on the keyboard to the btn_name 
        getRootPane().setDefaultButton(btn_guess); 
		
		btn_guess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String guess = txt_guess.getText();
                if ( guess.length() > 0 && Character.isLetter(guess.charAt(0))) {
                    refreshGame(newGame.userGuess(guess.charAt(0)));
                }
                else
                {
                	lbl_display_message.setText("Invalid guess made! Please enter a letter for guess.");
                }
                txt_guess.setText("");
            }

        });
		
		east_guessPanel.add(lbl_guess);
		east_guessPanel.add(txt_guess);
		east_guessPanel.add(btn_guess);
		
		add(east_guessPanel,BorderLayout.EAST);
		
		//CENTRE content
		lbl_word_guess = new JLabel();
		lbl_word_guess.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_word_guess.setFont(new Font("Arial", Font.BOLD, 24));
		
		lbl_guessed_list = new JLabel("List of Letters guessed already: ");
		lbl_guessed_list.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_guessed_list.setFont(new Font("Arial", Font.PLAIN, 24));
		
		lbl_lives = new JLabel("Remaining Lives or Incorrect Guesses: ");
		lbl_lives.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_lives.setFont(new Font("Arial", Font.BOLD, 24));
		
		JPanel center_guessPanel = new JPanel(new GridLayout(3,1));

		center_guessPanel.add(lbl_guessed_list);
		center_guessPanel.add(lbl_word_guess);
		center_guessPanel.add(lbl_lives);

		add(center_guessPanel,BorderLayout.CENTER);

		
		pack();
		
        setLocationRelativeTo(null);
        setVisible(true);
        
        newGame = new logic_code();
        refreshGame(5);
	}

	//[2]
	//This is the constructor of this class
	//It loads the gui for the game
	public Game_gui() {
		// TODO Auto-generated method stub
		setTitle("Hangman Game - Welcome User");
		setDefaultCloseOperation(3);
		setSize(400, 300);
		setLayout(new FlowLayout());
		
		JPanel panel_intro = new JPanel(new GridLayout(3, 1));
		
		lbl_name = new JLabel("Hello User! Welcome to Hangman! Enter your name: ");
        panel_intro.add(lbl_name);
        
        txt_name = new JTextField();
        panel_intro.add(txt_name);

        //Create a button 
        btn_name = new JButton("Enter");
        panel_intro.add(btn_name);
        add(panel_intro);
        //assign the 'ENTER' button on the keyboard to the btn_name 
        getRootPane().setDefaultButton(btn_name);        
        
        btn_name.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txt_name.getText();
                if (!username.isEmpty()) {
                    try {
                        startGame(username);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    // Display an error message or perform other actions for empty input
                    JOptionPane.showMessageDialog(Game_gui.this, "Please enter a username.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        setVisible(true);
	}
	
	//[1]
	//Main method which calls the constructor of this game UI file
	public static void main(String args[]) 
	{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Game_gui();
            }
        });

	}
	
}