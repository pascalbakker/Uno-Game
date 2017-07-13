import java.util.ArrayList;
import java.util.Random;

public class AI extends Player{
	private String playerName;	//The name of the player
	private ArrayList<Card> hand;
	public AI(String name) {
		super(name);
		playerName=name;
		hand = new ArrayList<Card>();
	}

	public ArrayList<Card> possiblePlays(Pile p){
		ArrayList<Card> possibleCardsToPlay = new ArrayList<Card>();
		for(Card cardInHand: hand){
			if(p.validAction(cardInHand)){
				possibleCardsToPlay.add(cardInHand);
			}
		}
		return possibleCardsToPlay;
	}
	
	public Card getRandomCardChoice(Pile p){
		ArrayList<Card> possiblePlays = possiblePlays(p);
        Random randomGenerator = new Random();
        Card cardToPlay = possiblePlays.get(randomGenerator.nextInt(possiblePlays.size()-1));
        hand.remove(cardToPlay);
        return cardToPlay;
	}

}
