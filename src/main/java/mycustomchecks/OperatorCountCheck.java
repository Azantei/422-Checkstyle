package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;

public class OperatorCountCheck extends AbstractCheck {

    private int operatorCount;

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset counter for new file
        operatorCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Increment count for each operator found
        operatorCount++;
    }

    @Override
    public int[] getDefaultTokens() {
        // Convert the operator token set to an int array
        return HalsteadTokenTypes.halsteadOperators.stream()
                .mapToInt(Integer::intValue)
                .toArray();
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
        log(rootAST.getLineNo(), "Operator count: " + operatorCount + " - C.O.");
    }
}