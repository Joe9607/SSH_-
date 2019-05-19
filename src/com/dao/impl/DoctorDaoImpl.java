package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.DoctorDao;
import com.model.Doctor;












public class DoctorDaoImpl extends HibernateDaoSupport implements  DoctorDao{


	public void deleteBean(Doctor bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Doctor bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Doctor selectBean(String where) {
		List<Doctor> list = this.getHibernateTemplate().find("from Doctor " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Doctor "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Doctor> selectBeanList(final int start,final int limit,final String where) {
		return (List<Doctor>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Doctor> list = session.createQuery("from Doctor "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Doctor bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
