package Parser;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.List;


public class Grammar
{	

	private ArrayList<Productions> productions;
	private ArrayList<String> terminals;
	private ArrayList<String> nonTerminals;


 	public Grammar()
 	 {
 		productions = new ArrayList<Productions>(42);
 		terminals = new ArrayList<String>();
 		nonTerminals = new ArrayList<String>();	
   }

  public ArrayList<Productions> getProductions()
   {
      return this.productions;
   } 

   protected void addProduction(String left , String right)
   {
   		ArrayList<String> rightHandSide = new ArrayList<String>();

   		StringTokenizer st = new StringTokenizer(right," ");

   		while(st.hasMoreTokens())
   		{
   			rightHandSide.add(st.nextToken().trim());
   		}

   		productions.add(new Productions(left , rightHandSide));
   }

   public Productions getProduction(String lhs)
   {  
      return productions.get(productions.indexOf(lhs));
   }


   public boolean isNonTerminal(String s)
   {
     return nonTerminals.contains(s);
   }

   public boolean isTerminal(String s)
   {
     return terminals.contains(s);
   }

   private void CalculateTerminalsAndNonTerminals()
   {

   		for(Productions produc:productions)
   		{
   			if(!nonTerminals.contains(produc.left))
   			    nonTerminals.add(produc.left);
   		}

   		for (Productions produc: productions)
   		{
   			ArrayList<String> possibleTerminals = produc.right; 

   			for(String s: possibleTerminals)
   			{
   				if(!nonTerminals.contains(s))
   					terminals.add(s);
   			}

   		}
   }

   public Hashtable<String , ArrayList<String>> CalculateFirstSets()
   {

      Hashtable<String , ArrayList<String>> firstSets = new Hashtable<String , ArrayList<String>>();

    	CalculateTerminalsAndNonTerminals();
    
   	 for(String s: nonTerminals)
   	 	firstSets.put(s,first(s));

   	 for (String s : terminals )
   	 	firstSets.put(s,first(s));


     // DisplayFirstSets();

     return firstSets;
   }

   private ArrayList<String> first(String s)
   {
        ArrayList<String> result=new ArrayList<String>();
        if(terminals.contains(s)){
            result.add(s);
        }else{
            for(Productions p :productions){
                if(p.left.equals(s))
                {
                    ArrayList<String> f=first(p.right);
                    
                    for(String string:f)
                    {
                      if(!result.contains(string))
                      result.add(string);
                    }
                }
            }
        }
        return result;
   }

   protected ArrayList<String> first(ArrayList<String> s)
   {
        ArrayList<String> result= new ArrayList<String>(0);
            boolean addEpsilon=true;
            for(String string:s){
                ArrayList<String> f=first(string);
                for(String string1:f)
                {
                    if(!string1.equals("lambda"))                     
                    {    
                      if(!result.contains(string))
                        result.add(string1);
                    }
                }
                if(!f.contains("lambda"))
                {
                    addEpsilon=false;
                    break;
                }
                
            }
            if(addEpsilon)
                result.add("lambda");
        return result;
   }

   protected Hashtable<String , ArrayList<String>> CalculateFollowSets()
   {

     Hashtable<String , ArrayList<String>> followSets = new Hashtable<String , ArrayList<String>>();
   	 
     for(String s: nonTerminals)
   	 	followSets.put(s,follow(s));

   	  // DisplayFollowSets();
      return followSets;
   }
 

  protected ArrayList<String> follow(String nonterminal)
  {

      ArrayList<String> follows = new ArrayList<String>();

      if(nonterminal.equals(productions.get(0).left))
        follows.add("$");

      for(Productions p : productions)
      {
          if(p.right.contains(nonterminal))
          { 
            //A->
            if((p.right.indexOf(nonterminal) == p.right.size()-1) && (!p.left.equals(nonterminal)))
            {
              ArrayList<String> fo = follow(p.left);

              for (String se : fo ) 
              {
                if(!se.equals("lambda")&& !follows.contains(se))
                 follows.add(se); 
              }
            }

            if((p.right.lastIndexOf(nonterminal) == p.right.size()-1) && (!p.left.equals(nonterminal)))
            {
                ArrayList<String> fo = follow(p.left);

                for (String se : fo ) 
                {
                  if(!se.equals("lambda")&& !follows.contains(se))
                    follows.add(se); 
                }
            }

            if((p.right.indexOf(nonterminal)!= p.right.size()-1) && (!p.left.equals(nonterminal)))
              {
                ArrayList<String> b = new ArrayList<String>();
                for (int i = p.right.indexOf(nonterminal)+1 ; i<p.right.size() ; i++ ) 
                {
                  b.add(p.right.get(i)); 
                }

                for (String a : first(b)) 
                {
                  if(!a.equals("lambda") && !follows.contains(a))
                    follows.add(a);               
                }

                if(first(b).contains("lambda") && !p.right.equals(nonterminal))
                {
                  ArrayList<String> q = follow(p.left);

                  for (String c : q) 
                  {
                    if(!c.equals("lambda") && !follows.contains(c))
                      follows.add(c);
                  }
                }
              }


              if((p.right.lastIndexOf(nonterminal)!= p.right.size()-1) && (!p.left.equals(nonterminal)))
              {
                ArrayList<String> b = new ArrayList<String>();
                for (int i = p.right.indexOf(nonterminal)+1 ; i<p.right.size() ; i++ ) 
                {
                  b.add(p.right.get(i)); 
                }

                for (String a : first(b)) 
                {
                  if(!a.equals("lambda") && !follows.contains(a))
                    follows.add(a);               
                }

                if(first(b).contains("lambda") && !p.right.equals(nonterminal))
                {
                  ArrayList<String> q = follow(p.left);

                  for (String c : q) 
                  {
                    if(!c.equals("lambda") && !follows.contains(c))
                      follows.add(c);
                  }
                }
              }
            }
          }
          return follows;
        }
      }
