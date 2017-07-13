import java.util.ArrayList;

public class CardProb {
	private Card cardType;
	private double maxProbabilty;
	private double currentProbabilty;
	private int maxCards;
	private int cardsUnknown;
	Deck knownDeck;
	ArrayList<Card> knownPile;
	
	CardProb(Card c,int numberOfCardTypeInDeck,int deckSize){
		cardType=c;
		cardsUnknown=numberOfCardTypeInDeck;
		maxCards = deckSize;
		knownPile=new ArrayList<Card>();
	}
	
	public void calculateProabilty(ArrayList<Card> hand){
		calculateProbabiltyFromHand(hand);
		calculateProbabiltyFromDeck();
		calculateProbabiltyFromPile();	
	}
	
	public double getProbabilty(){
		return currentProbabilty;
	}
	
	public void cardDiscovered(){
		cardsUnknown--;
		if(maxCards-cardsUnknown!=0)
			currentProbabilty = 1/(deckSize-cardsUnknown);
		else
			currentProbabilty=0;
	}
	
	public void undoPlay(){
		cardsUnknown++;
		if(maxCards-cardsUnknown!=0)
			currentProbabilty = 1/(maxCards-cardsUnknown);
		else
			currentProbabilty=0;
	}
	
	public void addCardToKnownPile(Card c){
		knownPile.add(c);
	}
	
	public Card getCard(){
		return cardType;
	}
	private void calculateProbabiltyFromHand(ArrayList<Card> hand){
		for(Card card: hand){
			if(cardType.equals(card)){
				cardDiscovered();
			}
		}
	}
	
	//To-Do
	private void calculateProbabiltyFromDeck(){
		
	}
	
	private void calculateProbabiltyFromPile(){
		for(Card card: knownPile){
			if(cardType.equals(card)){
				cardDiscovered();
			}
		}
	}
}
