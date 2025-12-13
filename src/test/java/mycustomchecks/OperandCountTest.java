package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OperandCountTest {
    
    private OperandCountCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new OperandCountCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
        testCheck.beginTree(mockAST);
    }

    @Test
    public void testGetDefaultTokens() {
        int[] defaultTokens = testCheck.getDefaultTokens();
        int[] expectedTokens = HalsteadTokenTypes.halsteadOperands.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        assertArrayEquals(expectedTokens, defaultTokens);
    }

    @Test
    public void testGetAcceptableTokens() {
        int[] acceptableTokens = testCheck.getAcceptableTokens();
        int[] expectedTokens = HalsteadTokenTypes.halsteadOperands.stream()
                .mapToInt(Integer::intValue)
                .toArray();
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
        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(3)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add 5 operands
        when(mockAST.getType()).thenReturn(TokenTypes.NUM_INT);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.IDENT);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.STRING_LITERAL);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.NUM_DOUBLE);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_TRUE);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getLineNo()).thenReturn(1);
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Verify log was called with count of 5
        verify(testCheck).log(eq(1), contains("5"));
    }
}