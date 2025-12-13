package mycustomchecks.testcases;

public class OperandTest {
	public int returnInt(int x)
	{
		return x;
	}
	
	public void foo()
	{
		String operand = "operand";
		
		int operand2 = 2;
		
		operand2 = returnInt(operand.length());
		
		operand = --operand2 + 5 + -2 + operand;
	}
}