package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadVolumeTest {
    
    private HalsteadVolumeCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new HalsteadVolumeCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
        testCheck.beginTree(mockAST);
    }

    @Test
    public void testGetDefaultTokens() {
        int[] defaultTokens = testCheck.getDefaultTokens();
        int[] expectedTokens = HalsteadTokenTypes.getCombinedSets();
        assertArrayEquals(expectedTokens, defaultTokens);
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
        // Add first operator
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        verify(testCheck).visitToken(mockAST);
        
        // Add same operator again (shouldn't increase vocabulary)
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        verify(testCheck, times(2)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add operators and operands
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        when(mockAST.getText()).thenReturn("+");
        testCheck.visitToken(mockAST);
        when(mockAST.getText()).thenReturn("-");
        testCheck.visitToken(mockAST);
        when(mockAST.getText()).thenReturn("x");
        testCheck.visitToken(mockAST);
        when(mockAST.getText()).thenReturn("y");
        testCheck.visitToken(mockAST);
        
        // Calculate expected volume: N=5, n=4, volume = 5 * log2(4) = 10.0
        int N = 5;  // length
        int n = 4;  // vocabulary
        double expectedVolume = N * (Math.log(n) / Math.log(2));
        
        when(mockAST.getLineNo()).thenReturn(1);
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Verify log was called with the expected volume
        verify(testCheck).log(eq(1), contains(String.valueOf(expectedVolume)));
    }
}