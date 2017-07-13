import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Pascal
 * @author Alexander 
 * @author Zachary
 * @author Christopher
 * @author Kevin
 * @version 2.0
 * @param <T> the class (or an ancestor class) of objects to be stored in the list
 */
public class Game {
	int turn;	//Determines who turn it is
	ArrayList<Player> players;	//An arraylist of all players/their hands
	int indexOfCurrentPlayer;	//The index of the player who is currently thinking of a move
	Deck deck;	//The deck of cards
	Pile pile;	//The pile of played cards
	int direction;	//Determines the current direction. If 1 clockwise, if -1 counterclockwise
	private Scanner input;	//Used for user input
	private Deck previousShuffledDeck; //Only used for AI to undo a move.
	public Game(ArrayList<Player> players){
		turn = 0;
		this.players=players;
		indexOfCurrentPlayer = 0;
		deck = new Deck();
		deck.shuffle();
		pile = new Pile();
		direction = 1;
	}
	
	
	public void playGame(){
		//Initialize variables
		Scanner input = new Scanner(System.in);
		String userInput="    ";
		//Adds cards to each player
		giveEachPlayer7Cards();
		
		//Adds 1 card to the pile
		pile.add(deck.drawCard());
		
		//TUTORIAL
		System.out.println("Is this your first time playing UNO™?(Y/N)");
		userInput = input.nextLine();
		if(userInput.equalsIgnoreCase("Y")){
			System.out.println("Goal: Be the first person to play all their cards. ");
			System.out.println("A card may be played if it the same color or have the same value(1-9,draw2,reverse,skip) has the top card.\nIf you cannot play a card, cards will be placed in your hand until you can play one.");
			System.out.println("Special cards impact other players.");
			System.out.println("Wild: Can be played on any color or value. When played, the player may change the current color");
			System.out.println("Wild_Draw4: Same rules as wild but next player must draw 4 cards");
			System.out.println("Skip: skips the next persons turn");
			System.out.println("Reverse: reverses the order of who plays next.");
			
			System.out.println("To play the game on console, type the card color, then the card value to play that card.");
			System.out.println("Example: GREEN DRAW2");
			System.out.println("Once you are done reading the rules, press enter to start the game.");
			userInput = input.nextLine();
		}
		
		//ACTUAL GAME
		System.out.println("==============================================");

		gameloop:
		while(players.size()>1){
			//If there are no more cards in the deck. Take the cards from the pile and add it back to the deck
			if(deck.isEmpty())
				addCardsBackToDeck();
			
			//USER CHOOSES A CARD TO PLAY
			//======================================================================================
			System.out.println(players.get(indexOfCurrentPlayer).getName()+"'s Turn"); // [player]'s turn
			System.out.println("==============================================");
			System.out.println("TOP CARD: "+pile.toString());	// TOP CARD: [top card of pile]
			
			addCardUntilPlayerCanMakeMove();	// If player cannot play card, add cards until they can
			System.out.println("Your current cards: "+players.get(indexOfCurrentPlayer).toString()); //Print player's cards
			System.out.println(players.get(indexOfCurrentPlayer).printCardsGUI()); //Print player's cards

			Card userCardChoice = null;
			//Ask for user input until they give a card from their deck
			do{
				//Asks user to choose a card
				System.out.print("User input: ");
				userInput = input.nextLine().trim();
				
				//Checks if user wants to terminate game
				if(userInput.equalsIgnoreCase("terminate")){
					break gameloop;
				}else if(userInput.equalsIgnoreCase("look at other players")){
					seeHowManyCardsEachPlayerHas();
				}else if(userInput.equalsIgnoreCase("look at deck")){
					
				}
				
				userCardChoice =Card.convertStringToCard(userInput);	//Converts user string to card
				//While player does not play a card in his/her hand and does not play a valid card, continue through the loop
			}while(!(players.get(indexOfCurrentPlayer).contains(userCardChoice)&&pile.validAction(userCardChoice)));
			playCard(userInput);
			
			//Checks for game over
			if(getCurrentPlayersDeckSize()==0){
				System.out.println(players.get(indexOfCurrentPlayer).getName()+" has won the game!");
				System.out.println("Continue Y/N?:" );
				userInput = input.nextLine();
				if(userInput.equalsIgnoreCase("Y"))
					players.remove(indexOfCurrentPlayer);
				else
					break;
			}
			
			nextTurn();	//changes currentplayerindex

			System.out.println("==============================================");
			
		}
		System.out.println("Game is over.");
	}
	
	public static void main(String[] args){
		//Initializes players
		ArrayList<Player> playersForTestGame = new ArrayList<Player>();
		Player pascal = new Player("Pascal");
		playersForTestGame.add(pascal);
		Player alexander = new Player("Alexander");
		playersForTestGame.add(alexander);
		Player zachary = new Player("Zachary");
		playersForTestGame.add(zachary);
		Player kevin = new Player("Kevin");
		playersForTestGame.add(kevin);
		
		//Initializes Game
		Game newGame = new Game(playersForTestGame);
		newGame.playGame();	
	}
	
	//Used for AI
	public int [] getNumberOfCardsInEachHand(){
		int [] handsize= new int[players.size()];
		for(int i=0;i<players.size();i++){
			handsize[i]=players.get(i).getHand().size();
		}
		return handsize;
	}
	
	//Plays a card from users hand into pile
	public void playCard(String cardString){
		Card playersCard = Card.convertStringToCard(cardString);
		//If the current player contains the desired card to played, then play it 
		//and if special card, do special card's action
		players.get(indexOfCurrentPlayer).removeCard(playersCard);
		switch(playersCard.getValue().toUpperCase()){
			case "SKIP":  nextTurn(); //The turn integer should always add/subtract 1 at the end of every turning.
				break;
			case "REVERSE": this.direction=-1;
				break;
			case "DRAW2": draw2();
				break;
			case "WILD_DRAW4": {
				wild_draw4(playersCard);
				return;
			}
			case "WILD": {
				wild(playersCard);
				return;
			}
		}
		pile.add(playersCard);
	}
	
	//Undos last card played and puts it back in player's hand
	public void undoLastMove(){
		//Get last played card
		int lastPlayerIndex = (turn-1)%players.size();
		Card lastCardPlayed = pile.removeCard();
		//Return card to player's hand
		players.get(lastPlayerIndex).addCard(lastCardPlayed);
		if(deck.isEmpty()){
			deck=previousShuffledDeck;
		}
	}
	
	//====================================================================================
	//PRIVATE METHODS
	//====================================================================================
	//Changes index to next player
	private void seeHowManyCardsEachPlayerHas(){
		for(Player p: players){
			System.out.println(p.getName()+":"+p.getHand().size()+" cards.");
		}
	}
	
	//Next player's turn
	private void nextTurn(){
		turn+=direction;
		if(turn==-1)
			turn=3;
		indexOfCurrentPlayer=turn%(players.size());
	}
	
	//Returns the index of the next player.
	private int getNextPlayerIndex(){
		int nextindex= turn;
		return (nextindex+1)%players.size();
	}
	
	//Continue to add cards to player's deck until he can make move
	private void addCardUntilPlayerCanMakeMove(){
		while(!canPlayerMakePlay()){
			players.get(indexOfCurrentPlayer).addCard(deck.drawCard());
		}
	}
	
	//returns true if player is able to play a card, false otherwise
	private boolean canPlayerMakePlay(){
		//If the pile is empty, the player can make any play he/she wants
		if(pile.isEmpty())
			return true;
		for(Card c: players.get(indexOfCurrentPlayer).getHand()){
			if(pile.validAction(c))
				return true;
		}
		return false;
	}
	
	//Is called when draw2 card is played
	private void draw2(){
		for(int i=0;i<2;i++)
			players.get(getNextPlayerIndex()).addCard(deck.drawCard());
	}
	
	//wild draw 4 does the following:
	//First it adds 4 to the next player's hand
	//Then it asks the current player to choose the next color
	//Then modifies the wild card to have a color
	//Then adds that card to the pile
	private void wild_draw4(Card c){
		for(int i=0;i<4;i++)
			players.get(getNextPlayerIndex()).addCard(deck.drawCard());
		String[] colors ={"RED","BLUE","GREEN","YELLOW"};
		input = new Scanner(System.in);
		String userInput="";
		boolean isAColor=false;
		do{
			System.out.print("Choose the new color: ");
			userInput = input.nextLine();
			userInput.trim();
			for(String color: colors)
				if(userInput.equalsIgnoreCase(color)){
					isAColor=true;
					break;
				}
		}while(!isAColor);
		c.setColorOfWild(userInput);
		pile.add(c);
	}
	
	//wild does the following:
	//First it asks the current player to choose the next color
	//Then modifies the wild card to have a color
	//Then adds that card to the pile
	private void wild(Card c){
		try{
			String[] colors ={"RED","BLUE","GREEN","YELLOW"};
			String userInput="";
			input = new Scanner(System.in);
			boolean isAColor=false;
			do{
				System.out.print("Choose the new color: ");
				userInput = input.nextLine();
				userInput.trim();
				for(String color: colors){
					if(userInput.equalsIgnoreCase(color)){
						isAColor=true;
						break;
					}
				}
			}while(!isAColor);
			c.setColorOfWild(userInput);
			pile.add(c);
		}catch(NullPointerException e){}
	}
	
	private int getCurrentPlayersDeckSize(){
		return players.get(indexOfCurrentPlayer).getHand().size();
	}
	
	public void giveEachPlayer7Cards(){
		for(int i=0;i<players.size();i++){
			for(int j=0;j<7;j++){
				players.get(i).addCard(deck.drawCard());
			}
		}
	}

	//Adds all the cards from the pile except the one on top back to the deck
	//Then shuffles deck
	public void addCardsBackToDeck(){
		previousShuffledDeck = deck;
		Card topCard = pile.removeCard();
		Deck newDeck = new Deck(pile);
		deck = newDeck;
		deck.shuffle();
		pile.add(topCard);
	}
	
}
