package com.hydra.hadoop.mr.logs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunLogAnalyzerTimesJob {
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "times analyzer";
		String input = "/usr/output/logs/dateStr/analyzer/part-r-00001".replaceAll("dateStr", dateStr);
		String output = "/usr/output/logs/dateStr/times".replaceAll("dateStr", dateStr);
		String[] path = {input, output};
		MRUtil.runTemplate(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunLogAnalyzerTimesJob.class);
				
				job.setMapperClass(TimesMapper.class);
				job.setReducerClass(TimesReduce.class);
				
				job.setMapOutputKeyClass(IntWritable.class);
				job.setMapOutputValueClass(Text.class);
				
				job.setSortComparatorClass(SortProcess.class);
			}
		}, path);
	}
	
	public static class TimesMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] ss = line.split("\\s");
			String timeStr = ss[4];
			String time = timeStr.split(":")[1].replaceAll("ms", "");
			context.write(new IntWritable(Integer.valueOf(time)), value);
		}
	}
	
	public static class TimesReduce extends Reducer<IntWritable, Text, IntWritable, Text>{
		@Override
		protected void reduce(IntWritable key, Iterable<Text> value, Context context)
				throws IOException, InterruptedException {
			for(Text t : value){
				context.write(key, t);
			}
		}
	}
}
