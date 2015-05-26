package part1;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;

import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

public class WordCount {
    public static void main(String[] args) throws Exception {
        WordCount wc = new WordCount();
        wc.run(args[0], args[1]); 
    }
	
    public void run(String inputPath, String outputPath) throws IOException {
        /* 
         * You can lookup usage of these api from this website. =)
         * http://tool.oschina.net/uploads/apidocs/hadoop/index.html?overview-summary.html
         */
		 
        JobConf conf = new JobConf(WordCount.class);
        conf.setJobName("word count");
		
        conf.setMapperClass(WordCountMapper.class);
        conf.setReducerClass(WordCountReducer.class);
		
        /*
         * If your mapper output key-value pair is different from final 
         * output key-value pair, please remember to setup these two APIs.
         */ 
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
		
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
		
        // TODO setup your key comparator class
        conf.setOutputKeyComparatorClass(WordCountKeyComparator.class);
	
        // TODO setup your partitioner class
        conf.setPartitionerClass(WordCountPartitioner.class);
        // TODO setup your key group comparator class
        conf.setOutputValueGroupingComparator(WordCountGroupComparator.class);		
		
        FileInputFormat.addInputPath(conf, new Path(inputPath));
        FileOutputFormat.setOutputPath(conf, new Path(outputPath));

        conf.setNumMapTasks(3);
        conf.setNumReduceTasks(2);

        JobClient.runJob(conf);
    }
}
