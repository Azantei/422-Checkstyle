package mycustomchecks.testcases;

public class HalLengthTest {
	
	public int returnInt(int x)
	{
		return x;
	}
	
	public void foo()
	{
		int[] arr = new int[] {1, 2, 3};
		
		for(int i = 0; i < arr.length;++i)
		{
			arr[i] += arr[0] + -2;
			
			i++;
			--i;
			i--;
			
			i += 2;
			i /= 2;
			i *= 2;
			i %= 10;
			
			i = returnInt(i);
		}
	}
}