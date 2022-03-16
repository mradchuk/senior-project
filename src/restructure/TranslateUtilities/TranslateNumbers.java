package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateNumbers extends TranslationUtils {
    
    
    public static void handleNumber(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        System.out.println("Found a Number Literal: " + currentData.lexeme);
    }
}
