package Lexer; 

public class Alphabets{
	

	private final String[] lower = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private final String[] upper = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private final String[] special= {"_","$"};

	public Tree at;

	public Alphabets()
	{
		at = new Tree();

		// Inserting all the data in tree at

		for(int x =0; x<lower.length; x++)
		  at.insert(lower[x]);
		
		for(int x =0; x<upper.length; x++)
		  at.insert(upper[x]);
		
		for(int x =0; x<special.length; x++)
		  at.insert(special[x]);

	}

	public boolean isFirstState(String c)      
	{ 
		return at.search(c);
	}

	public void displayTree()
	{
		at.display(at.getRoot());
	}
}