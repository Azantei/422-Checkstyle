package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class LoopCountTest {
    
    private LoopCountCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new LoopCountCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
        testCheck.beginTree(mockAST);
    }

    @Test
    public void testGetDefaultTokens() {
        int[] expected = new int[] { 
            TokenTypes.LITERAL_FOR, 
            TokenTypes.LITERAL_WHILE, 
            TokenTypes.LITERAL_DO 
        };
        assertArrayEquals(expected, testCheck.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        int[] expected = new int[] { 
            TokenTypes.LITERAL_FOR, 
            TokenTypes.LITERAL_WHILE, 
            TokenTypes.LITERAL_DO 
        };
        assertArrayEquals(expected, testCheck.getAcceptableTokens());
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
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_FOR);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(3)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add 5 loop statements
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_FOR);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_WHILE);
        testCheck.visitToken(mockAST);
        when(mockAST.getType()).thenReturn(TokenTypes.LITERAL_DO);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getLineNo()).thenReturn(1);
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Verify log was called with count of 5
        verify(testCheck).log(eq(1), contains("5"));
    }
}