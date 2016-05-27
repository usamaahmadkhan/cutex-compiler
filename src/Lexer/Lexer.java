package Lexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.LinkedList;

public class Lexer
{
	
	private final String pathtoWriteSymbols = "..\\gen\\symbols.txt";
  private final String pathtoWriteStatements = "..\\gen\\statements.txt";

	private BufferedReader br;
	private BufferedWriter bw;

	private TokenType tt;

  private LinkedList<String> statements;

	public Lexer(String sourcefile) throws IOException, FileNotFoundException
	{
		bw = new BufferedWriter(new FileWriter(pathtoWriteSymbols));
		br = new BufferedReader(new FileReader(sourcefile));
    tt = new TokenType();
    statements = new LinkedList<String>();
	}


	public void PerformAnalysis() throws IOException
	{       

      LinkedList<String> symbols = new LinkedList<String>();
		
		while(br.ready()){
        

        String codestring = br.readLine();
        StringTokenizer s = new StringTokenizer(codestring," ");
        statements.add(codestring);
        
        while(s.hasMoreTokens())
        {
              
              String o = s.nextToken().trim();

              if(tt.isKeyWord(o))
              {
                    symbols.add(o);
                    bw.append(o);
                  
              }else if(tt.isOperator(o))
              {
                    String r = tt.WhichOperator(o);
                    symbols.add(o);
                    bw.append(r); 
                  
              }else if(tt.isRational(o))
              {
                     symbols.add(o);
                     bw.append("RATIONAL");

              }else if(tt.isInteger(o))
              {
                     symbols.add(o);
                     bw.append("DIGIT");

              }else if(tt.isVariable(o))
              {
                     symbols.add(o);
                     bw.append("ID");
              }else
              {
              	  bw.append(o+" "+"Cannot Recognize the type");
              }
              	bw.newLine();
              
         }
              
              
    }
        bw.close();

    }
}
