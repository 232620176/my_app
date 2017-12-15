package com.hydra.hadoop.mr.logs.process;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunLogAnalyzeProcessJob {
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "process analyze";
		String input = "/usr/output/logs/dateStr/analyzer/part-r-00000".replaceAll("dateStr", dateStr);
		String output = "/usr/output/logs/dateStr/process".replaceAll("dateStr", dateStr);
		String[] path = {input, output};
		MRUtil.runTemplate(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunLogAnalyzeProcessJob.class);
				
				job.setMapperClass(ProcessMapper.class);
				job.setReducerClass(ProcessReduce.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(IntWritable.class);
			}
		}, path);
	}
	
	public static class ProcessMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private static final IntWritable ONE = new IntWritable(1);
		private Text keyText = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] ss = line.split("\\s");
			keyText.set(ss[3]);
			context.write(keyText, ONE);
		}
	}
	
	public static class ProcessReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
		private IntWritable iSum = new IntWritable();
		
		@Override
		protected void reduce(Text key, Iterable<IntWritable> value, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable i : value){
				sum += i.get();
			}
			iSum.set(sum);
			context.write(key, iSum);
		}
	}
}
