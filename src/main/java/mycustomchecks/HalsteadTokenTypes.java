package mycustomchecks;

import java.util.Set;
import java.util.stream.Stream;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HalsteadTokenTypes {
    // Set of token types representing Halstead operators
    public static final Set<Integer> halsteadOperators = Set.of(
        // Arithmetic operators
        TokenTypes.PLUS,           // Addition
        TokenTypes.MINUS,          // Subtraction
        TokenTypes.STAR,           // Multiplication
        TokenTypes.DIV,            // Division
        TokenTypes.MOD,            // Modulus
        TokenTypes.INC,            // Increment (pre)
        TokenTypes.DEC,            // Decrement (pre)
        TokenTypes.POST_INC,       // Increment (post)
        TokenTypes.POST_DEC,       // Decrement (post)
        TokenTypes.ASSIGN,         // Assignment
        TokenTypes.PLUS_ASSIGN,    // Addition assignment
        TokenTypes.MINUS_ASSIGN,   // Subtraction assignment
        TokenTypes.STAR_ASSIGN,    // Multiplication assignment
        TokenTypes.DIV_ASSIGN,     // Division assignment
        TokenTypes.MOD_ASSIGN,     // Modulus assignment
        
        // Bitwise operators
        TokenTypes.BAND,           // Bitwise AND
        TokenTypes.BOR,            // Bitwise OR
        TokenTypes.BNOT,           // Bitwise NOT
        
        // Conditions operator
        TokenTypes.EQUAL,          // Equality comparison
        TokenTypes.NOT_EQUAL,      // Inequality comparison
        TokenTypes.GT,             // Greater than comparison
        TokenTypes.LT,             // Less than comparison
        TokenTypes.GE,             // Greater than or equal to
        TokenTypes.LE,             // Less than or equal to
        
        // Other symbols
        TokenTypes.COLON,
        TokenTypes.SEMI,
        TokenTypes.DOT,
        TokenTypes.INDEX_OP,       // Array declarator []
        TokenTypes.LCURLY,         // Curly braces {}
        TokenTypes.LPAREN,         // Left parenthesis
        
        // Literals
        TokenTypes.LITERAL_INT,    // Integer literals
        TokenTypes.LITERAL_FLOAT,  // Float literals
        TokenTypes.LITERAL_DOUBLE, // Double literals
        TokenTypes.LITERAL_LONG,
        TokenTypes.LITERAL_BYTE,
        TokenTypes.LITERAL_INSTANCEOF, // instanceof operator
        TokenTypes.LITERAL_TRY,
        TokenTypes.LITERAL_CATCH,
        TokenTypes.LITERAL_BREAK,
        TokenTypes.LITERAL_DO,
        TokenTypes.DO_WHILE,       // Literal While in do-while loop
        TokenTypes.LITERAL_IF,     // If statement
        TokenTypes.FOR_EACH_CLAUSE, // For each statement
        TokenTypes.LITERAL_WHILE,  // While loop
        TokenTypes.LITERAL_SWITCH, // Switch statement
        TokenTypes.LITERAL_CASE,   // Case statement
        TokenTypes.LITERAL_RETURN,
        
        // Others:
        TokenTypes.TYPECAST,
        TokenTypes.METHOD_CALL
    );

    // Set of token types that represent Halstead operands
    public static final Set<Integer> halsteadOperands = Set.of(
        TokenTypes.LITERAL_THIS,   // This keyword
        TokenTypes.IDENT,          // Variable/method/field names
        TokenTypes.NUM_INT,        // Integer literals
        TokenTypes.NUM_FLOAT,      // Float literals
        TokenTypes.NUM_DOUBLE,     // Double literals
        TokenTypes.NUM_LONG,
        TokenTypes.STRING_LITERAL, // String literals
        TokenTypes.CHAR_LITERAL,   // Character literals
        TokenTypes.LITERAL_NULL,   // Null literal
        TokenTypes.LITERAL_TRUE,
        TokenTypes.LITERAL_FALSE
    );
        
    public static int[] getCombinedSets() {
        // Helper method to convert the token sets to an array
        return Stream.concat(HalsteadTokenTypes.halsteadOperators.stream(), HalsteadTokenTypes.halsteadOperands.stream())
                     .mapToInt(Integer::intValue)
                     .toArray();
    }
}