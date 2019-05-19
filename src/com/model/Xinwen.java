package com.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//医院新闻
@Entity
@Table(name="t_Xinwen")
public class Xinwen {

	@Id
	@GeneratedValue
	private int id;
	
	private int deletestatus;//表示是否删除的状态，0表示未删除，1表示删除
	
	private String xtitle;//新闻标题
	
	@Column(name="xcontent", columnDefinition="TEXT")
	private String xcontent;//新闻内容
	
	private String ctime;//添加日期
	
	private int vist;//点击数

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

	public String getXtitle() {
		return xtitle;
	}

	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	public String getXcontent() {
		return xcontent;
	}

	public void setXcontent(String xcontent) {
		this.xcontent = xcontent;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public int getVist() {
		return vist;
	}

	public void setVist(int vist) {
		this.vist = vist;
	}
	
	
	
	
	
	
	
}
