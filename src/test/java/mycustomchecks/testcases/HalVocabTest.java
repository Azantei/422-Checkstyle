package mycustomchecks.testcases;

public class HalVocabTest {
	public int bar1(int x)
	{
		return x;
	}
	
	public void bar2()
	{
		
	}
	
	public void foo()
	{
		int[] arr = new int[] {1, 2, 3};
		int test2 = 1;
		
		for(int i = 0; i < arr.length;++i)
		{
			arr[i] = 1;
					
			i = bar1(i);
		}
		
		while(arr[1] == 1);
		
		do
		{
			bar2();
		} while(false);
	}
}