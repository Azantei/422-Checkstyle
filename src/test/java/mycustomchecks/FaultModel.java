package mycustomchecks;

public class FaultModel {

    public void main() {

        // ========================================
        // Fault Model 1: CommentCountCheck
        // ========================================
        
        // 1.1: Multiple "//" on one line might get counted as separate comments
        // First comment // Second comment
        
        // 1.2: Block comments 
        /* Block comment */
        
        // 1.3: Javadoc comments might not be counted right
        /**
         * Javadoc 
         */

        // ========================================
        // Fault Model 2: CommentLineCountCheck
        // ========================================
        
        // 2.1: Block comment on multiple lines - off by one error maybe?
        /* Line 1
         * Line 2
         */
        
        // 2.2: Multiple block comments on same line
        /* Comment 1 */ /* Comment 2 */
        
        // 2.3: Empty lines in block comments
        /*
         * 
         */

        // ========================================
        // Fault Model 3: LoopCountCheck
        // ========================================
        
        // 3.1: Nested loops
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
            }
        }
        
        // 3.2: For-each loop - not sure if counted
        int[] arr = {1, 2, 3};
        for (int num : arr) {
        }
        
        // 3.3: Do-while loop
        int x = 0;
        do {
            x++;
        } while (x < 5);

        // ========================================
        // Fault Model 4: OperatorCountCheck
        // ========================================
        
        // 4.1: Compound operators like +=
        int a = 5;
        a += 3;
        
        // 4.2: Increment operators
        int b = 10;
        b++;
        ++b;
        
        // 4.3: Array brackets []
        int[] array = new int[5];
        
        // 4.4: Control flow operators
        if (a > 5) { }
        try { } catch(Exception e) { }

        // ========================================
        // Fault Model 5: OperandCountCheck  
        // ========================================
        
        // 5.1: Literal values
        int num = 42;
        String str = "hello";
        
        // 5.2: null, true, false, this
        Object obj = null;
        boolean flag = true;
        
        // 5.3: Variable names
        int var1 = 5;
        int var2 = var1;

        // ========================================
        // Fault Model 6: ExpressionCountCheck
        // ========================================
        
        // 6.1: Simple expressions
        int exp1 = 5;
        int exp2 = exp1 + 10;
        
        // 6.2: Nested expression
        int exp3 = (exp1 + exp2) * 2;
        
        // 6.3: Expressions in if statements and loops
        if (exp1 > exp2) { }

        // ========================================
        // Fault Model 7: HalsteadLengthCheck
        // ========================================
        
        // 7.1: Count all operators and operands
        int len1 = 10;
        int len2 = len1 + 5;
        
        // 7.2: Duplicate values - should all be counted
        int dup = 1;
        int dup2 = 1;

        // ========================================
        // Fault Model 8: HalsteadVocabularyCheck
        // ========================================
        
        // 8.1: Unique tokens only
        int vocab1 = 5;
        int vocab2 = 5; // 5 appears twice but should only count once
        
        // 8.2: Same operator used multiple times
        int v1 = 1 + 2;
        int v2 = 3 + 4; // + should only count once

        // ========================================
        // Fault Model 9: HalsteadVolumeCheck
        // ========================================
        
        // 9.1: Volume = length * log2(vocabulary)
        int vol1 = 1;
        int vol2 = vol1 + 2;
        
        // 9.2: Edge case with no operands/operators

        // ========================================
        // Fault Model 10: HalsteadDifficultyCheck
        // ========================================
        
        // 10.1: Difficulty formula = (n1/2) * (N2/n2)
        int diff1 = 1;
        int diff2 = diff1 + 2;
        
        // 10.2: Zero operands edge case

        // ========================================
        // Fault Model 11: HalsteadEffortCheck
        // ========================================
        
        // 11.1: Effort = Difficulty * Volume
        int eff1 = 1;
        int eff2 = eff1 + 2;
        
        // 11.2: Zero edge case
    }
}