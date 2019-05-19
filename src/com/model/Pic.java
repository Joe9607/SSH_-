package com.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//网站动态图片
@Entity
@Table(name="t_Pic") //对应表名
public class Pic {

	@Id      //指定表的主键
	@GeneratedValue
	private int id;
	
	private String path;//图片路径
	
	private String info;//说明
	
	private String href;//图片链接

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	

	
	
	
	
	
}
