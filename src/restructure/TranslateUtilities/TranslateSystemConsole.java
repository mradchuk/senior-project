package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateSystemConsole extends TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                  PRIMARY SYSTEM CONSOLE SPECIFICATION FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleSystemConsole(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        String newLexeme;
        newLexeme = determineSystemConsole(tList, currentData.lexeme, currentIndex);
        currentData.lexeme = newLexeme;
    }

    // --------------------------------------------------------------------------------
    //                  SECONDARY SYSTEM CONSOLE SPECIFICATION FUNCTION
    // --------------------------------------------------------------------------------

    private static String determineSystemConsole(ArrayList<TokenData> tList, String wordToCheck, int currentIndex) {
        
        // Case: System
        if(wordToCheck.toUpperCase().equals("SYSTEM")) {handleSystem();}
        
        // Case: Out
        if(wordToCheck.toUpperCase().equals("OUT")) {handleOut();}

        // Case: Println
        if(wordToCheck.toUpperCase().equals("PRINTLN")) {handlePrintLn();}

        return "";
    }

    // Case: System
    private static void handleSystem() {
        System.out.println("Found a System Console Call: System");
    }

    // Case: Out
    private static void handleOut() {
        System.out.println("Found a System Console Call: Out");
    }

    // Case: Println
    private static void handlePrintLn() {
        System.out.println("Found a System Console Call: Println");
    }
    
}
