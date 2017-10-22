package com.hydra.hadoop.mr.tp;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class TemperaturePartition extends Partitioner<KeyPair, Text>{
	@Override
	public int getPartition(KeyPair key, Text value, int numPartitions) {
		//按年份分区
		return key.getYear() * 127 % numPartitions;
	}
}
