package com.hydra.hadoop.mr.logs.exception;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.logs.SortProcess;
import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunSortLogAnalyzeExceptionJob {
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "sort exception analyze";
		String input = "/usr/output/logs/dateStr/exception/part-r-00000".replaceAll("dateStr", dateStr);
		String output = "/usr/output/logs/dateStr/sortException".replaceAll("dateStr", dateStr);
		String[] path = {input, output};
		MRUtil.runTemplate(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunSortLogAnalyzeExceptionJob.class);
				
				job.setMapperClass(ExceptionMapper.class);
				job.setReducerClass(ExceptionReduce.class);
				
				job.setMapOutputKeyClass(IntWritable.class);
				job.setMapOutputValueClass(Text.class);
				
				job.setSortComparatorClass(SortProcess.class);
			}
		}, path);
	}
	
	public static class ExceptionMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
		private IntWritable keyInt = new IntWritable();
		private Text valText = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] ss = line.split("\\t");
			keyInt.set(Integer.valueOf(ss[1]));
			valText.set(ss[0]);
			context.write(keyInt, valText);
		}
	}
	
	public static class ExceptionReduce extends Reducer<IntWritable, Text, IntWritable, Text>{
		@Override
		protected void reduce(IntWritable key, Iterable<Text> value, Context context)
				throws IOException, InterruptedException {
			for(Text t : value){
				context.write(key, t);
			}
		}
	}
}
