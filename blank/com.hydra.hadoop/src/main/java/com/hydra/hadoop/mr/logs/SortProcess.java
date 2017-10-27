package com.hydra.hadoop.mr.logs;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
	public class SortProcess extends WritableComparator{
		public SortProcess(){
			super(IntWritable.class, true);
		}
		
		@Override
		@SuppressWarnings("rawtypes")
		public int compare(WritableComparable a, WritableComparable b) {
			return -super.compare(a, b);
		}
	}