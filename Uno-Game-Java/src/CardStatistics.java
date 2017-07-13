import java.util.ArrayList;

public class CardStatistics {
	
	ArrayList<CardProb> cardTypes;
	public static final String[] COLORS = {"RED","BLUE","GREEN","YELLOW"};
	public static final String[] VALUES = {"0","1","2","3","4","5","6","7","8","9","SKIP","REVERSE","DRAW2","WILD","WILD_DRAW4"};
	
	public CardStatistics(){
		cardTypes=new ArrayList<CardProb>();
		for(String color: COLORS){ 
			for(String value: VALUES){
				if (value == "0") cardTypes.add(new CardProb(new Card(color, value),3,60));
				else if((value == "WILD") || (value == "WILD_DRAW4")) cardTypes.add(new CardProb(new Card(color, value),3,60));
				else{
					cardTypes.add(new CardProb(new Card(color, value),3,60));
					cardTypes.add(new CardProb(new Card(color, value),3,60));
				}
			}
		}
	}
	public void cardPlayed(Card c){
		for(CardProb cardp: cardTypes){
			if(c.equals(cardp.getCard())){
				cardTypes.get(cardTypes.indexOf(cardp)).cardDiscovered();
			}
		}
	}
}
