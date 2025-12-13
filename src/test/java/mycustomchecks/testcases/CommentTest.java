package mycustomchecks.testcases;

public class CommentTest {
	// 1
	void testMethod()
	{
		String test1 = "// not a comment";
		String test2 = "/* not a comment */";
		/*2 */ /*3 */ /*4 */
		
		// // 5 /* */
		
		int test3 = /* 6 */ 2;
		
		/* 7
		 * 7
		 * 7
		 */
	}
}
// 8