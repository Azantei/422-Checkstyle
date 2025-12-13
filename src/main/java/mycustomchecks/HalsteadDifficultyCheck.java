package mycustomchecks;

import java.util.HashSet;
import java.util.Set;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadDifficultyCheck extends AbstractCheck {

    private Set<String> uniqueOperands;
    private Set<String> uniqueOperators;
    private int totalOperands;

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
        // Reset counters and sets for new file
        uniqueOperands = new HashSet<>();
        uniqueOperators = new HashSet<>();
        totalOperands = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Track operands: count total and collect unique values
        if (HalsteadTokenTypes.halsteadOperands.contains(ast.getType())) {
            totalOperands++;
            uniqueOperands.add(ast.getText());
        } 
        // Track operators: collect unique values
        else if (HalsteadTokenTypes.halsteadOperators.contains(ast.getType())) {
            uniqueOperators.add(ast.getText());
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        // Halstead Difficulty: D = (n1 / 2) * (N2 / n2)
        // where n1 = unique operators, N2 = total operands, n2 = unique operands

        double difficulty = 0.0;

        // Calculate difficulty if operands exist (avoid division by zero)
        if (uniqueOperands.size() != 0) {
            double n1 = uniqueOperators.size();
            double n2 = uniqueOperands.size();
            double N2 = totalOperands;

            difficulty = (n1 / 2.0) * (N2 / n2);
        }

        // Log the total when done
        String formattedDifficulty = String.format("%.2f", difficulty);
        log(rootAST.getLineNo(), "Halstead Difficulty: " + formattedDifficulty + " - C. O.");
    }
}