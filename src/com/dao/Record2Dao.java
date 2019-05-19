package com.dao;

import java.util.List;

import com.model.Record2;






public interface Record2Dao  {
	
	
	
	public void insertBean(Record2 bean);
	
	public void deleteBean(Record2 bean);
	
	public void updateBean(Record2 bean);

	public Record2 selectBean(String where);
	
	public List<Record2> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
