package com.hydra.hadoop.mr.logs;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class LogAnalyzePartition extends Partitioner<Text, Text> {
	@Override
	public int getPartition(Text key, Text value, int numPartitions) {
		return key.hashCode() % numPartitions;
	}
}
