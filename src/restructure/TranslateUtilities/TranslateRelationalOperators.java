package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateRelationalOperators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY RELATIONAL OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleRelationalOperator(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineRelationalOperator(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY RELATIONAL OPERATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String determineRelationalOperator(ArrayList<TokenData> tList, String charToCheck, int currentIndex) {
        
        // Case: ==
        if(charToCheck.equals("==")) {handleDoubleEquals();}

        // Case: !=
        if(charToCheck.equals("!=")) {handleNotEqual();}

        // Case: <
        if(charToCheck.equals("<")) {handleLessThan();}

        // Case: >
        if(charToCheck.equals(">")) {handleGreaterThan();}

        // Case: <=
        if(charToCheck.equals("<=")) {handleLessThanEqualTo();}

        // Case: >=
        if(charToCheck.equals(">=")) {handleGreaterThanEqualTo();}

        return "";
    }

    // Case: ==
    private static void handleDoubleEquals() {
        System.out.println("Found a Relational Operator: ==");
    }

    // Case: !=
    private static void handleNotEqual() {
        System.out.println("Found a Relational Operator: !=");
    }

    // Case: <
    private static void handleLessThan() {
        System.out.println("Found a Relational Operator: <");
    }

    // Case: >
    private static void handleGreaterThan() {
        System.out.println("Found a Relational Operator: >");
    }

    // Case: <=
    private static void handleLessThanEqualTo() {
        System.out.println("Found a Relational Operator: <=");
    }

    // Case: >=
    private static void handleGreaterThanEqualTo() {
        System.out.println("Found a Relational Operator: >=");
    }
    
}
