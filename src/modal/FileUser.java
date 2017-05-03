package modal;

import java.io.Serializable;

public class FileUser implements Serializable {
	private String filePath;
	private String method;
	
	public FileUser(){
		super();
		
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
