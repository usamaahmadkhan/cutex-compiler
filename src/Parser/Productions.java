package Parser;

import java.util.ArrayList;

public class Productions{
	
	protected String left;
	protected ArrayList<String> right;


	public Productions(String left, ArrayList<String> right)
	{	
		this.left =left;
		this.right = right;
	}
	
}