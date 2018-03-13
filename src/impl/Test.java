package impl;

import ortherproject.TestExtent;

public class Test {

	private String name;
	private int age;
	
	public Test(){
	}
	
	public Test(String name,int age){
		this.name = name;
		this.age =age;
	}

//	public Test(String name, int age) {
//		this.name = name;
//		this.age = age;
//	}

	public static Test CreateNewObject() throws Exception{
		return Test.CreateObject(Test.class);
	}
	
	
	public static Test CreateObject(Class _class) throws Exception{
		try {
			return (Test)_class.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e); 
		} 
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return this.name;
	}

	public int getAge() {
		return this.age;
	}

	@SuppressWarnings("unused")
	public boolean compareTo(Object object) {
		// 判断传入的对象是否属于Test类或者子孙类
		if (object instanceof TestExtent) {
			if (object == this) {
				return true;
			} else if (object == null) {
				return false;
			} else if (this.name.equals(((Test) object).name) && this.age == ((Test) object).age) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void print(){};
}
