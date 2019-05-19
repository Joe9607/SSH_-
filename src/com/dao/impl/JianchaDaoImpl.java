package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.JianchaDao;
import com.model.Jiancha;












public class JianchaDaoImpl extends HibernateDaoSupport implements  JianchaDao{


	public void deleteBean(Jiancha bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Jiancha bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Jiancha selectBean(String where) {
		List<Jiancha> list = this.getHibernateTemplate().find("from Jiancha " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Jiancha "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Jiancha> selectBeanList(final int start,final int limit,final String where) {
		return (List<Jiancha>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Jiancha> list = session.createQuery("from Jiancha "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Jiancha bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
