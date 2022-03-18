package restructure.TranslateUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import restructure.TokenData;

public abstract class TranslationUtils {
    
    // --------------------------------------------------------------------------------
    //                              VARIABLES
    // --------------------------------------------------------------------------------

    public static int tabCount = 0;

    protected static HashMap<String, ArrayList<String>> pythonMap = new HashMap<String, ArrayList<String>>();

    public static ArrayList<Integer> indicesForRemoval = new ArrayList<Integer>();

    // --------------------------------------------------------------------------------
    //                          GENERAL FUNCTIONS
    // --------------------------------------------------------------------------------

    public static void populatePythonMap() {
        ArrayList<String> keywords = new ArrayList<String>();
        keywords.add("TRUE");
        keywords.add("FALSE");
        keywords.add("NONE");
        keywords.add("AND");
        keywords.add("AS");
        keywords.add("ASSERT");
        keywords.add("ASYNC");

        keywords.add("AWAIT");
        keywords.add("BREAK");
        keywords.add("CLASS");
        keywords.add("CONTINUE");
        keywords.add("DEF");
        keywords.add("DEL");
        keywords.add("ELIF");

        keywords.add("ELSE");
        keywords.add("EXCEPT");
        keywords.add("FINALLY");
        keywords.add("FOR");
        keywords.add("FROM");
        keywords.add("GLOBAL");
        keywords.add("IF");

        keywords.add("IMPORT");
        keywords.add("IN");
        keywords.add("IS");
        keywords.add("LAMBDA");
        keywords.add("NONLOCAL");
        keywords.add("NOT");
        keywords.add("OR");

        keywords.add("PASS");
        keywords.add("RAISE");
        keywords.add("RETURN");
        keywords.add("TRY");
        keywords.add("WHILE");
        keywords.add("WITH");
        keywords.add("YIELD");

        pythonMap.put("keyword", keywords);
    }

    // If the value is a Python keyword, return true, else return false
    public static Boolean isPythonKeyword(String value) {
        if(pythonMap.get("keyword").contains(value.toUpperCase())) {
            return true;
        }
        return false;
    }

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

    // To make merging all of the modified lists simpler, this keeps track of all locations that need to be removed
    // So that removals can occur after all merges have taken place
    public static void removeCharacter(ArrayList<TokenData> dataList, ArrayList<Integer> indices, int removalIndex) {        
        indicesForRemoval.add(removalIndex);
        //dataList.remove(removalIndex);
        //decrementIndices(indices);
    }

    public static String replaceWithTab() {
        return "\t";
    }

    public static String getCurrentTabs(){
        String tabString = "";
        for(int index = 0; index < tabCount; index++) {
            tabString += "\t";
        }
        return tabString;
    }
}
