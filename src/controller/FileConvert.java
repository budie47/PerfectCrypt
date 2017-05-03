package controller;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileConvert {
	String file;
	int sizeFile;
	public FileConvert(){
		super();
	}
	public void setFile(String f){
		file =f;
	}
	public String getFileName(){
		return file;
	}
	
	public int getSizeFile() {
		return sizeFile;
	}
	public void setSizeFile(int sizeFile) {
		this.sizeFile = sizeFile;
	}
	public byte[] convertToByte(String filePath){
		byte[] data = null;
		try{
			Path path = Paths.get(filePath);
			data = Files.readAllBytes(path);
	
		}catch (Exception err){
			err.printStackTrace();
		}
		return data;
	}
	
	public void convertToFile(byte[] plainText, String fileName){
		try{
			
			FileOutputStream fos = new FileOutputStream(fileName);
			fos.write(plainText);
			fos.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
