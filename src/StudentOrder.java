import java.io.*;
import java.time.Instant;
import java.util.Scanner;

public class StudentOrder {
    private String redi;
    private String student_id;
    private String menu_location=Locations.getMenu_location();
    private String order_location=Locations.getOrder_location();
    public String getRedi() {
        return redi;
    }
    public StudentOrder(String redi) {
        this.redi = redi;
    }
    public void order() throws FileNotFoundException {
        String item_id, item_name;
        int quantity;
        ShowMenu sm=new ShowMenu();
        sm.show(RediID.getId());
        Scanner in=new Scanner(System.in);
        System.out.println("Enter Item ID of the Item you want to order");
        item_id=in.next();
        System.out.println("Enter Item Name of the Item you want to order");
        item_name=in.next();
        if(checkItem(item_id)){
            System.out.println("Enter quantity to be ordered");
            quantity=in.nextInt();
            if(checkQuantity(item_id,quantity)){
                System.out.println("Order Placed!!");
                long epoch=Instant.now().toEpochMilli();
                DecreaseQuantity.decreaseQuantity(getRedi(), item_id, Integer.toString(quantity));//decrease quantity in menu
                StringBuffer neworder = new StringBuffer("");
                double price=getPrice(item_id);
                student_id=StudentID.getId();
                neworder.append(student_id+","+epoch+","+redi+","+item_id+","+item_name+","+price+","+quantity+","+(quantity*price)+","+"Wait");
                String itemData=(neworder.toString());
                File path = new File(order_location);
                BufferedReader reader = null;
                FileWriter writer=null;
                String content = "";
                try {
                    reader = new BufferedReader(new FileReader(path));
                    String line = reader.readLine();

                    while (line != null){
                        content = content + line + System.lineSeparator();
                        line = reader.readLine();
                    }

                    content = content + itemData + System.lineSeparator();
                    writer=new FileWriter(path);
                    writer.write(content);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        writer.flush();
                        writer.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else System.out.println("Insufficient Quantity!!");
        }
        else System.out.println("Item does not exist");



    }
    private boolean checkItem(String item_id){
        boolean flag=false;
        try {
            File file = new File(menu_location);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[1].equals(item_id) && tempArr[0].equals(redi)) {
                    flag = true;
                    break;
                }
                line = br.readLine();
            }
        }
        catch(Exception e){System.out.println("Exception Caught in checkItem");}
        return flag;
    }
    private boolean checkQuantity(String item, int quantity){
        try {
            File file = new File(menu_location);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[1].equals(item)) {
                    if (Integer.parseInt(tempArr[4]) >= quantity)
                        return true;
                }
                line = br.readLine();
            }
        }
        catch(Exception e){}
        return false;
    }
    private double getPrice(String item_id){
        try {
            File file = new File(menu_location);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] tempArr;
            while (line != null) {
                tempArr = line.split(",");
                if (tempArr[1].equals(item_id)&&tempArr[0].equalsIgnoreCase(redi) ) {
                    return Double.parseDouble(tempArr[3]);
                }
                line = br.readLine();
            }
        }
        catch(Exception e){System.out.println("Exception Caught in checkItem");}
        return 0.0;
    }
}
