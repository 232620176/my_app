package com.hydra.hadoop.mr.tp;

import org.apache.hadoop.io.WritableComparator;

public class SortTemperature extends WritableComparator{
//	@Override
//	@SuppressWarnings("rawtypes")
//	public int compare(WritableComparable a, WritableComparable b) {
//		// WritableComparable与需求不一致时需覆盖该方法
//		return super.compare(a, b);
//	}
	
	public SortTemperature(){
		super(KeyPair.class, true);
	}
}
