package restructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import application.TokenData;

public class JavaParse {
    protected static HashMap<String, String> javaMap = new HashMap<String, String>();
    protected static HashMap<String, String> pythonMap = new HashMap<String, String>();
    protected static HashMap<String, Boolean> tokenMap = new HashMap<String, Boolean>();

    public static ArrayList<TokenData> tokenList = new ArrayList<TokenData>();

    public static void populateJavaMap() {

        // Keywords
        javaMap.put("keyword", "ABSTRACT");
        javaMap.put("keyword", "ASSERT");
        javaMap.put("keyword", "BOOLEAN");
        javaMap.put("keyword", "BREAK");
        javaMap.put("keyword", "BYTE");
        javaMap.put("keyword", "CASE");
        javaMap.put("keyword", "CATCH");
        javaMap.put("keyword", "CHAR");
        javaMap.put("keyword", "CLASS");

        javaMap.put("keyword", "CONTINUE");
        javaMap.put("keyword", "DEFAULT");
        javaMap.put("keyword", "DO");
        javaMap.put("keyword", "DOUBLE");
        javaMap.put("keyword", "ELSE");
        javaMap.put("keyword", "ENUM");
        javaMap.put("keyword", "EXTENDS");
        javaMap.put("keyword", "FINAL");
        javaMap.put("keyword", "FINALLY");
        javaMap.put("keyword", "FLOAT");

        javaMap.put("keyword", "FOR");
        javaMap.put("keyword", "IF");
        javaMap.put("keyword", "IMPLEMENTS");
        javaMap.put("keyword", "IMPORT");
        javaMap.put("keyword", "INSTANCEOF");
        javaMap.put("keyword", "INT");
        javaMap.put("keyword", "INTERFACE");
        javaMap.put("keyword", "LONG");
        javaMap.put("keyword", "NATIVE");

        javaMap.put("keyword", "NEW");
        javaMap.put("keyword", "PACKAGE");
        javaMap.put("keyword", "PRIVATE");
        javaMap.put("keyword", "PROTECTED");
        javaMap.put("keyword", "PUBLIC");
        javaMap.put("keyword", "RETURN");
        javaMap.put("keyword", "SHORT");
        javaMap.put("keyword", "STATIC");
        javaMap.put("keyword", "STRICTFP");
        javaMap.put("keyword", "SUPER");

        javaMap.put("keyword", "SWITCH");
        javaMap.put("keyword", "SYNCHRONIZED");
        javaMap.put("keyword", "THIS");
        javaMap.put("keyword", "THROW");
        javaMap.put("keyword", "THROWS");
        javaMap.put("keyword", "TRANSIENT");
        javaMap.put("keyword", "TRY");
        javaMap.put("keyword", "VOID");
        javaMap.put("keyword", "VOLATILE");
        javaMap.put("keyword", "WHILE");

        // String Literal
        javaMap.put("string literal", "\"");

        // Integer Literal
        javaMap.put("integer literal", "0.0");

        // Double Literal
        //javaMap.put("double literal", );

        // Boolean Literal
        javaMap.put("boolean literal", "TRUE");
        javaMap.put("boolean literal", "FALSE");

        // Null Literal
        javaMap.put("null literal", "NULL");

        // Constant
        //javaMap.put("double literal", );

        // Arithmetic Operator
        javaMap.put("arithmetic operator", "+");
        javaMap.put("arithmetic operator", "-");
        javaMap.put("arithmetic operator", "/");
        javaMap.put("arithmetic operator", "*");
        javaMap.put("arithmetic operator", "%");

        // Unary Operator
        javaMap.put("unary operator", "++");
        javaMap.put("unary operator", "--");

        // Assignmnet Operator
        javaMap.put("assignment operator", "=");
        javaMap.put("assignment operator", "+=");
        javaMap.put("assignment operator", "-=");
        javaMap.put("assignment operator", "*=");
        javaMap.put("assignment operator", "/=");
        javaMap.put("assignment operator", "%=");

        // Relational Operator
        javaMap.put("relational operator", "==");
        javaMap.put("relational operator", "!=");
        javaMap.put("relational operator", "<");
        javaMap.put("relational operator", ">");
        javaMap.put("relational operator", "<=");
        javaMap.put("relational operator", ">=");

        // Logical Operator
        javaMap.put("logical operator", "&");
        javaMap.put("logical operator", "|");
        javaMap.put("logical operator", "!");

        // Separator
        javaMap.put("separator", "[");
        javaMap.put("separator", "]");
        javaMap.put("separator", "(");
        javaMap.put("separator", ")");
        javaMap.put("separator", "{");
        javaMap.put("separator", "}");
        javaMap.put("separator", ",");
        javaMap.put("separator", ";");
        javaMap.put("separator", ".");

        // Main Method Specification
        javaMap.put("main identifier", "MAIN");
        javaMap.put("string identifier", "STRING");
        javaMap.put("args identifier", "ARGS");

    }

    public static void populatePythonMap() {
        pythonMap.put("keyword", "TRUE");
        pythonMap.put("keyword", "FALSE");
        pythonMap.put("keyword", "NONE");
        pythonMap.put("keyword", "AND");
        pythonMap.put("keyword", "AS");
        pythonMap.put("keyword", "ASSERT");
        pythonMap.put("keyword", "ASYNC");

        pythonMap.put("keyword", "AWAIT");
        pythonMap.put("keyword", "BREAK");
        pythonMap.put("keyword", "CLASS");
        pythonMap.put("keyword", "CONTINUE");
        pythonMap.put("keyword", "DEF");
        pythonMap.put("keyword", "DEL");
        pythonMap.put("keyword", "ELIF");

        pythonMap.put("keyword", "ELSE");
        pythonMap.put("keyword", "EXCEPT");
        pythonMap.put("keyword", "FINALLY");
        pythonMap.put("keyword", "FOR");
        pythonMap.put("keyword", "FROM");
        pythonMap.put("keyword", "GLOBAL");
        pythonMap.put("keyword", "IF");

        pythonMap.put("keyword", "IMPORT");
        pythonMap.put("keyword", "IN");
        pythonMap.put("keyword", "IS");
        pythonMap.put("keyword", "LAMBDA");
        pythonMap.put("keyword", "NONLOCAL");
        pythonMap.put("keyword", "NOT");
        pythonMap.put("keyword", "OR");

        pythonMap.put("keyword", "PASS");
        pythonMap.put("keyword", "RAISE");
        pythonMap.put("keyword", "RETURN");
        pythonMap.put("keyword", "TRY");
        pythonMap.put("keyword", "WHILE");
        pythonMap.put("keyword", "WITH");
        pythonMap.put("keyword", "YIELD");
    }

    public static void populateTokenMap() {
        tokenMap.put("keyword", false);
        tokenMap.put("string literal", false);
        tokenMap.put("integer literal", false);
        tokenMap.put("boolean literal", false);
        tokenMap.put("null literal", false);
        tokenMap.put("constant", false);
        tokenMap.put("arithmetic operator", false);
        tokenMap.put("unary operator", false);
        tokenMap.put("assignment operator", false);
        tokenMap.put("relational operator", false);
        tokenMap.put("logical operator", false);
        tokenMap.put("separator", false);
    }

    public static void parseFile(File thisFile)  {
        // Create new FileReader
        // Create Stream Tokenizer
        try {
            FileReader reader = new FileReader(thisFile);
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            try {
                int currentToken = tokenizer.nextToken();    
                while(currentToken != StreamTokenizer.TT_EOF) {
                    System.out.println("Type Num: " + tokenizer.ttype);

                    if(tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                        System.out.println("Num Value: " + tokenizer.nval);
                    }
                    
                    if(tokenizer.ttype == StreamTokenizer.TT_WORD) {
                        System.out.println("Word value: " + tokenizer.sval);
                    }
                }
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }
            
        }
        catch(FileNotFoundException error) {
            System.out.println(error);
        }
    }

    public static void main(String[] args) {
        File testFile = new File("test.txt");

        parseFile(testFile);
    }
}