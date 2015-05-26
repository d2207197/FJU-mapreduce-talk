package part1;

import java.io.IOException;

import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class WordCountMapper extends MapReduceBase
    implements Mapper<LongWritable, Text, Text, IntWritable> {

    public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, 
                    Reporter reporter) throws IOException{
        String delimiter = " "; 
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line, delimiter);
			
        while (tokenizer.hasMoreTokens()) {
            String aWord = tokenizer.nextToken();
            output.collect(new Text(aWord), new IntWritable(1));
        }
    }
}

