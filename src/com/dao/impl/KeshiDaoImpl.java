package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.KeshiDao;
import com.model.Keshi;












public class KeshiDaoImpl extends HibernateDaoSupport implements  KeshiDao{


	public void deleteBean(Keshi bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Keshi bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Keshi selectBean(String where) {
		List<Keshi> list = this.getHibernateTemplate().find("from Keshi " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Keshi "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Keshi> selectBeanList(final int start,final int limit,final String where) {
		return (List<Keshi>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Keshi> list = session.createQuery("from Keshi "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Keshi bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
