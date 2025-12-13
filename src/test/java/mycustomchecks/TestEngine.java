package mycustomchecks;

import java.io.File;
import java.io.IOException;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class TestEngine {
	private AbstractCheck check;
	
	public TestEngine(AbstractCheck checkType) {
		check = checkType;
	}
	
	public void Check(String filePath) throws IOException, CheckstyleException {
		// Build File
		File file = new File(filePath);
		FileText ft = new FileText(file, "UTF-8");
		FileContents fc = new FileContents(ft);
		
		// Fill AST with FileContents
		DetailAST root = JavaParser.parse(fc);
		
		// Add comment nodes if required
		if (check.isCommentNodesRequired()) {
			root = JavaParser.appendHiddenCommentNodes(root);
		}
		
		// Configure Check
		check.configure(new DefaultConfiguration("Local"));
		check.contextualize(new DefaultContext());
		
		// Initialize Local Variables in Check
		check.beginTree(root);
		
		// Visit Each Token in Tree
		walk(check, root);
		
		// Complete tree and display intended logs to user
		check.finishTree(root);
	}
	
	private void walk(AbstractCheck b, DetailAST a) {
		while (a != null) {
			if (validToken(a.getType())) {
				b.visitToken(a);
			}
			walk(b, a.getFirstChild());
			a = a.getNextSibling();
		}
	}
	
	private boolean validToken(int type) {
		for (int token : check.getAcceptableTokens()) {
			if (type == token) {
				return true;
			}
		}
		return false;
	}
}