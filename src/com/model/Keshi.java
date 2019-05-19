package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//科室
@Entity
@Table(name="t_Keshi")
public class Keshi {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	
	
	private String ktitle;//科室名称
	
	@Column(name="jieshao", columnDefinition="TEXT")
	private String jieshao;//科室介绍
	
	private String ctime;//添加日期

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

	public String getKtitle() {
		return ktitle;
	}

	public void setKtitle(String ktitle) {
		this.ktitle = ktitle;
	}

	public String getJieshao() {
		return jieshao;
	}

	public void setJieshao(String jieshao) {
		this.jieshao = jieshao;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	
	
	
	
	
}
