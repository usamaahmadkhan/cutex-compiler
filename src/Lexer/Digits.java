package Lexer;

public class Digits
{
	private final String[] digits= {"0","1","2","3","4","5","6","7","8","9"};
	//private final char[] extra = {'`','~','!','@',''}

	private Tree at;

	//If no argumnests are passed means a variable state is to be checked
	public Digits()
	{
		at = new  Tree();

		for(int x =0; x<digits.length; x++)
		  at.insert(digits[x]);
	}

	// If two arguments are passed then it means an integer is to be checked
	public Digits(String plus,String minus)   
	{
		at = new  Tree();

		for(int x =0; x<digits.length; x++)
		  at.insert(digits[x]);

		at.insert(plus);
		at.insert(minus);
	}


	//If three arguments are passed then it means a rational is to be checked
	public Digits(String plus,String minus,String decimal)   
	{
		at = new  Tree();

		for(int x =0; x<digits.length; x++)
		  at.insert(digits[x]);

		at.insert(plus);
		at.insert(minus);
		at.insert(decimal);
	}


	// search for variables
	public boolean isSecondState(String c)
	{
		return at.search(c);
	}

	// search for integers / rationals

	public boolean search(String s)
	{
		return at.search(s);
	}


	public void displayTree()
	{
		at.display(at.getRoot());
	}
}