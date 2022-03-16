package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateLogicalOperators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                      PRIMARY LOGICAL OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleLogicalOperator(ArrayList<TokenData> tList, ArrayList<Integer> indices, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = chooseLogicalOperatorCase(tList, indices, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                      SECONDARY LOGICAL OPERATOR FUNCTIONS
    // --------------------------------------------------------------------------------

    private static String chooseLogicalOperatorCase(ArrayList<TokenData> fullList, ArrayList<Integer> indices, String charToCheck, int caseIndex) {

        // Case: &
        if (charToCheck.equals("&")) {
            System.out.println("Found a Logical Operator: &");
            charToCheck = handleAmpersand(fullList, indices, caseIndex);
        }
        // Case: |
        else if(charToCheck.equals("|")) {
            System.out.println("Found a Logical Operator: |");
            charToCheck = handleVertBar(fullList, indices, caseIndex);
        }
        // Case: !
        else {
            // Must be a ! operator
            System.out.println("Found a Logical Operator: !");
            charToCheck = handleExclamation();
        }

        return charToCheck;
    }

    // Ampersand logic
    private static String handleAmpersand(ArrayList<TokenData> fullList, ArrayList<Integer> indices, int tempLexemeInt) {
        String ampersandSymbol = "&";

        // Check for double &&
        removeDoubleCharacter(fullList, indices, tempLexemeInt, ampersandSymbol);

        // If Single & in java, needs to be replaced with "and"
        // Single & in Python is a bitwise AND function, which is very different

        return "and ";
    }

    // Vertical Bar logic
    private static String handleVertBar(ArrayList<TokenData> fullList,  ArrayList<Integer> indices, int tempLexemeInt) {
        String vertBarSymbol = "|";

        // Check for double ||
        removeDoubleCharacter(fullList, indices, tempLexemeInt, vertBarSymbol);

        return "or ";
    }

    // Exclamation logic
    private static String handleExclamation() {
        // No doubles for !, so if the symbol is ! it just needs to be replaced with "not"
        return "not ";
    }

    // Handles double characters
    private static void removeDoubleCharacter(ArrayList<TokenData> currentList, ArrayList<Integer> indices, int initialIndex, String charToCheck) {
        if(initialIndex + 1 != currentList.size() && currentList.get(initialIndex + 1).lexeme.equals(charToCheck)) {
            removeCharacter(currentList, indices, initialIndex+1);
        }
    }
}
