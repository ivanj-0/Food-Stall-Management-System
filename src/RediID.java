public class RediID {
    static String id;
    public static void setID(String id){
        RediID.id=id;
    }
    public static String getId(){
        return id;
    }
}
