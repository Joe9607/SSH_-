package com.dao.impl;

import java.sql.SQLException;
import java.util.List;




import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dao.YuyueDao;
import com.model.Yuyue;












public class YuyueDaoImpl extends HibernateDaoSupport implements  YuyueDao{


	public void deleteBean(Yuyue bean) {
		this.getHibernateTemplate().delete(bean);
		
	}

	public void insertBean(Yuyue bean) {
		this.getHibernateTemplate().save(bean);
		
	}

	@SuppressWarnings("unchecked")
	public Yuyue selectBean(String where) {
		List<Yuyue> list = this.getHibernateTemplate().find("from Yuyue " +where);
		if(list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public int selectBeanCount(String where) {
		long count = (Long)this.getHibernateTemplate().find("select count(*) from Yuyue "+where).get(0);
		return (int)count;
	}

	@SuppressWarnings("unchecked")
	public List<Yuyue> selectBeanList(final int start,final int limit,final String where) {
		return (List<Yuyue>)this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(final Session session)throws HibernateException, SQLException {				
				List<Yuyue> list = session.createQuery("from Yuyue "+where)
				.setFirstResult(start)
				.setMaxResults(limit)
				.list();
				return list;
			}
		});
	}

	public void updateBean(Yuyue bean) {
		this.getHibernateTemplate().update(bean);
		
	}
	
	
}
