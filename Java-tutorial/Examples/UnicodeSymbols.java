
public class UnicodeSymbols {

	public static void main(String[] args) {
		System.out.println("Checking bangla characters");
		
		for(char i=0x0980; i<=0x09FF; i++) {
			System.out.print(i);
		}
	}
}
