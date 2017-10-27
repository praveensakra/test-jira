import java.util.Base64;


public class EncodeTest {
	
	public static void main(String[] args) {
		String auth = "";
		String encoded = Base64.getEncoder().encodeToString(auth.getBytes());
		System.out.println("encoded :: " + encoded);
	}

}
