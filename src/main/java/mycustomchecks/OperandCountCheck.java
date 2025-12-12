package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;

public class OperandCountCheck extends AbstractCheck {

    private int operandCount;

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset counter for new file
        operandCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Increment count for each operand found
        operandCount++;
    }

    @Override
    public int[] getDefaultTokens() {
        // Convert the operand token set to an int array
        return HalsteadTokenTypes.halsteadOperands.stream()
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
        log(rootAST.getLineNo(), "Operand count: " + operandCount + " - C.O.");
    }
}