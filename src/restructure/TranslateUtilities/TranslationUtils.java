package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public abstract class TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                          GENERAL FUNCTIONS
    // --------------------------------------------------------------------------------

    public static Boolean checkImmediateToken(ArrayList<TokenData> list, boolean beforeCurrent, int currentIndex, String checkAgainst) {
        
        if(beforeCurrent) {
            return list.get(currentIndex-1).token.equals(checkAgainst);
        }
        else {
            return list.get(currentIndex+1).token.equals(checkAgainst);
        }
    }

    public static void decrementIndices(ArrayList<Integer> indices) {
        for(int nums: indices) {
            indices.set(indices.indexOf(nums), nums - 1);
        }
    }

    public static void removeCharacter(ArrayList<TokenData> dataList, ArrayList<Integer> indices, int removalIndex) { 
        System.out.println("Removing: " + dataList.get(removalIndex).lexeme);
        dataList.remove(removalIndex);
        decrementIndices(indices);
    }
}
