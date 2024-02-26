
public class RegularExpression {

	public static void main(String[] args) {
//		String str = "ashish.pondit@gmail.com";
//		
//		int dividerIndex = str.indexOf("@");
//		
//		String name=str.substring(0,dividerIndex);
//		String company=str.substring(dividerIndex+1,str.length());
//		
//		System.out.println("Email username: "+name);
//		System.out.println("Email company: "+company);
//		
//		if(company.indexOf("gmail")!=-1) {
//			System.out.println("It is a gmail account");
//		}else {
//			System.out.println("Not a gmail");
//		}
		
		//remove special character from string
		
//		String str = "aBc@#d12&*34*5";
//		String str2 = str.replaceAll("[\\W]", "");
//		System.out.println("Original string: "+str);
//		System.out.println("Processed string: "+str2);
		
//		// remove extra space
//		String str = "I   am    ashish.   What is your    name?";
//		String str2 = str.replaceAll("[ ]+", " ");
//		System.out.println("Original string: "+str);
//		System.out.println("Processed string: "+str2);
		
		// split words
		String str = "I   am    ashish.   What is your    name?";
		String str2 = str.trim().replaceAll("[\\W]+", " ").replaceAll("[\\s]+", " ");
		System.out.println("Original string: "+str);
		String words[] = str2.split("\\s");
		System.out.println(words.length);
		

	}

}
