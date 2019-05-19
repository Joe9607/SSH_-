package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.Record2Dao;
import com.model.Record2;












public class Record2DaoImpl extends HibernateDaoSupport implements  Record2Dao{


	public void deleteBean(Record2 bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Record2 bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Record2 selectBean(String where) {
		List<Record2> list = this.getHibernateTemplate().find("from Record2 " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Record2 "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Record2> selectBeanList(final int start,final int limit,final String where) {
		return (List<Record2>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Record2> list = session.createQuery("from Record2 "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Record2 bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
