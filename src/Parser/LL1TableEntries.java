package Parser;

import java.util.ArrayList;

public class LL1TableEntries
{
	public String nonTerminal;
	public String terminal;
	public ArrayList<String> entry;


	public LL1TableEntries(String a ,String b, ArrayList<String> c)
	{
		this.nonTerminal = a;
		this.terminal = b;
		this.entry = c;
	}

}