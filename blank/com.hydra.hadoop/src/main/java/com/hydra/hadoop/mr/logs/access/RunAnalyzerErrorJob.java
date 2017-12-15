package com.hydra.hadoop.mr.logs.access;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import com.hydra.hadoop.mr.logs.SortProcess;
import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class RunAnalyzerErrorJob {
	private static final String PATTERN_STR = "([^ ]*) ([^ ]*) ([^ ]*) (-|\\[.*\\]) ([^ \\\"]*|\\\"[^\\\"]*\\\") (-|[0-9]*) (-|[0-9]*) ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ ]*) ([^ ]*) ([^ ]*)(?: ([^ \\\"]*|\\\".*\\\") ([^ \\\"]*|\\\"[^\\\"]*\\\") ([^ \\\"]*|\\\".*\\\"))?";
	private static final Pattern PATTERN = Pattern.compile(PATTERN_STR);
	
	public static void main(String[] args) throws Exception{
		String dateStr = args[0];
		String name = "access log error";
		String in = "access/dateStr/analyzer".replaceAll("dateStr", dateStr);
		String out = "access/dateStr/analyzer".replaceAll("dateStr", dateStr);
		String[] pathes = {in, out};
		MRUtil.runTemplateWithBasePath(name, new Callable() {
			@Override
			public void initJob(Job job) {
				job.setJarByClass(RunAnalyzerErrorJob.class);
				
				job.setMapperClass(CountErrorMapper.class);
				job.setReducerClass(CountErrorReduce.class);
				
				job.setMapOutputKeyClass(Pojo.class);
				job.setMapOutputValueClass(IntWritable.class);
				
				job.setSortComparatorClass(SortProcess.class);
				job.setGroupingComparatorClass(SortProcess.class);
			}
		}, pathes);
	}
	
	public static class CountErrorMapper extends Mapper<LongWritable, Text, Pojo, IntWritable>{
		private static final IntWritable ONE = new IntWritable(1);
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			Matcher mat = PATTERN.matcher(line);
			boolean match = mat.matches();
			if(match){
				String statu = mat.group(6);
				if(!"200".equals(statu)){
					String url = mat.group(5).split("\\?")[0];
					Pojo pojo = new Pojo();
					pojo.setStatus(statu);
					pojo.setUrl(url);
					context.write(pojo, ONE);
				}
			}
		}
	}
	
	public static class CountErrorReduce extends Reducer<Pojo, IntWritable, Pojo, IntWritable>{
		private IntWritable iSum = new IntWritable();
		
		@Override
		protected void reduce(Pojo key, Iterable<IntWritable> value, Context context)
				throws IOException, InterruptedException {
			int sum = 0;
			for(IntWritable i : value){
				sum += i.get();
			}
			key.sum = sum;
			iSum.set(sum);
			context.write(key, iSum);
		}
	}
	
	public static class Pojo implements WritableComparable<Pojo>{
		private String url;
		private String status;
		private volatile int sum;
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public String getStatus() {
			return status;
		}
		
		public void setStatus(String status) {
			this.status = status;
		}
		
		@Override
		public void write(DataOutput out) throws IOException {
			out.writeUTF(url);
			out.writeUTF(status);
		}
		
		@Override
		public void readFields(DataInput in) throws IOException {
			this.url = in.readUTF();
			this.status = in.readUTF();
		}
		
		@Override
		public int compareTo(Pojo pojo) {
			int res = this.status.compareTo(pojo.status);
			if(res == 0){
				return this.url.compareTo(pojo.url);
			}
			return res;
		}
		
		@Override
		public String toString() {
			return this.status + "\t" + this.sum + "\t" + this.url;
		}
	}
}
