package com.dao;

import java.util.List;

import com.model.Jiancha;






public interface JianchaDao  {
	
	
	
	public void insertBean(Jiancha bean);
	
	public void deleteBean(Jiancha bean);
	
	public void updateBean(Jiancha bean);

	public Jiancha selectBean(String where);
	
	public List<Jiancha> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
