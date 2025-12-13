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

public class HalsteadVocabularyTest {

    private HalsteadVocabularyCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new HalsteadVocabularyCheck());
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
        verify(testCheck).beginTree(mockAST);
    }

    @Test
    public void testVisitToken() {
        // Visit an operator
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(1)).visitToken(mockAST);

        // Visit same operator again
        testCheck.visitToken(mockAST);
        verify(testCheck, times(2)).visitToken(mockAST);

        // Visit a different operator
        when(mockAST.getText()).thenReturn("-");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(3)).visitToken(mockAST);

        // Visit an operand
        when(mockAST.getText()).thenReturn("sort");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(4)).visitToken(mockAST);

        // Visit another operand
        when(mockAST.getText()).thenReturn("a");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(5)).visitToken(mockAST);
    }

    @Test
    public void testFinishTreeZeroTokens() {
        doNothing().when(testCheck).log(anyInt(), anyString());
        
        testCheck.finishTree(mockAST);
        
        verify(testCheck).log(eq(1), contains("Halstead Vocabulary: 0 - C. O."));
    }

    @Test
    public void testFinishTreeWithTokens() {
        // Add unique tokens: +, -, sort, a
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        
        when(mockAST.getText()).thenReturn("-");
        testCheck.visitToken(mockAST);
        
        when(mockAST.getText()).thenReturn("sort");
        testCheck.visitToken(mockAST);
        
        when(mockAST.getText()).thenReturn("a");
        testCheck.visitToken(mockAST);

        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);

        verify(testCheck).log(eq(1), contains("Halstead Vocabulary: 4 - C. O."));
    }

    @Test
    public void testFinishTreeDuplicateTokens() {
        // Add tokens with duplicates: x, x, x, y, y
        when(mockAST.getText()).thenReturn("x");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getText()).thenReturn("y");
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);

        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);

        verify(testCheck).log(eq(1), contains("Halstead Vocabulary: 2 - C. O."));
    }
}