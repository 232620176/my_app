package com.hydra.hadoop.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

import com.hydra.hadoop.mr.util.MRUtil;
import com.hydra.hadoop.mr.util.MRUtil.Callable;

public class JobRun {
	public static void main(String[] args) throws Exception{
		String tkName = "word count";
		String tkPath = "wc";
		String[] path = {MRUtil.BASE_INPUT_PATH + tkPath,
				MRUtil.BASE_OUTPUT_PATH + tkPath};
		String[] param = MRUtil.mergeArray(args, path);
		MRUtil.runTemplate(tkName, new Callable(){
			@Override
			public void initJob(Job job) {
				job.setJarByClass(JobRun.class);
				
				job.setMapperClass(WcMapper.class);
				job.setReducerClass(WcReducer.class);
				
				job.setMapOutputKeyClass(Text.class);
				job.setMapOutputValueClass(IntWritable.class);
			}
		}, param);
	}
}
