package com.praveen.jira.plugin;

import java.util.ArrayList;
import java.util.List;


public enum Command {
	LOGWORK("log","POST","http://localhost:8080/rest/api/2/issue/${jiraId}/worklog","1");
	
	private String cmd;
	private String method;
	private String endpoint;
	private List<Integer> pathParamIndices;
	
	Command(String cmd,String method,String url,String indices){
		this.cmd = cmd;
		this.method = method;
		this.endpoint = url;
		String[] indexStr = indices.split(",");
		this.pathParamIndices = new ArrayList<>();
		for(String index : indexStr){
			this.pathParamIndices.add(Integer.parseInt(index));
		}
	}
	
	
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public List<Integer> getPathParamIndices() {
		return pathParamIndices;
	}



	public void setPathParamIndices(List<Integer> pathParamIndices) {
		this.pathParamIndices = pathParamIndices;
	}



	public static Command getCommandData(String cmd){
		for(Command command : Command.values()){
			if(cmd.equalsIgnoreCase(command.getCmd()))
				return command;
		}
		return null;
	}
}
