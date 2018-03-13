package ortherproject;

public class Sort {
	
	public int num=0;

	public Sort(){
		
	}
	
	public static Sort CreatNewObject() throws Exception{
		try {
			return (Sort)Sort.class.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}
	/**
	 * 
	 * 插入排序
	 */

	private static void Insertsort(int[] a) {
		System.out.println("排序前");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println();
		int temp = 0;
		for (int i = 1; i < a.length; i++) {
			int j = i - 1;
			temp = a[i];
			for (; j >= 0 && temp < a[j]; j--) {
				a[j + 1] = a[j];// 将大于temp的值整体后移一个单位
			}
			a[j + 1] = temp;
		}
		//
		System.out.println("排序后");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}

	/**
	 * 
	 * 冒泡排序
	 */

	private static void BubbleSort(int[] a) {
		System.out.println("排序前");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println();
		int temp = 0;
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (a[j] > a[j + 1]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
		System.out.println("排序后");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}

	/**
	 * 
	 * 选择排序
	 */

	public  void Selectionsort(int[] a) {
		System.out.println("排序前");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println();
		int currPosition = 0;
		for (int i = 0; i < a.length; i++) {
			currPosition = i;
			int temp = a[i];
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < temp) {
					temp = a[j];
					currPosition = j;
				}
			}
			a[currPosition] = a[i];
			a[i] = temp;
		}
		System.out.println("排序后");
		for (int i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}

	public static void main(String[] args) {
		// 排序
		// 冒泡排序
		// 传入数组
		int[] a = { 12, 32, 43, 53, 1, 4, 65, 23, 98, 76, 23 };
		System.out.println("冒泡排序:");
		BubbleSort(a);
		// 选择排序
		int[] b = { 12, 32, 43, 53, 1, 4, 65, 23, 98, 76, 23 };
		System.out.println();
		System.out.println("选择排序:");
//		Selectionsort(b);
		// 插入排序
		int[] c = { 12, 32, 43, 53, 1, 4, 65, 23, 98, 76, 23 };
		System.out.println();
		System.out.println("插入排序:");
		Insertsort(c);

	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
