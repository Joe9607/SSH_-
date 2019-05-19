package com.dao;

import java.util.List;

import com.model.Liuyan;






public interface LiuyanDao  {
	
	
	
	public void insertBean(Liuyan bean);
	
	public void deleteBean(Liuyan bean);
	
	public void updateBean(Liuyan bean);

	public Liuyan selectBean(String where);
	
	public List<Liuyan> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
