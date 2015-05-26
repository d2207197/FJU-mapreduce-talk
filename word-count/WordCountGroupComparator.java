package part1;
import java.lang.Character;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordCountGroupComparator extends WritableComparator {

    public WordCountGroupComparator() {
        super(Text.class, true);
    }	

    // TODO group by start letter
    public int compare(WritableComparable o1, WritableComparable o2) {
		
        Text key1 = (Text) o1;
        Text key2 = (Text) o2;
        Character c1 = key1.toString().charAt(0);
        Character c2 = key2.toString().charAt(0);
        return c1.compareTo(c2);
	//	return ((key1.toString().charAt(0)).compareTo((key2.toString().charAt(0))));
        //return key1.compareTo(key2);
    }
}
