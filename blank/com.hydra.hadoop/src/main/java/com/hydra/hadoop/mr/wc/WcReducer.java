package com.hydra.hadoop.mr.wc;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WcReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	private IntWritable iSum = new IntWritable();
	
	@Override
	protected void reduce(Text key, Iterable<IntWritable> iterable,
			Context context) throws IOException, InterruptedException {
		int sum = 0;
		for(IntWritable i : iterable){
			sum += i.get();
		}
		iSum.set(sum);
		context.write(key, iSum);
	}

}
