package mycustomchecks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CommentLineCountTest {
    
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
        int[] expected = new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.BLOCK_COMMENT_END
        };
        assertArrayEquals(expected, testCheck.getAcceptableTokens());
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
        // Test single line comment
        when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        testCheck.visitToken(mockAST);
        verify(testCheck, times(1)).visitToken(mockAST);
        
        // Test block comment begin
        when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(mockAST.getLineNo()).thenReturn(5);
        testCheck.visitToken(mockAST);
        
        // Test block comment end
        when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_END);
        when(mockAST.getLineNo()).thenReturn(8);
        testCheck.visitToken(mockAST);
        
        verify(testCheck, times(3)).visitToken(mockAST);
    }

    @Test
    public void testFinishTree() {
        // Add single line comment
        when(mockAST.getType()).thenReturn(TokenTypes.SINGLE_LINE_COMMENT);
        testCheck.visitToken(mockAST);
        
        // Add block comment (3 lines)
        when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_BEGIN);
        when(mockAST.getLineNo()).thenReturn(5);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getType()).thenReturn(TokenTypes.BLOCK_COMMENT_END);
        when(mockAST.getLineNo()).thenReturn(7);
        testCheck.visitToken(mockAST);
        
        when(mockAST.getLineNo()).thenReturn(1);
        doNothing().when(testCheck).log(anyInt(), anyString());
        testCheck.finishTree(mockAST);
        
        // Should have 1 single line + 3 block comment lines = 4 total
        verify(testCheck).log(eq(1), contains("4"));
    }
}