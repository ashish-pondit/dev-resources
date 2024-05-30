
public class DataTypeDetails {

	public static void main(String[] args) {
		int number = 50;
		System.out.println("Decimal number \t\tis "+number);
		System.out.println("Equivalent binary \tis "+Integer.toBinaryString(number));
		System.out.println("Equivalent octal \tis "+Integer.toOctalString(number));
		System.out.println("Equivalent binary \tis "+Integer.toHexString(number));
	}
}
