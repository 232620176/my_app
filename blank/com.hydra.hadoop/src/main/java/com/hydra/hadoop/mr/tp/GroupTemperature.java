package com.hydra.hadoop.mr.tp;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupTemperature extends WritableComparator{
	public GroupTemperature() {
		super(KeyPair.class, true);
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public int compare(WritableComparable a, WritableComparable b) {
		KeyPair key1 = (KeyPair)a;
		KeyPair key2 = (KeyPair)b;
		return Integer.compare(key1.getYear(), key2.getYear());
	}
}
