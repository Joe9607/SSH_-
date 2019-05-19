package com.action;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dao.DoctorDao;
import com.dao.GonggaoDao;
import com.dao.JianchaDao;
import com.dao.KeshiDao;
import com.dao.LiuyanDao;
import com.dao.PicDao;
import com.dao.Record2Dao;
import com.dao.RecordDao;
import com.dao.UserDao;
import com.dao.XinwenDao;
import com.dao.YuyueDao;
import com.model.Doctor;
import com.model.Gonggao;
import com.model.Jiancha;
import com.model.Keshi;
import com.model.Liuyan;
import com.model.Pic;
import com.model.Record;
import com.model.User;
import com.model.Xinwen;
import com.model.Yuyue;
import com.opensymphony.xwork2.ActionSupport;
import com.util.Pager;
import com.util.Util;


public class ManageAction extends ActionSupport {

	private static final long serialVersionUID = -4304509122548259589L;


	private String url = "./";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	//获取请求对象
	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	
	//获取响应对象
	public HttpServletResponse getResponse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		return response;
	}
	
	//获取session对象
	public HttpSession getSession(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		return session;
	}
	
	
	//获取输出对象
	public PrintWriter getPrintWriter() {
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return writer;
	}
	

	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
//登入请求
	public String login() throws IOException {
		HttpServletRequest request = this.getRequest();
		
		PrintWriter writer = this.getPrintWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		String role = request.getParameter("role");
		
		User user = userDao.selectBean(" where username = '" + username
				+ "' and password= '" + password + "' and role="+role+" and deletestatus=0 ");
		if (user != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			this.setUrl("manage/index.jsp");
			return "redirect";
		} else {
			writer.print("<script language=javascript>alert('用户名或者密码错误');window.location.href='manage/login.jsp';</script>");
		}
		return null;
	}
	
//用户退出
	public String loginout() {
		HttpServletRequest request = this.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		this.setUrl("manage/login.jsp");
		return SUCCESS;
	}
	
//跳转到修改密码页面
	public String changepwd() {
		this.setUrl("manage/password.jsp");
		return SUCCESS;
	}
	
//修改密码操作
	public void changepwd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		User bean = userDao.selectBean(" where username= '"+u.getUsername()+"' and password= '"+password1+"' and deletestatus=0");
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			writer.print(
							"<script language=javascript>alert('修改成功');window.location.href='method!changepwd';</script>");
		}else{
			writer.print(
							"<script language=javascript>alert('原密码错误');window.location.href='method!changepwd';</script>");
		}
	}
	
	
	
	private XinwenDao xinwenDao;

	public XinwenDao getXinwenDao() {
		return xinwenDao;
	}

	public void setXinwenDao(XinwenDao xinwenDao) {
		this.xinwenDao = xinwenDao;
	}
	
	
	//医院新闻列表
	public String xinwenlist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String xtitle = request.getParameter("xtitle");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (xtitle != null && !"".equals(xtitle)) {

			sb.append("xtitle like '%" + xtitle + "%'");
			sb.append(" and ");
			request.setAttribute("xtitle", xtitle);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = xinwenDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", xinwenDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!xinwenlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!xinwenlist?menu="+menu);
		request.setAttribute("url2", "method!xinwen");
		request.setAttribute("title", "医院新闻管理");
		this.setUrl("manage/xinwen/xinwenlist.jsp");
		return SUCCESS;

	}
	
//跳转到添加医院新闻页面
	public String xinwenadd() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		request.setAttribute("url", "method!xinwenadd2?menu="+menu);
		request.setAttribute("title", "添加医院新闻");
		this.setUrl("manage/xinwen/xinwenadd.jsp");
		return SUCCESS;
	}
	

	
	

	//添加医院新闻操作
	public void xinwenadd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		
		String xcontent = request.getParameter("xcontent");
		String xtitle = request.getParameter("xtitle");
				
		Xinwen bean =  new Xinwen();
		
		
		bean.setCtime(Util.getRiqi());
		bean.setXcontent(xcontent);
		bean.setXtitle(xtitle);
		
		
		
		
		xinwenDao.insertBean(bean);
		
		String menu = request.getParameter("menu");
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist?menu="+menu+"';</script>");
		
		
		
		
		
	}
	
//跳转到更新医院新闻页面
	public String xinwenupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Xinwen bean = xinwenDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!xinwenupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "医院新闻修改");
		this.setUrl("manage/xinwen/xinwenupdate.jsp");
		return SUCCESS;
	}
	
//更新医院新闻操作
	public void xinwenupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String xcontent = request.getParameter("xcontent");
		String xtitle = request.getParameter("xtitle");
		
		Xinwen bean = xinwenDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		
		bean.setXcontent(xcontent);
		bean.setXtitle(xtitle);

		
		xinwenDao.updateBean(bean);
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist?menu="+menu+"';</script>");
		
	}
	
	
//删除医院新闻操作
	public void xinwendelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Xinwen bean = xinwenDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		xinwenDao.updateBean(bean);
	
		
	
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!xinwenlist?menu="+menu+"';</script>");
	}
	
	//跳转到查看医院新闻页面
	public String xinwenupdate3() {
		HttpServletRequest request = this.getRequest();
		Xinwen bean = xinwenDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "医院新闻查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/xinwen/xinwenupdate3.jsp");
		return SUCCESS;
	}
	
	
	private KeshiDao keshiDao;

	public KeshiDao getKeshiDao() {
		return keshiDao;
	}

	public void setKeshiDao(KeshiDao keshiDao) {
		this.keshiDao = keshiDao;
	}
	
	
	
	
	
	//科室列表
	public String keshilist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String ktitle = request.getParameter("ktitle");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (ktitle != null && !"".equals(ktitle)) {

			sb.append("ktitle like '%" + ktitle + "%'");
			sb.append(" and ");
			request.setAttribute("ktitle", ktitle);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = keshiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", keshiDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!keshilist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!keshilist?menu="+menu);
		request.setAttribute("url2", "method!keshi");
		request.setAttribute("title", "科室管理");
		this.setUrl("manage/keshi/keshilist.jsp");
		return SUCCESS;

	}
	
//跳转到添加科室页面
	public String keshiadd() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		request.setAttribute("url", "method!keshiadd2?menu="+menu);
		request.setAttribute("title", "添加科室");
		this.setUrl("manage/keshi/keshiadd.jsp");
		return SUCCESS;
	}
	

	
	

	//添加科室操作
	public void keshiadd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		
		String jieshao = request.getParameter("jieshao");
		String ktitle = request.getParameter("ktitle");
				
		Keshi bean =  new Keshi();
		
		
		bean.setCtime(Util.getRiqi());
		
		bean.setJieshao(jieshao);
		bean.setKtitle(ktitle);
		
		
		
		keshiDao.insertBean(bean);
		
		String menu = request.getParameter("menu");
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!keshilist?menu="+menu+"';</script>");
		
		
		
		
		
	}
	
//跳转到更新科室页面
	public String keshiupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Keshi bean = keshiDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!keshiupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "科室修改");
		this.setUrl("manage/keshi/keshiupdate.jsp");
		return SUCCESS;
	}
	
//更新科室操作
	public void keshiupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String jieshao = request.getParameter("jieshao");
		String ktitle = request.getParameter("ktitle");
		
		Keshi bean = keshiDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		
		bean.setJieshao(jieshao);
		bean.setKtitle(ktitle);

		
		keshiDao.updateBean(bean);
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!keshilist?menu="+menu+"';</script>");
		
	}
	
	
//删除科室操作
	public void keshidelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Keshi bean = keshiDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		keshiDao.updateBean(bean);
	
		
	
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!keshilist?menu="+menu+"';</script>");
	}
	
	//跳转到查看科室页面
	public String keshiupdate3() {
		HttpServletRequest request = this.getRequest();
		Keshi bean = keshiDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "科室查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/keshi/keshiupdate3.jsp");
		return SUCCESS;
	}
		
	
	private DoctorDao doctorDao;

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
	
	
	
	//医生列表
	public String doctorlist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String name = request.getParameter("name");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			request.setAttribute("name", name);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = doctorDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", doctorDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!doctorlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!doctorlist?menu="+menu);
		request.setAttribute("url2", "method!doctor");
		request.setAttribute("title", "医生管理");
		this.setUrl("manage/doctor/doctorlist.jsp");
		return SUCCESS;

	}
	
//跳转到添加医生页面
	public String doctoradd() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		request.setAttribute("keshilist", keshiDao.selectBeanList(0, 9999, " where deletestatus=0   "));
		
		request.setAttribute("url", "method!doctoradd2?menu="+menu);
		request.setAttribute("title", "添加医生");
		this.setUrl("manage/doctor/doctoradd.jsp");
		return SUCCESS;
	}
	

	private File uploadfile;//

	public File getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(File uploadfile) {
		this.uploadfile = uploadfile;
	}
	
	

	//添加医生操作
	public void doctoradd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		
		String jianjie = request.getParameter("jianjie");
		String keshiid = request.getParameter("keshiid");
		String name = request.getParameter("name");
		String shanchang = request.getParameter("shanchang");
		String zhiwu = request.getParameter("zhiwu");
		
		String renshu = request.getParameter("renshu");
		String[] weeks = request.getParameterValues("week");
		StringBuffer sb = new StringBuffer();
		
		if(weeks!=null&&weeks.length>0){
			for(int i=0;i<weeks.length;i++){
				sb.append(weeks[i]);
				if(i<weeks.length-1){
					sb.append(",");
				}
			}
			
		}
		
				
		String bianhao = Util.getbianhao(doctorDao.selectBeanCount(""));
		
		Doctor bean =  new Doctor();
		
		bean.setBianhao(bianhao);
		
		bean.setWeek(sb.toString());
		bean.setRenshu(Integer.parseInt(renshu));
		bean.setJianjie(jianjie);
		bean.setKeshi(keshiDao.selectBean(" where id= "+keshiid));
		bean.setName(name);
		
		bean.setShanchang(shanchang);
		bean.setZhiwu(zhiwu);
		if(uploadfile!=null){
			String savapath = ServletActionContext.getServletContext().getRealPath("/")+"/uploadfile/";
			String time = Util.getTime2();
			String imgpath = time+".jpg";
			File file = new File(savapath+imgpath);
			Util.copyFile(uploadfile, file);
			bean.setPhoto(imgpath);
		}
		
		
		doctorDao.insertBean(bean);
		
		User user = new User();
		
		user.setUsername(bianhao);
		user.setPassword("111111");
		user.setRole(2);
		user.setXingming(name);
		
		userDao.insertBean(user);
		
		
		
		
		String menu = request.getParameter("menu");
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!doctorlist?menu="+menu+"';</script>");
		
		
		
		
		
	}
	
//跳转到更新医生页面
	public String doctorupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		request.setAttribute("keshilist", keshiDao.selectBeanList(0, 9999, " where deletestatus=0   "));
		
		
		Doctor bean = doctorDao.selectBean(" where id= "
				+ request.getParameter("id"));
		if(bean.getWeek()!=null){
			String[] weeks = bean.getWeek().split(",");
			
			if(weeks!=null&&weeks.length>0){
				for(int i=0;i<weeks.length;i++){
					
					
					if("星期一".equals(weeks[i])){
						request.setAttribute("week1", "1");
					}
					if("星期二".equals(weeks[i])){
						request.setAttribute("week2", "1");
					}
					if("星期三".equals(weeks[i])){
						request.setAttribute("week3", "1");
					}
					if("星期四".equals(weeks[i])){
						request.setAttribute("week4", "1");
					}
					if("星期五".equals(weeks[i])){
						request.setAttribute("week5", "1");
					}
					if("星期六".equals(weeks[i])){
						request.setAttribute("week6", "1");
					}
					if("星期日".equals(weeks[i])){
						request.setAttribute("week7", "1");
					}
				}
				
			}
			
		}
		
		
		
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!doctorupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "医生修改");
		this.setUrl("manage/doctor/doctorupdate.jsp");
		return SUCCESS;
	}
	
//更新医生操作
	public void doctorupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String jianjie = request.getParameter("jianjie");
		String keshiid = request.getParameter("keshiid");
		String name = request.getParameter("name");
		String shanchang = request.getParameter("shanchang");
		String zhiwu = request.getParameter("zhiwu");
		
		String renshu = request.getParameter("renshu");
		String[] weeks = request.getParameterValues("week");
		StringBuffer sb = new StringBuffer();
		
		if(weeks!=null&&weeks.length>0){
			for(int i=0;i<weeks.length;i++){
				sb.append(weeks[i]);
				if(i<weeks.length-1){
					sb.append(",");
				}
			}
			
		}
		
		Doctor bean = doctorDao.selectBean(" where id= "+ request.getParameter("id"));
		
		bean.setWeek(sb.toString());
		bean.setRenshu(Integer.parseInt(renshu));
		
		bean.setJianjie(jianjie);
		bean.setKeshi(keshiDao.selectBean(" where id= "+keshiid));
		bean.setName(name);
		
		bean.setShanchang(shanchang);
		bean.setZhiwu(zhiwu);
		if(uploadfile!=null){
			String savapath = ServletActionContext.getServletContext().getRealPath("/")+"/uploadfile/";
			String time = Util.getTime2();
			String imgpath = time+".jpg";
			File file = new File(savapath+imgpath);
			Util.copyFile(uploadfile, file);
			bean.setPhoto(imgpath);
		}

		
		doctorDao.updateBean(bean);
		
		
		User user = userDao.selectBean(" where username='"+bean.getBianhao()+"' ");
		
		
		user.setXingming(name);
		
		userDao.updateBean(user);
		
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!doctorlist?menu="+menu+"';</script>");
		
	}
	
	
//删除医生操作
	public void doctordelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Doctor bean = doctorDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		doctorDao.updateBean(bean);
	
		User user = userDao.selectBean(" where username='"+bean.getBianhao()+"' ");
		
		user.setDeletestatus(1);
		
		userDao.updateBean(user);
		
		
	
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!doctorlist?menu="+menu+"';</script>");
	}
	
	//跳转到查看医生页面
	public String doctorupdate3() {
		HttpServletRequest request = this.getRequest();
		Doctor bean = doctorDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "医生查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/doctor/doctorupdate3.jsp");
		return SUCCESS;
	}
	
	
	private GonggaoDao gonggaoDao;

	public GonggaoDao getGonggaoDao() {
		return gonggaoDao;
	}

	public void setGonggaoDao(GonggaoDao gonggaoDao) {
		this.gonggaoDao = gonggaoDao;
	}
	
	
	
	//系统公告列表
	public String gonggaolist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String gtitle = request.getParameter("gtitle");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (gtitle != null && !"".equals(gtitle)) {

			sb.append("gtitle like '%" + gtitle + "%'");
			sb.append(" and ");
			request.setAttribute("gtitle", gtitle);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = gonggaoDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", gonggaoDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!gonggaolist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!gonggaolist?menu="+menu);
		request.setAttribute("url2", "method!gonggao");
		request.setAttribute("title", "系统公告管理");
		this.setUrl("manage/gonggao/gonggaolist.jsp");
		return SUCCESS;

	}
	
//跳转到添加系统公告页面
	public String gonggaoadd() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		request.setAttribute("url", "method!gonggaoadd2?menu="+menu);
		request.setAttribute("title", "添加系统公告");
		this.setUrl("manage/gonggao/gonggaoadd.jsp");
		return SUCCESS;
	}
	

	
	

	//添加系统公告操作
	public void gonggaoadd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		
		String gtitle = request.getParameter("gtitle");
		String gcontent = request.getParameter("gcontent");
				
		Gonggao bean =  new Gonggao();
		
		
		bean.setCtime(Util.getRiqi());
		
		bean.setGtitle(gtitle);
		bean.setGcontent(gcontent);
		
		
		
		
		gonggaoDao.insertBean(bean);
		
		String menu = request.getParameter("menu");
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist?menu="+menu+"';</script>");
		
		
		
		
		
	}
	
//跳转到更新系统公告页面
	public String gonggaoupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Gonggao bean = gonggaoDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!gonggaoupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "系统公告修改");
		this.setUrl("manage/gonggao/gonggaoupdate.jsp");
		return SUCCESS;
	}
	
//更新系统公告操作
	public void gonggaoupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String gtitle = request.getParameter("gtitle");
		String gcontent = request.getParameter("gcontent");
		
		Gonggao bean = gonggaoDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		
		bean.setGtitle(gtitle);
		bean.setGcontent(gcontent);

		
		gonggaoDao.updateBean(bean);
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist?menu="+menu+"';</script>");
		
	}
	
	
//删除系统公告操作
	public void gonggaodelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Gonggao bean = gonggaoDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		gonggaoDao.updateBean(bean);
	
		
	
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!gonggaolist?menu="+menu+"';</script>");
	}
	
	//跳转到查看系统公告页面
	public String gonggaoupdate3() {
		HttpServletRequest request = this.getRequest();
		Gonggao bean = gonggaoDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "系统公告查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/gonggao/gonggaoupdate3.jsp");
		return SUCCESS;
	}
	
	
	private RecordDao recordDao;

	public RecordDao getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
	}
	
	//预约记录列表
	public String recordlist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String ktitle = request.getParameter("ktitle");
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String xingming = request.getParameter("xingming");
		String sfz = request.getParameter("sfz");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (ktitle != null && !"".equals(ktitle)) {

			sb.append(" yuyue.doctor.keshi.ktitle like '%" + ktitle + "%'");
			sb.append(" and ");
			request.setAttribute("ktitle", ktitle);
		}
		if (name != null && !"".equals(name)) {

			sb.append(" yuyue.doctor.name like '%" + name + "%'");
			sb.append(" and ");
			request.setAttribute("name", name);
		}
		if (username != null && !"".equals(username)) {

			sb.append(" user.username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (xingming != null && !"".equals(xingming)) {

			sb.append(" user.xingming like '%" + xingming + "%'");
			sb.append(" and ");
			request.setAttribute("xingming", xingming);
		}
		if (sfz != null && !"".equals(sfz)) {

			sb.append(" user.sfz like '%" + sfz + "%'");
			sb.append(" and ");
			request.setAttribute("sfz", sfz);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = recordDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", recordDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!recordlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!recordlist?menu="+menu);
		request.setAttribute("url2", "method!record");
		request.setAttribute("title", "挂号预约记录");
		this.setUrl("manage/record/recordlist.jsp");
		return SUCCESS;

	}
	
	
	//跳转到查看预约记录页面
	public String recordupdate3() {
		HttpServletRequest request = this.getRequest();
		Record bean = recordDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "预约记录查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/record/recordupdate3.jsp");
		return SUCCESS;
	}
	
	
	private LiuyanDao liuyanDao;

	public LiuyanDao getLiuyanDao() {
		return liuyanDao;
	}

	public void setLiuyanDao(LiuyanDao liuyanDao) {
		this.liuyanDao = liuyanDao;
	}
	
	
	
	//在线留言列表
	public String liuyanlist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String ltitle = request.getParameter("ltitle");
		
		String username = request.getParameter("username");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (ltitle != null && !"".equals(ltitle)) {

			sb.append("ltitle like '%" + ltitle + "%'");
			sb.append(" and ");
			request.setAttribute("ltitle", ltitle);
		}
		if (username != null && !"".equals(username)) {

			sb.append(" user.username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		
		sb.append("   deletestatus=0 and doctor.bianhao='"+u.getUsername()+"'  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = liuyanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", liuyanDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!liuyanlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!liuyanlist?menu="+menu);
		request.setAttribute("url2", "method!liuyan");
		request.setAttribute("title", "在线留言管理");
		this.setUrl("manage/liuyan/liuyanlist.jsp");
		return SUCCESS;

	}
	
	
//跳转到回复留言页面
	public String liuyanupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Liuyan bean = liuyanDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!liuyanupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "回复留言");
		this.setUrl("manage/liuyan/liuyanupdate.jsp");
		return SUCCESS;
	}
	
//更新在线留言操作
	public void liuyanupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String hcontent = request.getParameter("hcontent");
		
		Liuyan bean = liuyanDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		
		bean.setHcontent(hcontent);
		bean.setHtime(Util.getRiqi());
		bean.setStatus("已回复");

		
		liuyanDao.updateBean(bean);
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!liuyanlist?menu="+menu+"';</script>");
		
	}
	
	
//删除在线留言操作
	public void liuyandelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Liuyan bean = liuyanDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		liuyanDao.updateBean(bean);
	
		
	
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!liuyanlist?menu="+menu+"';</script>");
	}
	
	//跳转到查看在线留言页面
	public String liuyanupdate3() {
		HttpServletRequest request = this.getRequest();
		Liuyan bean = liuyanDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "在线留言查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/liuyan/liuyanupdate3.jsp");
		return SUCCESS;
	}
	
	private PicDao picDao;

	public PicDao getPicDao() {
		return picDao;
	}

	public void setPicDao(PicDao picDao) {
		this.picDao = picDao;
	}
	
	
	
	
	//网站动态图片列表
	public String piclist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		
		
		
		sb.append("   0=0  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = picDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", picDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!piclist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!piclist?menu="+menu);
		request.setAttribute("url2", "method!pic");
		request.setAttribute("title", "网站动态图片管理");
		this.setUrl("manage/pic/piclist.jsp");
		return SUCCESS;

	}
	
//跳转到更新网站动态图片页面
	public String picupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		Pic bean = picDao.selectBean(" where id= "
				+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!picupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "修改网站动态图片");
		this.setUrl("manage/pic/picupdate.jsp");
		return SUCCESS;
	}
	
	
//更新网站动态图片操作
	public void picupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String href = request.getParameter("href");
		String info = request.getParameter("info");
		
		Pic bean = picDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setHref(href);
		bean.setInfo(info);
		
		if(uploadfile!=null){
			String savapath = ServletActionContext.getServletContext().getRealPath("/")+"/upload/";
			String time = Util.getTime2();
			String imgpath = time+".jpg";
			File file = new File(savapath+imgpath);
			Util.copyFile(uploadfile, file);

			bean.setPath(imgpath);
		}

		
		picDao.updateBean(bean);
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!piclist?menu="+menu+"';</script>");
		
	}
	
	
	//医生账户查询列表
	public String userlist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String username = request.getParameter("username");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (username != null && !"".equals(username)) {

			sb.append("username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		
		
		
		sb.append("   deletestatus=0 and role=2   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = userDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", userDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!userlist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!userlist?menu="+menu);
		request.setAttribute("url2", "method!user");
		request.setAttribute("title", "医生账户查询");
		this.setUrl("manage/user/userlist.jsp");
		return SUCCESS;

	}
	

	private YuyueDao yuyueDao;
	
	

	public YuyueDao getYuyueDao() {
		return yuyueDao;
	}

	public void setYuyueDao(YuyueDao yuyueDao) {
		this.yuyueDao = yuyueDao;
	}

	//当天预约列表
	public String recordlist2() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		List<Yuyue> ylist = yuyueDao.selectBeanList(0, 99999, " where  deletestatus=0  ");
		
		for(Yuyue bean:ylist){
			
			List<Record> list = recordDao.selectBeanList(0, 9999, " where deletestatus=0 and yuyue.id= "+bean.getId() +" and status='预约成功'  order by id");
			
			int total = bean.getDoctor().getRenshu();
			
			
			int total2 = (int)(total/2);
			
			for(int i=0;i<list.size();i++){
				Record re = list.get(i);
				
				re.setShunxu(i+1);
				
				if((i+1)<=total2){
					re.setSj("上午");
				}else{
					re.setSj("下午");
				}
				
				
				recordDao.updateBean(re);
			}
			
			
			
		}
		
		String username = request.getParameter("username");
		String xingming = request.getParameter("xingming");
		String sfz = request.getParameter("sfz");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		
		if (username != null && !"".equals(username)) {

			sb.append(" user.username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (xingming != null && !"".equals(xingming)) {

			sb.append(" user.xingming like '%" + xingming + "%'");
			sb.append(" and ");
			request.setAttribute("xingming", xingming);
		}
		if (sfz != null && !"".equals(sfz)) {

			sb.append(" user.sfz like '%" + sfz + "%'");
			sb.append(" and ");
			request.setAttribute("sfz", sfz);
		}
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		
		String riqi = Util.getRiqi2();
		
		sb.append("   deletestatus=0  and yuyue.doctor.bianhao='"+u.getUsername()+"' and riqi = '"+riqi+"'  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = recordDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", recordDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!recordlist2?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!recordlist2?menu="+menu);
		request.setAttribute("url2", "method!record");
		request.setAttribute("title", "当天预约");
		this.setUrl("manage/record/recordlist2.jsp");
		return SUCCESS;

	}
	
	
	//所有预约列表
	public String recordlist3() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
List<Yuyue> ylist = yuyueDao.selectBeanList(0, 99999, " where  deletestatus=0  ");
		
		for(Yuyue bean:ylist){
			
			List<Record> list = recordDao.selectBeanList(0, 9999, " where deletestatus=0 and yuyue.id= "+bean.getId() +" and status='预约成功'  order by id");
			
			int total = bean.getDoctor().getRenshu();
			
			
			int total2 = (int)(total/2);
			
			for(int i=0;i<list.size();i++){
				Record re = list.get(i);
				
				re.setShunxu(i+1);
				
				if((i+1)<=total2){
					re.setSj("上午");
				}else{
					re.setSj("下午");
				}
				
				
				recordDao.updateBean(re);
			}
			
			
			
		}
		
		String username = request.getParameter("username");
		String xingming = request.getParameter("xingming");
		String sfz = request.getParameter("sfz");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		
		if (username != null && !"".equals(username)) {

			sb.append(" user.username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (xingming != null && !"".equals(xingming)) {

			sb.append(" user.xingming like '%" + xingming + "%'");
			sb.append(" and ");
			request.setAttribute("xingming", xingming);
		}
		if (sfz != null && !"".equals(sfz)) {

			sb.append(" user.sfz like '%" + sfz + "%'");
			sb.append(" and ");
			request.setAttribute("sfz", sfz);
		}
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		
	
		sb.append("   deletestatus=0  and yuyue.doctor.bianhao='"+u.getUsername()+"'   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = recordDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", recordDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!recordlist3?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!recordlist3?menu="+menu);
		request.setAttribute("url2", "method!record");
		request.setAttribute("title", "所有预约");
		this.setUrl("manage/record/recordlist3.jsp");
		return SUCCESS;

	}
	
	
	//医生列表
	public String doctorlist2() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String name = request.getParameter("name");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			request.setAttribute("name", name);
		}
		
		HttpSession session = request.getSession();
		User u = (User)session.getAttribute("user");
		
		sb.append("   deletestatus=0 and bianhao='"+u.getUsername()+"'  order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = doctorDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", doctorDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!doctorlist2?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!doctorlist2?menu="+menu);
		request.setAttribute("url2", "method!doctor");
		request.setAttribute("title", "个人信息查询");
		this.setUrl("manage/doctor/doctorlist2.jsp");
		return SUCCESS;

	}
	
	private JianchaDao jianchaDao;

	public JianchaDao getJianchaDao() {
		return jianchaDao;
	}

	public void setJianchaDao(JianchaDao jianchaDao) {
		this.jianchaDao = jianchaDao;
	}
	
	
	
	//检查项目列表
	public String jianchalist() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		String jname = request.getParameter("jname");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (jname != null && !"".equals(jname)) {

			sb.append("jname like '%" + jname + "%'");
			sb.append(" and ");
			request.setAttribute("jname", jname);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = jianchaDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", jianchaDao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!jianchalist?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!jianchalist?menu="+menu);
		request.setAttribute("url2", "method!jiancha");
		request.setAttribute("title", "检查项目管理");
		this.setUrl("manage/jiancha/jianchalist.jsp");
		return SUCCESS;

	}
	
//跳转到添加检查项目页面
	public String jianchaadd() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		request.setAttribute("url", "method!jianchaadd2?menu="+menu);
		request.setAttribute("title", "添加检查项目");
		this.setUrl("manage/jiancha/jianchaadd.jsp");
		return SUCCESS;
	}
	

	
	

	//添加检查项目操作
	public void jianchaadd2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		
		String jname = request.getParameter("jname");
		String money = request.getParameter("money");
		String shuoming = request.getParameter("shuoming");
		String zhuyi = request.getParameter("zhuyi");
		
		String renshu = request.getParameter("renshu");
		String[] weeks = request.getParameterValues("week");
		StringBuffer sb = new StringBuffer();
		
		if(weeks!=null&&weeks.length>0){
			for(int i=0;i<weeks.length;i++){
				sb.append(weeks[i]);
				if(i<weeks.length-1){
					sb.append(",");
				}
			}
			
		}
		
				
		
		Jiancha bean =  new Jiancha();
		
		bean.setJname(jname);
		bean.setMoney(money);
		
		bean.setShuoming(shuoming);
		bean.setZhuyi(zhuyi);
		
		bean.setWeek(sb.toString());
		bean.setRenshu(Integer.parseInt(renshu));
		
		
		
		
		jianchaDao.insertBean(bean);
		

		
		String menu = request.getParameter("menu");
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!jianchalist?menu="+menu+"';</script>");
		
		
		
		
		
	}
	
//跳转到更新检查项目页面
	public String jianchaupdate() {
		HttpServletRequest request = this.getRequest();
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		
		
		
		Jiancha bean = jianchaDao.selectBean(" where id= "
				+ request.getParameter("id"));
		if(bean.getWeek()!=null){
			String[] weeks = bean.getWeek().split(",");
			
			if(weeks!=null&&weeks.length>0){
				for(int i=0;i<weeks.length;i++){
					
					
					if("星期一".equals(weeks[i])){
						request.setAttribute("week1", "1");
					}
					if("星期二".equals(weeks[i])){
						request.setAttribute("week2", "1");
					}
					if("星期三".equals(weeks[i])){
						request.setAttribute("week3", "1");
					}
					if("星期四".equals(weeks[i])){
						request.setAttribute("week4", "1");
					}
					if("星期五".equals(weeks[i])){
						request.setAttribute("week5", "1");
					}
					if("星期六".equals(weeks[i])){
						request.setAttribute("week6", "1");
					}
					if("星期日".equals(weeks[i])){
						request.setAttribute("week7", "1");
					}
				}
				
			}
			
		}
		
		
		
		request.setAttribute("bean", bean);
		request.setAttribute("url", "method!jianchaupdate2?id="+bean.getId()+"&menu="+menu);
		request.setAttribute("title", "检查项目修改");
		this.setUrl("manage/jiancha/jianchaupdate.jsp");
		return SUCCESS;
	}
	
//更新检查项目操作
	public void jianchaupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();

		String jname = request.getParameter("jname");
		String money = request.getParameter("money");
		String shuoming = request.getParameter("shuoming");
		String zhuyi = request.getParameter("zhuyi");
		
		String renshu = request.getParameter("renshu");
		String[] weeks = request.getParameterValues("week");
		StringBuffer sb = new StringBuffer();
		
		if(weeks!=null&&weeks.length>0){
			for(int i=0;i<weeks.length;i++){
				sb.append(weeks[i]);
				if(i<weeks.length-1){
					sb.append(",");
				}
			}
			
		}
		
		Jiancha bean = jianchaDao.selectBean(" where id= "+ request.getParameter("id"));
		
		bean.setWeek(sb.toString());
		bean.setRenshu(Integer.parseInt(renshu));
		
		bean.setJname(jname);
		bean.setMoney(money);
		
		bean.setShuoming(shuoming);
		bean.setZhuyi(zhuyi);
		
		
		
		jianchaDao.updateBean(bean);
		
		
		
		
		String menu = request.getParameter("menu");
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!jianchalist?menu="+menu+"';</script>");
		
	}
	
	
//删除检查项目操作
	public void jianchadelete() throws IOException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		String menu = request.getParameter("menu");
		Jiancha bean = jianchaDao.selectBean(" where id= "+ request.getParameter("id"));
		
		
		bean.setDeletestatus(1);
		jianchaDao.updateBean(bean);
	
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='method!jianchalist?menu="+menu+"';</script>");
	}
	
	//跳转到查看检查项目页面
	public String jianchaupdate3() {
		HttpServletRequest request = this.getRequest();
		Jiancha bean = jianchaDao.selectBean(" where id= "+ request.getParameter("id"));
		request.setAttribute("bean", bean);
		request.setAttribute("title", "检查项目查看");
		String menu = request.getParameter("menu");
		request.setAttribute("menu",menu );
		this.setUrl("manage/jiancha/jianchaupdate3.jsp");
		return SUCCESS;
	}
	
	
	private Record2Dao record2Dao;

	public Record2Dao getRecord2Dao() {
		return record2Dao;
	}

	public void setRecord2Dao(Record2Dao record2Dao) {
		this.record2Dao = record2Dao;
	}
	
	
	//预约记录列表
	public String record2list() {
		HttpServletRequest request = this.getRequest();
		//控制左边菜单栏的展示或者闭合
		String menu = request.getParameter("menu");

		request.setAttribute("menu",menu );
		
		
		String jname = request.getParameter("jname");
		String username = request.getParameter("username");
		String xingming = request.getParameter("xingming");
		String sfz = request.getParameter("sfz");
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		if (jname != null && !"".equals(jname)) {

			sb.append(" yuyue2.jiancha.jname like '%" + jname + "%'");
			sb.append(" and ");
			request.setAttribute("jname", jname);
		}
		
		
		if (username != null && !"".equals(username)) {

			sb.append(" user.username like '%" + username + "%'");
			sb.append(" and ");
			request.setAttribute("username", username);
		}
		if (xingming != null && !"".equals(xingming)) {

			sb.append(" user.xingming like '%" + xingming + "%'");
			sb.append(" and ");
			request.setAttribute("xingming", xingming);
		}
		if (sfz != null && !"".equals(sfz)) {

			sb.append(" user.sfz like '%" + sfz + "%'");
			sb.append(" and ");
			request.setAttribute("sfz", sfz);
		}
		
		
		
		sb.append("   deletestatus=0   order by id desc ");
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 10;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		int total = record2Dao.selectBeanCount(where.replaceAll("order by id desc", ""));
		request.setAttribute("list", record2Dao.selectBeanList((currentpage - 1)
				* pagesize, pagesize, where));
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,
				currentpage, "method!record2list?menu="+menu, "共有" + total + "条记录"));
		request.setAttribute("url", "method!record2list?menu="+menu);
		request.setAttribute("url2", "method!record2");
		request.setAttribute("title", "检查预约记录");
		this.setUrl("manage/record2/record2list.jsp");
		return SUCCESS;

	}
	
	
}
