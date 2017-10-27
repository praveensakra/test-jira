package com.praveen.jira.plugin;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

public class JiraApiService {
	
	
	public Object getFromJiraApi() throws Exception {
		
		URL myURL = new URL("http://localhost:8080/rest/api/2/issue/TP-1");
		HttpURLConnection connection = (HttpURLConnection)myURL.openConnection();
 		connection.setRequestProperty ("Authorization", AppCache.get("basicAuth").toString());
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		InputStream in = connection.getInputStream();
		
		String respone = IOUtils.toString(in);
		
		System.out.println("response :: " + respone);
		System.out.println("response code :: " + connection.getResponseCode());
	
		
		return null;
	}
	
	
	public int postToJiraApi(String url,String payLoad) throws Exception {
		///rest/api/2/issue/{issueIdOrKey}/worklog
		
		URL myURL = new URL(url);
		HttpURLConnection connection = (HttpURLConnection)myURL.openConnection();
 //		String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
 		System.out.println("Base encoded ... " + AppCache.get("basicAuth"));
		connection.setRequestProperty ("Authorization", AppCache.get("basicAuth").toString());
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Language", "en-US");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		
		connection.getOutputStream().write(payLoad.getBytes());
		connection.getOutputStream().flush();
		
		InputStream in = connection.getInputStream();
		
		String respone = IOUtils.toString(in);
		
		System.out.println("response :: " + respone);
		System.out.println("response code :: " + connection.getResponseCode());
		return connection.getResponseCode();
	}
	
	
	@SuppressWarnings("rawtypes")
	public Object callJiraApi(String cmd){
		
		StringBuilder payloadBuilder = new StringBuilder("{");
		String payload = "";
		
		if(cmd!=null && cmd.length() >0){
			List<String> params = new LinkedList<>(Arrays.asList(cmd.split(" ")));
			if(params.size() > 0){
				Command jiraCmd = Command.getCommandData(params.get(0));
				String endPoint = jiraCmd.getEndpoint();
				if(jiraCmd!=null && AppCache.get(params.get(0))!=null) {
					
					Map<String,String> pathParams = new HashMap<>();
					if("POST".equalsIgnoreCase(jiraCmd.getMethod())){
						
						if(AppCache.get(params.get(0)) instanceof Map){
							Map paramData = (Map)AppCache.get(params.get(0));
							
							if(paramData.get(String.valueOf(params.size()-1)) !=null){
								String reqKeyStr = paramData.get(String.valueOf(params.size()-1)).toString();
								List<String> reqKeys = new LinkedList<>(Arrays.asList(reqKeyStr.split(",")));
								
								for(Integer index : jiraCmd.getPathParamIndices()){
									pathParams.put(reqKeys.get(index-1), params.get(index).toString());
									reqKeys.remove(reqKeys.get(index-1));
									params.remove(params.get(index));
								}
								
								int i=1;
								for(String key : reqKeys){
									payloadBuilder.append("\"" + key + "\":");
									payloadBuilder.append("\"" + params.get(i) + "\",");
									i++;
								}
							}
							
						}
						payload = payloadBuilder.toString().substring(0, payloadBuilder.toString().length()-1);
						payload = payload + "}";
						
						try {
							
							StrSubstitutor substitutor = new StrSubstitutor(pathParams);
							endPoint = substitutor.replace(endPoint);
							System.out.println("JIRA end point ::" + endPoint);
							
							int code = postToJiraApi(endPoint, payload);
							System.out.println(code +" --- " + cmd );
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					
					
					
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		try {
			new JiraApiService().postToJiraApi("http://localhost:8080/rest/api/2/issue/PROJ1-11/worklog","{\"comment\": \"Test workasfasfsfsfsfaava client.\",\"started\": \"2017-05-25T13:47:18.251+0000\",\"timeSpentSeconds\": 12000}");
			//{\"comment\": \"I did some work here.\",\"started\": \"2017-05-25T13:47:18.251+0000\",\"timeSpentSeconds\": 12000}
			
			// old encoded header
			// cHJhdmVlbnNha3JhOnByYXZlZW5zYWtyYQ
			System.out.println(Command.getCommandData("log"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
