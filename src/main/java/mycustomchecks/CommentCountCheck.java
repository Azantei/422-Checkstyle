package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;


public class CommentCountCheck extends AbstractCheck {
	
	private int commentCount;

	@Override
    public int[] getDefaultTokens() {
        return new int[] {
        		// Look for comment content tokens
        		TokenTypes.COMMENT_CONTENT };
        };

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[0];
    }

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }
    
    @Override
    public void beginTree(DetailAST rootAST) {
    		// Reset counter for new file
        commentCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
    		// Increment counter for found comments
        commentCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
    	// Log the total count when done
    	log(rootAST.getLineNo(), "Comment count: " + commentCount + " - C.O.");
    }
	
}
