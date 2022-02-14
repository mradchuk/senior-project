package application;
import java.util.*;
import java.io.*;

/*
    Java tokens to be stored in the constructor of the lexical analyzer class or whatever class
    does the lexical analysis of the Java code. I've created a custom Lexer to illustrate the concept
    of how identifiers and literals will be stored during analysis, and will see about having this solution
    adapted (however much of it necessary) to James R.'s Lexer.
 */

public class LexicalAnalyzer {

    /*
        Tokens are classified as keywords, identifiers,
        literals, constants, comments, operators,
        and separators.
    */
    static protected Map<String,String> keyWord = new HashMap<>();
    static protected Map<String,String> identifier = new HashMap<>();

    static protected Map<String, String> literal = new HashMap<>();
    static protected Map<Character, String> stringLiteral = new HashMap<>();
    static protected Map<String, String> characterLiteral = new HashMap<>();
    static protected Map<String, String> integerLiteral = new HashMap<>();
    static protected Map<String, String> doubleLiteral = new HashMap<>();
    static protected Map<String, String> booleanLiteral = new HashMap<>();
    static protected Map<String, String> nullLiteral = new HashMap<>();

    static protected Map<String,String> constant = new HashMap<>();

    static protected Map<String,String> comment = new HashMap<>();

    static protected Map<String,String> operator = new HashMap<>();
    static protected Map<String,String> opArithmetic = new HashMap<>();
    static protected Map<String,String> opUnary = new HashMap<>();
    static protected Map<String,String> opAssignment = new HashMap<>();
    static protected Map<String,String> opRelational = new HashMap<>();
    static protected Map<String,String> opLogical = new HashMap<>();

    static protected Map<String,String> separator = new HashMap<>();


    static protected Map<String,String> mainIdent = new HashMap<>();
    static protected Map<String,String> stringIdent = new HashMap<>();
    static protected Map<String,String> argsIdent = new HashMap<>();

    static protected ArrayList<String> tokens = new ArrayList<String>();

    static ArrayList<TokenData> arrayOfTokens = new ArrayList<>();

    static boolean isDataType = false;

    static String[] varDeclarationArr = new String[3];

    static boolean publicBool = false;
    static boolean classBool = false;

    // Store tokens
    public LexicalAnalyzer() {

        booleanLiteral.put("true", "boolean literal");
        booleanLiteral.put("false", "boolean literal");

        characterLiteral.put("'", "character literal");

        comment.put("//", "comment");
        comment.put("/*", "comment");
        comment.put("*/", "comment");

        keyWord.put("ABSTRACT", "keyword");
        keyWord.put("BOOLEAN", "keyword");
        keyWord.put("BREAK", "keyword");
        keyWord.put("BYTE", "keyword");
        keyWord.put("CASE", "keyword");
        keyWord.put("CATCH", "keyword");
        keyWord.put("CHAR", "keyword");
        keyWord.put("CLASS", "T_CLASS");
        keyWord.put("CONTINUE", "keyword");
        keyWord.put("DEFAULT", "keyword");
        keyWord.put("DO", "keyword");
        keyWord.put("DOUBLE", "T_DOUBLE");
        keyWord.put("ELSE", "keyword");
        keyWord.put("ENUM", "keyword");
        keyWord.put("EXTENDS", "keyword");
        keyWord.put("FINAL", "keyword");
        keyWord.put("FINALLY", "keyword");
        keyWord.put("FLOAT", "T_FLOAT");
        keyWord.put("FOR", "keyword");
        keyWord.put("IF", "keyword");
        keyWord.put("IMPORT", "keyword");
        keyWord.put("INT", "T_INT");
        keyWord.put("LONG", "keyword");
        keyWord.put("NEW", "keyword");
        keyWord.put("NULL", "keyword");
        keyWord.put("PACKAGE", "keyword");
        keyWord.put("PRIVATE", "T_PRIVATE");
        keyWord.put("PROTECTED", "T_PROTECTED");
        keyWord.put("PUBLIC", "T_PUBLIC");
        keyWord.put("RETURN", "keyword");
        keyWord.put("SHORT", "T_SHORT");
        keyWord.put("STATIC", "T_STATIC");
        keyWord.put("SUPER", "keyword");
        keyWord.put("SWITCH", "keyword");
        keyWord.put("SYNCHRONIZED", "keyword");
        keyWord.put("THROW", "keyword");
        keyWord.put("THROWS", "keyword");
        keyWord.put("TRY", "keyword");
        keyWord.put("VOID", "T_VOID");
        keyWord.put("WHILE", "keyword");

        nullLiteral.put("null", "null literal");

        opArithmetic.put("+", "arithmetic operator");
        opArithmetic.put("-", "arithmetic operator");
        opArithmetic.put("/", "arithmetic operator");
        opArithmetic.put("*", "arithmetic operator");
        opArithmetic.put("%", "arithmetic operator");

        opUnary.put("++", "unary operator");
        opUnary.put("--", "unary operator");
        opUnary.put("!", "unary operator");

        opAssignment.put("=", "assignment operator");
        opAssignment.put("+=", "assignment operator");
        opAssignment.put("-=", "assignment operator");
        opAssignment.put("*=", "assignment operator");
        opAssignment.put("/=", "assignment operator");
        opAssignment.put("%=", "assignment operator");

        opRelational.put("==", "relational operator");
        opRelational.put("!=", "relational operator");
        opRelational.put("<", "relational operator");
        opRelational.put(">", "relational operator");
        opRelational.put("<=", "relational operator");
        opRelational.put(">=", "relational operator");

        opLogical.put("&&", "logical operator");
        opLogical.put("||", "logical operator");

        stringLiteral.put('"', "string literal");

        separator.put("[", "T_LBRACKET");
        separator.put("]", "T_RBRACKET");
        separator.put("(", "T_LPARENTH");
        separator.put(")", "T_RPARENTH");
        separator.put("{", "T_LBRACE");
        separator.put("}", "T_RBRACE");
        separator.put(",", "T_COMMA");
        separator.put("=", "T_ASSIGN");
        separator.put(";", "T_SEMICOLON");
        separator.put(".", "T_DOT");

        mainIdent.put("MAIN", "MAIN_IDENTIFIER");
        stringIdent.put("STRING", "STRING_IDENTIFIER");
        argsIdent.put("ARGS", "ARGS_IDENTIFIER");

        integerLiteral.put("0.0", "integer literal");
    }

    public static List<Object> streamTokenizer(Reader r) throws IOException
    {
        StreamTokenizer st = new StreamTokenizer(r);
        List<Object> tokens = new ArrayList<Object>();

        //Set the quotations character to be counted as a word character to keep it attached
        //to the string it is encasing
        st.wordChars('"',' ');

        //Read tokens until there is none and add to the token list
        //TT_EOF = end of file, TT_Word = string value, tt_number = a number value
        int currToken = st.nextToken();
        while (currToken != st.TT_EOF)
        {
            if (st.ttype == st.TT_NUMBER) {

                tokens.add(st.nval);

            } else if (st.ttype == st.TT_WORD || st.ttype == '\'' || st.ttype == '"') {

                if (st.ttype == '"') {
                    tokens.add('"' + st.sval + '"');
                } else {
                    tokens.add(st.sval);
                }

            } else {
                tokens.add((char) currToken);
            }

            currToken = st.nextToken();
        }
        return tokens;
    }

    public static boolean isNumber(Object potentialNumber) {
        return potentialNumber instanceof Number;
    }

    static void printTranslator() {
        PythonConverter.translateDriver(arrayOfTokens);
    }

    static String getPythonStr() { return PythonConverter.resultPythonStr; }

    public static void directNumber(Object testObject) {

        String strObject = testObject.toString();

        arrayOfTokens.add(new TokenData("NUMBER", strObject));
        System.out.println("NUMBER" + " - " + strObject);

    }

    public static void translateToPython(Object testObject) {

        // I expect that the "instanceof" functionality (see isNumber()) may be necessary for the checks.
        // Secondary object is call the Objects toString() method and store/manipulate that way.

        // Keyword check - May require further steps depending on the keyword
        // Loop/Call a method that loops through the list of keywords that, as of writing this, doesn't exist. Check against all known keywords.
        // Replace where necessary. Methods should be created for most of the keywords to ensure they are converted correctly.
        // Part of this should include catching the label that follows the keyword, where applicable (such as a class or variable name)
        //------------------------------------------------------
        // If it's a Keyword:


        //------------------------------------------------------

        // String check - Catches anything starting or ending with '"', makes no change to the rest
        //------------------------------------------------------
        // If it's a String:


        //------------------------------------------------------

        // Separator check - May need two separator checks, one for open and the other for close.
        //------------------------------------------------------
        // If it's a Separator:


        //------------------------------------------------------

        // Operator check - Should be fairly straight forward, no change really needs to happen to it.
        //If we stick to manipulating a list, we may not even need this check - we could just do nothing, make no change, if it's an operator
        //------------------------------------------------------
        // If it's an Operator:


        //------------------------------------------------------

        // Input check - I call this input, but I wasn't sure how to handle the "System.out.println()" content in general. May be a better name for this.
        //------------------------------------------------------
        // If it's an Input:


        //------------------------------------------------------

        String strObject = testObject.toString();

        String prevToken = "";

        if(keyWord.containsKey(strObject.toUpperCase())) {

            if(strObject.toUpperCase().equals("INT") || strObject.toUpperCase().equals("DOUBLE") ||
                    strObject.toUpperCase().equals("SHORT") || strObject.toUpperCase().equals("FLOAT")) {

                        isDataType = true;

            }

            if(strObject.equals("public")) {
                publicBool = true;
            }

            if(strObject.equals("class")) {
                classBool = true;
            }

            arrayOfTokens.add(new TokenData(keyWord.get(strObject.toUpperCase()), strObject));
            System.out.println(keyWord.get(strObject.toUpperCase()) + " - " + strObject);

        } else if(separator.containsKey(strObject.toUpperCase())) {

            arrayOfTokens.add(new TokenData(separator.get(strObject.toUpperCase()), strObject));
            System.out.println(separator.get(strObject.toUpperCase()) + " - " + strObject);

        } else if(strObject.charAt(0) == '"' && strObject.charAt(strObject.length()-1) == '"') {

            arrayOfTokens.add(new TokenData(stringLiteral.get('"'), strObject));
            System.out.println(stringLiteral.get('"') + " - " + strObject);

        } else if(mainIdent.containsKey(strObject.toUpperCase())) {

            arrayOfTokens.add(new TokenData(mainIdent.get(strObject.toUpperCase()), strObject));
            System.out.println(mainIdent.get(strObject.toUpperCase()) + " - " + strObject);

        } else if(stringIdent.containsKey(strObject.toUpperCase())) {

            arrayOfTokens.add(new TokenData(stringIdent.get(strObject.toUpperCase()), strObject));
            System.out.println(stringIdent.get(strObject.toUpperCase()) + " - " + strObject);

        } else if(argsIdent.containsKey(strObject.toUpperCase())) {

            arrayOfTokens.add(new TokenData(argsIdent.get(strObject.toUpperCase()), strObject));
            System.out.println(argsIdent.get(strObject.toUpperCase()) + " - " + strObject);

        } else if(!strObject.contains(".")) {
            /*
                If the unrecognized string object DOES NOT contain a '.' separator, it is likely NEITHER a print nor casting statement.
                So the string object could be a class name, method name, variable identifier.
            */

            /* The string object belongs to statement that contains a datatype keyword */
            if(isDataType) {

                /*
                   If the string object is not 'f' that is separated by the stream tokenizer from
                   a number of float type, ex: 45.87f, recognize the string object as a variable identifier.

                   If the string object is 'f', skip over this object (by not using an else statement)
                   because Python already recognized the accompanying number as a float type.
                */
                if(!strObject.equals("f")) {

                    arrayOfTokens.add(new TokenData("VAR_IDENTIFIER", strObject));
                    System.out.println("VAR_IDENTIFIER" + " - " + strObject);

                }

            } else if(classBool && publicBool) {

                // Public class [class name]...

                arrayOfTokens.add(new TokenData("CLASS_NAME", strObject));
                System.out.println("CLASS_NAME" + " - " + strObject);

                classBool = false;
                publicBool = false;

            } else {

                arrayOfTokens.add(new TokenData("METHOD_NAME", strObject));
                System.out.println("METHOD_NAME" + " - " + strObject);

            }


        } else {

            /* If the unrecognized string object DOES contain a '.' separator, it is likely EITHER a print or casting statement */

            for(char c : strObject.toCharArray()) {

                /* If the char separator is likely '.', evaluate the previous token that comes before '.' that should belong to a cast
                    or print statement. Ex: System., out., Integer.
                 */
                if(separator.containsKey(String.valueOf(c))) {

                    if(prevToken.equals("System")) {
                        arrayOfTokens.add(new TokenData("System", prevToken));
                        System.out.println("System" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("out")) {
                        arrayOfTokens.add(new TokenData("out", prevToken));
                        System.out.println("out" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("String")) {
                        arrayOfTokens.add(new TokenData("String Cast", prevToken));
                        System.out.println("String Cast" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("Integer")) {
                        arrayOfTokens.add(new TokenData("Integer Cast", prevToken));
                        System.out.println("Integer Cast" + " - " + prevToken);
                        prevToken = "";
                    }

                    arrayOfTokens.add(new TokenData(separator.get(String.valueOf(c)), String.valueOf(c)));
                    System.out.println(separator.get(String.valueOf(c)) + " - " + c);

                } else {

                    /*
                        Create a valid token from successive characters that either that belongs to a print
                        or cast statement to be including in the array of tokens
                     */
                    prevToken += c;

                    // Whatever string occurs after the '.' separator (ex: .println, .toString, .format)
                    if(prevToken.equals("println")) {
                        arrayOfTokens.add(new TokenData("println", prevToken));
                        System.out.println("println" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("toString")) {
                        arrayOfTokens.add(new TokenData("toString method", prevToken));
                        System.out.println("toString method" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("valueOf")) {
                        arrayOfTokens.add(new TokenData("valueOf method", prevToken));
                        System.out.println("valueOf method" + " - " + prevToken);
                        prevToken = "";
                    } else if(prevToken.equals("format")) {
                        arrayOfTokens.add(new TokenData("String Format", prevToken));
                        System.out.println("String Format" + " - " + prevToken);
                        prevToken = "";
                    }

                }

            }

        }

    }

}

