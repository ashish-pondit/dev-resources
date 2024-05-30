import java.lang.*;
import java.util.*;

class SecondProgram{
	public static void main(String args[]){
		Scanner s = new Scanner(System.in);
		System.out.println("Enter two variable");
		int var1,var2,sum;
		var1 = s.nextInt();
		var2 = s.nextInt();
		sum = var1 + var2;
		System.out.println("Total sum is "+sum);
		s.close();
	}

}
