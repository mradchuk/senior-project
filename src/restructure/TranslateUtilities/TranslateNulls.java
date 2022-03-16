package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateNulls extends TranslationUtils {
    

    public static void handleNull(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        System.out.println("Found a Null Literal: NULL");
    }
}
