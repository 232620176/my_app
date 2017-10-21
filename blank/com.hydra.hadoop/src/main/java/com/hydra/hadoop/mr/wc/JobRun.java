package com.hydra.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JobRun {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf, "word count");
		job.setJarByClass(JobRun.class);
		job.setMapperClass(WcMapper.class);
		job.setReducerClass(WcReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
//		job.setNumReduceTasks(1);//设置reduce任务的个数
		
		//mapreduce 输入数据所在目录或者文件
		FileInputFormat.addInputPath(job, new Path("/usr/input/wc"));
		//mr执行之后的输出数据的目录
		FileOutputFormat.setOutputPath(job, new Path("/usr/output/wc"));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
