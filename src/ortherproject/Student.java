package ortherproject;

public class Student extends Person {

	public Student(String name, int age) {
		super();
		super.setAge(age);
		super.setName(name);
	}

	public String toString() {
		return "Student = [ name = " + this.getName() + ", age = " + this.getAge() + " ]";
	}
}
