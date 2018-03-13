package ortherproject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import net.sf.json.JSONObject;

public class UseTest {
	public static void main(String[] args) {
		// int num = 2147483647;
		// num += 2L;
		// System.out.println(num);
		// num = 68;
		// char c = (char) num;
		// System.out.println(c);

		try {

			// IHumen i = new Person();
			// Integer y = 10;
			// int x = 10;
			// System.out.println(y.intValue());
			// String a = "10";
			// System.out.println(Integer.valueOf(a) * 10);
			// Sort sort = Sort.CreatNewObject();
			// sort.main(null);
			//

			// Student pr = new Student("小王", 20);
			// Student stu =pr;
			// pr.print();
			String str = "             发布时间:2017020912312";
			int r = str.indexOf("发");
			// System.out.println(r);
			str = str.substring(r);
			str = str.substring(5, 13);
			System.out.println(str);
			// System.out.println("201701021213".substring(0, 8));

			ArrayList<String> al = new ArrayList();

			al.add("a");

			al.add("b");

			// al.add("b");

			// al.add("c");

			// al.add("d");

			for (int i = 0; i < al.size(); i++) {

				if (al.get(i) == "b") {

					al.remove(i);

					i--;

				}

			}

			for (String s : al) {
				System.out.println(s);
			}

			String a = "abv";
			String b = "abv";
			String c = new String("abv");
			System.out.println(a == c);

			double d = 0.57;
			BigDecimal bigDecimal1 = new BigDecimal(Double.toString(d));
			System.out.println(bigDecimal1.multiply(new BigDecimal(Double.toString(100))));

			String str1 = "";
			int a1 = 1;
			int b1 = 2;
			str1 += a1;
			str1 += b1;
			System.out.println(str1);

			// String Url = "/wcm/app/jj/index.jsp";
			//// int nIdx = Url.lastIndexOf("/");
			// String sFileName = Url.substring("/wcm/".length());
			// System.out.println(sFileName);
			// String sMediaId = "asd";
			// if(sMediaId!=null && !sMediaId.equals("")){
			// System.out.println("66666666666666666666");
			// }else{
			// System.out.println("44444444444444444444");
			// }

			int i = Integer.valueOf(111);

			System.out.println(i);

			// Test test3 = new TestExtent();
			// test3.
			// Test test1 = Test.CreateNewObject();
			// test1.setName("小于");
			// test1.setAge(20);
			// System.out.println(test1.getAge());
			// test3.setName("小于");
			// test3.setAge(20);
			// System.out.println(test3.compareTo(test1));
			// test3.a();
			// test3.num++;
			// TestExtent test2 = TestExtent.CreateNewObject();
			// System.out.println(test2.num);
			// System.out.println(test3.compareTo(test1));
			// test1.setName("小王");
			// test1.setAge(20);
			// TestExtent test2 = test3;
			// test2.setName("小李");
			// String a = "123123";
			// String b = "yooooo";
			// System.out.println(b==a);
			// String d = new String("123123").intern();
			// d.substring(1,2);
			// System.out.println(d.substring(1,2)=="1");
			// a.replaceAll("2", "3");
			// System.out.println(a.replace("2", "3"));
			// System.out.println(b.substring(0,1).toUpperCase()+b.substring(1));

			// MyThread task = new MyThread();
			// Thread t1 = new Thread(task,"第1人");
			// Thread t2 = new Thread(task,"第2人");
			// Thread t3 = new Thread(task,"第3人");
			// t1.setPriority(Thread.MIN_PRIORITY);
			// t1.start();
			// t2.start();
			// t3.start();
			
			int _inta = 10;
			String _strb="12";
			System.out.println(_inta+_strb);
			
			int in =1234;
			String st = "1234";
			String sting = new String("1234");
			
			
			int X=100;
			int y=0;
			int n=1;
			while(true){
				if(X>=(n*(n-1))*5&&X<=n*10+(n*(n-1))*5){
					y=n;
					break;
				}
				n++;
			}
			
			System.out.println("================"+y);
			
//			String sMediaId = null;
//			Object o = null;
//			if (sMediaId != null && !sMediaId.equals("")) {
//				System.out.println("6666666666666");
//			}
//			System.out.println("4444444444444444");

			/*
			 * 将时间戳转换为时间
			 */
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("create_time", 1513648002);
			String res;
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long lt = jsonObject.getLong("create_time");

			Date date = new Date(Long.valueOf(lt + "000"));
			res = simpleDateFormat.format(date);
			System.out.println(res);

			long startTime = 0l;
			long endTime = 0l;
			int dateType = 4;
			String asda = getTime(startTime, endTime, dateType);
			System.out.println(Long.valueOf(asda.split(",")[0]) == 0l);
			System.out.println(Long.valueOf(asda.split(",")[1]) == 0l);
			System.out.println(simpleDateFormat.format(new Date(Long.valueOf(asda.split(",")[0]))));
			System.out.println(simpleDateFormat.format(new Date(Long.valueOf(asda.split(",")[1]))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String getTime(long startTime, long endTime, int dateType) {
		String Time = "";
		// 获取当前时间的时间戳
		long currtTime = System.currentTimeMillis();
		long date = 1000 * 3600 * 24;
		long ZoneTime = TimeZone.getDefault().getRawOffset();
		switch (dateType) {
		// 如果不限时间
		case 0:
			startTime = 0l;
			endTime = 0l;
			Time = startTime + "," + endTime;
			break;
		// 今天
		case 1:
			// 获取当前时间的时间戳
			endTime = currtTime;
			// 获取今天凌晨12点的时间戳
			startTime = currtTime / (date) * (date) - ZoneTime;
			Time = startTime + "," + endTime;
			break;
		// 昨天
		case 2:
			// 获取当前时间的时间戳
			endTime = currtTime / (date) * (date) - ZoneTime - 1;
			startTime = (currtTime - date) / (date) * (date) - ZoneTime;
			Time = startTime + "," + endTime;
			break;
		// 前天
		case 3:
			endTime = (currtTime - date) / (date) * (date) - ZoneTime - 1;
			startTime = (currtTime - 2 * date) / (date) * (date) - ZoneTime;
			Time = startTime + "," + endTime;
			break;
		// 五天之内
		case 4:
			endTime = currtTime;
			startTime = (currtTime - 4 * date) / (date) * (date) - ZoneTime;
			Time = startTime + "," + endTime;
			break;
		default:
			startTime = 0l;
			endTime = 0l;
			Time = startTime + "," + endTime;
			break;
		}
		return Time;
	}
}
