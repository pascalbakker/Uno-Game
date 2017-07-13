import java.util.ArrayList;

public class Player {
	private String playerName;	//The name of the player
	private ArrayList<Card> hand;
	
	public Player(String name){
		playerName=name;
		hand = new ArrayList<Card>();
	}
	
	public void removeCard(Card c){
		for(Card card: hand){
			if(card.getColor().equalsIgnoreCase(c.getColor())&&card.getValue().equalsIgnoreCase(c.getValue())){
				hand.remove(card);
				break;
			}
				
		}
	}
	
	public void addCard(Card c){
		hand.add(c);
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public String getName(){
		return playerName;
	}
	
	public String toString(){
		String list="";
		for(int i=0;i<hand.size();i++){
			list+=hand.get(i).toString();
			if(i!=hand.size()-1)
				list+=", ";
			if(list.length()>200)
				list+="\n";
		}
		return list;
	}
	
	public boolean hasUno(){
		return hand.size()==1;
	}
	
	public boolean contains(Card c){
		for(int i=0;i<hand.size();i++){
			if(hand.get(i).toString().equalsIgnoreCase(c.toString()))
				return true;
		}
		return false;
	}
	
	public String printCardsGUI(){
		if(hand.size()>15)
			return "";
		String guiCards="";
		//Add top layer
		for(int i=0;i<hand.size();i++){
			guiCards+=" ____________  ";
		}
		guiCards+="\n";
		
		for(int i=0;i<hand.size();i++){
			guiCards+="|            | ";
		}
		guiCards+="\n";
		//Prints the color of the card
		for(int i=0;i<hand.size();i++){
			guiCards+="|";
			int colorLength = hand.get(i).getColor().length();
			for(int j=0;j<(12-colorLength)/2;j++)
				guiCards+=" ";
			guiCards+=hand.get(i).getColor();
			for(int j=0;j<(12-colorLength)/2;j++)
				guiCards+=" ";
			if(colorLength%2==1)
				guiCards+=" ";
			
			guiCards+="| ";
		}
		guiCards+= "\n";
		for(int i=0;i<hand.size();i++){
			guiCards+="|            | ";
		}
		guiCards+="\n";
		
		//Prints the value of the card
		for(int i=0;i<hand.size();i++){
			guiCards+="|";
			int handSymbolLength = hand.get(i).getValue().length();
			
			
			for(int j=0;j<(12-handSymbolLength)/2+1;j++)
				guiCards+=" ";
			if(handSymbolLength%2==0)
				guiCards=guiCards.substring(0,guiCards.length()-1);


			guiCards+=hand.get(i).getValue();
			for(int j=0;j<(12-handSymbolLength)/2-1;j++)
				guiCards+=" ";
			if(handSymbolLength%2==0)
				guiCards+=" ";
			if(handSymbolLength%2==1)
				guiCards+=" ";
			guiCards+="| ";
		}
		guiCards+="\n";
		for(int i=0;i<hand.size();i++){
			guiCards+="|            | ";
		}		
		guiCards+="\n";
		for(int i=0;i<hand.size();i++){
			guiCards+="|            | ";
		}		
		guiCards+="\n";
		for(int i=0;i<hand.size();i++){
			guiCards+="|            | ";
		}
		guiCards+="\n";
		for(int i=0;i<hand.size();i++){
			guiCards+="|____________| ";
		}
		guiCards+="\n";
		return guiCards;
	}

	
	public static void main(String[] args){
		Player p = new Player("Pascal");
		System.out.println("Created new player: "+p.getName());
		System.out.println("Cards in hand: "+p.toString());
		System.out.println("Adding 2 cards.");
		Card red8 = new Card("red","8");
		Card wild4 = new Card("WILD_DRAW4");
		p.addCard(red8);
		p.addCard(wild4);
		p.addCard(red8);
		Card green = new Card("GREEN","REVERSE");
		p.addCard(green);
		System.out.println(p.printCardsGUI());

		System.out.println("Cards in hand: "+p.toString());
		System.out.println("Removing red8 card.");
		p.removeCard(red8);
		System.out.println("Cards in hand: "+p.toString());
		System.out.println("Does "+p.getName()+" have uno? "+p.hasUno());
	}
	
	
}
