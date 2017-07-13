public class Card {
	private String color,value;
	//One constructor for numbered cards
	//value: value of card. Can be 0-9/skip/draw2/reverse
	//Color: color of card. Can be blue/red/yellow/green
	public Card(String color, String value){
		this.color=color;
		this.value=value;
	}

	//One constructor for wild cards
	public Card(String value){
		this.value=value;
		this.color="";
	}
	
	public String getColor(){
		return color.toUpperCase();
	}

	public String getValue(){
		return value.toUpperCase();
	}
	
	//If a colored card it will return color+" "+value
	//Ex. "red 9"
	//Else it returns the value
	//Ex "wildDraw4"
	public String toString(){
		if(color=="")
			return value.toUpperCase();
		else
			return color.toUpperCase()+" "+value.toUpperCase();
	}
	
	//If needed, one can convert a string to a card
	public static Card convertStringToCard(String cardString){
		if(cardString.contains(" ")){
			String color = cardString.substring(0, cardString.indexOf(" ")); 
			String value = cardString.substring(cardString.indexOf(" ")+1,cardString.length());
			return new Card(color,value);
		}else{
			return new Card(cardString);
		}
	}
	
	public void setColorOfWild(String choosenColor){
		color= choosenColor;
	}
	
	//For testing purposes
	public static void main(String[] args){
		System.out.println("Testing Card Class:");
		Card red8 = new Card("red","8");
		System.out.println("Created a new card: "+red8.toString());
		System.out.println("Get color: "+red8.getColor());
		System.out.println("Get value: "+red8.getValue());
		
		Card wild4 = new Card("WILD_DRAW4");
		System.out.println("Created a new card: "+wild4.toString());
		System.out.println("Get color: "+wild4.getColor());
		System.out.println("Get value: "+wild4.getValue());

	}

}
