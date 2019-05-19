package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.Yuyue2Dao;
import com.model.Yuyue2;












public class Yuyue2DaoImpl extends HibernateDaoSupport implements  Yuyue2Dao{


	public void deleteBean(Yuyue2 bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Yuyue2 bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Yuyue2 selectBean(String where) {
		List<Yuyue2> list = this.getHibernateTemplate().find("from Yuyue2 " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Yuyue2 "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Yuyue2> selectBeanList(final int start,final int limit,final String where) {
		return (List<Yuyue2>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Yuyue2> list = session.createQuery("from Yuyue2 "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Yuyue2 bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
