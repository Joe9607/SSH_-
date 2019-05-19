package com.dao;

import java.util.List;

import com.model.Doctor;






public interface DoctorDao  {
	
	
	
	public void insertBean(Doctor bean);
	
	public void deleteBean(Doctor bean);
	
	public void updateBean(Doctor bean);

	public Doctor selectBean(String where);
	
	public List<Doctor> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
	
}
