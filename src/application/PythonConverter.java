package application;
import java.util.*;

public class PythonConverter {

    // ------------------------------------------------------
    //                    VARIABLES
    // ------------------------------------------------------

    public static String resultPythonStr = "";

    static String statementArr[] = new String[3];
    static String equalOpStatement[] = new String[2];

    static String methodToCall = "";
    static String getClassName = "";

    static String outputPyStr = "";

    static String intCastStr = "";
    static String strCastStr = "";
    static String strCastFormat = "";

    static boolean checkEqualOperator = false;
    static boolean checkNotEqual = false;

    private static ArrayList<String> tokenList = new ArrayList<String>();
    private static ArrayList<String> lexemeList = new ArrayList<String>();
    private static ArrayList<Integer> indices = new ArrayList<Integer>();

    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - GENERAL
    // --------------------------------------------------------------------------------

    public static void deletePyStr(ArrayList<TokenData> list) {
        list.clear();
        outputPyStr = "";
        resultPythonStr = "";

    }

    /* Complex1 should use complex1 = Complex1() then complex1.main() to output python.
        Complex2 - Casting should use castSection() to output python.
     */
    public static String callClassMethod(String str) {

        if(!str.isEmpty()) {

            outputPyStr += "\n" + getClassName.toLowerCase();
            outputPyStr += " = " + getClassName + "()" + "\n";
            outputPyStr += getClassName.toLowerCase() + ".";
            outputPyStr += methodToCall + "()";

            return outputPyStr;

        } else {

            outputPyStr += "\n" + methodToCall + "()";

            return outputPyStr;
        }

    }

    public static void translateDriver(ArrayList<TokenData> list) {

        String pythonStr = "";

        String checkPrintStatement = "";

        int count = 0;

        boolean casting = false;

        System.out.println();

        ArrayList<TokenData> tempData = new ArrayList<TokenData>();

        for(int i = 0; i < list.size(); i++) {

            switch(list.get(i).token) {

                case "T_PUBLIC":

                    continue;

                case "T_CLASS":

                    pythonStr += list.get(i).lexeme + " ";
                    break;

                case "CLASS_NAME":

                    pythonStr += list.get(i).lexeme;
                    getClassName = list.get(i).lexeme;
                    break;

                case "METHOD_NAME":

                    methodToCall = list.get(i).lexeme;
                    pythonStr += "def " + list.get(i).lexeme;
                    break;

                case "T_LBRACE":

                    count++;

                    pythonStr += ":\n";

                    for(int j = 0; j < count; j++) {
                        pythonStr += "\t";
                    }

                    break;

                case "T_STATIC":

                    continue;

                case "T_VOID":

                    continue;

                case "MAIN_IDENTIFIER":

                    pythonStr += "def " + list.get(i).lexeme;
                    methodToCall = "main";
                    break;

                case "T_LPARENTH":

                    // double var =
                    if(statementArr[0] == "T_DOUBLE" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN") {
                        casting = true;
                        for(int l = 0; l < statementArr.length; l++) {
                            statementArr[l] = "";
                        }
                        pythonStr += "float(";
                        continue;
                    }

                    // int var =
                    if(statementArr[0] == "T_INT" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN") {
                        casting = true;
                        for(int l = 0; l < statementArr.length; l++) {
                            statementArr[l] = "";
                        }
                        pythonStr += "int(";
                        continue;
                    }

                    // float var =
                    if(statementArr[0] == "T_FLOAT" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN") {
                        casting = true;
                        for(int l = 0; l < statementArr.length; l++) {
                            statementArr[l] = "";
                        }
                        pythonStr += "";
                        continue;
                    }

                    // String var = Integer.toString, or String var = String.valueOf, String var = String.format
                    if(statementArr[0] == "String Identifier" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN"
                       && (intCastStr.equals("Integer.toString") || strCastStr.equals("String.valueOf") || strCastFormat.equals("String.format"))) {

                            casting = true;

                            intCastStr = "";
                            strCastStr = "";
                            strCastFormat = "";

                            for(int l = 0; l < statementArr.length; l++) {
                                statementArr[l] = "";
                            }

                            pythonStr += "str(";
                            continue;

                    }

                    pythonStr += list.get(i).lexeme;

                    break;

                case "STRING_IDENTIFIER":

                    statementArr[0] = "String Identifier";
                    break;

                case "ARGS_IDENTIFIER":

                    pythonStr += list.get(i).lexeme;
                    break;

                case "T_RPARENTH":

                    if(casting) {
                        casting = false;
                        continue;
                    } else {
                        pythonStr += list.get(i).lexeme;
                        break;
                    }

                case "System":

                    checkPrintStatement += list.get(i).lexeme;
                    break;

                case "T_DOT":

                    intCastStr += ".";
                    strCastStr += ".";
                    strCastFormat += ".";

                    checkPrintStatement += list.get(i).lexeme;
                    break;

                case "out":

                    checkPrintStatement += list.get(i).lexeme;
                    break;

                case "println":

                    checkPrintStatement += list.get(i).lexeme;

                    if(checkPrintStatement.equals("System.out.println")) {
                        pythonStr += "print";
                    }

                    break;

                case "string literal":

                    String temp = list.get(i).lexeme;

                    // If the string literal is format specifier "%f"
                    if(casting && temp.length() == 4 && temp.charAt(1) == '%' && temp.charAt(2) == 'f') {
                        continue;
                    } else {
                        pythonStr += list.get(i).lexeme;
                        break;
                    }

                case "T_SEMICOLON":

                    checkPrintStatement = "";
                    pythonStr += "\n";

                    //Determine syntax of python output by tabbing new lines
                    for(int k = 0; k < count; k++) {
                        pythonStr += "\t";
                    }

                    break;

                case "T_INT":

                    statementArr[0] = "T_INT";
                    continue;

                case "T_DOUBLE":

                    statementArr[0] = "T_DOUBLE";
                    break;

                case "T_FLOAT":

                    statementArr[0] = "T_FLOAT";
                    break;

                case "T_SHORT":

                    statementArr[0] = "T_SHORT";
                    continue;

                case "String Cast":

                    strCastStr += "String";
                    strCastFormat += "String";
                    break;

                case "Integer Cast":

                    intCastStr += "Integer";
                    break;

                case "toString method":

                    intCastStr += "toString";
                    break;

                case "valueOf method":

                    strCastStr += "valueOf";
                    break;

                case "String Format":

                    strCastFormat += "format";
                    break;

                case "VAR_IDENTIFIER":

                    /*
                        Analyze the last six characters or four characters in the collected python string "pythonStr"
                        to determine if the statement is casting the variable identifier.

                        If only 'print(' is occurring before the variable identifier, it is not casting and ensures
                        an additional enclosing ')' is not appended to pythonStr because a bug initially produced
                        'print(..))' instead of the correct 'print(..) output.
                    */

                    String lastSixChars = "";
                    String lastFourChars = "";

                    int lenOfLastSixChars = 6;
                    int lenOfLastFourChars = 4;
                    
                    int pyStrIndexLen = pythonStr.length() - 1;

                    int counter1 = (pyStrIndexLen - lenOfLastSixChars) + 1;
                    int counter2 = (pyStrIndexLen - lenOfLastFourChars) + 1;

                    while(counter1 < pyStrIndexLen) {
                        lastSixChars += pythonStr.charAt(counter1);
                        counter1++;
                    }
                    lastSixChars += pythonStr.charAt(pyStrIndexLen);

                    while(counter2 < pyStrIndexLen) {
                        lastFourChars += pythonStr.charAt(counter2);
                        counter2++;
                    }
                    lastFourChars += pythonStr.charAt(pyStrIndexLen);

                    if(lastSixChars.equals("float(")) {
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme + ")";
                        break;
                    } else if(lastFourChars.equals("int(") && lastSixChars.equals("print(")) { //prevents the 'print(..))' bug
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme;
                        break;
                    } else if(lastFourChars.equals("int(") && !lastSixChars.equals("print(")) {
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme + ")";
                        break;
                    } else if(lastFourChars.equals("str(")) {
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme + ")";
                        break;
                    } else {
                        statementArr[1] = "VAR_IDENTIFIER";
                        equalOpStatement[0] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme;
                        break;
                    }

                case "T_COMMA":

                    if(casting) {
                        continue;
                    }


                case "T_ASSIGN":

                    /* Check for the equal operator */
                    if(list.get(i+1).lexeme.equals("=")) {

                        checkEqualOperator = true;
                        break;

                    } else if(checkEqualOperator) {

                        pythonStr += " == ";
                        equalOpStatement[1] = "Equal Operator";
                        checkEqualOperator = false;

                        /* James B's logical operators code */
                        tempData.add(new TokenData("comparison operator", "=="));

                        break;

                    } else if(checkNotEqual) {

                        pythonStr += " != ";
                        checkNotEqual = false;
                        break;

                    } else {

                        statementArr[2] = "T_ASSIGN";
                        pythonStr += " " + list.get(i).lexeme + " ";

                        /* James B's logical operators code */
                        tempData.add(new TokenData("assignment operator", "="));

                        break;

                    }


                case "NUMBER":

                    /*
                       The stream tokenizer seems to produce numbers ending in '.0' if they are whole (ex: 2045 -> 2045.0).
                       So we need a way to ensure an incoming whole number does not end in '.0' if it is assigned
                       to a variable of type integer of short in the statement.
                    */

                    //If 'int x =' or 'short x ='
                    if((statementArr[0] == "T_INT" || statementArr[0] == "T_SHORT") && statementArr[1] == "VAR_IDENTIFIER"
                       && statementArr[2] == "T_ASSIGN") {

                       String num = list.get(i).lexeme;

                       // Remove the last two characters in the number if its assigned variable is type short or int
                       if(num.charAt(num.length()-2) == '.' && num.charAt(num.length()-1) == '0') {

                           num = num.substring(0, num.length() -1);
                           num = num.substring(0, num.length() -1);

                           pythonStr += num + " ";

                           for(int z = 0; z < statementArr.length; z++) {
                               statementArr[z] = "";
                           }

                           break;

                       } else {

                           pythonStr += list.get(i).lexeme + " ";
                           break;

                       }


                    } else if(equalOpStatement[0] == "VAR_IDENTIFIER" && equalOpStatement[1] == "Equal Operator") {

                        String num = list.get(i).lexeme;

                        // Remove the last two characters in the number if its assigned variable is type short or int
                        if(num.charAt(num.length()-2) == '.' && num.charAt(num.length()-1) == '0') {

                            num = num.substring(0, num.length() -1);
                            num = num.substring(0, num.length() -1);

                            pythonStr += num;

                            for(int a = 0; a < equalOpStatement.length; a++) {
                                equalOpStatement[a] = "";
                            }

                            break;

                        }

                    } else {

                        pythonStr += list.get(i).lexeme + " ";
                        break;

                    }

                case "logical operator":

                    if(list.get(i).lexeme.equals("&") && list.get(i+1).lexeme.equals("&")) {

                        pythonStr += " and ";

                        /* James B's logic operators code */
                        tempData.add(new TokenData("logical operator", "&"));
                        tempData.add(new TokenData("logical operator", "&"));

                        break;
                    }

                    if(list.get(i).lexeme.equals("|") && list.get(i+1).lexeme.equals("|")) {

                        pythonStr += " or ";

                        /* James B's logical operators code */
                        tempData.add(new TokenData("logical operator", "|"));
                        tempData.add(new TokenData("logical operator", "|"));

                        break;
                    }



                case "unary operator":

                    if(list.get(i).lexeme.equals("!") && list.get(i+1).lexeme.equals("=")) {
                        checkNotEqual = true;
                        break;
                    }

                case "relational operator":

                    if(list.get(i).lexeme.equals("<") || list.get(i).lexeme.equals(">")) {
                        pythonStr += " " + list.get(i).lexeme + " ";
                        break;
                    }

            }

        }

        /*
            Add the python string to the python string that calls the class method or simply
            the method depending on if Complex1 or Complex2 - Casting is being run.
         */
        resultPythonStr = pythonStr + callClassMethod(getClassName);
        System.out.println(pythonStr + outputPyStr);

        System.out.println();

        /* James B's logical operators code */
        splitTokenList(tempData);
        handleLogicalOperators();
        handleUnaryOperators();

    }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - GENERAL
    // --------------------------------------------------------------------------------

    // Using the list of tokens to find where all logical operators are located in
    // lexeme list
    private static void listAllOfType(String thisType) {
        
        // Clean slate
        indices.clear();

        for (int index = 0; index < tokenList.size(); index++) {
            if (tokenList.get(index).equals(thisType)) {
                indices.add(index);
            }
        }
    }

    // Decreases each value in the indice list by one. Used when the primary list removes an item
    // to keep the indice list accurate to the list it's representing
    private static void decrementIndices() {
        for (int nums : indices) {
            indices.set(indices.indexOf(nums), nums - 1);
        }
    }

    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - LOGICAL OPERATORS
    // --------------------------------------------------------------------------------

    // Beginning of logical operator logic
    private static void handleLogicalOperators() {
        String logicalOperatorString = "logical operator";
        String tempLexeme;
        int tempLexemeInt;

        // Where are their specific locations
        listAllOfType(logicalOperatorString);

        // Loop until all logical operators are handled
        while (indices.size() > 0) {
            tempLexemeInt = indices.get(0);
            tempLexeme = lexemeList.get(tempLexemeInt);

            // Case: &
            if (tempLexeme == "&") {
                tempLexeme = handleAmpersand(tempLexemeInt);
            }
            // Case: |
            else if(tempLexeme == "|") {
                tempLexeme = handleVertBar(tempLexemeInt);
            }
            // Case: !
            else {
                // Must be a ! operator
                tempLexeme = handleExclamation();
            }
            

            // Replace symbol with word
            lexemeList.set(tempLexemeInt, tempLexeme);
            // Moves onto next logical operator
            indices.remove(indices.get(0));
        }
    }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - LOGICAL OPERATORS
    // --------------------------------------------------------------------------------

    // Makes the TokenData a little more managable by splitting into two
    private static void splitTokenList(List<TokenData> allTokenData) {
        for (TokenData tokenData : allTokenData) {
            tokenList.add(tokenData.token);
            lexemeList.add(tokenData.lexeme);
        }
    }

    

    // Ampersand logic
    private static String handleAmpersand(int tempLexemeInt) {
        String ampersandSymbol = "&";

        // Check for double &&
        handleDoubles(tempLexemeInt, lexemeList.size(), ampersandSymbol);

        // If Single & in java, needs to be replaced with "and"
        // Single & in Python is a bitwise AND function, which is very different
        return "and";
    }

    // Vertical Bar logic
    private static String handleVertBar(int tempLexemeInt) {
        String vertBarSymbol = "|";

        // Check for double ||
        handleDoubles(tempLexemeInt, lexemeList.size(), vertBarSymbol);

        return "or";
    }

    // Exclamation logic
    private static String handleExclamation() {
        // String exclamationSymbol = "!";
        // No doubles for !, so if the symbol is ! it just needs to be replaced with "not".
        return "not";
    }

    // Logic for double symbols && and ||
    private static void handleDoubles(int tempLexemeInt, int lexListSize, String symbolToCheck) {
        if (tempLexemeInt + 1 != lexListSize && lexemeList.get(tempLexemeInt + 1).equals(symbolToCheck)) {
            // && becomes 'and', both lists need doubled sign removed
            lexemeList.remove(tempLexemeInt + 1);
            tokenList.remove(tempLexemeInt + 1);
            indices.remove(indices.get(0));

            decrementIndices();        
        }
    }

    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - UNARY OPERATORS
    // --------------------------------------------------------------------------------

    // Beginning of unary operator logic
    private static void handleUnaryOperators() {
        String unaryOperatorString = "unary operator";
        String variableString = "VAR_IDENTIFIER";
        String tempLexeme;

        int tempLexemeInt;

        // true = ++, false = --;
        boolean unaryType;
        // true = ++x/--x, false = x++/x--
        boolean beforeAfter;

        listAllOfType(unaryOperatorString);

        // Loop until all unary operators are handled
        while(indices.size() > 0) {
            tempLexemeInt = indices.get(0);
            tempLexeme = lexemeList.get(tempLexemeInt);

            unaryType = false;
            beforeAfter = false;

            // Case: ++
            if(tempLexeme == "++") {
                unaryType = true;
            }
            // If not changed -> Case: --
            
            if(tempLexemeInt != 0 && tokenList.get(tempLexemeInt - 1).equals(variableString)) {
                beforeAfter = true;
            }

            // Gets correct replacement for unary operator case
            tempLexeme = handleUnary(tempLexemeInt, unaryType, beforeAfter);
            
            // If false, element behind tempLexemeInt is removed, so tempLexemeInt needs to shift as well
            if(!beforeAfter) {tempLexemeInt -= 1;}

            // Replace symbols with new expression
            lexemeList.set(tempLexemeInt, tempLexeme);
            // Moves onto the next unary operator
            indices.remove(indices.get(0));
        }
    }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - UNARY OPERATORS
    // --------------------------------------------------------------------------------

    // Main logic for handling Unary Operator cases
    private static String handleUnary(int lexemeInt, boolean type, boolean placement) {
        String unaryReplacement;
        // Case: True/True -> ++x
        if(type && placement) {
            // "x += 1", remove x
            unaryReplacement = lexemeList.get(lexemeInt+1) + "+=1";
            lexemeList.remove(lexemeInt + 1);
        }
        // Case: True/False -> x++
        else if(type && !placement) {
            // remove x, "x += 1"
            unaryReplacement = lexemeList.get(lexemeInt-1) + "+=1";
            lexemeList.remove(lexemeInt-1);
        }
        // Case: False/True -> --x
        else if(!type && placement) {
            // "x -= 1", remove x
            unaryReplacement = lexemeList.get(lexemeInt+1) + "-=1";
            lexemeList.remove(lexemeInt + 1);
        } 
        // Case: False/False -> x--
        else {
            // remove x, "x -= 1"
            unaryReplacement = lexemeList.get(lexemeInt-1) + "+=1";
            lexemeList.remove(lexemeInt-1);
        }
        
        // Each case removes something, so the indices need to be decremented else 
        decrementIndices();

        return unaryReplacement;
    }

}

