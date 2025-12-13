package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OperatorCountTest {
    
    private OperatorCountCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new OperatorCountCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
        testCheck.beginTree(mockAST);
    }

    @Test
    public void testGetDefaultTokens() {
        int[] defaultTokens = testCheck.getDefaultTokens();
        int[] expectedTokens = HalsteadTokenTypes.halsteadOperators.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        assertArrayEquals(expectedTokens, defaultTokens);
    }

    @Test
    public void testGetAcceptableTokens() {
        int[] acceptableTokens = testCheck.getAcceptableTokens();
        int[] expectedTokens = HalsteadTokenTypes.halsteadOperators.stream()
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
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(3)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add 5 operators
        when(mockAST.getType()).thenReturn(TokenTypes.PLUS);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.MINUS);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.STAR);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.DIV);
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