package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateArithmeticOperators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY ARITHMETIC OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleArithmeticOperator(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineArithmeticOperator(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY ARITHMETIC OPERATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String determineArithmeticOperator(ArrayList<TokenData> tList, String charToCheck, int currentIndex) {
        
        // Case: +
        if(charToCheck.equals("+")) {handlePlus();}

        // Case: -
        if(charToCheck.equals("-")) {handleMinus();}

        // Case: *
        if(charToCheck.equals("*")) {handleAsterisk();}

        // Case: /
        if(charToCheck.equals("/")) {handleForwardSlash();}

        // Case: %
        if(charToCheck.equals("%")) {handleModulo();}

        return "";
    }

    // Case: +
    private static void handlePlus() {
        System.out.println("Found an Arithmetic Operator: +");
    }

    // Case: -
    private static void handleMinus() {
        System.out.println("Found an Arithmetic Operator: -");
    }

    // Case: *
    private static void handleAsterisk() {
        System.out.println("Found an Arithmetic Operator: *");
    }

    // Case: /
    private static void handleForwardSlash() {
        System.out.println("Found an Arithmetic Operator: /");
    }

    // Case: %
    private static void handleModulo() {
        System.out.println("Found an Arithmetic Operator: %");
    }
}
