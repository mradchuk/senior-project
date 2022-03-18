package restructure.TranslateUtilities;

import java.util.Arrays;
import java.util.ArrayList;
import restructure.TokenData;

public class TranslateLabels extends TranslationUtils{
    
    private static ArrayList<String> javaVariableTypes = new ArrayList<>(Arrays.asList("BOOLEAN", "BYTE", "CHAR", "DOUBLE", "FLOAT", "INT", "LONG", "SHORT", "STRING"));
    public static TokenData mainDeclaration;

    public static void handleLabel(ArrayList<TokenData> tList, int currentIndex) {
        determineLabelType(tList, currentIndex);
        mainMethodCheck(tList, currentIndex);
    }

    private static void determineLabelType(ArrayList<TokenData> tList, int currentIndex) {
        
        // If previous token was keyword, current is a class, method, or variable name
        if(tList.get(currentIndex-1).token.equals("keyword")) {
            String previousLexeme = tList.get(currentIndex-1).lexeme;

            // Case: Variable Label
            if(javaVariableTypes.contains(previousLexeme)) {
                // Nothing here for now, likely to change
            }

            // Case: Class Label
            if(previousLexeme.equals("class")) {
                addClassColon(tList, currentIndex);
            }

            // Case: Method Label
            if(!(previousLexeme.equals("class")) && !javaVariableTypes.contains(previousLexeme)) {

                addDef(tList, currentIndex);
                addMethodColon(tList, currentIndex);
            }

        }        
    }

    private static void addClassColon(ArrayList<TokenData> tList, int currentIndex) {
        tList.get(currentIndex).lexeme += ":";
    }

    private static void addDef(ArrayList<TokenData> tList, int currentIndex) {
        String addedValue = "def ";
        tList.get(currentIndex).lexeme = addedValue + tList.get(currentIndex).lexeme;
    }

    private static void addMethodColon(ArrayList<TokenData> tList, int currentIndex) {
        ArrayList<TokenData> listAfterCurrentIndex = new ArrayList<TokenData>(tList.subList(currentIndex, tList.size()));
        int parenthesisCount = 0;

        // Find next open and close parenthesis. If multiple, keep count and when count is 0, add colon
        for(TokenData data: listAfterCurrentIndex) {
            if(data.lexeme.equals("(")) {
                parenthesisCount++;
            }

            if(data.lexeme.equals(")")) {
                parenthesisCount--;

                if(parenthesisCount == 0) {
                    data.lexeme += ":";
                    break;
                }
                
            }
        }
    }

    private static void mainMethodCheck(ArrayList<TokenData> tList, int currentIndex) {
        if(tList.get(currentIndex).lexeme.equals("def main")) {
            String declarationString = "\n\nif__name__==\"__main__\":\n\tMain.main([])";
            mainDeclaration = new TokenData("main declaration", declarationString);
        }
    }
}
