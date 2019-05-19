package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//留言
@Entity
@Table(name="t_Liuyan")
public class Liuyan {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String ltitle;//留言标题
	
	@Column(name="lcontent", columnDefinition="TEXT")
	private String lcontent;//留言内容
	
	private String ctime;//留言日期
	
	private String status;//回复状态
	
	@Column(name="hcontent", columnDefinition="TEXT")
	private String hcontent;//回复内容
	
	private String htime;//回复日期

	@ManyToOne
	@JoinColumn(name="userid")
	private User user;//留言用户
	
	@ManyToOne
	@JoinColumn(name="doctorid")
	private Doctor doctor;//回答的医生，外键

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

	public String getLtitle() {
		return ltitle;
	}

	public void setLtitle(String ltitle) {
		this.ltitle = ltitle;
	}

	public String getLcontent() {
		return lcontent;
	}

	public void setLcontent(String lcontent) {
		this.lcontent = lcontent;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHcontent() {
		return hcontent;
	}

	public void setHcontent(String hcontent) {
		this.hcontent = hcontent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getHtime() {
		return htime;
	}

	public void setHtime(String htime) {
		this.htime = htime;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	
	
	
	
	
	
}
