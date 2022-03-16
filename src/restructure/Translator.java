package restructure;

import java.util.HashMap;
import java.util.ArrayList;
import restructure.TokenData;
import restructure.TranslateUtilities.*;

public class Translator {
    
    // --------------------------------------------------------------------------------
    //                          VARIABLES - HASH MAPS
    // --------------------------------------------------------------------------------

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
    //                      GENERAL TRANSLATION FUNCTIONS - STRING FOCUS
    // --------------------------------------------------------------------------------

    // Keywords
    public static void handleAllKeywords(ArrayList<TokenData> tList) {
        String keywordToken = "keyword";
        ArrayList<Integer> allKeywordIndices = findAllTokenLocations(tList, keywordToken);

        for(int location: allKeywordIndices) {
            TranslateKeywords.handleKeyword(tList, tList.get(location), location);
        }
    }

    // String Labels
    public static void handleAllLabels(ArrayList<TokenData> tList) {
        String labelToken = "string label";
        ArrayList<Integer> allStringLabelIndices = findAllTokenLocations(tList, labelToken);

        for(int location: allStringLabelIndices) {
            TranslateLabels.handleLabel(tList, tList.get(location), location);
        }
    }

    // String Literals
    public static void handleAllStrings(ArrayList<TokenData> tList) {
        String stringToken = "string literal";
        ArrayList<Integer> allStringLiteralIndices = findAllTokenLocations(tList, stringToken);

        for(int location: allStringLiteralIndices) {
            TranslateLiterals.handleStringLiteral(tList, tList.get(location), location);
        }
    }

    // Boolean Literals
    public static void handleAllBooleans(ArrayList<TokenData> tList) {
        String booleanToken = "boolean literal";
        ArrayList<Integer> allBooleanLiteralIndices = findAllTokenLocations(tList, booleanToken);

        for(int location: allBooleanLiteralIndices) {
            TranslateBooleans.handleBoolean(tList, tList.get(location), location);
        }
    }

    // Null Literals
    public static void handleAllNulls(ArrayList<TokenData> tList) {
        String nullToken = "null literal";
        ArrayList<Integer> allNullLiteralIndices = findAllTokenLocations(tList, nullToken);

        for(int location: allNullLiteralIndices) {
            TranslateNulls.handleNull(tList, tList.get(location), location);
        }
    }

    // System Console Specific
    public static void handleAllSystemConsoles(ArrayList<TokenData> tList) {
        String nullToken = "system console specific";
        ArrayList<Integer> allSystemConsoleIndices = findAllTokenLocations(tList, nullToken);

        for(int location: allSystemConsoleIndices) {
            TranslateSystemConsole.handleSystemConsole(tList, tList.get(location), location);
        }
    }

    // --------------------------------------------------------------------------------
    //                      GENERAL TRANSLATION FUNCTIONS - NUMBER FOCUS
    // --------------------------------------------------------------------------------

    // Number Literals
    public static void handleAllNumbers(ArrayList<TokenData> tList) {
        String numberToken = "number literal";
        ArrayList<Integer> allNumberLiteralIndices = findAllTokenLocations(tList, numberToken);

        for(int location: allNumberLiteralIndices) {
            TranslateNumbers.handleNumber(tList, tList.get(location), location);
        }
    }

    // --------------------------------------------------------------------------------
    //                      GENERAL TRANSLATION FUNCTIONS - SYMBOL FOCUS
    // --------------------------------------------------------------------------------

    // Arithmetic Operators
    public static void handleAllArithmeticOperators(ArrayList<TokenData> tList) {
        String arithmeticToken = "arithmetic operator";
        ArrayList<Integer> allArithmeticOperatorIndices = findAllTokenLocations(tList, arithmeticToken);

        for(int location: allArithmeticOperatorIndices) {
            TranslateArithmeticOperators.handleArithmeticOperator(tList, tList.get(location), location);
        }
    }

    // Unary Operators will need to be identified in Arithmetic Operators
    // Unary Operators
    public static void handleAllUnaryOperators(ArrayList<TokenData> tList) {
        String unaryToken = "unary operator";
        ArrayList<Integer> allUnaryOperatorIndices = findAllTokenLocations(tList, unaryToken);

        for(int location : allUnaryOperatorIndices) {
            TranslateUnaryOperators.handleUnaryOperator(tList, tList.get(location), location);
        }
    }

    // Assignment Operators
    public static void handleAllAssignmentOperators(ArrayList<TokenData> tList) {
        String assignmentToken = "assignment operator";
        ArrayList<Integer> allAssignmentOperatorIndices = findAllTokenLocations(tList, assignmentToken);

        for(int location: allAssignmentOperatorIndices) {
            TranslateAssignmentOperators.handleAssignmentOperator(tList, tList.get(location), location);
        }
    }

    // Relational Operators
    public static void handleAllRelationalOperators(ArrayList<TokenData> tList) {
        String relationalToken = "relational operator";
        ArrayList<Integer> allRelationalOperatorIndices = findAllTokenLocations(tList, relationalToken);

        for(int location: allRelationalOperatorIndices) {
            TranslateRelationalOperators.handleRelationalOperator(tList, tList.get(location), location);
        }
    }

    // Logical Operators
    public static void handleAllLogicalOperators(ArrayList<TokenData> tList) {
        String logicalToken = "logical operator";
        ArrayList<Integer> allLogicalOperatorIndices = findAllTokenLocations(tList, logicalToken);

        for(int location : allLogicalOperatorIndices) {
            TranslateLogicalOperators.handleLogicalOperator(tList, allLogicalOperatorIndices, tList.get(location), location);
        }
    }

    // Separators
    public static void handleAllSeparators(ArrayList<TokenData> tList) {
        String separatorToken = "separator";
        ArrayList<Integer> allSeparatorIndices = findAllTokenLocations(tList, separatorToken);

        for(int location: allSeparatorIndices) {
            TranslateSeparators.handleSeparator(tList, tList.get(location), location);
        }
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY FUNCTIONS - MISCELLANEOUS
    // --------------------------------------------------------------------------------

    public static ArrayList<Integer> findAllTokenLocations(ArrayList<TokenData> dataList, String specificToken) {
        ArrayList<Integer> tokenLocations = new ArrayList<Integer>();

        for(TokenData dataObject : dataList) {
            if(dataObject.token.equals(specificToken)) {
                tokenLocations.add(dataList.indexOf(dataObject));
            }
        }

        return tokenLocations;
    }
    
    private static ArrayList<TokenData> cloneList(ArrayList<TokenData> originalList) {
        ArrayList<TokenData> copiedList = new ArrayList<TokenData>(originalList.size());
        String tempToken;
        String tempLexeme;
        TokenData tempData;
        for(TokenData data : originalList) {
            tempToken = data.token;
            tempLexeme = data.lexeme;
            tempData = new TokenData(tempToken, tempLexeme);
            copiedList.add(tempData);
        }
        return copiedList;
    }

    // --------------------------------------------------------------------------------
    //                          PRIMARY FUNCTIONS
    // --------------------------------------------------------------------------------

    // Determine Necessary Categories to Run
    public static void determineTranslators(HashMap<String, Boolean> tMap, ArrayList<TokenData> tList) {
        
        // String Focus
        if(tMap.get("keyword")) {handleAllKeywords(cloneList(tList));}
        if(tMap.get("string label")) {handleAllLabels(cloneList(tList));}
        if(tMap.get("string literal")) {handleAllStrings(cloneList(tList));}
        if(tMap.get("boolean literal")) {handleAllBooleans(cloneList(tList));}
        if(tMap.get("null literal")) {handleAllNulls(cloneList(tList));}
        if(tMap.get("system console specific")) {handleAllSystemConsoles(cloneList(tList));}

        // Number Focus
        if(tMap.get("number literal")) {handleAllNumbers(cloneList(tList));}

        // Symbol Focus
        if(tMap.get("arithmetic operator")) {handleAllArithmeticOperators(cloneList(tList));}
        if(tMap.get("unary operator")) {handleAllUnaryOperators(cloneList(tList));}
        if(tMap.get("assignment operator")) {handleAllAssignmentOperators(cloneList(tList));}
        if(tMap.get("relational operator")) {handleAllRelationalOperators(cloneList(tList));}
        if(tMap.get("logical operator")) {handleAllLogicalOperators(cloneList(tList));}
        if(tMap.get("separator")) {handleAllSeparators(cloneList(tList));}
    }

    public static void runTranslator(HashMap<String, Boolean> tMap, ArrayList<TokenData> tList) {
        setTokenMap(tMap);
        setTokenList(tList);

        determineTranslators(tMap, tList);

    }
}
