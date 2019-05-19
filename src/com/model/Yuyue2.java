package com.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//预约检查
@Entity
@Table(name="t_Yuyue2")
public class Yuyue2 {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	
	@ManyToOne
	@JoinColumn(name="jianchaid")
	private Jiancha jiancha;//关联的检查，外键
	
	private int riqi;//日期
	
	private String week;//星期
	
	private int shiji;//当天已预约人数
	
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

	

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public int getShiji() {
		return shiji;
	}

	public void setShiji(int shiji) {
		this.shiji = shiji;
	}

	public int getRiqi() {
		return riqi;
	}

	public void setRiqi(int riqi) {
		this.riqi = riqi;
	}

	public Jiancha getJiancha() {
		return jiancha;
	}

	public void setJiancha(Jiancha jiancha) {
		this.jiancha = jiancha;
	}

	
	
	
	
	
	
}
