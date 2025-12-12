package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;

public class ExpressionCountCheck extends AbstractCheck {
	
	private int expressionCount;

    @Override
    public void beginTree(DetailAST rootAST) {
    	// Reset counter for new file
        expressionCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
    		// Increment counter for found expressions
        expressionCount++;
    }

    @Override
    public int[] getDefaultTokens() {
    		// Count each expression
        return new int[] { TokenTypes.EXPR };
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
    public void finishTree(DetailAST rootAST) {
    		// Log the total count when done
        log(rootAST.getLineNo(), "Expression count: " + expressionCount + " - C.O.");
    }

}
