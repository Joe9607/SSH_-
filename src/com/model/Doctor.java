package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//医生（单表映射实体bean）
@Entity
@Table(name="t_Doctor")
public class Doctor {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String bianhao;//医生编号
	
	@ManyToOne
	@JoinColumn(name="keshiid")
	private Keshi keshi;//关联的科室，外键
	
	private String name;//医生姓名
	
	private String zhiwu;//职务
	
	private String photo;//照片
	
	
	@Column(name="shanchang", columnDefinition="TEXT")
	private String shanchang;//学术擅长

	@Column(name="jianjie", columnDefinition="TEXT")
	private String jianjie;//医生简介
	
	private int renshu;//每天最多预约人数
	
	private String week;//门诊时间
	
	
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

	public Keshi getKeshi() {
		return keshi;
	}

	public void setKeshi(Keshi keshi) {
		this.keshi = keshi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZhiwu() {
		return zhiwu;
	}

	public void setZhiwu(String zhiwu) {
		this.zhiwu = zhiwu;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getShanchang() {
		return shanchang;
	}

	public void setShanchang(String shanchang) {
		this.shanchang = shanchang;
	}

	public String getJianjie() {
		return jianjie;
	}

	public void setJianjie(String jianjie) {
		this.jianjie = jianjie;
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

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	
	
	
	
}
