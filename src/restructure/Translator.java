package restructure;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;
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
    private static ArrayList<TokenData> finalPythonList = new ArrayList<TokenData>();
    private static ArrayList<ArrayList<TokenData>> masterCopyList = new ArrayList<ArrayList<TokenData>>();

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
    public static ArrayList<TokenData> handleAllKeywords(ArrayList<TokenData> tList) {
        String keywordToken = "keyword";
        ArrayList<Integer> allKeywordIndices = findAllTokenLocations(tList, keywordToken);
        TranslateKeywords.setIndicesList(allKeywordIndices);

        for(int location: allKeywordIndices) {
            TranslateKeywords.handleKeyword(tList, tList.get(location), location);
        }
        return tList;
    }

    // String Labels
    public static ArrayList<TokenData> handleAllLabels(ArrayList<TokenData> tList) {
        String labelToken = "string label";
        ArrayList<Integer> allStringLabelIndices = findAllTokenLocations(tList, labelToken);

        for(int location: allStringLabelIndices) {
            TranslateLabels.handleLabel(tList, location);
        }

        return tList;
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
    public static ArrayList<TokenData> handleAllSystemConsoles(ArrayList<TokenData> tList) {
        String nullToken = "system console specific";
        ArrayList<Integer> allSystemConsoleIndices = findAllTokenLocations(tList, nullToken);
        TranslateSystemConsole.setIndicesList(allSystemConsoleIndices);

        for(int location: allSystemConsoleIndices) {
            TranslateSystemConsole.handleSystemConsole(tList, tList.get(location), location);
        }

        return tList;
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
            TranslateUnaryOperators.handleUnaryOperator(tList, allUnaryOperatorIndices, tList.get(location), location);
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
    public static ArrayList<TokenData> handleAllSeparators(ArrayList<TokenData> tList) {
        String separatorToken = "separator";
        ArrayList<Integer> allSeparatorIndices = findAllTokenLocations(tList, separatorToken);
        TranslateSeparators.setIndicesList(allSeparatorIndices);

        for(int location: allSeparatorIndices) {
            TranslateSeparators.handleSeparator(tList, tList.get(location), location);
        }

        return tList;
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

    // Removes all items tagged for removal. Loops through list in reverse to prevent further indices 
    // from having to be changed after each removal.
    private static ArrayList<TokenData> handleRemovals(ArrayList<TokenData> finalList) {

        ArrayList<Integer> removalList = TranslationUtils.indicesForRemoval;

        // Sort in reverse order to make sure removal indices don't overlap
        Collections.sort(removalList, Collections.reverseOrder());

        for(int index = 0; index < removalList.size() ; index++) {
            int indexToRemove = removalList.get(index);
            finalList.remove(finalList.get(indexToRemove));
        }

        return finalList;
    }

    private static ArrayList<TokenData> checkAllTokenData(ArrayList<TokenData> primary, ArrayList<TokenData> secondary) {
        boolean sameData = false;
        TokenData tempPrimary, tempSecondary;

        // Assumption: Both Lists are the same size as removals occur after list merge
        for(int index = 0; index < primary.size(); index++) {
            tempPrimary = primary.get(index);
            tempSecondary = secondary.get(index);
            sameData = compareTokenData(primary.get(index), secondary.get(index));

            if(!sameData) {

                // If the tokens are the same, but the lexemes aren't
                if(tempPrimary.token.equals(tempSecondary.token)) {

                    // If primary hasn't changed from the original script, replace with new lexeme
                    if(tempPrimary.lexeme.equals(tokenList.get(index).lexeme)) {
                        tempPrimary.lexeme = tempSecondary.lexeme;
                        primary.set(index, tempPrimary);
                    }
                    
                    // Else do nothing to Primary List
                    
                }

                // If the lexemes are the same but the tokens aren't, change to the new token
                else if(tempPrimary.lexeme.equals(tempSecondary.lexeme)) {
                    tempPrimary.token = tempSecondary.token;
                    primary.set(index, tempPrimary);
                }
                // If nothing is the same, set the old to the new
                else {
                    primary.set(index, tempSecondary);
                }

            }
        }


        // Return updated primary list
        return primary;

    }

    private static Boolean compareTokenData(TokenData primary, TokenData secondary) {
        if(primary.token.equals(secondary.token) && primary.lexeme.equals(secondary.lexeme)) {
            return true;
        } 
        else {
            return false;   
        }
    }

    private static void mainMethodCheck() {
        if(TranslateLabels.mainDeclaration != null) {
            finalPythonList.add(TranslateLabels.mainDeclaration);
        }
    }

    // --------------------------------------------------------------------------------
    //                          PRIMARY FUNCTIONS
    // --------------------------------------------------------------------------------

    public static void outputPython() {
        System.out.println("Beginning of Translated Program");
        for(TokenData data : finalPythonList) {
            System.out.print(data.lexeme + " ");
        }
        System.out.println("Translator Finished");
    }

    public static void mergeLists() {

        finalPythonList = cloneList(tokenList);

        for(ArrayList<TokenData> list : masterCopyList) {
            
            // for(TokenData data : list) {
            //     System.out.println("During List Loop: " + data.lexeme);
            // }

            finalPythonList = checkAllTokenData(finalPythonList, list);          
        }

        // Now remove all necessary elements
        finalPythonList = handleRemovals(cloneList(finalPythonList));
    }

    // Determine Necessary Categories to Run
    // Each category that is run will return an altered copy of tokenList. These are added to a separate list
    // To be handled in mergeLists()
    // NOTE: Uncommenting categories as they are developed
    public static void determineTranslators(HashMap<String, Boolean> tMap, ArrayList<TokenData> tList) {
        
        ArrayList<TokenData> tempTokenList = new ArrayList<TokenData>();

        // String Focus
        if(tMap.get("keyword")) {
            tempTokenList = handleAllKeywords(cloneList(tList));
            masterCopyList.add(tempTokenList);
        }
        
        if(tMap.get("string label")) {
            tempTokenList = handleAllLabels(cloneList(tList));
            masterCopyList.add(tempTokenList);
        }
        // if(tMap.get("string literal")) {handleAllStrings(cloneList(tList));}
        // if(tMap.get("boolean literal")) {handleAllBooleans(cloneList(tList));}
        // if(tMap.get("null literal")) {handleAllNulls(cloneList(tList));}
        if(tMap.get("system console specific")) {
            tempTokenList = handleAllSystemConsoles(cloneList(tList));
            masterCopyList.add(tempTokenList);
        }

        // // Number Focus
        // if(tMap.get("number literal")) {handleAllNumbers(cloneList(tList));}

        // // Symbol Focus
        // if(tMap.get("arithmetic operator")) {handleAllArithmeticOperators(cloneList(tList));}
        // if(tMap.get("unary operator")) {handleAllUnaryOperators(cloneList(tList));}
        // if(tMap.get("assignment operator")) {handleAllAssignmentOperators(cloneList(tList));}
        // if(tMap.get("relational operator")) {handleAllRelationalOperators(cloneList(tList));}
        // if(tMap.get("logical operator")) {handleAllLogicalOperators(cloneList(tList));}
        if(tMap.get("separator")) {
            tempTokenList = handleAllSeparators(cloneList(tList));
            masterCopyList.add(tempTokenList);
        }
    }

    public static void runTranslator(HashMap<String, Boolean> tMap, ArrayList<TokenData> tList) {
        setTokenMap(tMap);
        setTokenList(tList);
        masterCopyList.clear();
        TranslationUtils.indicesForRemoval.clear();
        TranslationUtils.populatePythonMap();

        determineTranslators(tMap, tList);
        // Any multithreading logic would go before this next line
        mergeLists();
        mainMethodCheck();
        outputPython();

    }
}
