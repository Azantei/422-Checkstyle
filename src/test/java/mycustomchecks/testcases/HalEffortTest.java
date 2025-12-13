package mycustomchecks.testcases;

public class HalEffortTest {
	
	public void foo()
	{
		int[] arr = new int[] {1, 2, 3};
		
		for(int i = 0; i < arr.length;++i)
		{
			arr[i] += i;
		}
	}
}