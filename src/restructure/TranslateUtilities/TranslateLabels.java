package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateLabels extends TranslationUtils{
    


    public static void handleLabel(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        System.out.println("Found a String Label: " + currentData.lexeme);
    }
}
