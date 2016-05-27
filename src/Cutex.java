import java.lang.Exception;
import java.lang.System;
import Lexer.*;
import Parser.*;

public class Cutex
{
	public static void main(String[] args) throws Exception {
		
		if(args.length != 2)
		{
			System.err.println("Invalid arguments , exactly TWO argument required\n\n\t Usage: \'TestLexicalAnalysis [FileName] [SymbolTableFileName]\'");
  			System.exit(1);
		}else
		{
	    	long start =System.currentTimeMillis();
		
			Lexer lexer = new Lexer(args[0]);
			lexer.PerformAnalysis();

			Parser parser = new Parser(args[1]);
			System.out.println(parser.parse());
			System.out.println(System.currentTimeMillis() - start);

		}


		
	}
}