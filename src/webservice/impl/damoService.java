package webservice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import bdconnection.DBSpaceConnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import ortherproject.Person;
import webservice.Idamo;

@WebService(serviceName = "damoService", endpointInterface = "webservice.Idamo")
public class damoService implements Idamo {

	private DBSpaceConnection dbc = new DBSpaceConnection();
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public String queryAllPerson() {
		// TODO Auto-generated method stub
		Person person = null;
		ArrayList<Person> PersonList = new ArrayList<Person>();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		dbc = new DBSpaceConnection();
		conn = dbc.getConnection();
		// 查询所有用户
		String sql = "select * from user";
		// 调用链接查询数据
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			// 判断结果集是否为空，不为空则先将第一个数据加入，再循环加入到list中
			if (rs.next()) {
				person.setName(rs.getString(2));
				person.setAge(rs.getInt(3));
				PersonList.add(person);
				while (rs.next()) {
					person.setName(rs.getString(2));
					person.setAge(rs.getInt(3));
					PersonList.add(person);
				}
				jsonArray = JSONArray.fromObject(PersonList);
				// 编写返回信息
				jsonObject.put("success", true);
				jsonObject.put("data", jsonArray);
				return jsonObject.toString();
			} else {
				jsonObject.put("success", true);
				jsonObject.put("data", "没有用户数据");
				return jsonObject.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			jsonObject.put("success", false);
			jsonObject.put("data", e.getMessage());
			return jsonObject.toString();
		}
		// 关闭连接
		finally {
			try {
				if (conn != null) {
					conn.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (rs != null) {
					conn.close();
				}
			} catch (Exception e) {
				jsonObject.put("success", false);
				jsonObject.put("data", e.getMessage());
				return jsonObject.toString();
			}
		}
	}

	public String findById() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {  
        String address="http://127.0.0.1:9090/Service/damoService";  
        Endpoint.publish(address, new damoService());  
        System.out.println(address+"已经发布");  
    } 

}
