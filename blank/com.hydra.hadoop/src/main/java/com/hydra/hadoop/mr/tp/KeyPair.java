package com.hydra.hadoop.mr.tp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class KeyPair implements WritableComparable<KeyPair>{
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.year);
		out.writeInt(this.temperature);
	}
	
	@Override
	public void readFields(DataInput in) throws IOException {
		this.year = in.readInt();
		this.temperature = in.readInt();
	}
	
	@Override
	public int compareTo(KeyPair o) {
		//年份升序
		int res = Integer.compare(this.year, o.year);
		if(0 == res){
			//温度降序
			return -Integer.compare(this.temperature, o.temperature);
		}else{
			return res;
		}
	}
	
	@Override
	public String toString() {
		return year + "\t" + temperature;
	}
	
	@Override
	public int hashCode() {
		return Integer.valueOf(year).hashCode()
				+ Integer.valueOf(temperature).hashCode();
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getTemperature() {
		return temperature;
	}
	
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	private int year;
	private int temperature;
}
