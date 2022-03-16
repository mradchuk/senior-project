package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateAssignmentOperators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY ASSIGNMENT OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleAssignmentOperator(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineAssignmentOperator(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY ASSIGNMENT OPERATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String determineAssignmentOperator(ArrayList<TokenData> tList, String charToCheck, int currentIndex) {
        
        // Case: =
        if(charToCheck.equals("=")) {handleBasicAssignment();}

        // Case: +=
        if(charToCheck.equals("+=")) {handleAddAssignment();}

        // Case: -=
        if(charToCheck.equals("-=")) {handleSubtractAssignment();}

        // Case: *=
        if(charToCheck.equals("*=")) {handleMultiplyAssignment();}

        // Case: /=
        if(charToCheck.equals("/=")) {handleDivideAssignment();}

        // Case: %=
        if(charToCheck.equals("%=")) {handleModuloAssignment();}

        return "";
    }

    // Case: =
    private static void handleBasicAssignment() {
        System.out.println("Found an Assignment Operator: =");
    }

    // Case: +=
    private static void handleAddAssignment() {
        System.out.println("Found an Assignment Operator: +=");
    }

    // Case: -=
    private static void handleSubtractAssignment() {
        System.out.println("Found an Assignment Operator: -=");
    }

    // Case: *=
    private static void handleMultiplyAssignment() {
        System.out.println("Found an Assignment Operator: *=");
    }

    // Case: /=
    private static void handleDivideAssignment() {
        System.out.println("Found an Assignment Operator: /=");
    }

    // Case: %=
    private static void handleModuloAssignment() {
        System.out.println("Found an Assignment Operator: %=");
    }
    
}
