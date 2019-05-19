package com.dao;

import java.util.List;

import com.model.Xinwen;






public interface XinwenDao  {
	
	
	
	public void insertBean(Xinwen bean);
	
	public void deleteBean(Xinwen bean);
	
	public void updateBean(Xinwen bean);

	public Xinwen selectBean(String where);
	
	public List<Xinwen> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
