package com.dao;

import java.util.List;

import com.model.Yuyue2;






public interface Yuyue2Dao  {
	
	
	
	public void insertBean(Yuyue2 bean);
	
	public void deleteBean(Yuyue2 bean);
	
	public void updateBean(Yuyue2 bean);

	public Yuyue2 selectBean(String where);
	
	public List<Yuyue2> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
