package dao;

import java.util.List;

import bean.Beans;

public interface OperateTree {
	
	public abstract int saveorupdate(Beans paramBeans);
	public abstract void delete(int id);
	public abstract List query();
}
