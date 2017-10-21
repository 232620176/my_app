package com.hydra.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;

import com.sun.jersey.core.impl.provider.entity.XMLJAXBElementProvider.Text;

public class JobRun {
	public static void main(String[] args) throws Exception{
		Configuration conf = new Configuration();
		conf.set("mapred.job.tracker", "node3:9001");
		JobConf jobConf = new JobConf(conf);
		jobConf.setJarByClass(JobRun.class);
		jobConf.setMapOutputKeyClass(Text.class);
		jobConf.setMapOutputValueClass(IntWritable.class);
		
		Job job = Job.getInstance(jobConf);
		job.setMapperClass(WcMapper.class);
		job.setReducerClass(WcReducer.class);
		
//		job.setNumReduceTasks(1);//设置reduce任务的个数
		
		//mapreduce 输入数据所在目录或者文件
		FileInputFormat.addInputPath(jobConf, new Path(""));
		//mr执行之后的输出数据的目录
		FileOutputFormat.setOutputPath(jobConf, new Path(""));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
