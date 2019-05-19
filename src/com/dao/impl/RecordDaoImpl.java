package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.RecordDao;
import com.model.Record;












public class RecordDaoImpl extends HibernateDaoSupport implements  RecordDao{


	public void deleteBean(Record bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Record bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Record selectBean(String where) {
		List<Record> list = this.getHibernateTemplate().find("from Record " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Record "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Record> selectBeanList(final int start,final int limit,final String where) {
		return (List<Record>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Record> list = session.createQuery("from Record "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Record bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
