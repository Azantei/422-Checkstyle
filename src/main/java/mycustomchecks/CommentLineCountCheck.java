package mycustomchecks;

import com.puppycrawl.tools.checkstyle.api.*;

public class CommentLineCountCheck extends AbstractCheck {

    private int commentLineCount;

    @Override
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        // Reset counter for new file
        commentLineCount = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        int type = ast.getType();

        if (type == TokenTypes.SINGLE_LINE_COMMENT) {
            // Increase counter for each single line comment
            commentLineCount++;
        }
        else if (type == TokenTypes.BLOCK_COMMENT_BEGIN) {
            // Subtract the starting line
            commentLineCount -= ast.getLineNo();
        }
        else if (type == TokenTypes.BLOCK_COMMENT_END) {
            // Add it back (endLine - startLine + 1)
            commentLineCount += ast.getLineNo() + 1;
        }
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.SINGLE_LINE_COMMENT,
            TokenTypes.BLOCK_COMMENT_BEGIN,
            TokenTypes.BLOCK_COMMENT_END
        };
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
        log(rootAST.getLineNo(), "Number of lines of comments: " + commentLineCount + " - C.O.");
    }
}