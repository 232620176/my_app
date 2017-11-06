package com.hydra.hadoop.mr.logs.access;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunCountErrorJob {
	private static final String PATTERN_STR = "([^ ]*) ([^ ]*) ([^ ]*) (-|\\[.*\\]) ([^ \\\"]*|\\\"[^\\\"]*\\\") (-|[0-9]*) (-|[0-9]*) ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ ]*) ([^ ]*) ([^ ]*)(?: ([^ \\\"]*|\\\".*\\\") ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ \\\"]*|\\\".*\\\"))?";
	private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);
	
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "access log error";
		String in = "access/dateStr/analyzer".replaceAll("dateStr", dateStr);
		String out = "access/dateStr/error".replaceAll("dateStr", dateStr);
		String[] pathes = {in, out};
		MRUtil.runTemplateWithBasePath(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunCountErrorJob.class);
				
				job.setMapperClass(CountErrorMapper.class);
				job.setReducerClass(CountErrorReduce.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(IntWritable.class);
			}
		}, pathes);
	}
	
	public static class CountErrorMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			Matcher mat = PATTERN.matcher(line);
			boolean match = mat.matches();
			if(match){
				String code = mat.group(6);
				context.write(new Text(code), new IntWritable(1));
			}
		}
	}
	
	public static class CountErrorReduce extends Reducer<Text, IntWritable, Text, IntWritable>{
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
