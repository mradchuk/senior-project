package restructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.*;


public class JavaParse {

    // --------------------------------------------------------------------------------
    //                          VARIABLES - FINALS
    // --------------------------------------------------------------------------------

    private static final int QUOTE_CHARACTER = '\'';
    private static final int DOUBLE_QUOTE_CHARACTER = '"';
    private static final String DEFAULT_NUMBER_LEXEME = "0.0";
    private static final String DEFAULT_STRING_LITERAL_LEXEME = "\"";
    private static final String DEFAULT_STRING_LABEL_LEXEME = "LABEL";
    private static final String KEY_NOT_FOUND_DEFAULT = "n/a";

    // --------------------------------------------------------------------------------
    //                          VARIABLES - HASH MAPS
    // --------------------------------------------------------------------------------

    protected static HashMap<String, ArrayList<String>> javaMap = new HashMap<String, ArrayList<String>>();
    protected static HashMap<String, Boolean> tokenMap = new HashMap<String, Boolean>();

    // --------------------------------------------------------------------------------
    //                          VARIABLES - LISTS
    // --------------------------------------------------------------------------------

    private static ArrayList<TokenData> tokenList = new ArrayList<TokenData>();

    // --------------------------------------------------------------------------------
    //                          GETTERS/SETTERS
    // --------------------------------------------------------------------------------

    public static HashMap<String, Boolean> getTokenMap() {return tokenMap;}
    public static void setTokenMap(HashMap<String, Boolean> tMap) {tokenMap = tMap;}

    public static ArrayList<TokenData> getTokenList() {return tokenList;}
    public static void setTokenList(ArrayList<TokenData> newList) {tokenList = newList;}

    // --------------------------------------------------------------------------------
    //                      FUNCTIONS - HASH MAP POPULATION
    // --------------------------------------------------------------------------------

    private static void populateJavaMap() {

        // Keywords
        ArrayList<String> keywords = new ArrayList<String>();

        keywords.add("ABSTRACT");
        keywords.add("ASSERT");
        keywords.add("BOOLEAN");
        keywords.add("BREAK");
        keywords.add("BYTE");
        keywords.add("CASE");
        keywords.add("CATCH");
        keywords.add("CHAR");
        keywords.add("CLASS");

        keywords.add("CONTINUE");
        keywords.add("DEFAULT");
        keywords.add("DO");
        keywords.add("DOUBLE");
        keywords.add("ELSE");
        keywords.add("ENUM");
        keywords.add("EXTENDS");
        keywords.add("FINAL");
        keywords.add("FINALLY");
        keywords.add("FLOAT");

        keywords.add("FOR");
        keywords.add("IF");
        keywords.add("IMPLEMENTS");
        keywords.add("IMPORT");
        keywords.add("INSTANCEOF");
        keywords.add("INT");
        keywords.add("INTERFACE");
        keywords.add("LONG");
        keywords.add("NATIVE");

        keywords.add("NEW");
        keywords.add("PACKAGE");
        keywords.add("PRIVATE");
        keywords.add("PROTECTED");
        keywords.add("PUBLIC");
        keywords.add("RETURN");
        keywords.add("SHORT");
        keywords.add("STATIC");
        keywords.add("STRICTFP");
        keywords.add("STRING");
        keywords.add("SUPER");

        keywords.add("SWITCH");
        keywords.add("SYNCHRONIZED");
        keywords.add("THIS");
        keywords.add("THROW");
        keywords.add("THROWS");
        keywords.add("TRANSIENT");
        keywords.add("TRY");
        keywords.add("VOID");
        keywords.add("VOLATILE");
        keywords.add("WHILE");

        javaMap.put("keyword", keywords);

        // String Label
        ArrayList<String> stringLabels = new ArrayList<String>();
        stringLabels.add("LABEL");
        javaMap.put("string label", stringLabels);

        // String Literal
        ArrayList<String> stringLiterals = new ArrayList<String>();
        stringLiterals.add("\"");
        javaMap.put("string literal", stringLiterals);

        // Number Literal
        ArrayList<String> numLiterals = new ArrayList<String>();
        numLiterals.add("0.0");
        javaMap.put("number literal", numLiterals);

        // Boolean Literal
        ArrayList<String> boolLiterals = new ArrayList<String>();
        boolLiterals.add("TRUE");
        boolLiterals.add("FALSE");

        javaMap.put("boolean literal", boolLiterals);

        // Null Literal
        ArrayList<String> nullLiterals = new ArrayList<String>();
        nullLiterals.add("NULL");
        javaMap.put("null literal", nullLiterals);

        // Arithmetic Operator
        ArrayList<String> arithmeticOperators = new ArrayList<String>();
        
        arithmeticOperators.add("+");
        arithmeticOperators.add("-");
        arithmeticOperators.add("/");
        arithmeticOperators.add("*");
        arithmeticOperators.add("%");

        javaMap.put("arithmetic operator", arithmeticOperators);

        // Unary Operator
        ArrayList<String> unaryOperators = new ArrayList<String>();
        unaryOperators.add("++");
        unaryOperators.add("--");
        javaMap.put("unary operator", unaryOperators);

        // Assignmnet Operator
        ArrayList<String> assignmentOperators = new ArrayList<String>();
        
        assignmentOperators.add("=");
        assignmentOperators.add("+=");
        assignmentOperators.add("-=");
        assignmentOperators.add("*=");
        assignmentOperators.add("/=");
        assignmentOperators.add("%=");

        javaMap.put("assignment operator", assignmentOperators);

        // Relational Operator
        ArrayList<String> relationalOperators = new ArrayList<String>();
        
        relationalOperators.add("==");
        relationalOperators.add("!=");
        relationalOperators.add("<");
        relationalOperators.add(">");
        relationalOperators.add("<=");
        relationalOperators.add(">=");

        javaMap.put("relational operator", relationalOperators);

        // Logical Operator
        ArrayList<String> logicalOperators = new ArrayList<String>();
        logicalOperators.add("&");
        logicalOperators.add("|");
        logicalOperators.add("!");

        javaMap.put("logical operator", logicalOperators);

        // Separator
        ArrayList<String> separators = new ArrayList<String>();
        separators.add("[");
        separators.add("]");
        separators.add("(");
        separators.add(")");
        separators.add("{");
        separators.add("}");
        separators.add(",");
        separators.add(";");
        separators.add(".");

        javaMap.put("separator", separators);

        // Main Method Specification
        ArrayList<String> systemConsoleSpecific = new ArrayList<String>();
        systemConsoleSpecific.add("SYSTEM");
        systemConsoleSpecific.add("OUT");
        systemConsoleSpecific.add("PRINTLN");

        javaMap.put("system console specific", systemConsoleSpecific);

    }

    private static void populateTokenMap() {
        tokenMap.put("keyword", false);
        tokenMap.put("string label", false);
        tokenMap.put("string literal", false);
        tokenMap.put("number literal", false);
        tokenMap.put("boolean literal", false);
        tokenMap.put("null literal", false);
        tokenMap.put("arithmetic operator", false);
        tokenMap.put("unary operator", false);
        tokenMap.put("assignment operator", false);
        tokenMap.put("relational operator", false);
        tokenMap.put("logical operator", false);
        tokenMap.put("separator", false);
        tokenMap.put("system console specific", false);
    }

    // --------------------------------------------------------------------------------
    //                      SECONDARY FUNCTIONS - HASH MAP FOCUS
    // --------------------------------------------------------------------------------

    // Credit for HashMap Search: https://www.programiz.com/java-programming/examples/get-key-from-hashmap-using-value
    // Searches given Hash Map for the ArrayList that contains the provided 'value', returns the key for that ArrayList
    // Purpose: To identify and return the token category that value belongs to
    private static String findKeyByValue(HashMap<String, ArrayList<String>> searchMap, String value) {
        String key = KEY_NOT_FOUND_DEFAULT;
        for(Entry<String, ArrayList<String>> entry: searchMap.entrySet()) {
            if(entry.getValue().contains(value.toUpperCase())) {
                key = entry.getKey();
            }
        }
        return key;
    }

    // Given a specific Hash Map (Type: <String,Boolean>), updates value to true based on given key
    // Purpose: Indicates which tokens need to be analyzed for translation
    private static void updateTokenMap(HashMap<String, Boolean> tMap, String key) {
        tMap.replace(key, true);
    }

    // --------------------------------------------------------------------------------
    //                      SECONDARY FUNCTIONS - TOKENDATA FOCUS
    // --------------------------------------------------------------------------------

    // Creates new TokenData object after determining whether given item is a number, a 'word', or a symbol. Adds TokenData object to list of TokenData
    // Purpose: Populates TokenData List after identifying necessary TokenData Properties (Token, Lexeme)
    private static void addTokenDataToList(ArrayList<TokenData> tokenDataList, HashMap<String, ArrayList<String>> thisMap, int typeNum, double numValue, String wordValue) {
        TokenData newTokenData;
        String tokenName;
        Boolean addToList = true;

        // Case: Type is Number (type is -2)
        if(typeNum == -2) {
            tokenName = findKeyByValue(thisMap, DEFAULT_NUMBER_LEXEME);
            newTokenData = new TokenData(tokenName, String.valueOf(numValue));
        }
        // Case: Type is 'Word' (type is -3)
        else if(typeNum == -3) {
            tokenName = findKeyByValue(thisMap, DEFAULT_STRING_LABEL_LEXEME);
            newTokenData = new TokenData(tokenName, wordValue);
            addToList = handleSpecialLabelCheck(thisMap, tokenDataList, newTokenData);
        }
        else if(typeNum == QUOTE_CHARACTER ||  typeNum == DOUBLE_QUOTE_CHARACTER) {
            tokenName = findKeyByValue(thisMap, DEFAULT_STRING_LITERAL_LEXEME);
            newTokenData = new TokenData(tokenName, (char)typeNum + wordValue + (char)typeNum);
        }
        // Case: Type is any other symbol
        else {
            tokenName = findKeyByValue(thisMap, String.valueOf((char)typeNum));
            newTokenData = new TokenData(tokenName, String.valueOf((char)typeNum));
        }

        // Flags token as necessary to translate
        if(addToList) {
            updateTokenMap(tokenMap, tokenName);
            tokenDataList.add(newTokenData);
        }
    }

    private static Boolean handleSpecialLabelCheck(HashMap<String, ArrayList<String>> mapToSearch, ArrayList<TokenData> tokenDataList, TokenData currentElement) {
        Boolean addedElementToList = false;
        
        // Dotwalk check
        addedElementToList = dotWalkCheck(mapToSearch, tokenDataList, currentElement);
        
        // Keyword check
        String keywordToken = "keyword";
        specialLabelCheck(mapToSearch, currentElement, keywordToken);

        // System Console check
        String sysConsoleToken = "system console specific";
        specialLabelCheck(mapToSearch, currentElement, sysConsoleToken);
        
        updateTokenMap(tokenMap, currentElement.token);

        return !addedElementToList;
    }

    private static void specialLabelCheck(HashMap<String, ArrayList<String>> mapToSearch, TokenData currentElement, String tokenToCheck) {
        ArrayList<String> tempList = mapToSearch.get(tokenToCheck);
        tokenSwap(tempList, currentElement, tokenToCheck);
    }

    // Checks if TokenData element is within ArrayList, changes token property to newToken if element is located in the list
    // Purpose: Flags proper TokenData elements as keywords
    private static void tokenSwap(ArrayList<String> listToSearch, TokenData currentElement, String newToken) {
        if(listToSearch.contains(currentElement.lexeme.toUpperCase())) {
            currentElement.token = newToken;
        }
    } 

    // --------------------------------------------------------------------------------
    //                      SECONDARY FUNCTIONS - MISCELLANEOUS
    // --------------------------------------------------------------------------------

    // As things are being parsed right now, the stream tokenizer catches likes like 'System.out.println' as one 'word'. 
    // DotWalkCheck is in charge of checking for cases like that.
    // NOTE: Assumption is that any String that isn't captured between quotations that has a period (.) character is attempting to dot walk.
    public static Boolean dotWalkCheck(HashMap<String, ArrayList<String>> thisMap, ArrayList<TokenData> dataList, TokenData currentElement) {
        int currentIndex = dataList.size();
        Boolean addedToList = false;

        if(currentElement.lexeme.indexOf(".") != -1) {
            splitDotWalking(thisMap, dataList, currentElement.lexeme, currentIndex);
            addedToList = true;
        }
        return addedToList;
    }

    // If DotWalkCheck is able to locate any 'words' that actually include 'dot walking', splitDotWalking uses . as a delimiter and splits up the dot walking, 
    // while also adding each portion of the original string
    
    public static void splitDotWalking(HashMap<String, ArrayList<String>> thisMap, ArrayList<TokenData> dataList, String stringLabel, int currentIndex) {
        String dotSymbolRegEx = "\\.";
        String dotToken = findKeyByValue(thisMap, ".");
        String primaryToken = findKeyByValue(thisMap, DEFAULT_STRING_LABEL_LEXEME);
        String tempToken = primaryToken;
        String[] splitString = stringLabel.split(dotSymbolRegEx);
        int dotCount = splitString.length - 1;
        TokenData tempTokenData;

        for(String split : splitString) {

            // If a token is found that isn't the "Key not found" token, make that the primary token
            if(!(findKeyByValue(thisMap, split).equals(KEY_NOT_FOUND_DEFAULT))) {
                tempToken = findKeyByValue(thisMap, split);
                updateTokenMap(tokenMap, tempToken);
            }

            tempTokenData = new TokenData(tempToken, split);
            dataList.add(currentIndex, tempTokenData);

            if(dotCount > 0) {
                currentIndex++;
                tempTokenData = new TokenData(dotToken, ".");
                dataList.add(currentIndex, tempTokenData);
                currentIndex++;
                dotCount--;
            }
            
            tempToken = primaryToken;
        }
    }

    // Purpose: Prints out TokenData information for Parse Testing
    private static void printTokenData(ArrayList<TokenData> tokenDataList) {
        for(TokenData tData : tokenDataList) {
            System.out.println("Token: " + tData.token);
            System.out.println("Lexeme: " + tData.lexeme);
        }
    }

    // --------------------------------------------------------------------------------
    //                          PRIMARY FUNCTIONS
    // --------------------------------------------------------------------------------

    private static void parseFile(File thisFile)  {
        
        try {
            FileReader reader = new FileReader(thisFile);
            StreamTokenizer tokenizer = new StreamTokenizer(reader);

            try {

                // Loops through file (until it reaches end of file - TT_EOF) and identifies each element based on type
                int currentToken = tokenizer.nextToken();    
                while(currentToken != StreamTokenizer.TT_EOF) {
                    if(tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                        addTokenDataToList(tokenList, javaMap, tokenizer.ttype, tokenizer.nval, "");
                    }
                    else if(tokenizer.ttype == StreamTokenizer.TT_WORD || tokenizer.ttype == QUOTE_CHARACTER || tokenizer.ttype == DOUBLE_QUOTE_CHARACTER) {
                        
                        // Stream tokenizer doesn't seem to catch the -- after a character, so this accounts for that
                        if(tokenizer.sval.contains("--")) {
                            addTokenDataToList(tokenList, javaMap, tokenizer.ttype, 0.0, tokenizer.sval.substring(0, tokenizer.sval.indexOf("--")));
                            addTokenDataToList(tokenList, javaMap, 45, 0.0, "");
                            addTokenDataToList(tokenList, javaMap, 45, 0.0, "");
                        }
                        else {
                            addTokenDataToList(tokenList, javaMap, tokenizer.ttype, 0.0, tokenizer.sval);
                        }
                    }
                    else {
                        addTokenDataToList(tokenList, javaMap, tokenizer.ttype, 0.0, "");
                    }
                    currentToken = tokenizer.nextToken();
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

    // Purpose: Manages Parsing Process
    public static void runParser(File fileToParse) {
        populateJavaMap();
        populateTokenMap();

        parseFile(fileToParse);
        //printTokenData(tokenList);
    }

    // --------------------------------------------------------------------------------
    //                          TESTING AREA
    // --------------------------------------------------------------------------------

    public static void main(String[] args) {
        File testFile = new File("restructure/test1.txt");

        runParser(testFile);
        Translator.runTranslator(getTokenMap(), getTokenList());
    }
}