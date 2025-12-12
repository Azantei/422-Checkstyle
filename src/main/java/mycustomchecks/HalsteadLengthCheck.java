package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadLengthCheck extends AbstractCheck {

    private int halsteadLength;

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset counter for new file
        halsteadLength = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Count each operator and operand
        halsteadLength++;
    }

    @Override
    public int[] getDefaultTokens() {
        // Return combined operators and operands
        return HalsteadTokenTypes.getCombinedSets();
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
        // Log the total Halstead length when done
        log(rootAST.getLineNo(), "Halstead Length: " + halsteadLength + " - C.O.");
    }
}