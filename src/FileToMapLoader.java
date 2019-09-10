/**
 * Created by shawn on 5/3/2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;

public class FileToMapLoader {
    public static final String FILEPATH = "C:\\Users\\shawn\\Desktop\\files\\stonybrook\\2016-2017\\Spring 2017\\courses\\CSE 214\\Homework\\chun_hung_li_110807126.hw6\\src\\sample.csv";
    public static class OrderComparator<E> implements Comparator<E>{
        Order order;
        public enum Order{
            NAME, PHONE, ORIGINAL
        }
        public OrderComparator(){
            order = Order.ORIGINAL;
        }
        public void setMode(Order order){
            this.order = order;
        }
        @Override
        public int compare(E o1, E o2){
            switch(order) {
                case NAME:
                    return ((String) o1).compareTo((String) o2);
                case PHONE:
                    long phone1 = Long.parseLong(((String)o1).replaceAll("[^0-9]",""));
                    long phone2 = Long.parseLong(((String)o2).replaceAll("[^0-9]",""));
                    if (phone1 < phone2) {
                        return -1;
                    } else if (phone1 == phone2) {
                        return 0;
                    } else {
                        return 1;
                    }
                case ORIGINAL:
                    return 1;
                default: break;
            }
            return 0;
        }
    }
    public static void main(String[] args){
        try {
            File data = new File(FILEPATH);
            Scanner s2 = new Scanner(data);
            OrderComparator orderComparator = new OrderComparator();
            Scanner s1 = new Scanner(System.in);
            int orderType;
            do {
                System.out.println("Input the integer corresponding to the order: ");
                System.out.println("1. NAME");
                System.out.println("2. PHONE");
                System.out.println("3. ORIGINAL");
                System.out.println();
                while (!s1.hasNextInt()) {
                    String err = s1.next();
                    System.out.printf("%s is not a valid choice", err);
                    System.out.println();
                    System.out.println("Input the integer corresponding to the order: ");
                    System.out.println("1. NAME");
                    System.out.println("2. PHONE");
                    System.out.println("3. ORIGINAL");
                }
                orderType = s1.nextInt();
            } while (orderType < 1 || orderType > 3);
            System.out.printf("%s was chosen", orderType);
            System.out.println();
            Map<String, String> TM = null;
            if (orderType == 1) {
                orderComparator.setMode(orderComparator.order.NAME);
                TM = new TreeMap(orderComparator);
            } else if (orderType == 2) {
                orderComparator.setMode(orderComparator.order.PHONE);
                TM = new TreeMap(orderComparator);
            } else {
                TM = new LinkedHashMap();
            }
            s2.useDelimiter("\\n");
            while (s2.hasNext()) {
                String currentEntry = s2.next();
                String[] parts = currentEntry.split(",");
                if (orderComparator.order == orderComparator.order.PHONE) {
                    TM.put(parts[1], parts[0]);
                } else {
                    TM.put(parts[0], parts[1]);
                }
            }
            Set keys = TM.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                StringBuilder key = new StringBuilder();
                key.append((String) i.next());
                //System.out.println(key);
                String value = TM.get(key.toString());
                while (key.length() < 40) {
                    key.append(" ");
                }
                System.out.println(key + value);
            }
        }catch(FileNotFoundException e){
            System.err.println("FILEPATH ERROR");
        }
        //System.out.println(TM.toString());
    }
}
