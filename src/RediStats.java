import java.io.*;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class RediStats implements Stats{
	private String location;
    
    public RediStats() {
		setLocation(Locations.getOrder_location());
	}
    
    public void display() {
    	System.out.println("Welcome to the Stats Option.");
    	System.out.println("1:Total Revenue  2:Most Frequent Ordered Item  3:Busiest Time of Day");
		System.out.println("Select: ");
    	
		int option;
		Scanner sc = new Scanner(System.in);
		option = sc.nextInt();
		
		System.out.println("Enter the redi_ID: ");
		String redi_ID = sc.next();
		switch(option) {
			case 1: totalRevenue(redi_ID);
					break;
			case 2:	mostOrdered(redi_ID);
					break;
			case 3: timeOfDay(redi_ID);
					break;
			default: System.out.println("Wrong Selection!!");
		}
    }
    
    public void totalRevenue(String redi_ID){
        double revenue=0.0;
        try {
            File file = new File(getLocation());
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[2].equalsIgnoreCase(redi_ID) && tempArr[8].equals("Accept")) {
                    revenue += Double.parseDouble(tempArr[7]);
                }
                line = br.readLine();
            }
        }
        catch(Exception e){}
        System.out.println("Total money earned by "+redi_ID+" Redi is Rupees "+revenue);
    }

    void mostOrdered(String redi_ID){
        HashMap<String, Integer> items = new HashMap<>();
        try {
            File file = new File(location);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[2].equalsIgnoreCase(redi_ID) && tempArr[8].equals("Accept")) {

                    if(items.containsKey(tempArr[4])) {
                        items.put(tempArr[4], (Integer.parseInt(tempArr[6]) + items.get(tempArr[4])));
                    }
                    else{
                        items.put(tempArr[4], (Integer.parseInt(tempArr[6])));
                    }
                }
                line = br.readLine();
            }
        }
        catch(Exception e){}
        if(!items.isEmpty()) {
            Map<String, Integer> sorteditems = sortByValue(items);

            int maxOrders = Collections.max(sorteditems.values());
            ArrayList<String> names = new ArrayList<>();

            for (Map.Entry<String, Integer> aa : sorteditems.entrySet())
                if (aa.getValue() == maxOrders) names.add(aa.getKey());

            System.out.println("Most Ordered Item/s is/are: " + names);
        }
        else{
            System.out.println("No accepted orders in this redi");
        }
    }
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2){
                return (o2.getValue()).compareTo(o1.getValue()); //have reverse o1 and o2 to get descending list
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    void timeOfDay(String redi){ //checks which time of day has most orders in some redi
        int earlymorning=0, /*6 to 9*/ morning=0, /*9 to 12*/ afternoon=0, /*12 to 3*/ evening=0, /*3 to 6*/ night=0 /*6 to 9*/;
        try {
            File file = new File(location);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if(tempArr[2].equalsIgnoreCase(redi) && tempArr[8].equals("Accept")){
                	
                	long timeStamp = Long.parseLong(tempArr[1]); //epoch time
                	LocalTime time = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault()).toLocalTime();
                	
                	if(time.compareTo(LocalTime.parse("06:00:00"))>=0 && time.compareTo(LocalTime.parse("08:59:59"))<=0) earlymorning++;
                	else if(time.compareTo(LocalTime.parse("09:00:00"))>=0 && time.compareTo(LocalTime.parse("11:59:59"))<=0) morning++;
                	else if(time.compareTo(LocalTime.parse("12:00:00"))>=0 && time.compareTo(LocalTime.parse("14:59:59"))<=0) afternoon++;
                	else if(time.compareTo(LocalTime.parse("15:00:00"))>=0 && time.compareTo(LocalTime.parse("17:59:59"))<=0) evening++;
                	else night++;
        		}
                line=br.readLine();
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch(IOException e){
        }
        
        int[] arr=new int[]{earlymorning, morning, afternoon,evening, night};
        Arrays.sort(arr);
        System.out.println("Most orders received during-");
        if(arr[4]==earlymorning){
            System.out.println("early morning");
        }
        else if(arr[4]==morning){
            System.out.println("morning");
        }
        else if(arr[4]==afternoon){
            System.out.println("afternoon");
        }
        else if(arr[4]==evening){
            System.out.println("evening");
        }
        else if(arr[4]==night){
            System.out.println("night");
        }
    }
    
	private String getLocation() {
		return location;
	}

	private void setLocation(String location) {
		this.location = location;
	}

	public static void main(String[] args) {
		RediStats summary = new RediStats();
		summary.display();
	}

}
