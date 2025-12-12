package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;


public class LoopCountCheck extends AbstractCheck {
	
	private int loopCount;

	@Override
    public int[] getDefaultTokens() {
		// Check for loop types: for, while, and do-while
		return new int[] {
        		TokenTypes.LITERAL_FOR,
        		TokenTypes.LITERAL_WHILE,
        		TokenTypes.LITERAL_DO
        		};
    }

    @Override
    public int[] getAcceptableTokens() {
    	return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
    	return new int[0];
    }

    @Override
    public void beginTree(DetailAST rootAST) {
    	// Reset counter for new file
    	loopCount=0;
    }

    @Override
    public void visitToken(DetailAST ast) {
    	// Increment counter for found loops
    	loopCount++;
    }

    @Override
    public void finishTree(DetailAST rootAST) {
    	// Log the total count when done
    	log(rootAST.getLineNo(), "Loop count: " + loopCount + "- C.O.");
    }
	
}
