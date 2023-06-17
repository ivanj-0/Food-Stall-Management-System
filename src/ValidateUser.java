import java.io.*;
import java.util.Scanner;

public class ValidateUser{
    private String usermailid;
    private String location;
    static String user_ID;
    
    public ValidateUser(){
        setLocation(Locations.getStudentDetails_location());
    }
     
	public boolean valid(){
		return validate();
    }
	
    private boolean validate(){
        boolean flag=false;
        try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            
            while (line != null) {
            	
                tempArr = line.split(",");
                if (tempArr[1].equals(getUsermailid())) {
                    flag = true;
                    break;
                }
                line=br.readLine();
            }
        }
        catch(FileNotFoundException e){
            flag=false;
        }
        catch(IOException e){
        	e.printStackTrace();
        }
        
        return flag;
    }
    
    private String getUsermailid() {
		return usermailid;
	}

	private void setUsermailid(String usermailid) {
		this.usermailid = usermailid;
	}

	private String getLocation() {
		return location;
	}
	
	private void setLocation(String location) {
		this.location = location;
	}
}
