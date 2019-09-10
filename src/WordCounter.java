/**
 * Created by shawn on 5/3/2017.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;

public class WordCounter {
    public static String FILEPATH;
    public static class ValueComparator implements Comparator<String>{

        HashMap<String, Integer> map = new HashMap<String, Integer>();

        public ValueComparator(HashMap<String, Integer> map){
            this.map.putAll(map);
        }

        @Override
        public int compare(String s1, String s2) {
            if(map.get(s1) >= map.get(s2)){
                return -1;
            }else{
                return 1;
            }
        }
    }
    public static void main(String[] args){
        try {
            System.out.println("Enter exact path of text file:");
            Scanner s = new Scanner(System.in);
            FILEPATH = s.nextLine();
            System.out.println(FILEPATH);
            File data = new File(FILEPATH);
            Scanner s1 = new Scanner(data);
            s1.useDelimiter("\\s+");
            HashMap TM = new HashMap();
            while (s1.hasNext()) {
                String word = s1.next();
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = word.toLowerCase();
                if (TM.containsKey(word)) {
                    TM.put(word, (int)TM.get(word) + 1);
                } else {
                    TM.put(word, 1);
                }
            }
            Comparator<String> comparator = new ValueComparator(TM);
            TreeMap<String, Integer> sortedTM = new TreeMap<String, Integer>(comparator);
            sortedTM.putAll(TM);
            Set keys = sortedTM.keySet();
            for (Iterator i = keys.iterator(); i.hasNext(); ) {
                StringBuilder key = new StringBuilder();
                key.append((String) i.next());
                String value = "" + TM.get(key.toString());
                while (key.length() < 40) {
                    key.append(" ");
                }
                System.out.println(key + value);
            }
        }catch(FileNotFoundException e){
            System.err.println("FILEPATH ERROR");
        }
    }
}
