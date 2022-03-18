package restructure.TranslateUtilities;

import java.util.ArrayList;
import restructure.TokenData;

public class TranslateKeywords extends TranslationUtils {
    
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
    //                  PRIMARY KEYWORD FUNCTION
    // --------------------------------------------------------------------------------

    public static void handleKeyword(ArrayList<TokenData> tList, TokenData currentData, int currentIndex) {
        
        // If it's already a Python keyword, just leave it be
        if(!isPythonKeyword(currentData.lexeme)) {
            determineKeyword(tList, currentData.lexeme, currentIndex);
        }
        else {
            currentData.lexeme = TranslationUtils.getCurrentTabs() + currentData.lexeme;
        }
    }

    // --------------------------------------------------------------------------------
    //             SECONDARY KEYWORD FUNCTION - IDENTIFY KEYWORD
    // --------------------------------------------------------------------------------

    private static void determineKeyword(ArrayList<TokenData> tList, String wordToCheck, int currentIndex) {
        
        // Case: Abstract
        if(wordToCheck.toUpperCase().equals("ABSTRACT")) {handleAbstract();}

        // Case: Assert
        if(wordToCheck.toUpperCase().equals("ASSERT")) {handleAssert();}

        // Case: Boolean
        if(wordToCheck.toUpperCase().equals("BOOLEAN")) {handleBoolean();}

        // Case: Break
        if(wordToCheck.toUpperCase().equals("BREAK")) {handleBreak();}

        // Case: Byte
        if(wordToCheck.toUpperCase().equals("BYTE")) {handleByte();}

        // Case: Case
        if(wordToCheck.toUpperCase().equals("CASE")) {handleCase();}

        // Case: Catch
        if(wordToCheck.toUpperCase().equals("CATCH")) {handleCatch();}

        // Case: Char
        if(wordToCheck.toUpperCase().equals("CHAR")) {handleChar();}
        
        // Case: Class
        if(wordToCheck.toUpperCase().equals("CLASS")) {handleClass();}

        // Case: Continue
        if(wordToCheck.toUpperCase().equals("CONTINUE")) {handleContinue();}

        // Case: Default
        if(wordToCheck.toUpperCase().equals("DEFAULT")) {handleDefault();}

        // Case: Do
        if(wordToCheck.toUpperCase().equals("DO")) {handleDo();}

        // Case: Double
        if(wordToCheck.toUpperCase().equals("DOUBLE")) {handleDouble();}

        // Case: Else
        if(wordToCheck.toUpperCase().equals("ELSE")) {handleElse();}

        // Case: Enum
        if(wordToCheck.toUpperCase().equals("ENUM")) {handleEnum();}

        // Case: Extends
        if(wordToCheck.toUpperCase().equals("EXTENDS")) {handleExtends();}

        // Case: Final
        if(wordToCheck.toUpperCase().equals("FINAL")) {handleFinal();}

        // Case: Finally
        if(wordToCheck.toUpperCase().equals("FINALLY")) {handleFinally();}

        // Case: Float
        if(wordToCheck.toUpperCase().equals("FLOAT")) {handleFloat();}

        // Case: For
        if(wordToCheck.toUpperCase().equals("FOR")) {handleFor();}

        // Case: If
        if(wordToCheck.toUpperCase().equals("IF")) {handleIf();}

        // Case: Implements
        if(wordToCheck.toUpperCase().equals("IMPLEMENTS")) {handleImplements();}

        // Case: Import
        if(wordToCheck.toUpperCase().equals("IMPORT")) {handleImport();}

        // Case: InstanceOf
        if(wordToCheck.toUpperCase().equals("INSTANCEOF")) {handleInstanceOf();}

        // Case: Int
        if(wordToCheck.toUpperCase().equals("INT")) {handleInt();}

        // Case: Interface
        if(wordToCheck.toUpperCase().equals("INTERFACE")) {handleInterface();}

        // Case: Long
        if(wordToCheck.toUpperCase().equals("LONG")) {handleLong();}

        // Case: Native
        if(wordToCheck.toUpperCase().equals("NATIVE")) {handleNative();}

        // Case: New
        if(wordToCheck.toUpperCase().equals("NEW")) {handleNew();}

        // Case: Package
        if(wordToCheck.toUpperCase().equals("PACKAGE")) {handlePackage();}

        // Case: Private
        if(wordToCheck.toUpperCase().equals("PRIVATE")) {handlePrivate();}

        // Case: Protected
        if(wordToCheck.toUpperCase().equals("PROTECTED")) {handleProtected();}

        // Case: Public
        if(wordToCheck.toUpperCase().equals("PUBLIC")) {handlePublic(tList, currentIndex);}

        // Case: Return
        if(wordToCheck.toUpperCase().equals("RETURN")) {handleReturn();}

        // Case: Short
        if(wordToCheck.toUpperCase().equals("SHORT")) {handleShort();}

        // Case: Static
        if(wordToCheck.toUpperCase().equals("STATIC")) {handleStatic(tList, currentIndex);}

        // Case: StrictFP
        if(wordToCheck.toUpperCase().equals("STRICTFP")) {handleStrictFP();}

        // Case: String
        if(wordToCheck.toUpperCase().equals("STRING")) {handleString(tList, currentIndex);}

        // Case: Super
        if(wordToCheck.toUpperCase().equals("SUPER")) {handleSuper();}

        // Case: Switch
        if(wordToCheck.toUpperCase().equals("SWITCH")) {handleSwitch();}

        // Case: Synchronized
        if(wordToCheck.toUpperCase().equals("SYNCHRONIZED")) {handleSynchronized();}

        // Case: This
        if(wordToCheck.toUpperCase().equals("THIS")) {handleThis();}

        // Case: Throw
        if(wordToCheck.toUpperCase().equals("THROW")) {handleThrow();}

        // Case: Throws
        if(wordToCheck.toUpperCase().equals("THROWS")) {handleThrows();}

        // Case: Transient
        if(wordToCheck.toUpperCase().equals("TRANSIENT")) {handleTransient();}

        // Case: Try
        if(wordToCheck.toUpperCase().equals("TRY")) {handleTry();}

        // Case: Void
        if(wordToCheck.toUpperCase().equals("VOID")) {handleVoid(tList, currentIndex);}

        // Case: Volatile
        if(wordToCheck.toUpperCase().equals("VOLATILE")) {handleVolatile();}

        // Case: While
        if(wordToCheck.toUpperCase().equals("WHILE")) {handleWhile();}

    }

    // --------------------------------------------------------------------------------
    //             SECONDARY KEYWORD FUNCTIONS - KEYWORD CASES
    // --------------------------------------------------------------------------------

    // Case: Abstract
    private static void handleAbstract() {
        System.out.println("Found a keyword: Abstract");
    }

    // Case: Assert
    private static void handleAssert() {
        System.out.println("Found a keyword: Assert");
    }

    // Case: Boolean
    private static void handleBoolean() {
        System.out.println("Found a keyword: Boolean");
    }

    // Case: Break
    private static void handleBreak() {
        System.out.println("Found a keyword: Break");
    }
    
    // Case: Byte
    private static void handleByte() {
        System.out.println("Found a keyword: Byte");
    }

    // Case: Case
    private static void handleCase() {
        System.out.println("Found a keyword: Case");
    }

    // Case: Catch
    private static void handleCatch() {
        System.out.println("Found a keyword: Catch");
    }

    // Case: Char
    private static void handleChar() {
        System.out.println("Found a keyword: Char");
    }

    // Case: Class
    private static void handleClass() {
        // As of now, class is a java and python keyword, so no change needs to occur
    }

    // Case: Continue
    private static void handleContinue() {
        System.out.println("Found a keyword: Continue");
    }

    // Case: Default
    private static void handleDefault() {
        System.out.println("Found a keyword: Default");
    }

    // Case: Do
    private static void handleDo() {
        System.out.println("Found a keyword: Do");
    }

    // Case: Double
    private static void handleDouble() {
        System.out.println("Found a keyword: Double");
    }

    // Case: Else
    private static void handleElse() {
        System.out.println("Found a keyword: Else");
    }

    // Case: Enum
    private static void handleEnum() {
        System.out.println("Found a keyword: Enum");
    }

    // Case: Extends
    private static void handleExtends() {
        System.out.println("Found a keyword: Extends");
    }

    // Case: Final
    private static void handleFinal() {
        System.out.println("Found a keyword: Final");
    }

    // Case: Finally
    private static void handleFinally() {
        System.out.println("Found a keyword: Finally");
    }

    // Case: Float
    private static void handleFloat() {
        System.out.println("Found a keyword: Float");
    }

    // Case: For
    private static void handleFor() {
        System.out.println("Found a keyword: For");
    }

    // Case: If
    private static void handleIf() {
        System.out.println("Found a keyword: If");
    }

    // Case: Implements
    private static void handleImplements() {
        System.out.println("Found a keyword: Implements");
    }

    // Case: Import
    private static void handleImport() {
        System.out.println("Found a keyword: Import");
    }

    // Case: InstanceOf
    private static void handleInstanceOf() {
        System.out.println("Found a keyword: InstanceOf");
    }

    // Case: Int
    private static void handleInt() {
        System.out.println("Found a keyword: Int");
    }

    // Case: Interface
    private static void handleInterface() {
        System.out.println("Found a keyword: Interface");
    }

    // Case: Long
    private static void handleLong() {
        System.out.println("Found a keyword: Long");
    }

    // Case: Native
    private static void handleNative() {
        System.out.println("Found a keyword: Native");
    }

    // Case: New
    private static void handleNew() {
        System.out.println("Found a keyword: New");
    }

    // Case: Package
    private static void handlePackage() {
        System.out.println("Found a keyword: Package");
    }

    // Case: Private
    private static void handlePrivate() {
        System.out.println("Found a keyword: Private");
    }

    // Case: Protected
    private static void handleProtected() {
        System.out.println("Found a keyword: Protected");
    }

    // Case: Public
    // Public is not explicitly used, so TokenData is removed
    private static void handlePublic(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
    }

    // Case: Return
    private static void handleReturn() {
        System.out.println("Found a keyword: Return");
    }

    // Case: Short
    private static void handleShort() {
        System.out.println("Found a keyword: Short");
    }

    // Case: Static
    private static void handleStatic(ArrayList<TokenData> tList, int currentIndex) {
        boolean beforeMethod = false;

        // Checks past 'return type'/'var type' (currentIndex + 1) and label (currentIndex + 2)
        if(tList.get(currentIndex+3).token.equals("separator")) {
            beforeMethod = true;
        }

        // Python adds statement for static method, but nothing for variable
        if(beforeMethod) {
            tList.get(currentIndex).lexeme = "@staticmethod\n";
        }
    }

    // Case: StrictFP
    private static void handleStrictFP() {
        System.out.println("Found a keyword: StrictFP");
    }

    // Case: String
    private static void handleString(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
    }

    // Case: Super
    private static void handleSuper() {
        System.out.println("Found a keyword: Super");
    }

    // Case: Switch
    private static void handleSwitch() {
        System.out.println("Found a keyword: Switch");
    }

    // Case: Synchronized
    private static void handleSynchronized() {
        System.out.println("Found a keyword: Synchronized");
    }

    // Case: This
    private static void handleThis() {
        System.out.println("Found a keyword: This");
    }

    // Case: Throw
    private static void handleThrow() {
        System.out.println("Found a keyword: Throw");
    }

    // Case: Throws
    private static void handleThrows() {
        System.out.println("Found a keyword: Throws");
    }

    // Case: Transient
    private static void handleTransient() {
        System.out.println("Found a keyword: Transient");
    }

    // Case: Try
    private static void handleTry() {
        System.out.println("Found a keyword: Try");
    }

    // Case: Void
    // Void is not explicitly used, so TokenData is removed
    private static void handleVoid(ArrayList<TokenData> tList, int currentIndex) {
        removeCharacter(tList, indices, currentIndex);
    }

    // Case: Volatile
    private static void handleVolatile() {
        System.out.println("Found a keyword: Volatile");
    }

    // Case: While
    private static void handleWhile() {
        System.out.println("Found a keyword: While");
    }

}


