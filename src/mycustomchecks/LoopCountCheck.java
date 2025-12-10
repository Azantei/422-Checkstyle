package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;


public class LoopCountCheck extends AbstractCheck {

	@Override
    public int[] getDefaultTokens() {
        return new int[] { };
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
    }

    @Override
    public void visitToken(DetailAST ast) {
    }

    @Override
    public void finishTree(DetailAST rootAST) {
    }
	
}
