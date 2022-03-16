package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateSeparators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY SEPARATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleSeparator(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineSeparator(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY SEPARATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String determineSeparator(ArrayList<TokenData> tList, String charToCheck, int currentIndex) {
        
        // Case: [
        if(charToCheck.equals("[")) {handleOpenBracket();}

        // Case: ]
        if(charToCheck.equals("]")) {handleCloseBracket();}

        // Case: (
        if(charToCheck.equals("(")) {handleOpenParenthesis();}

        // Case: )
        if(charToCheck.equals(")")) {handleCloseParenthesis();}

        // Case: {
        if(charToCheck.equals("{")) {handleOpenCurlyBrace();}

        // Case: }
        if(charToCheck.equals("}")) {handleCloseCurlyBrace();}

        // Case: ,
        if(charToCheck.equals(",")) {handleComma();}
        
        // Case: ;
        if(charToCheck.equals(";")) {handleSemicolon();}

        // Case: .
        if(charToCheck.equals(".")) {handlePeriod();}
        
        return "";
    }
    
    // Case: [
    private static void handleOpenBracket() {
        System.out.println("Found a Separator: [");
    }

    // Case: ]
    private static void handleCloseBracket() {
        System.out.println("Found a Separator: ]");
    }

    // Case: (
    private static void handleOpenParenthesis() {
        System.out.println("Found a Separator: (");
    }

    // Case: )
    private static void handleCloseParenthesis() {
        System.out.println("Found a Separator: )");
    }

    // Case: {
    private static void handleOpenCurlyBrace() {
        System.out.println("Found a Separator: {");
    }

    // Case: }
    private static void handleCloseCurlyBrace() {
        System.out.println("Found a Separator: }");
    }

    // Case: ,
    private static void handleComma() {
        System.out.println("Found a Separator: ,");
    }

    // Case: ;
    private static void handleSemicolon() {
        System.out.println("Found a Separator: ;");
    }

    // Case: .
    private static void handlePeriod() {
        System.out.println("Found a Separator: .");
    }
}
