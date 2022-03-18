package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateUnaryOperators extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                      PRIMARY UNARY OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleUnaryOperator(ArrayList<TokenData> tList, ArrayList<Integer> indices, TokenData currentData, int currentIndex) {
        String labelToken = "string label";
        boolean increment = false;
        boolean afterToken = false;

        if(currentData.lexeme.equals("++")) { increment = true;}
        if(currentIndex != 0 && checkImmediateToken(tList, true, currentIndex, labelToken)) { afterToken = true;}

        currentData.lexeme = determineUnaryAlternative(tList, indices, currentIndex, increment, afterToken);
    }

    // --------------------------------------------------------------------------------
    //                      SECONDARY LOGICAL OPERATOR FUNCTION
    // --------------------------------------------------------------------------------

    private static String determineUnaryAlternative(ArrayList<TokenData> tList, ArrayList<Integer> indices, int currentIndex, boolean increment, boolean afterToken) {
        String alternative = "";

        System.out.println("Found an Unary Operator: " + tList.get(currentIndex).lexeme);

        // Case: True/True -> x++
        if(increment && afterToken) {
            // remove x, "x += 1"
            alternative = tList.get(currentIndex-1).lexeme + "+=1";
            removeCharacter(tList, indices, currentIndex-1);
        }
        // Case: True/False -> ++x
        else if(increment && !afterToken) {
            // "x += 1", remove x
            alternative = tList.get(currentIndex+1).lexeme + "+=1";
            removeCharacter(tList, indices, currentIndex+1);
        }
        // Case: False/True -> x--
        else if(!increment && afterToken) {
            // remove x, "x -= 1"
            alternative = tList.get(currentIndex-1).lexeme + "-=1";
            removeCharacter(tList, indices, currentIndex-1);
        }
        // Case: False/False -> --x
        else {
            // "x -= 1", remove x
            alternative = tList.get(currentIndex+1).lexeme + "-=1";
            removeCharacter(tList, indices, currentIndex+1);
        }

        return alternative;
    }
}
