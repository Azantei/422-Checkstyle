package mycustomchecks;

import java.util.HashSet;
import java.util.Set;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class HalsteadEffortCheck extends AbstractCheck {

    private Set<String> uniqueOperands;
    private Set<String> uniqueOperators;
    private int totalOperands;
    private int totalOperators;

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
        totalOperators = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Track operands: count total and collect unique values
        if (HalsteadTokenTypes.halsteadOperands.contains(ast.getType())) {
            totalOperands++;
            uniqueOperands.add(ast.getText());
        } 
        // Track operators: count total and collect unique values
        else if (HalsteadTokenTypes.halsteadOperators.contains(ast.getType())) {
            totalOperators++;
            uniqueOperators.add(ast.getText());
        }
    }

    @Override
    public void finishTree(DetailAST rootAST) {
        /* Halstead Effort: E = D * V
           where D = Difficulty = (n1 / 2) * (N2 / n2)
           and V = Volume = N * log2(n)
           N = total length (N1 + N2), n = vocabulary (n1 + n2) */

        double effort = 0.0;

        // Calculate effort if operands exist (avoid division by zero)
        if (uniqueOperands.size() != 0) {
            double n1 = uniqueOperators.size();
            double n2 = uniqueOperands.size();
            double N = totalOperators + totalOperands;
            double n = n1 + n2;

            // Calculate Difficulty: D = (n1 / 2) * (N2 / n2)
            double difficulty = (n1 / 2.0) * (totalOperands / n2);

            // Calculate Volume: V = N * log2(n)
            double volume = N * (Math.log(n) / Math.log(2));

            // Calculate Effort: E = D * V
            effort = difficulty * volume;
        }

        // Log the total when done
        String formattedEffort = String.format("%.2f", effort);
        log(rootAST.getLineNo(), "Halstead Effort: " + formattedEffort + " - C. O.");
    }
}