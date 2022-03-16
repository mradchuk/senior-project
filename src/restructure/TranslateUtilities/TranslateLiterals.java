package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateLiterals extends TranslationUtils {
    

    public static void handleStringLiteral(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        System.out.println("Found a String Literal: " + currentData.lexeme);
    }
}
