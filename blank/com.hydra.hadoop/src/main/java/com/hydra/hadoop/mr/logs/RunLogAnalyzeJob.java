package com.hydra.hadoop.mr.logs;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunLogAnalyzeJob {
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "log analyze";
		String path = "logs/dateStr/analyzer".replaceAll("dateStr", dateStr);
		String[] pathes = {path, path};
		MRUtil.runTemplateWithBasePath(name, 3, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunLogAnalyzeJob.class);
				
				job.setMapperClass(LogAnalyzeMapper.class);
				job.setReducerClass(LogAnalyzeReduce.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(Text.class);
				
				job.setPartitionerClass(LogAnalyzePartition.class);
			}
		}, pathes);
	}
	
	public static class LogAnalyzeMapper extends Mapper<LongWritable, Text, Text, Text>{
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] as = line.split("\\s");
			if(line.indexOf("process start") > -1){
				StringBuilder sb = new StringBuilder(as[0]).append(" ").append(as[1])
						.append(" ").append(line.split("--process start---\\s")[1]);
				context.write(new Text("process"), new Text(sb.toString()));
			}else if(line.indexOf("process end") > -1){
				StringBuilder sb = new StringBuilder(as[0]).append(" ").append(as[1])
						.append(" ").append(line.split("--process end---\\s")[1]);
				context.write(new Text("times"), new Text(sb.toString()));
			}else if(line.indexOf("Exception") > -1){
				context.write(new Text("exception"), value);
			}
		}
	}
	
	public static class LogAnalyzeReduce extends Reducer<Text, Text, Text, Text>{
		@Override
		protected void reduce(Text key, Iterable<Text> value, Context context)
				throws IOException, InterruptedException {
			for(Text t : value){
				context.write(key, t);
			}
		}
	}
}
