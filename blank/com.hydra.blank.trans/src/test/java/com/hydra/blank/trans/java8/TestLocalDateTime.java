package com.hydra.blank.trans.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;

import org.junit.Test;

public class TestLocalDateTime {
	@Test
	public void testLocalDateTime(){
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(ldt);
		System.out.println(Year.MAX_VALUE);
		long mv = 999_999_999l;
		System.out.println(mv);
	}
	
	@Test
	public void testLocalDate(){
		LocalDate ld = LocalDate.now();
		System.out.println(ld.minusMonths(1));
	}
	
	@Test
	public void testLocalTime(){
		LocalTime lt = LocalTime.now();
		System.out.println(lt);
	}
}
