package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentCountTest {
    
    private CommentLineCountCheck testCheck;
    private DetailAST mockAST;

    @BeforeEach
    public void setUp() {
        testCheck = spy(new CommentLineCountCheck());
        mockAST = mock(DetailAST.class);
        when(mockAST.getLineNo()).thenReturn(1);
    }

    @Test
    public void testIsCommentNodesRequired() {
        assertTrue(testCheck.isCommentNodesRequired());
    }

    @Test
    public void testGetDefaultTokens() {
        int[] expected = new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.BLOCK_COMMENT_END
        };
        assertArrayEquals(expected, testCheck.getDefaultTokens());
    }

    @Test
    public void testGetAcceptableTokens() {
        assertArrayEquals(testCheck.getDefaultTokens(), testCheck.getAcceptableTokens());
    }

    @Test
    public void testGetRequiredTokens() {
        assertArrayEquals(new int[0], testCheck.getRequiredTokens());
    }

    @Test
    public void testBeginTree() {
        testCheck.beginTree(mockAST);
        verify(testCheck).beginTree(mockAST);
    }

    @Test
    public void testVisitToken() {
        // Visit a single line comment
        when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(1)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add some comments
        when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        testCheck.visitToken(mockAST);
        
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Verify log was called with correct count
        verify(testCheck).log(eq(1), contains("3"));
    }
}