package ortherproject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Scanner;

public class CopyFile {

	/**
	 * 文件复制
	 * 
	 */
	private static boolean copyFile(String oldfile, String newfile) {
		try {
			File oldFile = new File(oldfile);
			if (!oldFile.exists()) {
				System.out.println("不存在该文件");
				System.out.println("正在为你创建文件.......");
				oldFile.createNewFile();
				System.out.println("创建成功");
			}
			// 向源文件输入东西
			Scanner scanner = new Scanner(System.in);
			System.out.println("请输入内容:");
			System.out.println("----------------------------");
			String sInput = scanner.next();
			FileWriter fileWrite = new FileWriter(oldFile);
			// 写入
			fileWrite.write(sInput);
			fileWrite.flush();
			fileWrite.close();
			// 创建复制文件
			String fileName = oldFile.getName();
			File newdir = new File(newfile);
			File newFile = new File(newdir, fileName);
			if (!newFile.exists()) {
				newdir.mkdirs();
				newFile.createNewFile();
			}
			System.out.println("-------------------------");
			System.out.println("源文件路径:" + oldFile.getAbsolutePath());
			System.out.println("复制文件路径:" + newFile.getAbsolutePath());
			FileInputStream in = new FileInputStream(oldFile);
			FileOutputStream out = new FileOutputStream(newFile);
			byte[] Byte = new byte[255];
			int length = in.read(Byte);
			while (length != -1) {
				out.write(Byte, 0, length);
				length = in.read(Byte);
			}
			out.flush();
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 文件复制方法
		if (copyFile("D:/temp.txt", "E:/test")) {
			System.out.println("文件复制成功");
		} else {
			System.out.println("文件复制失败");
		}
	}
}
