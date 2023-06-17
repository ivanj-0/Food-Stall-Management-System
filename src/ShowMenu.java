import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class ShowMenu {
	private String location;
	
	public ShowMenu() {
		setLocation(Locations.getMenu_location());
	}
	public void show(String redi_ID){
		try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
				if(tempArr[0].equalsIgnoreCase(redi_ID))
                System.out.println(Arrays.toString(tempArr));
                line = br.readLine();
            }
        }
        catch(Exception e){}
	}
	private String getLocation() {
		return location;
	}
	private void setLocation(String location) {
		this.location = location;
	}

}
