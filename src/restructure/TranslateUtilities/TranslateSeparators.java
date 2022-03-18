package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateSeparators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                          VARIABLES
    // --------------------------------------------------------------------------------

    private static ArrayList<Integer> indices = new ArrayList<Integer>();

    // --------------------------------------------------------------------------------
    //                          GETTERS/SETTERS
    // --------------------------------------------------------------------------------

    public static ArrayList<Integer> getIndicesList() {return indices;}
    public static void setIndicesList(ArrayList<Integer> newList) {indices = newList;}

    // --------------------------------------------------------------------------------
    //                  PRIMARY SEPARATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleSeparator(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        determineSeparator(tList, currentData.lexeme, currentIndex);
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY SEPARATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static void determineSeparator(ArrayList<TokenData> tList, String charToCheck, int currentIndex) {
        
        // Case: [
        if(charToCheck.equals("[")) {handleOpenBracket(tList, currentIndex);}

        // Case: ]
        if(charToCheck.equals("]")) {handleCloseBracket(tList, currentIndex);}

        // Case: (
        if(charToCheck.equals("(")) {handleOpenParenthesis();}

        // Case: )
        if(charToCheck.equals(")")) {handleCloseParenthesis(tList, currentIndex);}

        // Case: {
        if(charToCheck.equals("{")) {handleOpenCurlyBrace(tList, currentIndex);}

        // Case: }
        if(charToCheck.equals("}")) {handleCloseCurlyBrace(tList, currentIndex);}

        // Case: ,
        if(charToCheck.equals(",")) {handleComma();}
        
        // Case: ;
        if(charToCheck.equals(";")) {handleSemicolon(tList, currentIndex);}

        // Case: .
        if(charToCheck.equals(".")) {handlePeriod(tList, currentIndex);}
        
    }
    
    // Case: [
    private static void handleOpenBracket(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
    }

    // Case: ]
    private static void handleCloseBracket(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
    }

    // Case: (
    private static void handleOpenParenthesis() {
        // Nothing needs to change at this time
    }

    // Case: )
    private static void handleCloseParenthesis(ArrayList<TokenData> tList, int currentIndex) {
        // Nothing needs to change at this time
    }

    // Case: {
    private static void handleOpenCurlyBrace(ArrayList<TokenData> tList, int currentIndex) {
        TranslationUtils.tabCount++;
        tList.get(currentIndex).lexeme = TranslationUtils.getCurrentTabs() + "\n";
    }

    // Case: }
    private static void handleCloseCurlyBrace(ArrayList<TokenData> tList, int currentIndex) {
        tList.get(currentIndex).lexeme = TranslationUtils.getCurrentTabs() + "\n";
        TranslationUtils.tabCount--;
    }

    // Case: ,
    private static void handleComma() {
        System.out.println("Found a Separator: ,");
    }

    // Case: ;
    private static void handleSemicolon(ArrayList<TokenData> tList, int currentIndex) {
        tList.get(currentIndex).lexeme = "\n";
    }

    // Case: .
    private static void handlePeriod(ArrayList<TokenData> tList, int currentIndex) {
        if(tList.get(currentIndex-1).token.equals("system console specific")) {
            // Any logic added later should go here. System console specific tokens are handled in their own segment
        }
    }
    
}
