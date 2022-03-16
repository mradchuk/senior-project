package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateBooleans extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY BOOLEAN LITERAL FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleBoolean(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineBooleanLiteral(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY BOOLEAN LITERAL FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String determineBooleanLiteral(ArrayList<TokenData> tList, String wordToCheck, int currendIndex) {
        
        // Case: TRUE
        if(wordToCheck.toUpperCase().equals("TRUE")) {handleTrue();}
        
        // Case: TRUE
        if(wordToCheck.toUpperCase().equals("FALSE")) {handleFalse();}

        return "";
    }

    // Case: TRUE
    private static void handleTrue() {
        System.out.println("Found a Boolean Literal: TRUE");
    }

    // Case: FALSE
    private static void handleFalse() {
        System.out.println("Found a Boolean Literal: FALSE");
    }

}
