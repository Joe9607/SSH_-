package com.dao;

import java.util.List;

import com.model.Yuyue;






public interface YuyueDao  {
	
	
	
	public void insertBean(Yuyue bean);
	
	public void deleteBean(Yuyue bean);
	
	public void updateBean(Yuyue bean);

	public Yuyue selectBean(String where);
	
	public List<Yuyue> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
