package com.hydra.blank.trans.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Test;

import com.hydra.blank.trans.java8.Employee.Status;
import com.hydra.core.util.CommonUtil;

public class TestLambda {
	@Test
	public void testCountTimeEnter(){
		Supplier<Object> sr = () -> {
			testInteger();
			return null;
		};
		CommonUtil.printRuntime(sr);
	}
	
	@Test
	public void testReduce(){
		Optional<Employee> emp = emps.stream()
			.reduce((e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2);
		System.out.println(emp.get());
		Employee emp2 = emps.stream()
				.reduce(emps.get(1), (e1, e2) -> e1.getSalary() > e2.getSalary() ? e1 : e2);
		System.out.println(emp2);
	}
	
	@Test
	public void testInteger(){
		/*
		int sum = IntStream.rangeClosed(1, Integer.MAX_VALUE)
							.parallel()//305ms,346ms,303ms
							.sum();//766ms,770ms,766ms
		System.out.println(sum);//-1073741824
		*/
		OptionalDouble ol = IntStream.rangeClosed(1, Integer.MAX_VALUE)
					.parallel()//1259ms,1234ms,1393ms
					.average();//1980ms,1968ms,1966ms
		double res = ol.getAsDouble();
		System.out.println(res);//1.073741824E9
		System.out.println(String.format("%1.0f", res));//1073741824
	}
	
	@Test
	public void testFilter(){
		List<Employee> res = emps.stream()
			.filter((x) -> x.getSalary() >5000.0)
			.collect(Collectors.toList());
		System.out.println(res);
	}
	
	@Test
	public void testSum(){
		long start = System.currentTimeMillis();
		Long sum = LongStream.rangeClosed(1, 10000000000l)
								.parallel()
								.sum();
		System.out.println(sum);
		long end = System.currentTimeMillis();
		System.out.println("耗费的时间为: " + (end - start));
	}
	
	@Test
	public void testForEach(){
		emps.stream()
			.forEach(System.out::println);
	}
	
	List<Employee> emps = Arrays.asList(
		new Employee("张三", 18, 9999.99, Status.FREE),
		new Employee("李四", 59, 6666.66, Status.BUSY),
		new Employee("王五", 28, 3333.33, Status.VOCATION),
		new Employee("赵六", 8, 8888.88, Status.BUSY),
		new Employee("田七", 47, 7777.77, Status.FREE),
		new Employee("马八", 22, 4444.44, Status.FREE),
		new Employee("冯九", 38, 5555.55, Status.BUSY)
	);
}
