package Lexer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


 public class TokenType
{
    private final String pathtoOperators = "..\\res\\operators.txt";
    private final String pathtoKeywords  = "..\\res\\keywords.txt";

    private BufferedReader br;
    private DeterministicFiniteAutomata dfa;

    public TokenType()
    {

    }

    public boolean isKeyWord(String  s) throws IOException, FileNotFoundException
    {  
        br = new BufferedReader(new FileReader(pathtoKeywords));

        boolean result = false;
        
        while(br.ready())
        {
            if(s.equalsIgnoreCase(br.readLine()))
            {
                result = true;
                break;
            }
            
        }
            return result;
        
    }
    
    public boolean isVariable(String object)
    {   
            dfa = new DeterministicFiniteAutomata();
            return dfa.testValidVariable(object.trim());
    }
    
    public boolean isOperator(String s) throws IOException, FileNotFoundException
    {
        br = new BufferedReader(new FileReader(pathtoOperators));

        boolean result = false;
        
        while(br.ready())
        {
            if(s.equalsIgnoreCase(br.readLine()))
            {
                result = true;
                break;
            }
            
        }
            return result;
        
        
    }

    public boolean isRational(String s)
    {
        dfa = new DeterministicFiniteAutomata("Rational");
        return dfa.testValidRational(s);
    }
    
    public boolean isInteger(String s)
    {
        dfa = new DeterministicFiniteAutomata("Digit");
        return dfa.testValidInteger(s);
    }

    public String WhichOperator(String s) {

        String result;

        switch(s)
        {
            case ";": result="SEMI_COLON"; break;
            case "=": result="EQUAL_TO"; break;
            case "|+":result="PLUS"; break;
            case "|-":result="MINUS"; break;
            case "|*":result="MULTIPLY"; break;
            case "|/":result="DIVIDE"; break;
            case "|%":result="MOD"; break;
            case "<>":result="CONCAT"; break;
            case "!": result="NOT"; break;
            case "|&":result="AND"; break;
            case "|#":result="OR"; break;
            case "[":result="PROG_START"; break;
            case "]":result="PROG_END"; break;
            case "(":result="BRACKET_START"; break;
            case ")":result="BRACKET_END"; break;
            case ":":result="COLON"; break;
            case ".":result="DOT"; break;
            case ">":result="GREATER_THAN"; break;
            case "<":result="LESS_THAN"; break;
            case ",":result="COMMA"; break;
            case "?":result="QUESTION"; break;
            case ">=":result="GREATER_THAN_OR_EQUALTO"; break;
            case "<=":result="LESS_THAN_OR_EQUALTO";break;
            case "==":result="COMPARE";break;
            default:
                    result= null;
        }

       // System.out.println(result);
         return result;
    }
    }
    