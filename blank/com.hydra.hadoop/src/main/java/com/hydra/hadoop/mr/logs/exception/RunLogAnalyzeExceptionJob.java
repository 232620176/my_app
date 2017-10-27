package com.hydra.hadoop.mr.logs.exception;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunLogAnalyzeExceptionJob {
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "exception analyze";
		String input = "/usr/output/logs/dateStr/analyzer/part-r-00002".replaceAll("dateStr", dateStr);
		String output = "/usr/output/logs/dateStr/exception".replaceAll("dateStr", dateStr);
		String[] path = {input, output};
		MRUtil.runTemplate(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunLogAnalyzeExceptionJob.class);
				
				job.setMapperClass(ExceptionMapper.class);
				job.setReducerClass(ExceptionReduce.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(IntWritable.class);
			}
		}, path);
	}
	
	public static class ExceptionMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private static final IntWritable ONE = new IntWritable(1);
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] ss = line.split(":");
			if(ss.length > 1){
				context.write(new Text(ss[ss.length - 1]), ONE);
			}
		}
	}
	
	public static class ExceptionReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		@Override
		protected void reduce(Text key, Iterable<IntWritable> value, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable i : value){
				sum += i.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
}
