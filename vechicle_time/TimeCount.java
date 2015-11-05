import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TimeCount {

    public static class TokenizerMapper extends Mapper<Object, Text, IntWritable, IntWritable>{

        private IntWritable id = new IntWritable();
        private IntWritable val = new IntWritable();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] line = value.toString().split(",");
            id.set(Integer.parseInt(line[0]));
            val.set((int) Float.parseFloat(line[7]));
            context.write(id,val);
        }
    }


    public static class IntSumReducer
            extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable> {
            private IntWritable result = new IntWritable();

            public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

                int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
                for (IntWritable val : values) {
                    int v = val.get();
                    if(v < min) min = v;
                    if(v > max) max = v;
        
                }
                result.set(max-min);
                context.write(key, result);
            }
    }
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Lane count");
        job.setJarByClass(TimeCount.class);
        job.setMapperClass(TokenizerMapper.class);
        //job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        //job.setNumReduceTasks(0);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
