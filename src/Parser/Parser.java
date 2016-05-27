package Parser;

import java.util.Hashtable;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class Parser
{
	private Grammar grammar;

	private BufferedReader br;

	private Hashtable<String , ArrayList<String>> firstSets;
	private Hashtable<String , ArrayList<String>> followSets;

	private ArrayList<LL1TableEntries> LL1Table;

	private Stack<String> parsingStack;

	public Parser(String sourcefile) throws IOException, FileNotFoundException
	{
		grammar = new Grammar();

				grammar.addProduction("Prog","DOT PROG_START stmt_list PROG_END");

  				grammar.addProduction("stmt_list","DOT stmt SEMI_COLON stmt_list");
  				grammar.addProduction("stmt_list","lambda");

  				grammar.addProduction("stmt","ACQUIRE ID");
  				grammar.addProduction("stmt","DISPLAY value");
  				grammar.addProduction("stmt","ID EQUAL_TO value");
  				grammar.addProduction("stmt","var ID COLON DATA_TYPE QUESTION");
          grammar.addProduction("stmt","loop ID COLON value COMMA value stmt_list");//needs to confirm with Ali
  				grammar.addProduction("stmt","function_def");
          grammar.addProduction("stmt","function_call");
          grammar.addProduction("stmt","fi BRACKET_START condition BRACKET_END stmt_list");//needs to confirm with Ali   
          grammar.addProduction("stmt","elif BRACKET_START condition BRACKET_END stmt_list");//needs to confirm with Ali

  				grammar.addProduction("value","ID");
  				grammar.addProduction("value","DIGIT");
  				grammar.addProduction("value","RATIONAL");
  				grammar.addProduction("value","function_call");

  			
  				grammar.addProduction("DATA_TYPE","DIGIT");
  				grammar.addProduction("DATA_TYPE","RATIONAL");


  				grammar.addProduction("condition","E op E");
  				
  				grammar.addProduction("op","OR");
  				grammar.addProduction("op","AND");
  				grammar.addProduction("op","EQUAL_TO");
  				grammar.addProduction("op","LESS_THAN");
  				grammar.addProduction("op","GREATER_THAN");
  				grammar.addProduction("op","LESS_THAN_OR_EQUALTO");
  				grammar.addProduction("op","GREATER_THAN_OR_EQUALTO");

  				grammar.addProduction("E","T E'");
  
  				grammar.addProduction("E'","PLUS T E'");
  				grammar.addProduction("E'","MINUS T E'");
  				grammar.addProduction("E'","lambda");

  				grammar.addProduction("T","F T'");
  				grammar.addProduction("T'","MULTIPLY F T'");
  				grammar.addProduction("T'","DIVIDE F T'");
  				grammar.addProduction("T'","lambda");
  
  				grammar.addProduction("F","value");
          grammar.addProduction("F","BRACKET_START E BRACKET_END");

          grammar.addProduction("function_call","BRACKET_START value_list BRACKET_END ID");
          grammar.addProduction("function_def","DATA_TYPE ID BRACKET_START arg_list BRACKET_END BRACKET_START stmt_list BRACKET_END");

          grammar.addProduction("value_list","value value_list");
          grammar.addProduction("value_list","lambda");
                
          grammar.addProduction("arg_list","DATA_TYPE ID arg_list");
          grammar.addProduction("arg_list","lambda");


		LL1Table = new ArrayList<LL1TableEntries>();

		parsingStack = new Stack<String>();

		generateLL1Table();

		br = new BufferedReader(new FileReader(sourcefile));

		/*DisplayFirstSets();
		DisplayFollowSets();*/
	}

	protected void generateLL1Table()
	{
		firstSets = grammar.CalculateFirstSets();
		followSets = grammar.CalculateFollowSets();
		
	    generateTable();
	}

	private void generateTable()
	{
		//A-> a

		for(Productions product: grammar.getProductions())
		{
			ArrayList<String> firstOf = grammar.first(product.right);
			ArrayList<String> followOf = grammar.follow(product.left);

			for (String firstElement : firstOf ) 
			{
				LL1Table.add(new LL1TableEntries(product.left, firstElement , product.right));
				
			}

          if(firstOf.contains("lambda"))
          {
          	for(String followElement: followOf)
          	LL1Table.add(new LL1TableEntries(product.left, followElement , product.right));

          	if(followOf.contains("$"))
          		LL1Table.add(new LL1TableEntries(product.left, "$" , product.right));
          
          }
		}
	}

	protected void DisplayFirstSets()
    {
    	System.out.println("Displaying First Sets: ");
 		 Enumeration non = firstSets.keys();

   	 	while(non.hasMoreElements())
   	  	{
   	 		 String str = (String) non.nextElement();
         	ArrayList<String> d = firstSets.get(str);

         	System.out.print(str+ " = { ");	

         	for (String s : d) 
         	{
         		System.out.print(s+" ");
         	}
         	System.out.print("}");
         	System.out.println();
   	  	}
    }

	protected void DisplayFollowSets()
 	{

    	System.out.println("Displaying Follow Sets: ");
 		Enumeration non = followSets.keys();

   	 	while(non.hasMoreElements())
   	  	{
   	 		 String str = (String) non.nextElement();
         	ArrayList<String> d = followSets.get(str);

         	System.out.print(str+ " = { ");	

         	for (String s : d) 
         	{
         		System.out.print(s+" ");
         	}
         	System.out.print("}");
         	System.out.println();
   	  	}
 	}	


 	public boolean parse() throws IOException
 	{ 

 		boolean result = true;
 		Stack<String> sourcestack = new Stack<String>();
 		StringBuilder sb = new StringBuilder();
 		String source = br.readLine();

 	while(source != null)
 	{
 		sb.append(source);
 		sb.append(" ");
 		source = br.readLine();
 	}
      sb.append(" $");
 	    source = sb.toString();
 	    br.close();

 		StringTokenizer s = new StringTokenizer(source," ");
 		
 		while(s.hasMoreTokens())
 		{
 			sourcestack.push(s.nextToken().trim());
 		}

 		Collections.reverse(sourcestack);


 		parsingStack.push("$");
 		parsingStack.push(grammar.getProductions().get(0).left);

 		System.out.println("EntryingLoop");

 		while( !(parsingStack.peek().equals("$")  && sourcestack.peek().equals("$") ))
 		{
 			/*System.out.println("Source Stack: "+sourcestack);
			System.out.println("parsing Stack: "+parsingStack);*/

 			if( parsingStack.peek().equals(sourcestack.peek()))
 			{
 				//System.out.println("Popping "+parsingStack.peek());
 				parsingStack.pop();
 				sourcestack.pop();

 			}else
 			{
 				for (LL1TableEntries e : LL1Table ) 
 				{

 					if(e.nonTerminal.equals(parsingStack.peek()) && e.terminal.equals(sourcestack.peek()))
 					{
 						parsingStack.pop();

 						if(!e.entry.isEmpty())       //error Checking
 						{
 							ArrayList<String> iter = new ArrayList<String>();
              iter.addAll(e.entry);
 							Collections.reverse(iter);

 							for (String str : iter) 
 							{
                if(str.equals("lambda"))
                  continue;
                else
                  {
 								     //System.out.println("Pushing "+str);
 								     parsingStack.push(str);
                  }
 							}	

 						/*	System.out.println("Parsing Stack: "+parsingStack);
 							System.out.println("Source Stack: "+sourcestack);*/
 							
 						}else
 						{
 							System.out.println("Accessing an empty entry in LL1Table");
 							result = false;
 							break;	
 						}
 					}	
 				}
 			}
 		}
 	return result;
 }


 	public void displayLL1ParseTable()
 	{
 		for (LL1TableEntries e : LL1Table ) 
 		{
 			System.out.println(e.nonTerminal +" "+ e.terminal+" contains "+ e.entry);
 		}
 	}

}