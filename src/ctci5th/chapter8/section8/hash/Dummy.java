package ctci5th.chapter8.section8.hash;

public class Dummy {
	private String name;
	private int age;
	public Dummy(String n, int a) {
		name = n;
		age = a;
	}
	
	@Override 
	public String toString() {
		return "(" + name + ", " + age + ")";
	}
	
	public int getAge() {
		return age;
	}
	
	public String getName() {
		return name;
	}
}
