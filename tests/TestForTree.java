public class TestForTree
{
	public TestForTree()
	{

	}
	
	public static void main (String[] args)
	{
		FirstState fse = new FirstState();
		System.out.println(fse.getTree().NoOfNodes(fse.getTree().getRoot()));
		
		fse.displayAlphabetTree();

		int a =65;
		while(a<200)
		{
			System.out.println(Character.toString((char)a)+ " "+fse.getTree().search(Character.toString((char)a)));
			a++;
		}
	}
}