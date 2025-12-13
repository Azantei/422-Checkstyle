package mycustomchecks.testcases;

public class CommentLineTest {
	// 1
	void testMethod()
	{
		String test1 = "// not a comment";
		String test2 = "/* not a comment */";
		/*2 */ /*2 */ /*2 */
		
		// // 3
		
		int test3 = /* 4 */ 2;
		
		/* 5
		 * 6
		 * 7
		 8 */
	}
}
// 9