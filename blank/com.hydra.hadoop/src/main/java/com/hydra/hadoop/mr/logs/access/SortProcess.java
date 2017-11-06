package com.hydra.hadoop.mr.logs.access;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class SortProcess extends WritableComparator{
	public SortProcess(){
		super(RunSortErrorJob.Pojo.class, true);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public int compare(WritableComparable a, WritableComparable b) {
		return -super.compare(a, b);
	}
}
