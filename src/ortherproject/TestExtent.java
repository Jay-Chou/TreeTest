package ortherproject;

import impl.Test;

public class TestExtent extends Test {

//	public TestExtent(String name, int age) {
//		super(name, age);
//		// TODO Auto-generated constructor stub
//	}

	private Sort sort=null;
	private int num  = 20;
	
//	
//	public TestExtent(int num) {
//		// TODO Auto-generated constructor stub
//		System.out.println("..............");
//		this.num = num;
//	}
	
	public static TestExtent CreateNewObject() throws Exception{
		return (TestExtent) Test.CreateObject(TestExtent.class);
	}
	
	
	@SuppressWarnings("unused")
	public boolean compareTo(Object object) {
		// 判断传入的对象是否属于Test类或者子孙类
		if (object instanceof Test) {
			System.out.println("............");
			if (object == this) {
				return true;
			} else if (object == null) {
				return false;
			} else if (this.getName().equals(((Test) object).getName()) && this.getAge() == ((Test) object).getAge()) {
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println(".......xxxxxxxxxxxx");
			return false;
		}
	}
	
	public void setNum(int num){
		this.num = num;
	}
	
	public int getNum(){
		return this.num;
	}
	
	public void a(){
		int[] b = { 12, 32, 43, 53, 1, 4, 65, 23, 98, 76, 23 };
		sort.Selectionsort(b);
		System.out.println(sort.num);
	}
	
	public void print(){
		System.out.println(this.num);
	}
}
