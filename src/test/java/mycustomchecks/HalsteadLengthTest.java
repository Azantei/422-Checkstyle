package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class HalsteadLengthTest {
    
    private HalsteadLengthCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new HalsteadLengthCheck());
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
    }

    @Test
    public void testGetRequiredTokens() {
        assertArrayEquals(new int[0], testCheck.getRequiredTokens());
    }

    @Test
    public void testBeginTree() {
        verify(testCheck).beginTree(mockAST);
    }

    @Test
    public void testVisitToken() {
        // Add operator
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        testCheck.visitToken(mockAST);
        verify(testCheck).visitToken(mockAST);
        
        // Add operand
        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(2)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add 5 tokens (operators and operands)
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.MINUS);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.ASSIGN);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getLineNo()).thenReturn(1);
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Verify log was called with count of 5
        verify(testCheck).log(eq(1), contains("5"));
    }
}