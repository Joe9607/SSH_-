package com.model;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//检查预约记录
@Entity
@Table(name="t_Record2")
public class Record2 {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	
	@ManyToOne
	@JoinColumn(name="yuyue2id")
	private Yuyue2 yuyue2;//关联的预约检查，外键
	
	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//预约的用户，外键
	
	private String status;//预约成功  取消预约
	
	private String ytime;//预约时间
	
	private int riqi;//日期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeletestatus() {
		return deletestatus;
	}

	public void setDeletestatus(int deletestatus) {
		this.deletestatus = deletestatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getYtime() {
		return ytime;
	}

	public void setYtime(String ytime) {
		this.ytime = ytime;
	}

	public int getRiqi() {
		return riqi;
	}

	public void setRiqi(int riqi) {
		this.riqi = riqi;
	}

	public Yuyue2 getYuyue2() {
		return yuyue2;
	}

	public void setYuyue2(Yuyue2 yuyue2) {
		this.yuyue2 = yuyue2;
	}

	
	

	
	
	
}
