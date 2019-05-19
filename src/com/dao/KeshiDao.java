package com.dao;

import java.util.List;

import com.model.Keshi;






public interface KeshiDao  {
	
	
	
	public void insertBean(Keshi bean);
	
	public void deleteBean(Keshi bean);
	
	public void updateBean(Keshi bean);

	public Keshi selectBean(String where);
	
	public List<Keshi> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
