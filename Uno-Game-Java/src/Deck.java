import java.util.Collections;
import java.util.Stack;

public class Deck{
	private Stack<Card> deck;
	public static final String[] COLORS = {"RED","BLUE","GREEN","YELLOW"};
	public static final String[] VALUES = {"0","1","2","3","4","5","6","7","8","9","SKIP","REVERSE","DRAW2","WILD","WILD_DRAW4"};
	
	public Deck(Pile p){	//used for flipping over the pile and using it as a deck when deck is empty
		deck = new Stack<Card>();
		
		while(!(p.isEmpty())) deck.push(p.removeCard());	//takes every card from pile until none remain
	}
	
	public Deck(){	//used for building a new deck from scratch
		deck = new Stack<Card>();
		for(int i=0;i<3;i++){
			for(String color: COLORS){ 
				for(String value: VALUES){
					if (value == "0") deck.push(new Card(color, value));
					else if((value == "WILD") || (value == "WILD_DRAW4")) deck.push(new Card(value));
					else{
						deck.push(new Card(color,value));
						deck.push(new Card(color,value));
					}
				}
			}
		}
	}
	public Card drawCard(){
		if (!(deck.isEmpty())) return deck.pop();
		else return null;
	}
	
	public boolean isEmpty(){
		return deck.isEmpty();
	}
	
	public void shuffle(){
		Collections.shuffle(deck);
	}
	
	public void addCard(Card c){
		deck.push(c);
	}
}