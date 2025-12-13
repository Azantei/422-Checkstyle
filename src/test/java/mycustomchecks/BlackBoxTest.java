package mycustomchecks;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class BlackBoxTest {
	
	private TestEngine walker;
	private String relativePath = "src/test/java/mycustomchecks/testcases/";
	
	@Test
	public void testCommentCount() {
		CommentCountCheck testCheck = spy(new CommentCountCheck());
		walker = new TestEngine(testCheck);
		check("CommentTest");
		verify(testCheck).log(anyInt(), contains("8"));
	}
	
	@Test
	public void testCommentLineCount() {
		CommentLineCountCheck testCheck = spy(new CommentLineCountCheck());
		walker = new TestEngine(testCheck);
		check("CommentLineTest");
		verify(testCheck).log(anyInt(), contains("11"));
	}
	
	@Test
	public void testLoopCount() {
		LoopCountCheck testCheck = spy(new LoopCountCheck());
		walker = new TestEngine(testCheck);
		check("LoopTest");
		verify(testCheck).log(anyInt(), contains("3"));
	}
	
	@Test
	public void testOperatorCount() {
		OperatorCountCheck testCheck = spy(new OperatorCountCheck());
		walker = new TestEngine(testCheck);
		check("OperatorTest");
		verify(testCheck).log(anyInt(), contains("35"));
	}
	
	@Test
	public void testOperandCount() {
		OperandCountCheck testCheck = spy(new OperandCountCheck());
		walker = new TestEngine(testCheck);
		check("OperandTest");
		verify(testCheck).log(anyInt(), contains("21"));
	}
	
	@Test
	public void testExpressionCount() {
		ExpressionCountCheck testCheck = spy(new ExpressionCountCheck());
		walker = new TestEngine(testCheck);
		check("ExpressionTest");
		verify(testCheck).log(anyInt(), contains("3"));
	}
	
	@Test
	public void testHalsteadLength() {
		HalsteadLengthCheck testCheck = spy(new HalsteadLengthCheck());
		walker = new TestEngine(testCheck);
		check("HalLengthTest");
		verify(testCheck).log(anyInt(), contains("79"));
	}
	
	@Test
	public void testHalsteadVocabulary() {
		HalsteadVocabularyCheck testCheck = spy(new HalsteadVocabularyCheck());
		walker = new TestEngine(testCheck);
		check("HalVocabTest");
		verify(testCheck).log(anyInt(), contains("29"));
	}
	
	@Test
	public void testHalsteadVolume() {
		HalsteadVolumeCheck testCheck = spy(new HalsteadVolumeCheck());
		walker = new TestEngine(testCheck);
		check("HalVolTest");
		verify(testCheck).log(anyInt(), contains("Halstead Volume:"));
	}
	
	@Test
	public void testHalsteadDifficulty() {
		HalsteadDifficultyCheck testCheck = spy(new HalsteadDifficultyCheck());
		walker = new TestEngine(testCheck);
		check("HalDiffTest");
		verify(testCheck).log(anyInt(), contains("Halstead Difficulty:"));
	}
	
	@Test
	public void testHalsteadEffort() {
		HalsteadEffortCheck testCheck = spy(new HalsteadEffortCheck());
		walker = new TestEngine(testCheck);
		check("HalEffortTest");
		verify(testCheck).log(anyInt(), contains("Halstead Effort:"));
	}
	
	private void check(String fileName) {
		try {
			walker.Check(relativePath + fileName + ".java");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (CheckstyleException e) {
			System.out.println(e.getMessage());
		}
	}
}