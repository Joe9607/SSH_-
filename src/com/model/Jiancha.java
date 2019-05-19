package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//检查项目
@Entity
@Table(name="t_Jiancha")
public class Jiancha {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String jname;//检查名称
	
	private String money;//检查费用

	@Column(name="zhuyi", columnDefinition="TEXT")
	private String zhuyi;//注意事项

	@Column(name="shuoming", columnDefinition="TEXT")
	private String shuoming;//检查说明
	
	private int renshu;//每天最多检查人数
	
	private String week;//检查开放时间

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

	public String getJname() {
		return jname;
	}

	public void setJname(String jname) {
		this.jname = jname;
	}

	public String getZhuyi() {
		return zhuyi;
	}

	public void setZhuyi(String zhuyi) {
		this.zhuyi = zhuyi;
	}

	public String getShuoming() {
		return shuoming;
	}

	public void setShuoming(String shuoming) {
		this.shuoming = shuoming;
	}

	public int getRenshu() {
		return renshu;
	}

	public void setRenshu(int renshu) {
		this.renshu = renshu;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	
	
	
	
	
}
