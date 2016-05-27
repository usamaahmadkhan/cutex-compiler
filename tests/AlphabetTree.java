public class AlphabetTree
	{
		
	   private Node root;

	   // Construct an empty tree
	   public AlphabetTree()
	   {
	      root = null;
	   }
	 
	   // Insert a new node
	   public void insert(String s)
	   {
	      Node newNode = new Node(s);
	      // If root is empty, then this node is the root
	      if (root == null) root = newNode;
	      // otherwise begin the recursive process
	      else root.insertNode(newNode);
	   }
	   
	   public boolean search(String s)
	   {
		   if(root.search(this.root,s) == true)
		    return true;
		   else
			   return false;
	   }
	   
	   public void display(Node root)
	   {

	   		if (root!= null)
	   		{
	   			display(root.left);
	   			System.out.println(root.alphabet);
	   			display(root.right);
	
	   		}
	   }

	   public Node getRoot()
	   {
		   	return this.root;
	   }

	   public int NoOfNodes(Node root)
	   {
    		int count = 0;
    		if (root != null) 
        	count = 1 + NoOfNodes(root.left) + NoOfNodes(root.right);
      
    		return count;
	   }
	   
	   
	   // A node of a tree stores a alphabet as well as references to
	   // the child nodes to the left and to the right.
	   // Note the use of an inner class
	   private class Node
	   {
	      public String alphabet;
	      public Node left;
	      public Node right;

	      // Construct a node
	      public Node(String s) {
	         alphabet = s;
	         left = null;
	         right = null;
	      }

	      // Inserts a new node as a descendant of this node
	      // If both spots are full, keep on going. . .
	      public void insertNode(Node newNode)
	      {

	         // If new alphabet is alphabetically before
	         if (newNode.alphabet.compareTo(this.alphabet) < 0 )
	         {
	            // if the spot is empty (null) insert here
	            // otherwise keep going!
	            if (left == null) left = newNode;
	            else left.insertNode(newNode);
	         }
	         // if new alphabet is alphabetical after
	         else if (newNode.alphabet.compareTo(this.alphabet) > 0 )
	         {
	            // if the spot is empty (null) insert here
	            // otherwise keep going!
	            if (right == null) right = newNode;
	            else 
	            	right.insertNode(newNode);
	         }
	      }
	      
	      private boolean search(Node node, String c )
	      {
	    	  while(node != null)
	    	  {
	    		  if(node.alphabet.compareTo(c) == 0 )
	    			  return true;
	    		  else  if(node.alphabet.compareTo(c) > 0)
	    			   node = node.left;
	    		  else 
	    			   node = node.right;
	    	  }
	    	  return false;
	      }
	   }
	}