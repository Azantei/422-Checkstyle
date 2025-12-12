package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;
import java.util.HashSet;
import java.util.Set;

public class HalsteadVolumeCheck extends AbstractCheck {

    private Set<String> vocabulary;
    private int length;

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset vocabulary and length for new file
        vocabulary = new HashSet<>();
        length = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Track unique tokens and total count
        vocabulary.add(ast.getText());
        length++;
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
        // Calculate Halstead Volume: N * log2(n)
        double volume = 0;
        if (vocabulary.size() != 0) {
            volume = length * (Math.log(vocabulary.size()) / Math.log(2));
        }
        log(rootAST.getLineNo(), "Halstead Volume: " + volume + " - C.O.");
    }
}