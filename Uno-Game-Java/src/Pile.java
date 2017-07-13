import java.util.Stack;

public class Pile{
	private Stack<Card> pile = new Stack<Card>();
	
	public Card removeCard(){
		if (!pile.isEmpty()){
			return pile.pop();
		}
		else return null;
	}

	public boolean addToPile(Card c){
		try{
			pile.push(c);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	public void add(Card c){
		pile.push(c);
	}
	
	public boolean isEmpty(){
		return pile.isEmpty();
	}
	
	//Given the card, returns true if the card can be placed.
	public boolean validAction(Card c){
		if(c.getValue()==null||c.getValue()=="")
			return false;
		
		String currentColor = pile.peek().getColor();
		String currentValue = pile.peek().getValue();

		//If Card c has the same value as the top card
		if(c.getValue().equalsIgnoreCase(currentValue))
			return true;
		//If Card c is the same color as the top card
		else if(c.getColor().equalsIgnoreCase(currentColor))
			return true;
		//If card is wild
		else if((c.getValue().equalsIgnoreCase("WILD")||c.getValue().equalsIgnoreCase("WILD_DRAW4")))
			return true;
		
		//If the first card played is a wild, user may play any card
		else if((currentValue.equalsIgnoreCase("WILD")||currentValue.equalsIgnoreCase("WILD_DRAW4"))&&currentColor=="")
			return true;

		return false;
	}
	
	//Peeks at the top card in the deck
	public String toString(){
		if (!pile.isEmpty()){
			return pile.peek().toString();
		}
		else return null;
	}
	
	public Card peekAtTop(){
		if (!pile.isEmpty())
			return pile.peek();
		return null;
	}

}