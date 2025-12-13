package mycustomchecks;

import java.util.HashSet;
import java.util.Set;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadVocabularyCheck extends AbstractCheck {

    private Set<String> uniqueTokens;

    @Override
    public int[] getDefaultTokens() {
        // Return all Halstead tokens (operators and operands)
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
    public void beginTree(DetailAST rootAST) {
        // Reset tokens (unique) set for new file
        uniqueTokens = new HashSet<>();
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Collect all token (unique) text values
        uniqueTokens.add(ast.getText());
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Halstead Vocabulary: total count of unique operators and operands
        // Log the total when done
        log(rootAST.getLineNo(), "Halstead Vocabulary: " + uniqueTokens.size() + " - C. O.");
    }
}