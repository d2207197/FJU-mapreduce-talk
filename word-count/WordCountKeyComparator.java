package part1;

import java.lang.Character;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordCountKeyComparator extends WritableComparator {
    public WordCountKeyComparator() {
        super(Text.class, true);
    }
	
    // TODO Order by A -> a -> B -> b .... 
    public int compare(WritableComparable o1, WritableComparable o2) {
        Text key1 = (Text) o1;
        Text key2 = (Text) o2;
        String str1 = key1.toString();
        String str2 = key2.toString();
        String lower1 = str1.toLowerCase();
        String lower2 = str2.toLowerCase();
        if(lower1.compareTo(lower2) >= 0){
            return str1.compareTo(str2);
        }
        else{
            return lower1.compareTo(lower2);
        }
			
        //	return key1.compareTo(key2);
		
    }
}
