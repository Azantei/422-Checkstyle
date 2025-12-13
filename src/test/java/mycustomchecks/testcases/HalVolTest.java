package mycustomchecks.testcases;

public class HalVolTest {
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
		int i = 1;
		
		while( i < arr.length)
		{
			arr[i] = 1;
					
			i = bar1(i);
			bar2();
		}
	}
}