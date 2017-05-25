package com.citi.jira.plugin;


public enum Command {
	LOGWORK("log","POST","");
	
	private String cmd;
	private String method;
	private String endpoint;
	
	Command(String cmd,String method,String url){
		this.cmd = cmd;
		this.method = method;
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

	
	
	public static Command getCommandData(String cmd){
		for(Command command : Command.values()){
			if(cmd.equalsIgnoreCase(command.getCmd()))
				return command;
		}
		return null;
	}
}
