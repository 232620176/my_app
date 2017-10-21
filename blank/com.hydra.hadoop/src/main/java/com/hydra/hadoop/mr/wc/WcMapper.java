package com.hydra.hadoop.mr.wc;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WcMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	@Override
	//每次调用map方法会传入split中一行数据key：该行数据所在文件中的位置下标，value：这行数据
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line);
		while(st.hasMoreTokens()){
			String world = st.nextToken();
			context.write(new Text(world), new IntWritable(1));//map的输出
		}
	}
}
