package Lexer;

public class DeterministicFiniteAutomata
{
	private Alphabets fs ;
	private Digits ss ;
	
	private String word;  

	public DeterministicFiniteAutomata()
	{

		fs = new Alphabets();
		ss = new  Digits();

	}

	public DeterministicFiniteAutomata(String s)
	{
		if(s.equals("Digit"))
			ss = new Digits("+","-");
		else
			ss = new Digits("+","-",".");

	}


	public Alphabets getAlphabetTree()
	{
		return fs;
	}

	public Digits getDigits()
	{
		return ss;
	}
	
	public boolean testValidVariable(String word)
	{
		boolean result = true;
		String literals = word;

		int x=0;
		while(x< literals.length())
		{
			if(x==0)				   //first char must starts from starting state (First State) 
			{
				if(!fs.isFirstState(Character.toString(literals.charAt(x))))		// is it not the first state and also the first char? 
				{	
					result= false;		// invalid variable
					break;
				}
			}else
			{ 
				if(!fs.isFirstState(Character.toString(literals.charAt(x))))
				 {
					if(!ss.isSecondState(Character.toString(literals.charAt(x))))   //Not the first state & not the second state ? zahir si baat h invalid h :-)
					{
						result = false;
						break;
					}

				 }

			}

			x++;				//test passes on char at x , moving onto next char
		}

			return result;
	}

	public boolean testValidInteger(String number)
	{
		boolean result = true;
		for(int x=0; x< number.length(); x++) 			//Testing if every char is in digits tree 
			{	
				if(!ss.search(Character.toString(number.charAt(x))))
					{
						result =false;
						break;
					}
			}
		return result;
	}

	public boolean testValidRational(String number)
	{

		boolean result = true;
		boolean decimal = false;

		for(int x=0; x< number.length(); x++)
			{	
				if(Character.toString(number.charAt(x)).equals("."))
					if(decimal ==false)
					  	decimal= true;
					else
						{	decimal =false; 
							break;
						}

				if(!ss.search(Character.toString(number.charAt(x))))
					{
						result =false;
						break;
					}
			}

		return (result && decimal) ;
	}
}