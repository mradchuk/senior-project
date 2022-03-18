package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateSystemConsole extends TranslationUtils {
    
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
    //                  PRIMARY SYSTEM CONSOLE SPECIFICATION FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleSystemConsole(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        determineSystemConsole(tList, currentData.lexeme, currentIndex);
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY SYSTEM CONSOLE SPECIFICATION FUNCTION
    // --------------------------------------------------------------------------------

    private static void determineSystemConsole(ArrayList<TokenData> tList, String wordToCheck, int currentIndex) {
        
        // Case: System
        if(wordToCheck.toUpperCase().equals("SYSTEM")) {handleSystem(tList, currentIndex);}
        
        // Case: Out
        if(wordToCheck.toUpperCase().equals("OUT")) {handleOut(tList, currentIndex);}

        // Case: Println
        if(wordToCheck.toUpperCase().equals("PRINTLN")) {handlePrintLn(tList, currentIndex);}

    }

    // Case: System
    private static void handleSystem(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
        removeCharacter(tList, indices, (currentIndex+1));
    }

    // Case: Out
    private static void handleOut(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
        removeCharacter(tList, indices, (currentIndex+1));
    }

    // Case: Println
    private static void handlePrintLn(ArrayList<TokenData> tList, int currentIndex) {
        tList.get(currentIndex).lexeme = "print";
    }
    
}
