package com.dao;

import java.util.List;

import com.model.Record;






public interface RecordDao  {
	
	
	
	public void insertBean(Record bean);
	
	public void deleteBean(Record bean);
	
	public void updateBean(Record bean);

	public Record selectBean(String where);
	
	public List<Record> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
