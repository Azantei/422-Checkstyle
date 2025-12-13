package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HalsteadDifficultyTest {

    private HalsteadDifficultyCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new HalsteadDifficultyCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
        
        testCheck.beginTree(mockAST);
    }

    @Test
    public void testGetDefaultTokens() {
        int[] defaultTokens = testCheck.getDefaultTokens();
        int[] expectedTokens = HalsteadTokenTypes.getCombinedSets();
        
        assertArrayEquals(expectedTokens, defaultTokens);
        assertTrue(defaultTokens.length > 0);
    }

    @Test
    public void testGetAcceptableTokens() {
        int[] acceptableTokens = testCheck.getAcceptableTokens();
        int[] expectedTokens = HalsteadTokenTypes.getCombinedSets();
        
        assertArrayEquals(expectedTokens, acceptableTokens);
        assertTrue(acceptableTokens.length > 0);
    }

    @Test
    public void testGetRequiredTokens() {
        int[] requiredTokens = testCheck.getRequiredTokens();
        
        assertArrayEquals(new int[0], requiredTokens);
    }

    @Test
    public void testBeginTree() {
        // Just verify it was called during setup
        verify(testCheck).beginTree(mockAST);
    }

    @Test
    public void testVisitToken() {
        // Visit an operator
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(1)).visitToken(mockAST);

        // Visit same operator again
        testCheck.visitToken(mockAST);
        verify(testCheck, times(2)).visitToken(mockAST);

        // Visit a different operator
        when(mockAST.getType()).thenReturn(TokenTypes.MINUS);
        when(mockAST.getText()).thenReturn("-");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(3)).visitToken(mockAST);

        // Visit an operand
        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        when(mockAST.getText()).thenReturn("x");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(4)).visitToken(mockAST);

        // Visit same operand (should increment total count)
        testCheck.visitToken(mockAST);
        verify(testCheck, times(5)).visitToken(mockAST);
    }

    @Test
    public void testFinishTreeZeroOperands() {
        doNothing().when(testCheck).log(anyInt(), anyString());
        
        testCheck.finishTree(mockAST);
        
        // Should log 0.00 when no operands exist
        verify(testCheck).log(eq(1), contains("Halstead Difficulty: 0.00"));
    }

    @Test
    public void testFinishTreeCalculation() {
        // Add operators: +, +, -, -, int, (, (
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.MINUS);
        when(mockAST.getText()).thenReturn("-");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_INT);
        when(mockAST.getText()).thenReturn("int");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.LPAREN);
        when(mockAST.getText()).thenReturn("(");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);

        // Add operands: sort, sort, 5, a, 5, a, b, 6
        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        when(mockAST.getText()).thenReturn("sort");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        when(mockAST.getText()).thenReturn("5");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        when(mockAST.getText()).thenReturn("a");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        when(mockAST.getText()).thenReturn("5");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        when(mockAST.getText()).thenReturn("a");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        when(mockAST.getText()).thenReturn("b");
        testCheck.visitToken(mockAST);

        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        when(mockAST.getText()).thenReturn("6");
        testCheck.visitToken(mockAST);

        // n1 = 4 unique operators (+, -, int, ()
        // n2 = 5 unique operands (sort, 5, a, b, 6)
        // N2 = 8 total operands
        // D = (n1 / 2) * (N2 / n2)
        double n1 = 4;
        double n2 = 5;
        double N2 = 8;
        double expectedDifficulty = (n1 / 2.0) * (N2 / n2);
        String formattedDifficulty = String.format("%.2f", expectedDifficulty);

        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);

        verify(testCheck).log(eq(1), contains(formattedDifficulty));
    }

    @Test
    public void testFinishTreeSimpleCase() {
        // One operator
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);

        // One operand
        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        when(mockAST.getText()).thenReturn("x");
        testCheck.visitToken(mockAST);

        // D = (1 / 2) * (1 / 1) = 0.5
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);

        verify(testCheck).log(eq(1), contains("0.50"));
    }
}