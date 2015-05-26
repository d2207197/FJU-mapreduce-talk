package part1;

import java.lang.Character;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Partitioner;

public class WordCountPartitioner implements Partitioner<Text, IntWritable> {
	
    public void configure(JobConf job) {
    }

    // TODO modify this function to assign the words start with Aa ~ Gg 
    //      to first reducer, and the remaining words to second reducer.
    public int getPartition(Text key, IntWritable value, int numPartitions) {
	
        if(key.toString().toLowerCase().charAt(0) <'g')
            return 0;
        else 
            return 1;
    }
}
