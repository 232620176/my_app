package com.hydra.blank.trans.java8;

import java.util.concurrent.atomic.AtomicInteger;

public class Employee {
	private static final AtomicInteger idCreator = new AtomicInteger(0);
	private int id;
	private String name;
	private int age;
	private double salary;
	private Status status;
	
	public Employee() {
		initID();
	}
	
	public Employee(String name) {
		initID();
		this.name = name;
	}
	
	public Employee(String name, int age) {
		initID();
		this.name = name;
		this.age = age;
	}
	
	public Employee(String name, int age, double salary) {
		initID();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	
	public Employee(String name, int age, double salary, Status status) {
		initID();
		this.name = name;
		this.age = age;
		this.salary = salary;
		this.status = status;
	}
	
	private void initID(){
		this.id = idCreator.incrementAndGet();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (age != other.age)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(salary) != Double
				.doubleToLongBits(other.salary))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age
				+ ", salary=" + salary + ", status=" + status + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public enum Status{
		FREE, BUSY, VOCATION
	}
}
