import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.IOUtils;


public class Test {
	
	public static void main(String[] args) throws Exception {
		URL myURL = new URL("http://localhost:8080/rest/api/2/issue/TP-1");
		HttpURLConnection connection = (HttpURLConnection)myURL.openConnection();
		String userCredentials = "praveensakra:praveensakra";
 		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
		connection.setRequestProperty ("Authorization", basicAuth);
		connection.setRequestMethod("GET");
		/*myURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		myURLConnection.setRequestProperty("Content-Length", "" + postData.getBytes().length);*/
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		StringBuilder strBuilder = new StringBuilder();
		InputStream in = connection.getInputStream();
		
		String respone = IOUtils.toString(in);
		
		
		
		
		System.out.println("response :: " + respone);
		System.out.println("response code :: " + connection.getResponseCode());
	}

}
