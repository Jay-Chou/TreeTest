package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import bdconnection.DBSpaceConnection;
import bean.Beans;
import dao.OperateTree;

public class OperateTreeImpl implements OperateTree {

	/**
	 * 添加或修改
	 * 
	 * @param paramBeans
	 * @return i;
	 */

	@Override
	public int saveorupdate(Beans paramBeans) {
		// TODO Auto-generated method stub
		DBSpaceConnection dbc = new DBSpaceConnection();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = dbc.getConnection();
		Format format = null;
		String selectsql = "select * from test1 where id = ?";
		String insertsql = "insert into test1(p_name,level_path,pid,p_order) values(?,?,?,?)";
		String updatesql = "update test1 set p_name = ? ,level_path = ?,pid = ?,p_order = ? where id = ?";
		try {
			ps = conn.prepareStatement(selectsql);
			//查新Id是否存在
			ps.setInt(1, paramBeans.getId());
			rs = ps.executeQuery();
			// 修改
			if (rs.next()) {
				ps = conn.prepareStatement(selectsql);
				// 首先查询插入数据是否存在父Id
				ps.setInt(1, paramBeans.getPid());
				ResultSet rs1 = ps.executeQuery();
				// 如果查询存在则说明有父Id 就需要找到父Id所在下的所有栏目
				// 再根据最近的栏目插入数据比如：若查询出来level_path为001001,001002
				// 那么插入时插入的数据level_path为001003;
				if (rs1.next()) {
					String selectPidSql = "select * from test1 where pid = ?";
					ps = conn.prepareStatement(selectPidSql);
					ps.setInt(1, paramBeans.getPid());
					ResultSet rs2 = ps.executeQuery();
					String level = null;
					// 给newNum赋初值为1；
					int newNum = 1;
					format = new DecimalFormat("000");
					while (rs2.next()) {
						level = rs2.getString("level_path");
						newNum += 1;
					}
					// 如果level和初值一样说明父Id下没有其他分类，那么要将父ID的level_path放置在前并在后面添加001
					// 组成为新的字符串
					if (level == null) {
						String newStr = format.format(1);
						level = rs1.getString("level_path") + newStr;
					}
					// 否则后三位+1
					else {
						// 截取前几位
						String newupStr = level.substring(0, level.length() - 3);
						// 截取最后三位并转化为数字加1在转化为字符串
						String newlastStr = format
								.format(Integer.valueOf(level.substring(level.length() - 3, level.length())) + 1);
						// 组合为新的level
						level = newupStr + newlastStr;
					}
					ps = conn.prepareStatement(updatesql);
					ps.setString(1, paramBeans.getName());
					ps.setString(2, level);
					ps.setInt(3, paramBeans.getPid());
					ps.setInt(4, newNum);
					ps.setInt(5, paramBeans.getId());
					int i = ps.executeUpdate();
					System.out.println(i);
					//同时原有的order顺序也将变换重排
					String updateorsersql = "update  test1 ,(SELECT @row_number:=0) AS t set p_order = (@row_number:=@row_number + 1) where pid = ?";
					ps = conn.prepareStatement(updateorsersql);
					ps.setInt(1, rs.getInt("pid"));
					int b = ps.executeUpdate();
					System.out.println(b);
					//将要移动的或者修改的分类修改之后将其子分类修改
					String selectPidToSql = "select * from test1 where level_path like '"+rs.getString("level_path")+"%'";
					ps = conn.prepareStatement(selectPidToSql);
					rs2 = ps.executeQuery();
					while(rs2.next()){
						String childlevel = rs2.getString("level_path");
						//新的level为截取父ID以前的长度到整个长度，在将这个字符串和新的父ID长度相加就得到现在的Childlevel
						childlevel = level + childlevel.substring(rs.getString("level_path").length(),childlevel.length());
						String updatePidToSql = "update test1 set level_path = '"+childlevel+"' where id = "+rs2.getInt("id")+"";
						Statement s = conn.createStatement();
						int j = s.executeUpdate(updatePidToSql);
						System.out.println(j);
					}
					try {
						rs2.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				// 若没有则无父Id pid为0，就需要查找父ID为0的顶级栏目
				// 并将数据插入最近的一个 比如：若查询出来level_path为001,002
				// 那么插入时插入的数据level_path为003;
				else {
					String selectPidSql = "select * from test1 where pid = 0";
					ps = conn.prepareStatement(selectPidSql);
					rs1 = ps.executeQuery();
					String level = null;
					int newNum = 1;
					while (rs1.next()) {
						level = rs1.getString("level_path");
						newNum += 1;
					}
					if (level == null) {
						level = "001";
					} else {
						// 截取前几位
						String newupStr = level.substring(0, level.length() - 3);
						// 截取最后三位并转化为数字加1在转化为字符串
						format = new DecimalFormat("000");
						String newlastStr = format
								.format(Integer.valueOf(level.substring(level.length() - 3, level.length())) + 1);
						// 组合为新的level
						level = newupStr + newlastStr;
					}
					// 插入数据
					ps = conn.prepareStatement(updatesql);
					ps.setString(1, paramBeans.getName());
					ps.setString(2, level);
					ps.setInt(3, 0);
					ps.setInt(4, newNum);
					ps.setInt(5, paramBeans.getId());
					int i = ps.executeUpdate();
					System.out.println(i);
					//同时原有的order顺序也将变换重排
					String updateorsersql = "update  test1 ,(SELECT @row_number:=0) AS t set p_order = (@row_number:=@row_number + 1) where pid = ?";
					ps = conn.prepareStatement(updateorsersql);
					ps.setInt(1, rs.getInt("pid"));
					int b = ps.executeUpdate();
					System.out.println(b);
					//将要移动的或者修改的分类修改之后将其子分类修改
					String selectPidToSql = "select * from test1 where level_path like '"+rs.getString("level_path")+"%'";
					ps = conn.prepareStatement(selectPidToSql);
					ResultSet rs2 = ps.executeQuery();
					while(rs2.next()){
						String childlevel = rs2.getString("level_path");
						//新的level为截取父ID以前的长度到整个长度，在将这个字符串和新的父ID长度相加就得到现在的Childlevel
						childlevel = level + childlevel.substring(rs.getString("level_path").length(),childlevel.length());
						String updatePidToSql = "update test1 set level_path = '"+childlevel+"' where id = "+rs2.getInt("id")+"";
						Statement s = conn.createStatement();
						int j = s.executeUpdate(updatePidToSql);
						System.out.println(j);
					}
					try {
						rs2.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				try {
					rs1.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			// 新增
			else {
				ps = conn.prepareStatement(selectsql);
				ps.setInt(1, paramBeans.getPid());
				ResultSet rs1 = ps.executeQuery();
				// 如果查询存在则说明有父Id 就需要找到父Id所在下的所有栏目
				// 再根据最近的栏目插入数据比如：若查询出来level_path为001001,001002
				// 那么插入时插入的数据level_path为001003;
				if (rs1.next()) {
					String selectPidSql = "select * from test1 where pid = ?";
					ps = conn.prepareStatement(selectPidSql);
					ps.setInt(1, paramBeans.getPid());
					ResultSet rs2 = ps.executeQuery();
					String level = null;
					// 给newNum赋初值为1；
					int newNum = 1;
					format = new DecimalFormat("000");
					while (rs2.next()) {
						level = rs2.getString("level_path");
						newNum += 1;
					}
					// 如果level和初值一样说明父Id下没有其他分类，那么要将父ID的level_path放置在前并在后面添加001
					// 组成为新的字符串
					if (level == null) {
						String newStr = format.format(1);
						level = rs1.getString("level_path") + newStr;
					}
					// 否则后三位+1
					else {
						// 截取前几位
						String newupStr = level.substring(0, level.length() - 3);
						// 截取最后三位并转化为数字加1在转化为字符串
						String newlastStr = format
								.format(Integer.valueOf(level.substring(level.length() - 3, level.length())) + 1);
						// 组合为新的level
						level = newupStr + newlastStr;
					}
					ps = conn.prepareStatement(insertsql);
					ps.setString(1, paramBeans.getName());
					ps.setString(2, level);
					ps.setInt(3, paramBeans.getPid());
					ps.setInt(4, newNum);
					int i = ps.executeUpdate();
					System.out.println(i);
					try {
						rs2.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				// 若没有则无父Id pid为0，就需要查找父ID为0的顶级栏目
				// 并将数据插入最近的一个 比如：若查询出来level_path为001,002
				// 那么插入时插入的数据level_path为003;
				else {
					String selectPidSql = "select * from test1 where pid = 0";
					ps = conn.prepareStatement(selectPidSql);
					rs = ps.executeQuery();
					String level = null;
					int newNum = 1;
					while (rs.next()) {
						level = rs.getString("level_path");
						newNum += 1;
					}
					if (level == null) {
						level = "001";
					} else {
						// 截取前几位
						String newupStr = level.substring(0, level.length() - 3);
						// 截取最后三位并转化为数字加1在转化为字符串
						format = new DecimalFormat("000");
						String newlastStr = format
								.format(Integer.valueOf(level.substring(level.length() - 3, level.length())) + 1);
						// 组合为新的level
						level = newupStr + newlastStr;
					}
					// 插入数据
					ps = conn.prepareStatement(insertsql);
					ps.setString(1, paramBeans.getName());
					ps.setString(2, level);
					ps.setInt(3, 0);
					ps.setInt(4, newNum);
					int i = ps.executeUpdate();
					System.out.println(i);
				}
				try {
					rs1.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} catch (

		Exception e)

		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally

		{
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (Exception exception) {
				// TODO: handle exception
				exception.printStackTrace();
			}
		}

		return 0;

	}

	/**
	 * 删除
	 * 
	 * @param id
	 * 
	 */
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		DBSpaceConnection dbc = new DBSpaceConnection();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = dbc.getConnection();
		String selectsql = "select * from test1 where id = ?";
		try {
			ps = conn.prepareStatement(selectsql);
			// 首先查询插入数据是否存在父Id
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				String level = rs.getString("level_path");
				String deletesql = "delete from test1 where level_path like '" + level + "%'";
				ps = conn.prepareStatement(deletesql);
				int a = ps.executeUpdate();
				System.out.println(a);
				// 删除之后需要重新排序
				String updatesql = "update  test1 ,(SELECT @row_number:=0) AS t set p_order = (@row_number:=@row_number + 1) where pid = ?";
				ps = conn.prepareStatement(updatesql);
				ps.setInt(1, rs.getInt("pid"));
				int b = ps.executeUpdate();
				System.out.println(b);
			} else {
				throw new Exception("没有该Id的分类");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询
	 * 
	 * @param paramBeans
	 * @return i;
	 */
	@Override
	public List query() {
		// TODO Auto-generated method stub
		Beans b = new Beans();
		ArrayList<Beans> aList = new ArrayList<Beans>();
		DBSpaceConnection dbc = new DBSpaceConnection();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		conn = dbc.getConnection();
		String sql = "select * from test1 order by level_path";
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				b.setId(rs.getInt(1));
				b.setName(rs.getString(2));
				b.setLevel_path(rs.getString(3));
				b.setPid(rs.getInt(4));
				b.setOrder(rs.getInt(5));
				aList.add(b);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return aList;
	}

	public static void main(String[] args) {
		// String a = "001001";
		// String c = a.substring(a.length() - 3, a.length());
		// String d = a.substring(0, a.length() - 3);
		// System.out.println(c);
		// System.out.println(d);
		// int b = Integer.valueOf(c);
		// System.out.println(b);
		// Format f1 = new DecimalFormat("000");
		// System.out.println(f1.format(b));
//		Beans b = new Beans();
//		b.setId(8);
//		b.setName("啤酒");
//		b.setPid(16);
//		System.out.println(new OperateTreeImpl().saveorupdate(b));
		String reg = ".*\\/\\/([^\\/]*).*";
		String rpg = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
		String str1 = "http://192.168.9.0:9080/caservice/webservice/caServiceWs?wsdl";
		String str2 = str1.replaceAll (reg, "$1");
		String str3 = str2.substring(0,str2.indexOf(":"));
		System.out.println(str3);
		if(str3.matches(rpg)){
			System.out.println(str3);
			System.out.println("yes");
		}
		System.out.println ("no");
		
		// new OperateTreeImpl().delete(5);
	}
}
