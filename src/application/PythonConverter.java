package application;
import java.util.*;
import java.lang.*;

public class PythonConverter {

    // ------------------------------------------------------
    //                    VARIABLES
    // ------------------------------------------------------

    static ArrayList<String> methodCollection = new ArrayList<String>();
    static ArrayList<String> variablesOutsideClassConstructor = new ArrayList<>();

    private static ArrayList<String> tokenList = new ArrayList<String>();
    private static ArrayList<String> lexemeList = new ArrayList<String>();
    private static ArrayList<Integer> indices = new ArrayList<Integer>();

    public static ArrayList<TokenData> doWhileCondition = new ArrayList<>();

    static String[] statementArr = new String[3];
    static String[] equalOpStatement = new String[2];
    static String[] arrInitAndDeclaration = new String[5];
    static String[] arrayAllocateMemory = new String[7];
    static String[] arrAtStaticMethod = new String[3];

    public static String resultPythonStr = "";

    static String pythonStr = "";
    static String checkPrintStatement = "";
    static String intCastStr = "";
    static String strCastStr = "";
    static String strCastFormat = "";
    static String getClassName  = "";

    static int count = 0;
    static int skipParenthesis = 0, doWhileBraceCount=0;

    static boolean checkEqualOperator = false;
    static boolean checkNotEqual = false;
    static boolean casting = false;
    static boolean castingArrayElement = false;
    static boolean isArrayStatement = false;
    static boolean isArrayStatementAddRbracket = false;
    static boolean castingMethodNameAddRParenth = false;
    static boolean isArrDecMemAllocStatement = false;
    static boolean doWhileLoop = false;
    static boolean castJavaMath = false;
    static boolean insideClassConstructor = false;
    static boolean classHasClassConstructor = false;
    static boolean insideMainMethod = false;
    static boolean isFloatingPointFormatSpecifier = false;
    static boolean isFixedPointDecimalNumber = false;

    //If file needs to import python math module
    static boolean needsMathImport = false;
    //Need this variable so math statements aren't identified as casting statements when also declaring a variable on the same line
    static boolean isAMathMethodDoNotCastIt = false;


    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - GENERAL
    // --------------------------------------------------------------------------------

    public static void deletePyStr(ArrayList<TokenData> list) {

        list.clear();

        tokenList.clear();
        lexemeList.clear();
        indices.clear();
        doWhileCondition.clear();
        methodCollection.clear();
        variablesOutsideClassConstructor.clear();

        for(int i1 = 0; i1 < statementArr.length; i1++) {
            statementArr[i1] = "";
        }

        for(int i2 = 0; i2 < equalOpStatement.length; i2++) {
            equalOpStatement[i2] = "";
        }

        for(int i3 = 0; i3 < arrInitAndDeclaration.length; i3++) {
            arrInitAndDeclaration[i3] = "";
        }

        for(int i4 = 0; i4 < arrayAllocateMemory.length; i4++) {
            arrayAllocateMemory[i4] = "";
        }

        for(int i5 = 0; i5 < arrAtStaticMethod.length; i5++) {
            arrAtStaticMethod[i5] = "";
        }

        resultPythonStr = "";
        pythonStr = "";
        checkPrintStatement = "";
        intCastStr = "";
        strCastStr = "";
        strCastFormat = "";
        getClassName = "";

        count = 0;

        checkEqualOperator = false;
        checkNotEqual = false;
        casting = false;
        castingArrayElement = false;
        needsMathImport = false;
        isAMathMethodDoNotCastIt = false;
        isArrayStatement = false;
        isArrayStatementAddRbracket = false;
        castingMethodNameAddRParenth = false;
        isArrDecMemAllocStatement = false;
        doWhileLoop = false;
        castJavaMath = false;
        insideClassConstructor = false;
        classHasClassConstructor = false;
        insideMainMethod = false;
        isFloatingPointFormatSpecifier = false;
        isFixedPointDecimalNumber = false;

        skipParenthesis = 0;

    }

    //Function called at the end to add to result string that outputs Python
    public static String callingMainOutput(String className) {

        String resultStr = "";

        resultStr += "if __name__ == " + '"' + "__main__" + '"' + ":";
        resultStr += "\n\t";

        resultStr += className + ".main([])";

        return resultStr;

    }

    public static boolean isDatatype(String str) {
        if(str.equals("int") || str.equals("short") || str.equals("double") || str.equals("byte") || str.equals("float")
                || str.equals("long") || str.equals("String") || str.equals("char") || str.equals("boolean")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isArithmeticOperator(String str) {
        if(str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
            return true;
        } else {
            return false;
        }
    }

    //Method for determining if checkprintstatement is a math statement to avoid adding :
    public static boolean isMathStatement(String substring)
    {
        if (substring.equals("Math.abs"))
            return true;
        else if (substring.equals("Math.min"))
            return true;
        else if (substring.equals("Math.max"))
            return true;
        else if (substring.equals("Math.pow"))
            return true;
        else if (substring.equals("Math.acos"))
            return true;
        else if (substring.equals("Math.asin"))
            return true;
        else if (substring.equals("Math.atan"))
            return true;
        else if (substring.equals("Math.atan2"))
            return true;
        else if (substring.equals("Math.cos"))
            return true;
        else if (substring.equals("Math.cosh"))
            return true;
        else if (substring.equals("Math.exp"))
            return true;
        else if (substring.equals("Math.log"))
            return true;
        else if (substring.equals("Math.log10"))
            return true;
        else if (substring.equals("Math.sin"))
            return true;
        else if (substring.equals("Math.sinh"))
            return true;
        else if (substring.equals("Math.sqrt"))
            return true;
        else if (substring.equals("Math.tan"))
            return true;
        else if (substring.equals("Math.tanh"))
            return true;
        else if (substring.equals("Math.toRadians"))
            return true;
        else if (substring.equals("Math.Degrees"))
            return true;
        else
            return false;
    }

    public static void translateDriver(ArrayList<TokenData> list) {

        System.out.println();

        ArrayList<TokenData> tempData = new ArrayList<TokenData>();

        arrAtStaticMethod[0] = "";
        arrAtStaticMethod[1] = "";
        arrAtStaticMethod[2] = "";

        statementArr[0] = "";

        for(int i = 0; i < list.size(); i++) {

            switch(list.get(i).token) {

                case "T_PUBLIC":

                    continue;

                case "T_CLASS":

                    pythonStr += list.get(i).lexeme + " ";
                    break;

                case "CLASS_NAME":

                    // public class_name() {  }
                    if(list.get(i-1).lexeme.equals("public") && list.get(i+1).lexeme.equals("(")) {

                        classHasClassConstructor = true;
                        insideClassConstructor = true;

                        /* Both default and parameterized constructors are declared with 'self' */
                        pythonStr += "def __init__(self, ";

                    } else if(!insideMainMethod){

                        pythonStr += list.get(i).lexeme;
                        getClassName = list.get(i).lexeme;

                    } else { //inside of main method

                        if(isArrDecMemAllocStatement) {
                            arrayAllocateMemory[3] = list.get(i).token;
                            break;

                            // class_name[] variable_identifier
                        } else if(list.get(i+1).lexeme.equals("[") && list.get(i+2).lexeme.equals("]")
                                && list.get(i+3).token.equals("VAR_IDENTIFIER")) {
                            break;

                            // class_name variable_identifier = new
                        } else if(list.get(i+1).token.equals("VAR_IDENTIFIER") && list.get(i+2).lexeme.equals("=")
                                && list.get(i+3).lexeme.equals("new")) {
                            break;

                        } else {
                            pythonStr += list.get(i).lexeme;
                        }

                    }

                    break;

                case "METHOD_NAME":

                    arrAtStaticMethod[2] = "METHOD_NAME";

                    /* Here we deal with static methods or methods with no return type */
                    if(arrAtStaticMethod[0].equals("static") && arrAtStaticMethod[1].equals("void") && arrAtStaticMethod[2].equals("METHOD_NAME")) {

                        pythonStr += "@staticmethod\n";

                        // the following method should be on the same indentation level as @staticmethod
                        for(int counting = 0; counting < count; counting++) {
                            pythonStr += "\t";
                        }

                        for(int clStaticMethod = 0; clStaticMethod < arrAtStaticMethod.length; clStaticMethod++) {
                            arrAtStaticMethod[clStaticMethod] = "";
                        }
                    }

                    if(arrAtStaticMethod[0].equals("static") && isDatatype(arrAtStaticMethod[1]) && arrAtStaticMethod[2].equals("METHOD_NAME")) {

                        pythonStr += "@staticmethod\n";

                        // the following method should be on the same indentation level as @staticmethod
                        for(int counting = 0; counting < count; counting++) {
                            pythonStr += "\t";
                        }

                        for(int clStaticMethod = 0; clStaticMethod < arrAtStaticMethod.length; clStaticMethod++) {
                            arrAtStaticMethod[clStaticMethod] = "";
                        }
                    }


                    String lastFourCharacters = "";
                    int lengthOfLastFourChars = 4;

                    int pyStrIndexLength = pythonStr.length() - 1;

                    int countr = (pyStrIndexLength - lengthOfLastFourChars) + 1;


                    while(countr < pyStrIndexLength) {
                        lastFourCharacters += pythonStr.charAt(countr);
                        countr++;
                    }
                    lastFourCharacters += pythonStr.charAt(pyStrIndexLength);

                    // if method is not inside main, meaning it is not tied to or being called by an object of the class instance
                    // because creating objects of class instances are done in the main method to invoke instance methods (or variables) in the class
                    if(classHasClassConstructor && !insideMainMethod && methodCollection.contains(list.get(i).lexeme)) {
                        if(lastFourCharacters.equals("str(")) {
                            castingMethodNameAddRParenth = true;
                        }
                        pythonStr += "self.";

                    }

                    /* How we deal with different Java method types and determine if the are being defined or only accessed */
                    if((list.get(i - 2).lexeme.equals("static")
                            || list.get(i - 2).lexeme.equals("public"))
                            || list.get(i - 2).lexeme.equals("private")){
                        pythonStr += "def " + list.get(i).lexeme;
                    }
                    else if(insideMainMethod && !classHasClassConstructor) {
                        pythonStr += getClassName + "." + list.get(i).lexeme;
                    }

                    else if(!(list.get(i - 1).lexeme.equals("static")
                            && !list.get(i - 1).lexeme.equals("public"))
                            && !list.get(i - 2).lexeme.equals("private")
                            && !classHasClassConstructor){
                        pythonStr += getClassName + "." + list.get(i).lexeme;
                    }

                    else {
                        pythonStr += list.get(i).lexeme;
                    }

                    /* Add every method in the class to the collection */
                    if(!methodCollection.contains(list.get(i).lexeme)) {
                        methodCollection.add(list.get(i).lexeme);
                    }

                    break;

                case "T_LBRACE":
                    if(doWhileLoop)
                        doWhileBraceCount+=1;

                    // data_type[] var_identifier = {number1, number2, number3};
                    if((arrInitAndDeclaration[0] == "T_INT" || arrInitAndDeclaration[0] == "String Identifier") && arrInitAndDeclaration[1] == "T_LBRACKET"
                            && arrInitAndDeclaration[2] == "T_RBRACKET" && arrInitAndDeclaration[3] == "VAR_IDENTIFIER" && arrInitAndDeclaration[4] == "T_ASSIGN"
                            && ((list.get(i+1).token == "NUMBER" && list.get(i+2).token == "T_COMMA") || list.get(i+1).token == "T_RBRACE")) {

                        isArrayStatement = true;
                        isArrayStatementAddRbracket = true;

                        for(int clrArr = 0; clrArr < arrInitAndDeclaration.length; clrArr++) {
                            arrInitAndDeclaration[clrArr] = "";
                        }

                    }

                    /* We have hit the left brace, time for a new line. */
                    if(count >= 0 && isArrayStatement == false) {
                        pythonStr += ":\n";

                        /* If there is an array, stay on the same line */
                    } else if(count >= 1 && isArrayStatement == true) {
                        pythonStr += "[";

                        // ensures all statement bellow the array are at the same indentation level
                        count++;
                        break;

                    }

                    //determines how many times a statement will be indented based on its syntactic position in the code
                    count++;

                    //tabbing or indenting the statement how many # of times to get the correct python syntax
                    for(int j = 0; j < count; j++) {
                        pythonStr += "\t";
                    }

                    break;

                case "T_RBRACE":
                    if(doWhileLoop)
                        doWhileBraceCount -= 1;

                    if(isArrayStatementAddRbracket) {
                        pythonStr += "]";
                        isArrayStatementAddRbracket = false;
                    }

                    pythonStr += "\n";

                    //We have reached the end of the current function. We now need less indentation for a new function declaration.
                    //Once we reach the left brace of the new function, make indentation based on reduced value of this 'count'.
                    count--;

                    for(int j = 0; j < count; j++) {
                        pythonStr += "\t";
                    }

                    insideClassConstructor = false;

                    break;

                case "T_LBRACKET":

                    // '(array or object) [ variable ]' or '(array or object) [ number ]'
                    if(list.get(i - 1).token.equals("VAR_IDENTIFIER") && (list.get(i + 1).token.equals("VAR_IDENTIFIER")
                            || list.get(i + 1).token.equals("NUMBER")) && list.get(i + 2).lexeme.equals("]")) {
                        pythonStr += "[";
                        break;
                    }

                    if(isArrDecMemAllocStatement) {

                        arrayAllocateMemory[4] = list.get(i).token;
                        pythonStr += list.get(i).lexeme;

                        break;

                    }

                    arrInitAndDeclaration[1] = list.get(i).token;

                    break;

                case "T_RBRACKET":

                    // '(array or object) [ variable ]' or '(array or object) [ number ]'
                    if((list.get(i - 1).token.equals("VAR_IDENTIFIER") || list.get(i - 1).token.equals("NUMBER") )
                            && list.get(i - 2).lexeme.equals("[") && list.get(i - 3).token.equals("VAR_IDENTIFIER")) {
                        pythonStr += "]";
                        break;
                    }

                    if(isArrDecMemAllocStatement) {

                        // var_identifier = [""] * length of the array
                        if(arrayAllocateMemory[3] == "String Identifier") {
                            pythonStr += "\"" + "\"" + "]*" + arrayAllocateMemory[5];
                        }

                        // var_identifier = [0] * length of the array
                        if(arrayAllocateMemory[3] == "T_INT" || arrayAllocateMemory[3] == "T_DOUBLE") {
                            pythonStr += "0" + "]*" + arrayAllocateMemory[5];
                        }

                        // 'object = [None] * (length of class object)'
                        if(arrayAllocateMemory[3] == "CLASS_NAME") {
                            pythonStr += "None] * (" + arrayAllocateMemory[5] + ")";
                        }

                        break;

                    }

                    arrInitAndDeclaration[2] = list.get(i).token;

                    break;

                case "T_STATIC":

                    arrAtStaticMethod[0] = list.get(i).lexeme;

                    continue;

                case "T_VOID":

                    arrAtStaticMethod[1] = list.get(i).lexeme;

                    continue;

                case "MAIN_IDENTIFIER":

                    arrAtStaticMethod[2] = "MAIN_IDENTIFIER";

                    /* Similar to how we are translating non-main methods */
                    if(arrAtStaticMethod[0].equals("static") && arrAtStaticMethod[1].equals("void") && arrAtStaticMethod[2].equals("MAIN_IDENTIFIER")) {
                        pythonStr += "@staticmethod\n";

                        for(int counting = 0; counting < count; counting++) {
                            pythonStr += "\t";
                        }

                        for(int clStaticMethod1 = 0; clStaticMethod1 < arrAtStaticMethod.length; clStaticMethod1++) {
                            arrAtStaticMethod[clStaticMethod1] = "";
                        }
                    }

                    pythonStr += "def " + list.get(i).lexeme;

                    insideMainMethod = true;

                    break;

                case "T_LPARENTH":

                    if(skipParenthesis!=0) {
                        if (list.get(i-1).lexeme.equals("equals")) {
                            skipParenthesis += 1;
                            break;
                        }
                        break;
                    }

                    // double var_identifier = ( data_type
                    if(statementArr[0] == "T_DOUBLE" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN" && isDatatype(list.get(i+1).lexeme) && !isAMathMethodDoNotCastIt) {

                        //doubles casted to float in python
                        pythonStr += "float(";

                        casting = true;

                        for(int a = 0; a < statementArr.length; a++) {
                            statementArr[a] = "";
                        }

                        break;
                    }

                    // 'var_identifier = ( data_type' or 'int var_identifier = ( data_type' for casting statements
                    if((statementArr[0] == "" || statementArr[0] == "T_INT") && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN" && isDatatype(list.get(i+1).lexeme) && !isAMathMethodDoNotCastIt) {

                        pythonStr += "int(";

                        for(int b = 0; b < statementArr.length; b++) {
                            statementArr[b] = "";
                        }

                        casting = true;

                        break;

                    }

                    // float var_identifier = ( data_type
                    if(statementArr[0] == "T_FLOAT" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN" && isDatatype(list.get(i+1).lexeme) && !isAMathMethodDoNotCastIt) {


                        pythonStr += "float(";

                        for(int c = 0; c < statementArr.length; c++) {
                            statementArr[c] = "";
                        }

                        casting = true;

                        break;
                    }

                    // long var_identifier = ( data_type
                    if(statementArr[0] == "T_LONG" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN" && isDatatype(list.get(i+1).lexeme) && !isAMathMethodDoNotCastIt) {


                        pythonStr += "";

                        for(int d = 0; d < statementArr.length; d++) {
                            statementArr[d] = "";
                        }

                        casting = true;

                        break;
                    }

                    // byte var_identifier = ( data_type
                    if(statementArr[0] == "T_BYTE" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN" && isDatatype(list.get(i+1).lexeme) && !isAMathMethodDoNotCastIt) {

                        //treat a float in Python the same way in Java
                        pythonStr += "";

                        for(int e = 0; e < statementArr.length; e++) {
                            statementArr[e] = "";
                        }

                        casting = true;

                        break;

                    }

                    // String var_identifier = Integer.toString, or String var_identifier = String.valueOf
                    // var_identifier = Integer.toString, or var = String.valueOf
                    if((statementArr[0] == "String Identifier" || statementArr[0] == "") && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN"
                            && (intCastStr.equals("Integer.toString") || strCastStr.equals("String.valueOf"))) {

                        pythonStr += "str(";

                        for(int d = 0; d < statementArr.length; d++) {
                            statementArr[d] = "";
                        }

                        casting = true;

                        intCastStr = "";
                        strCastStr = "";
                        strCastFormat = "";

                        break;

                    }

                    // '+ String.valueOf' or '+ Integer.toString'
                    if(list.get(i-4).lexeme.equals("+") && (intCastStr.equals("Integer.toString") || strCastStr.equals("String.valueOf"))) {

                        pythonStr += "str(";

                        for(int d = 0; d < statementArr.length; d++) {
                            statementArr[d] = "";
                        }

                        casting = true;

                        intCastStr = "";
                        strCastStr = "";
                        strCastFormat = "";

                        break;

                    }

                    // 'String var_identifier = String.format' floating point format
                    if(statementArr[0] == "String Identifier" && statementArr[1] == "VAR_IDENTIFIER" && statementArr[2] == "T_ASSIGN"
                            && strCastFormat.equals("String.format")) {

                        if(list.get(i+1).lexeme.length() == 4 && list.get(i+1).lexeme.charAt(1) == '%' && list.get(i+1).lexeme.charAt(2) == 'f') {

                            // floating point format string specifier
                            pythonStr += "\"" + "{}" + "\"" + ".format(";

                            isFloatingPointFormatSpecifier = true;

                            for(int d = 0; d < statementArr.length; d++) {
                                statementArr[d] = "";
                            }

                            casting = true;

                            intCastStr = "";
                            strCastStr = "";

                            break;
                        }

                        // 'String var_identifier = String.format' decimal formatting
                        if(list.get(i+1).lexeme.length() == 6 && list.get(i+1).lexeme.charAt(1) == '%' && list.get(i+1).lexeme.charAt(2) == '.'
                                && Character.isDigit(list.get(i+1).lexeme.charAt(3)) && list.get(i+1).lexeme.charAt(4) == 'f') {

                            // decimal fixed point format
                            pythonStr += "\"" + "{:." + list.get(i+1).lexeme.charAt(3) + "f}" + "\"" + ".format(";

                            isFixedPointDecimalNumber = true;

                            for(int d = 0; d < statementArr.length; d++) {
                                statementArr[d] = "";
                            }

                            casting = true;

                            intCastStr = "";
                            strCastStr = "";

                            break;
                        }



                    }

                    /*
                        Don't skip parenthesis if a method belongs to a conditional function.
                        Ex: 'if (method_name() >= number)'
                    */
                    if((list.get(i-1).lexeme.equals("if") && !list.get(i+1).token.equals("METHOD_NAME"))
                            || list.get(i-1).lexeme.equals("equals") || list.get(i-1).lexeme.equals("while")){
                        skipParenthesis += 1;
                        break;
                    }

                    // We have already added left parenthesis in 'def __init__(self, ' under the class name case
                    if(insideClassConstructor) {
                        break;
                    }

                    pythonStr += list.get(i).lexeme;

                    if(classHasClassConstructor && list.get(i-1).token.equals("METHOD_NAME")) {

                        // if the method is being called, do not pass self parameter. Otherwise, the method
                        // is being defined and we pass the 'self' param. if class has constructor
                        if(isDatatype(list.get(i-2).lexeme) || list.get(i-2).token.equals("T_VOID")) {
                            pythonStr += "self";
                        } else {
                            pythonStr += "";
                        }

                        // if class has constructor and the method has parameters
                        if(isDatatype(list.get(i+1).lexeme)) {
                            pythonStr += ", ";
                        }
                    }

                    break;

                case "STRING_IDENTIFIER":

                    if(isArrDecMemAllocStatement) {

                        arrayAllocateMemory[3] = "String Identifier";
                        break;

                    }

                    statementArr[0] = "String Identifier";
                    arrInitAndDeclaration[0] = "String Identifier";
                    arrAtStaticMethod[1] = list.get(i).lexeme;

                    break;

                case "ARGS_IDENTIFIER":

                    pythonStr += list.get(i).lexeme;
                    break;

                case "T_RPARENTH":

                    if(skipParenthesis!=0){
                        skipParenthesis -=1;
                        break;
                    }

                    if(castJavaMath) {
                        pythonStr += ")";
                        castJavaMath = false;
                    }

                    if(casting && !castingArrayElement && !isFloatingPointFormatSpecifier && !isFixedPointDecimalNumber) {

                        if(castingMethodNameAddRParenth) {
                            pythonStr += ")";
                            castingMethodNameAddRParenth = false;
                        }

                        //finished casting the current statement
                        casting = false;
                        break;

                    }

                    if(casting && castingArrayElement && !isFloatingPointFormatSpecifier && !isFixedPointDecimalNumber) {

                        pythonStr += ")";

                        //finished casting the current statement
                        casting = false;
                        castingArrayElement = false;
                        break;

                    }

                    else if(casting && (isFloatingPointFormatSpecifier || isFixedPointDecimalNumber)) {
                        pythonStr += ")";
                        isFloatingPointFormatSpecifier = false;
                        isFixedPointDecimalNumber = false;
                        casting = false;
                        strCastFormat = "";
                        break;
                    }

                    else if (isAMathMethodDoNotCastIt) {

                        //math statement is translated and no longer subject to being misinterpreted
                        isAMathMethodDoNotCastIt = false;
                        pythonStr += list.get(i).lexeme;
                        break;

                    }

                    else {

                        pythonStr += list.get(i).lexeme;
                        break;

                    }

                case "System":

                    checkPrintStatement += list.get(i).lexeme;
                    break;

                case "T_DOT":

                    if(intCastStr.equals("Integer")) {
                        intCastStr += ".";
                    }

                    if(strCastStr.equals("String")) {
                        strCastStr += ".";
                        strCastFormat += ".";
                    }

                    // self.array_name
                    if(insideClassConstructor) {
                        pythonStr += list.get(i).lexeme;
                    }

                    // 'object_name[var_identifier].method_name()' or 'object_name[number].method_name()'
                    if(list.get(i-4).token.equals("VAR_IDENTIFIER") && list.get(i-3).lexeme.equals("[") && (list.get(i-2).token.equals("VAR_IDENTIFIER")
                            || list.get(i-2).token.equals("NUMBER")) && list.get(i-1).lexeme.equals("]") && list.get(i+1).token.equals("METHOD_NAME")) {

                        pythonStr += list.get(i).lexeme;

                    }

                    // 'object_name.method_name'
                    if(list.get(i-1).token.equals("VAR_IDENTIFIER") && list.get(i+1).token.equals("METHOD_NAME")) {

                        pythonStr += list.get(i).lexeme;

                    }

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

                    //Properly reset this variable once println is added to the pythonStr
                    checkPrintStatement = "";

                    break;

                case "print":

                    checkPrintStatement += list.get(i).lexeme;

                    if(checkPrintStatement.equals("System.out.print")) {
                        pythonStr += "print";
                    }

                    //Properly reset this variable once println is added to the pythonStr
                    checkPrintStatement = "";

                    break;
                case "java class import":
                    pythonStr += "";
                    break;
                case "java util class import":
                    pythonStr += "";
                    break;
                case "Scanner class":
                    pythonStr += "";
                    break;
                case "in":
                    pythonStr += "";
                    break;
                case "Integer Input":
                    
                    System.out.println("Token in List 2 spots back: " + list.get(i-2).token);
                    System.out.println("Lexeme in List 2 spots back: " + list.get(i-2).lexeme);
                    System.out.println("Token in List 1 spot back: " + list.get(i-1).token);
                    System.out.println("Lexeme in List 1 spot back: " + list.get(i-1).lexeme);

                    if(list.get(i-1).token.equals("VAR_IDENTIFIER")) {
                        int prevVarSize = list.get(i-1).lexeme.length();
                        int pythonStrCurrentSize = pythonStr.length();

                        pythonStr = pythonStr.substring(pythonStrCurrentSize - prevVarSize);

                    }

                    pythonStr += "(int)input";
                    break;
                case "Double Input":


                    if(list.get(i-1).token.equals("VAR_IDENTIFIER")) {
                        int prevVarSize = list.get(i-1).lexeme.length();
                        int pythonStrCurrentSize = pythonStr.length();

                        pythonStr = pythonStr.substring(pythonStrCurrentSize - prevVarSize);

                    }

                    pythonStr += "(float)input";
                    break;
                case "string literal":

                    // If the string literal is format specifier "%f"
                    if(isFloatingPointFormatSpecifier || isFixedPointDecimalNumber) {
                        break;
                    } else {
                        pythonStr += list.get(i).lexeme;
                        break;
                    }

                case "T_SEMICOLON":
                    if(doWhileLoop && doWhileBraceCount==0){
                        doWhileLoop = false;
                        pythonStr += ":\n";

                        for(int p = 0; p < count+2; p++) {
                            pythonStr += "\t";
                            if(p==count+1)
                                pythonStr += "continue\n";
                        }

                        for(int p = 0; p < count+1; p++) {
                            pythonStr += "\t";
                            if(p==count)
                                pythonStr += "else:\n";
                        }

                        for(int p = 0; p < count+2; p++) {
                            pythonStr += "\t";
                            if(p==count+1)
                                pythonStr += "break\n";
                        }
                    }

                    // Reset all statement evaluation arrays after every statement or semicolon to prevent array
                    // value carryover when analyzing the next statement in the function.
                    for(int clrStatementArr = 0; clrStatementArr < statementArr.length; clrStatementArr++) {
                        statementArr[clrStatementArr] = "";
                    }

                    for(int clrEqualOpSt = 0; clrEqualOpSt < equalOpStatement.length; clrEqualOpSt++) {
                        equalOpStatement[clrEqualOpSt] = "";
                    }

                    for(int clrArrInitAndDec = 0; clrArrInitAndDec < arrInitAndDeclaration.length; clrArrInitAndDec++) {
                        arrInitAndDeclaration[clrArrInitAndDec] = "";
                    }

                    for(int clrDecAllMem = 0; clrDecAllMem < arrayAllocateMemory.length; clrDecAllMem++) {
                        arrayAllocateMemory[clrDecAllMem] = "";
                    }

                    isArrDecMemAllocStatement = false;
                    isArrayStatement = false;

                    /*---------------------------------------------------------------------------------------*/

                    pythonStr += "\n";

                    //Determine syntax of python output by tabbing new lines
                    for(int p = 0; p < count; p++) {
                        pythonStr += "\t";
                    }

                    checkPrintStatement = "";

                    break;

                case "T_INT":

                    if(isArrDecMemAllocStatement) {

                        arrayAllocateMemory[3] = "T_INT";
                        break;
                    }

                    statementArr[0] = "T_INT";
                    arrInitAndDeclaration[0] = "T_INT";
                    arrAtStaticMethod[1] = "T_INT";

                    break;

                case "T_DOUBLE":

                    if(isArrDecMemAllocStatement) {

                        arrayAllocateMemory[3] = "T_DOUBLE";
                        break;
                    }

                    statementArr[0] = "T_DOUBLE";
                    arrAtStaticMethod[1] = "T_DOUBLE";

                    break;

                case "T_FLOAT":

                    statementArr[0] = "T_FLOAT";
                    break;

                case "T_SHORT":

                    statementArr[0] = "T_SHORT";
                    break;

                case "T_LONG":

                    statementArr[0] = "T_LONG";
                    break;

                case "T_BYTE":

                    statementArr[0] = "T_BYTE";
                    break;

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
                    String lastFiveChars = "";

                    String lastTwoChars = "";
                    lastTwoChars += pythonStr.charAt(pythonStr.length()-2);
                    lastTwoChars += pythonStr.charAt(pythonStr.length()-1);

                    int lenOfLastSixChars = 6;
                    int lenOfLastFourChars = 4;
                    int lenOfLastFiveChars = 5;

                    int pyStrIndexLen = pythonStr.length() - 1;

                    int counter1 = (pyStrIndexLen - lenOfLastSixChars) + 1;
                    int counter2 = (pyStrIndexLen - lenOfLastFourChars) + 1;
                    int counter3 = (pyStrIndexLen - lenOfLastFiveChars) + 1;

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

                    while(counter3 < pyStrIndexLen) {
                        lastFiveChars += pythonStr.charAt(counter3);
                        counter3++;
                    }
                    lastFiveChars += pythonStr.charAt(pyStrIndexLen);

                    if(lastSixChars.equals("float(")) {
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme + ")";
                        break;

                        // 'print(', not 'int(' integer casting
                    } else if( (lastFourChars.equals("int(") && lastSixChars.equals("print(")) || strCastFormat.equals("String.format") ) { //prevents the 'print(..))' bug

                        // length of the variable inside of print statement
                        if(list.get(i+1).token.equals("T_DOT") && list.get(i+2).token.equals("length")) {
                            pythonStr += "len(" + list.get(i).lexeme + ")";
                        } else {
                            statementArr[1] = "VAR_IDENTIFIER";
                            pythonStr += list.get(i).lexeme;
                        }

                        break;

                        // 'int(' integer casting, not 'print('
                    } else if(lastFourChars.equals("int(") && !lastSixChars.equals("print(")) {
                        statementArr[1] = "VAR_IDENTIFIER";
                        pythonStr += list.get(i).lexeme + ")";
                        break;

                    } else if(lastFourChars.equals("str(")) {

                        // If we are casting an array element. Ex: str(array[index])
                        if(list.get(i+1).lexeme.equals("[") && list.get(i+2).token.equals("VAR_IDENTIFIER")) {

                            if(classHasClassConstructor && !insideClassConstructor && variablesOutsideClassConstructor.contains(list.get(i).lexeme)) {

                                pythonStr += "self.";

                            }
                            pythonStr += list.get(i).lexeme;
                            castingArrayElement = true;
                            break;
                        } else {
                            statementArr[1] = "VAR_IDENTIFIER";
                            pythonStr += list.get(i).lexeme + ")";
                            break;
                        }


                    } else if(lastFiveChars.equals("while") || lastTwoChars.equals("if")) {
                        pythonStr += " " + list.get(i).lexeme;
                        break;

                    } else {

                        // 'var [ ] ='. Inside of the class constructor, we only want the instance of the class for the
                        // variable on the L.H.S. of the assignment statement when we assign it to the parameter variable
                        // of the constructor on the R.H.S.
                        if(insideClassConstructor && list.get(i+1).lexeme.equals("[") && list.get(i+3).lexeme.equals("]")
                                && list.get(i+4).lexeme.equals("=")) {

                            pythonStr += "self.";

                        }

                        statementArr[1] = "VAR_IDENTIFIER";
                        equalOpStatement[0] = "VAR_IDENTIFIER";
                        arrInitAndDeclaration[3] = "VAR_IDENTIFIER";

                        arrayAllocateMemory[0] = "VAR_IDENTIFIER";

                        // If the class has constructor and if outside the constructor, we need to access the variables already declared
                        // in the class by doing 'self.variable' to get the class instance of the variables.
                        if(classHasClassConstructor && !insideClassConstructor && variablesOutsideClassConstructor.contains(list.get(i).lexeme)) {

                            if(list.get(i+1).lexeme.equals(".") && list.get(i+2).lexeme.equals("length")) {

                                pythonStr += "len(" + "self." + list.get(i).lexeme + ")";
                                break;

                            } else {
                                pythonStr += "self.";
                            }

                        }

                        if(!variablesOutsideClassConstructor.contains(list.get(i).lexeme)) {

                            if(list.get(i+1).lexeme.equals(".") && list.get(i+2).lexeme.equals("length")) {

                                pythonStr += "len(" + list.get(i).lexeme + ")";
                                break;

                            }
                        }


                        pythonStr += list.get(i).lexeme;

                        //Initializes a default array type variable to None
                        if(list.get(i-1).token.equals("T_RBRACKET") && list.get(i-2).token.equals("T_LBRACKET") && isDatatype(list.get(i-3).lexeme)  && list.get(i+1).token.equals("T_SEMICOLON") ) {

                            pythonStr += " = None";

                        }

                        //initializes a  default double type variable to 0
                        if(list.get(i-1).token.equals("T_DOUBLE") && list.get(i+1).token.equals("T_SEMICOLON") ) {

                            pythonStr += " = 0";

                        }

                        //initializes a  default int type variable to 0
                        if(list.get(i-1).token.equals("T_INT") && list.get(i+1).token.equals("T_SEMICOLON") ) {

                            pythonStr += " = 0";

                        }

                        //initializes a  default String type variable to None
                        if(list.get(i-1).token.equals("STRING_IDENTIFIER") && list.get(i+1).token.equals("T_SEMICOLON") ) {

                            pythonStr += "= None" ;

                        }

                        //initializes a  default boolean type variable to False
                        if(list.get(i-1).token.equals("T_BOOL") && list.get(i+1).token.equals("T_SEMICOLON") ) {

                            pythonStr += "= False" ;

                        }


                        // we only need to worry about getting the instance of the class of variables for those that are
                        // are defined outside of class constructor.
                        if(!variablesOutsideClassConstructor.contains(list.get(i).lexeme) && !classHasClassConstructor) {
                            variablesOutsideClassConstructor.add(list.get(i).lexeme);
                        }

                        break;
                    }

                case "T_COMMA":

                    if(casting) {
                        continue;
                    } else {
                        pythonStr += list.get(i).lexeme + " ";
                        break;
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

                        break;

                    } else if(checkNotEqual) {

                        pythonStr += list.get(i).lexeme + " ";
                        checkNotEqual = false;

                        break;

                        // '+=', '-=', '*=', or '/='
                    } else if(isArithmeticOperator(list.get(i-1).lexeme)) {

                        pythonStr += list.get(i).lexeme + " ";
                        break;

                    } else {

                        if(list.get(i-1).lexeme.equals("<") || list.get(i-1).lexeme.equals(">")) {
                            pythonStr += list.get(i).lexeme + " ";
                        } else {
                            pythonStr += " " + list.get(i).lexeme + " ";
                        }

                        statementArr[2] = "T_ASSIGN";
                        arrInitAndDeclaration[4] = "T_ASSIGN";
                        arrayAllocateMemory[1]= "T_ASSIGN";

                        break;

                    }


                case "NUMBER":

                    if(isArrDecMemAllocStatement) {

                        String num = list.get(i).lexeme;

                        // Remove '.0' from 'num.0'
                        num = num.substring(0, num.length() -1);
                        num = num.substring(0, num.length() -1);

                        arrayAllocateMemory[5] = num;

                        break;
                    }

                    if(isArrayStatementAddRbracket) {

                        String num = list.get(i).lexeme;

                        num = num.substring(0, num.length() -1);
                        num = num.substring(0, num.length() -1);

                        pythonStr += num;

                        break;

                    }

                    // (array or object) [ number ] =
                    if(list.get(i - 2).token.equals("VAR_IDENTIFIER") && list.get(i - 1).lexeme.equals("[")
                            && list.get(i + 1).lexeme.equals("]") && list.get(i + 2).token.equals("T_ASSIGN")) {

                        String num = list.get(i).lexeme;

                        num = num.substring(0, num.length() -1);
                        num = num.substring(0, num.length() -1);

                        pythonStr += num;

                        break;
                    }

                    /*
                       The stream tokenizer seems to produce numbers ending in '.0' if they are whole (ex: 2045 -> 2045.0).
                       So we need a way to ensure an incoming whole number does not end in '.0' if it is assigned
                       to a variable of type integer of short in the statement.
                    */

                    //If 'int x =', 'short x =', or 'byte x ='
                    if((statementArr[0] == "T_INT" || statementArr[0] == "T_SHORT" || statementArr[0] == "T_BYTE") && statementArr[1] == "VAR_IDENTIFIER"
                            && statementArr[2] == "T_ASSIGN") {

                        String num = list.get(i).lexeme;

                        // Remove the last two characters in the number if its assigned variable is type short or int
                        if(num.charAt(num.length()-2) == '.' && num.charAt(num.length()-1) == '0') {

                            num = num.substring(0, num.length() -1);
                            num = num.substring(0, num.length() -1);

                            pythonStr += num + " ";

                            for(int q = 0; q < statementArr.length; q++) {
                                statementArr[q] = "";
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

                            for(int r = 0; r < equalOpStatement.length; r++) {
                                equalOpStatement[r] = "";
                            }

                            break;

                        }

                        // Check number with long specifier
                    } else if(statementArr[0] == "T_LONG" && statementArr[1] == "VAR_IDENTIFIER"
                            && statementArr[2] == "T_ASSIGN" && list.get(i+1).lexeme.equals("L")) {

                        String numStr = list.get(i).lexeme.replace(".", "");

                        // convert the current number to an array of character digits
                        char[] charDigits = new char[numStr.length()];

                        for(int count = 0; count < numStr.length(); count++) {
                            charDigits[count] = numStr.charAt(count);
                        }

                        int count1 = 0;
                        String resultNum = "";

                        // while the end of the array has not been reached a letter character in char array of digits (L specifier)
                        while(charDigits[count1] != 'E') {

                            resultNum += charDigits[count1];
                            count1++;
                        }

                        pythonStr += resultNum + " ";

                        for(int s = 0; s < statementArr.length; s++) {
                            statementArr[s] = "";
                        }

                        break;

                    }

                    else {

                        //Remove the decimal from numbers whose decimal is 0 but is not caught in the above methods because it is not a variable declaration
                        try {

                            boolean castNumber = false;

                            if(list.get(i-3).lexeme.equals("(") && isDatatype(list.get(i-2).lexeme) && list.get(i-1).lexeme.equals(")")) {

                                // remove last two character for python string. these are '(' and ')' that are picked up by default in
                                // t_lparenth and t_rparenth cases when casting the number parameters in java when calling the function
                                // with parameters in the main method in Complex2
                                pythonStr = pythonStr.substring(0, pythonStr.length() - 2);

                                // all of these in java cast to int in Python
                                if(list.get(i-2).lexeme.equals("int") || list.get(i-2).lexeme.equals("short") || list.get(i-2).lexeme.equals("byte") || list.get(i-2).lexeme.equals("long")) {
                                    pythonStr += "int(";
                                    castNumber = true;
                                }

                                // float and double in Java cast to float in python
                                if(list.get(i-2).lexeme.equals("float") || list.get(i-2).lexeme.equals("double")) {
                                    pythonStr += "float(";
                                    castNumber = true;
                                }

                            }

                            if(Double.valueOf(list.get(i).lexeme) - Double.valueOf(list.get(i).lexeme).intValue() < 0.000000000000001) {
                                pythonStr += Double.valueOf(list.get(i).lexeme).intValue();
                                if(castNumber) {
                                    pythonStr += ")";
                                }
                                break;
                            }
                        }
                        catch(Exception e){
                        }

                        pythonStr += list.get(i).lexeme + " ";
                        break;

                    }

                case "logical operator":

                    // Modified Logical Operator Functionality
                    list.set(i, handleLogicalOperator(list, list.get(i), i));
                    pythonStr += " " + list.get(i).lexeme;
                    break;

                case "unary operator":

                    // Modified Unary Operator Functionality
                    //handleUnaryOperator(list, list.get(i).lexeme, i);
                    break;

                case "relational operator":

                    if(list.get(i).lexeme.equals("<") || list.get(i).lexeme.equals(">")) {

                        // if '>=' or '<='
                        if(list.get(i+1).lexeme.equals("=")) {
                            pythonStr += " " + list.get(i).lexeme;
                        } else {
                            pythonStr += " " + list.get(i).lexeme + " ";
                        }

                        break;
                    }

                case "arithmetic operator":

                    // var_identifier + + ;
                    if(list.get(i).lexeme.equals("+") && list.get(i+1).lexeme.equals("+") && list.get(i+2).token.equals("T_SEMICOLON")) {
                        handleUnaryOperator(list, "++", i);
                        pythonStr += list.get(i).lexeme.substring(1);
                        break;

                        // var_identifier - - ;
                    } else if(list.get(i).lexeme.equals("-") && list.get(i+1).lexeme.equals("-") && list.get(i+2).token.equals("T_SEMICOLON")) {
                        handleUnaryOperator(list, "--", i);
                        pythonStr += list.get(i).lexeme.substring(1);
                        break;

                        // Have not found the proper way to use handleUnaryOperator() for pre-increments and pre-decrements due to how the unary
                        // functions alter the original list of TokenData. This implementation should work for now.

                        // + + var_identifier ;
                    } else if(list.get(i).lexeme.equals("+") && list.get(i+1).lexeme.equals("+") && list.get(i+2).token.equals("VAR_IDENTIFIER")) {
                        pythonStr += list.get(i+2).lexeme + "+=1";
                        list.remove(i+2);
                        break;

                        // - - var_identifier ;
                    } else if(list.get(i).lexeme.equals("-") && list.get(i+1).lexeme.equals("-") && list.get(i+2).token.equals("VAR_IDENTIFIER")) {
                        pythonStr += list.get(i+2).lexeme + "-=1";
                        list.remove(i+2);
                        break;

                        //if there are no other arithmetic operators on either side of the current ar. operator, then it is a single ar. op.
                    } else if(!list.get(i-1).lexeme.equals(list.get(i).lexeme) && !list.get(i+1).lexeme.equals(list.get(i).lexeme)) {
                        pythonStr += list.get(i).lexeme;
                        break;
                    } else {
                        break;
                    }

                case "long specification":

                    continue;

                case "boolean literal":

                    if(list.get(i).lexeme.equals("true")) {
                        pythonStr += "True";
                    } else if(list.get(i).lexeme.equals("false")) {
                        pythonStr += "False";
                    }

                    break;

                case "character literal":

                    pythonStr += list.get(i).lexeme;
                    break;

                case "T_IF":

                    pythonStr += "if ";
                    break;

                case "T_ELSE":
                    if(list.get(i+1).lexeme.equals("if")){
                        pythonStr += "elif ";
                        i+=1;
                        break;
                    }
                    pythonStr += "else";
                    break;

                case "Equals Method":
                    pythonStr += " == ";
                    break;

                //Math handling
                case "Math":
                    checkPrintStatement += list.get(i).lexeme;
                    if(list.get(i-3).lexeme.equals("(") && isDatatype(list.get(i-2).lexeme) && list.get(i-1).lexeme.equals(")")) {
                        castJavaMath = true;
                    }
                    isAMathMethodDoNotCastIt = true;
                    break;
                case "abs":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.abs"))
                        pythonStr += "abs";
                    break;
                case "min":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.min"))
                        pythonStr += "min";
                    break;
                case "max":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.max"))
                        pythonStr += "max";
                    break;
                case "pow":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.pow"))
                        pythonStr += "pow";
                    break;
                case "acos":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.acos"))
                        pythonStr += "math.acos";
                    needsMathImport = true;
                    break;
                case "asin":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.asin"))
                        pythonStr += "math.asin";
                    needsMathImport = true;
                    break;
                case "atan":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.atan"))
                        pythonStr += "math.atan";
                    needsMathImport = true;
                    break;
                case "atan2":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.atan2"))
                        pythonStr += "math.atan2";
                    needsMathImport = true;
                    break;
                case "cos":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.cos"))
                        pythonStr += "math.cos";
                    needsMathImport = true;
                    break;
                case "cosh":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.cosh"))
                        pythonStr += "math.cosh";
                    needsMathImport = true;
                    break;
                case "exp":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.exp"))
                        pythonStr += "math.exp";
                    needsMathImport = true;
                    break;
                case "log":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.log"))
                        pythonStr += "math.log";
                    needsMathImport = true;
                    break;
                case "log10":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.log10"))
                        pythonStr += "math.log10";
                    needsMathImport = true;
                    break;
                case "sin":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.sin"))
                        pythonStr += "math.sin";
                    needsMathImport = true;
                    break;
                case "sinh":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.sinh"))
                        pythonStr += "math.sinh";
                    break;
                case "sqrt":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.sqrt"))
                        pythonStr += "math.sqrt";
                    needsMathImport = true;
                    break;
                case "tan":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.tan"))
                        pythonStr += "math.tan";
                    break;
                case "tanh":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.tanh"))
                        pythonStr += "math.tanh";
                    needsMathImport = true;
                    break;
                case "toRadians":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.toRadians"))
                        pythonStr += "math.radians";
                    needsMathImport = true;
                    break;
                case "toDegrees":
                    checkPrintStatement += list.get(i).lexeme;
                    if(checkPrintStatement.equals("Math.toDegrees"))
                        pythonStr += "math.degrees";
                    needsMathImport = true;
                    break;

                case "T_FOR":
                    String variableIdentifier="", conditionalValue="";

                    for(int x=i+1; !list.get(x).lexeme.equals("{"); x++){
                        if(list.get(x).token.equals("VAR_IDENTIFIER"))
                            variableIdentifier = list.get(x).lexeme;

                        if(list.get(x).token.equals("relational operator") && !list.get(x+1).token.equals("T_ASSIGN"))
                            conditionalValue = list.get(x+1).lexeme;

                        if(list.get(x).token.equals("relational operator") && list.get(x+1).token.equals("T_ASSIGN"))
                            conditionalValue = list.get(x+2).lexeme;

                        list.get(x).token = "T_SKIP";
                    }

                    try {
                        double cV = Double.parseDouble(conditionalValue);
                        if (cV % 1 == 0)
                            conditionalValue = String.valueOf((int) cV);
                    } catch (NumberFormatException ex){}

                    pythonStr += "for " + variableIdentifier + " in range(" + conditionalValue + ")";
                    break;

                case "T_WHILE":
                    if(doWhileLoop && doWhileBraceCount==0){
                        pythonStr += "\tif";
                        skipParenthesis+=1;
                        break;
                    }

                    pythonStr += "while";
                    break;

                case "T_DO":
                    pythonStr += "while True";
                    doWhileLoop = true;
                    break;

                case "T_SKIP":
                    break;

                case "T_NEW":

                    arrayAllocateMemory[2] = "T_NEW";

                    // if var = new (any data type or class name)[], it is an array declaration and memory allocation statement
                    if(arrayAllocateMemory[0] == "VAR_IDENTIFIER" && arrayAllocateMemory[1] == "T_ASSIGN" && arrayAllocateMemory[2] == "T_NEW"
                            && (isDatatype(list.get(i+1).lexeme) || list.get(i+1).token.equals("CLASS_NAME")) && list.get(i+2).lexeme.equals("[")
                            && list.get(i+4).lexeme.equals("]")) {

                        isArrDecMemAllocStatement = true;

                    }
                    break;

                case "T_THIS":

                    if(insideClassConstructor) {
                        pythonStr += "self";
                    }

                    break;

                case "T_RETURN":
                    pythonStr += list.get(i).lexeme + " ";
                    break;

                case "T_BREAK":
                    pythonStr += list.get(i).lexeme;
                    break;

                case "T_BOOL":

                    arrAtStaticMethod[1] = list.get(i).lexeme;
                    break;

            }

        }

        //Add math import statement if required
        if(needsMathImport)
            pythonStr = "import math \n\n" + pythonStr;


        // get the whole python string with the code in python that calls the main method (with its class instance) at the very end
        resultPythonStr = pythonStr + callingMainOutput(getClassName) + "\n";

        System.out.println(pythonStr + callingMainOutput(getClassName));

        System.out.println();

        /* James B's logical operators code */
        //splitTokenList(tempData);
        //handleLogicalOperators();
        //handleUnaryOperators();

        //System.out.println(lexemeList);

    }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - GENERAL
    // --------------------------------------------------------------------------------

    // Using the list of tokens to find where all logical operators are located in
    // lexeme list
    // private static void listAllOfType(String thisType) {

    //     // Clean slate
    //     indices.clear();

    //     for (int index = 0; index < tokenList.size(); index++) {
    //         if (tokenList.get(index).equals(thisType)) {
    //             indices.add(index);
    //         }
    //     }
    // }

    // // Makes the TokenData a little more managable by splitting into two
    // private static void splitTokenList(List<TokenData> allTokenData) {
    //     for (TokenData tokenData : allTokenData) {
    //         tokenList.add(tokenData.token);
    //         lexemeList.add(tokenData.lexeme);
    //     }
    // }

    // // Decreases each value in the indice list by one. Used when the primary list removes an item
    // // to keep the indice list accurate to the list it's representing
    // private static void decrementIndices() {
    //     for (int nums : indices) {
    //         indices.set(indices.indexOf(nums), nums - 1);
    //     }
    // }

    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - LOGICAL OPERATORS
    // --------------------------------------------------------------------------------

    private static TokenData handleLogicalOperator(ArrayList<TokenData> fullList, TokenData currentItem, int currentIndex) {
        String tempLexeme = currentItem.lexeme;

        tempLexeme = chooseLogicalOperatorCase(fullList, tempLexeme, currentIndex);

        currentItem.lexeme = tempLexeme;
        return currentItem;
    }

    // Beginning of logical operator logic
    // private static void handleLogicalOperators() {
    //     String logicalOperatorString = "logical operator";
    //     String tempLexeme;
    //     int tempLexemeInt;

    //     // Where are their specific locations
    //     listAllOfType(logicalOperatorString);

    //     // Loop until all logical operators are handled
    //     while (indices.size() > 0) {
    //         tempLexemeInt = indices.get(0);
    //         tempLexeme = lexemeList.get(tempLexemeInt);

    //         tempLexeme = chooseLogicalOperatorCase(tempLexeme, tempLexemeInt);


    //         // Replace symbol with word
    //         lexemeList.set(tempLexemeInt, tempLexeme);
    //         // Moves onto next logical operator
    //         indices.remove(indices.get(0));
    //     }
    // }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - LOGICAL OPERATORS
    // --------------------------------------------------------------------------------

    private static String chooseLogicalOperatorCase(ArrayList<TokenData> fullList, String caseToCheck, int caseIndex) {

        // Case: &
        if (caseToCheck.equals("&")) {
            caseToCheck = handleAmpersand(fullList, caseIndex);
        }
        // Case: |
        else if(caseToCheck.equals("|")) {
            caseToCheck = handleVertBar(fullList, caseIndex);
        }
        // Case: !
        else {

            if(fullList.get(caseIndex + 1).lexeme.equals("=")) {
                caseToCheck = "!";
                checkNotEqual = true;
            } else {
                // Must be a ! operator
                caseToCheck = handleExclamation();
            }

        }

        return caseToCheck;
    }

    // Ampersand logic
    private static String handleAmpersand(ArrayList<TokenData> fullList, int tempLexemeInt) {
        String ampersandSymbol = "&";

        // Check for double &&
        handleDoubles(fullList, tempLexemeInt, fullList.size(), ampersandSymbol);

        // If Single & in java, needs to be replaced with "and"
        // Single & in Python is a bitwise AND function, which is very different

        return "and ";
    }

    // Vertical Bar logic
    private static String handleVertBar(ArrayList<TokenData> fullList,  int tempLexemeInt) {
        String vertBarSymbol = "|";

        // Check for double ||
        handleDoubles(fullList, tempLexemeInt, fullList.size(), vertBarSymbol);

        return "or ";
    }

    // Exclamation logic
    private static String handleExclamation() {
        // String exclamationSymbol = "!";
        // No doubles for !, so if the symbol is ! it just needs to be replaced with "not".

        return "not ";
        //return "!";
    }

    // Logic for double symbols && and ||
    private static void handleDoubles(ArrayList<TokenData> fullList, int tempLexemeInt, int listSize, String symbolToCheck) {
        if (tempLexemeInt + 1 != listSize && fullList.get(tempLexemeInt + 1).lexeme.equals(symbolToCheck)) {
            // && becomes 'and', both lists need doubled sign removed
            //lexemeList.remove(tempLexemeInt + 1);
            //tokenList.remove(tempLexemeInt + 1);
            //indices.remove(indices.get(0));


            //decrementIndices();

            // Modified for switch case implementation
            fullList.remove(tempLexemeInt + 1);
        }
    }

    // --------------------------------------------------------------------------------
    //          PRIMARY FUNCTIONS - UNARY OPERATORS
    // --------------------------------------------------------------------------------

    private static void handleUnaryOperator(ArrayList<TokenData> fullList, String caseToCheck, int caseIndex) {
        String variableString = "VAR_IDENTIFIER";

        // True = ++, False = --
        boolean unaryType = false;
        // True = ++x/--x, False = x++/x--;
        boolean beforeAfter = false;

        if(caseToCheck.equals("++")) {
            unaryType = true;
        }

        // If index isn't 0 and previous index isn't a variable
        if(caseIndex != 0 && fullList.get(caseIndex-1).token.equals(variableString)) {
            beforeAfter = true;
        }

        caseToCheck = handleUnary(fullList, caseIndex, unaryType, beforeAfter);


        if(!beforeAfter) {caseIndex -= 1;}

        fullList.get(caseIndex).lexeme = caseToCheck;
    }

    // Beginning of unary operator logic
    // private static void handleUnaryOperators() {
    //     String unaryOperatorString = "unary operator";
    //     String variableString = "VAR_IDENTIFIER";
    //     String tempLexeme;

    //     int tempLexemeInt;

    //     // true = ++, false = --;
    //     boolean unaryType;
    //     // true = ++x/--x, false = x++/x--
    //     boolean beforeAfter;

    //     //listAllOfType(unaryOperatorString);

    //     // Loop until all unary operators are handled
    //     while(indices.size() > 0) {
    //         tempLexemeInt = indices.get(0);
    //         tempLexeme = lexemeList.get(tempLexemeInt);

    //         unaryType = false;
    //         beforeAfter = false;

    //         // Case: ++
    //         if(tempLexeme == "++") {
    //             unaryType = true;
    //         }
    //         // If not changed -> Case: --

    //         if(tempLexemeInt != 0 && tokenList.get(tempLexemeInt - 1).equals(variableString)) {
    //             beforeAfter = true;
    //         }

    //         // Gets correct replacement for unary operator case
    //         tempLexeme = handleUnary(tempLexemeInt, unaryType, beforeAfter);

    //         // If false, element behind tempLexemeInt is removed, so tempLexemeInt needs to shift as well
    //         if(!beforeAfter) {tempLexemeInt -= 1;}

    //         // Replace symbols with new expression
    //         lexemeList.set(tempLexemeInt, tempLexeme);
    //         // Moves onto the next unary operator
    //         indices.remove(indices.get(0));
    //     }
    // }

    // --------------------------------------------------------------------------------
    //          SECONDARY FUNCTIONS - UNARY OPERATORS
    // --------------------------------------------------------------------------------

    // Main logic for handling Unary Operator cases
    private static String handleUnary(ArrayList<TokenData> fullList, int lexemeInt, boolean type, boolean placement) {
        String unaryReplacement;
        // Case: True/True -> ++x
        if(type && placement) {
            // "x += 1", remove x
            unaryReplacement = fullList.get(lexemeInt+1).lexeme + "+=1";
            fullList.remove(lexemeInt + 1);
        }
        // Case: True/False -> x++
        else if(type && !placement) {
            // remove x, "x += 1"
            unaryReplacement = fullList.get(lexemeInt-1).lexeme + "+=1";
            fullList.remove(lexemeInt-1);
        }
        // Case: False/True -> --x
        else if(!type && placement) {
            // "x -= 1", remove x
            unaryReplacement = fullList.get(lexemeInt+1).lexeme + "-=1";
            fullList.remove(lexemeInt + 1);
        }
        // Case: False/False -> x--
        else {
            // remove x, "x -= 1"
            unaryReplacement = fullList.get(lexemeInt-1).lexeme + "-=1";
            fullList.remove(lexemeInt-1);
        }

        // Each case removes something, so the indices need to be decremented else
        //decrementIndices();

        return unaryReplacement;
    }

}
