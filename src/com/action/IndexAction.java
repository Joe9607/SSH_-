package com.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
import com.dao.Yuyue2Dao;
import com.dao.YuyueDao;
import com.model.Doctor;
import com.model.Gonggao;
import com.model.Jiancha;
import com.model.Keshi;
import com.model.Liuyan;
import com.model.Pic;
import com.model.Record;
import com.model.Record2;
import com.model.User;
import com.model.Xinwen;
import com.model.Yuyue;
import com.model.Yuyue2;
import com.opensymphony.xwork2.ActionSupport;
import com.util.Pager;
import com.util.Util;

public class IndexAction extends ActionSupport {

	private static final long serialVersionUID = -4304509122548259589L;

	private String url = "./";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// 获取请求对象
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}

	// 获取响应对象
	public HttpServletResponse getResponse() {
		HttpServletResponse response = ServletActionContext.getResponse();
		return response;
	}

	// 获取session对象
	public HttpSession getSession() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		return session;
	}

	// 获取输出对象
	public PrintWriter getPrintWriter() {
		//获取当前的响应对象
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter writer = null;
		try {
			//打印输出文本
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer;
	}
	
	private XinwenDao xinwenDao;

	public XinwenDao getXinwenDao() {
		return xinwenDao;
	}

	public void setXinwenDao(XinwenDao xinwenDao) {
		this.xinwenDao = xinwenDao;
	}

	
	//试题随机数的方法
	private static List<Doctor> suiji(List<Doctor> list, int num) {
		Collections.shuffle(list);
		List<Doctor> list2 = new ArrayList<Doctor>();
		if (list.size() <= num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			list2.add(list.get(i));
		}
		return list2;
	}
	
	// 网站首页
	@SuppressWarnings("static-access")
	public String index() throws Exception {

		
		
		HttpServletRequest request = this.getRequest();
		Util.init(request);
		
		request.setAttribute("xinwenlist", xinwenDao.selectBeanList(0, 9, " where deletestatus=0 order by id desc  "));
		
		
		//随机取4条
		request.setAttribute("doctorlist",this.suiji(doctorDao.selectBeanList(0, 9999, " where deletestatus=0 order by id desc  "), 4) );
		
		
		List<Pic> list = picDao.selectBeanList(0, 9999, " order by id   ");
		
		StringBuffer  pics= new StringBuffer();
		StringBuffer links= new StringBuffer();
		StringBuffer texts= new StringBuffer();
		
		for(int i=0;i<list.size();i++){
			pics.append("upload/"+list.get(i).getPath());
			links.append(list.get(i).getHref());
			texts.append(list.get(i).getInfo());
			if(i<list.size()-1){
				pics.append("|");
				links.append("|");
				texts.append("|");
			}
			
			
		}
		
		request.setAttribute("pics",pics );
		request.setAttribute("links",links);
		request.setAttribute("texts",texts );
		
		
		
		return "success";
	}
	
	
	//医院新闻列表
	public String xinwenlist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		String xtitle = request.getParameter("xtitle");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (xtitle != null && !"".equals(xtitle)) {

			sb.append("xtitle like '%" + xtitle + "%'");
			sb.append(" and ");
			request.setAttribute("xtitle", xtitle);
		}
		
		sb.append("  deletestatus=0  order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = xinwenDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("xinwenlist", xinwenDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!xinwenlist", "共有" + total + "条记录"));

		request.setAttribute("url", "indexmethod!xinwenlist");
		request.setAttribute("title", "医院新闻");
		this.setUrl("xinwenlist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到查看医院新闻页面
	public String xinwenshow() {
		HttpServletRequest request = this.getRequest();
		Xinwen bean = xinwenDao.selectBean(" where id= "+ request.getParameter("id"));
		bean.setVist(bean.getVist()+1);
		
		xinwenDao.updateBean(bean);
		
		request.setAttribute("title", "新闻详情");
		request.setAttribute("bean", bean);
		this.setUrl("xinwenshow.jsp");
		return SUCCESS;
	}
	
	private GonggaoDao gonggaoDao;
	
	
	public GonggaoDao getGonggaoDao() {
		return gonggaoDao;
	}

	public void setGonggaoDao(GonggaoDao gonggaoDao) {
		this.gonggaoDao = gonggaoDao;
	}

	//跳转到查看系统公告页面
	public String gonggaoshow() {
		HttpServletRequest request = this.getRequest();
		Gonggao bean = gonggaoDao.selectBean(" where id= "+ request.getParameter("id"));
		
		gonggaoDao.updateBean(bean);
		
		request.setAttribute("title", "统公告详情");
		request.setAttribute("bean", bean);
		this.setUrl("gonggaoshow.jsp");
		return SUCCESS;
	}
	
	
	
	private KeshiDao keshiDao;

	public KeshiDao getKeshiDao() {
		return keshiDao;
	}

	public void setKeshiDao(KeshiDao keshiDao) {
		this.keshiDao = keshiDao;
	}

	
	
	//科室介绍列表
	public String keshilist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		String ktitle = request.getParameter("ktitle");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (ktitle != null && !"".equals(ktitle)) {

			sb.append("ktitle like '%" + ktitle + "%'");
			sb.append(" and ");
			request.setAttribute("ktitle", ktitle);
		}
		
		sb.append("  deletestatus=0  order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = keshiDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("keshilist", keshiDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!keshilist", "共有" + total + "条记录"));

		request.setAttribute("url", "indexmethod!keshilist");
		request.setAttribute("title", "科室介绍");
		this.setUrl("keshilist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到查看科室介绍页面
	public String keshishow() {
		HttpServletRequest request = this.getRequest();
		Keshi bean = keshiDao.selectBean(" where id= "+ request.getParameter("id"));
		
		keshiDao.updateBean(bean);
		
		request.setAttribute("title", "科室详情");
		request.setAttribute("bean", bean);
		this.setUrl("keshishow.jsp");
		return SUCCESS;
	}
	
	
	
	private DoctorDao doctorDao;

	public DoctorDao getDoctorDao() {
		return doctorDao;
	}

	public void setDoctorDao(DoctorDao doctorDao) {
		this.doctorDao = doctorDao;
	}
	
	
	
	
	//预约挂号列表
	public String doctorlist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		String name = request.getParameter("name");
		
		String ktitle = request.getParameter("ktitle");
		
		request.setAttribute("keshilist", keshiDao.selectBeanList(0, 9999, " where deletestatus=0   "));
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (name != null && !"".equals(name)) {

			sb.append("name like '%" + name + "%'");
			sb.append(" and ");
			request.setAttribute("name", name);
		}
		
		if (ktitle != null && !"".equals(ktitle)) {

			sb.append(" keshi.ktitle like '%" + ktitle + "%'");
			sb.append(" and ");
			request.setAttribute("ktitle", ktitle);
		}
		
		sb.append("  deletestatus=0  order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = doctorDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("doctorlist", doctorDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!doctorlist", "共有" + total + "条记录"));

		request.setAttribute("url", "indexmethod!doctorlist");
		request.setAttribute("title", "预约挂号");
		this.setUrl("doctorlist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到医生介绍页面
	public String doctorshow() {
		HttpServletRequest request = this.getRequest();
		Doctor bean = doctorDao.selectBean(" where id= "+ request.getParameter("id"));
		
		doctorDao.updateBean(bean);
		
		request.setAttribute("title", "医生介绍");
		request.setAttribute("bean", bean);
		this.setUrl("doctorshow.jsp");
		return SUCCESS;
	}
	
	
	private UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	

	//用户注册操作
	public void register() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();

		String address = request.getParameter("address");
		String dianhua = request.getParameter("dianhua");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		String xingming = request.getParameter("xingming");
		String sex = request.getParameter("sex");
		String sfz = request.getParameter("sfz");

		User bean = userDao.selectBean(" where deletestatus=0 and username='"+ username + "' ");
		if (bean == null) {
			bean = new User();
			bean.setAddress(address);
			bean.setCreatetime(Util.getTime());
			bean.setDianhua(dianhua);
			bean.setPassword(password);
			bean.setRole(1);
			bean.setUsername(username);
			bean.setXingming(xingming);
			bean.setSex(sex);
			bean.setSfz(sfz);
			userDao.insertBean(bean);
			writer
					.print("<script language=javascript>alert('注册成功');window.location.href='.';</script>");
		} else {
			writer
					.print("<script language=javascript>alert('注册失败，该用户名已经存在');window.location.href='register.jsp';</script>");
		}

	}
	

	

	// 登录操作
	public void login() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User bean = userDao.selectBean(" where username = '" + username+ "' and password= '" + password+ "' and deletestatus=0 and role=1 ");

		if (bean != null) {
			HttpSession session = request.getSession();
			session.setAttribute("qiantai", bean);

			writer
					.print("<script language=javascript>alert('登录成功');window.location.href='.';</script>");
		} else {
			writer
					.print("<script language=javascript>alert('用户名或者密码错误');window.location.href='.';</script>");
		}

	}

	// 用户退出
	public void loginout() throws IOException {
		HttpServletRequest request =  this.getRequest();
		PrintWriter writer = this.getPrintWriter();	
		HttpSession session = request.getSession();
		session.removeAttribute("qiantai");
		writer.print("<script language=javascript>alert('退出成功');window.location.href='.';</script>");
	}
	
	
	// 跳转到修改个人信息页面
	public String userupdate() {
		HttpServletRequest request = this.getRequest();

		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		
		User bean = userDao.selectBean(" where id= "+uu.getId());
		
		request.setAttribute("bean", bean);
		
		request.setAttribute("url", "indexmethod!userupdate2?id="+bean.getId());
		request.setAttribute("title", "个人信息");
		this.setUrl("userupdate.jsp");
		return SUCCESS;
	}

	// 个人信息操作
	public void userupdate2() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();

		String address = request.getParameter("address");
		String dianhua = request.getParameter("dianhua");

		User bean = userDao.selectBean(" where id= "+request.getParameter("id"));
		
		bean.setAddress(address);	
		bean.setDianhua(dianhua);
		
		userDao.updateBean(bean);
		writer
				.print("<script language=javascript>alert('修改成功');window.location.href='indexmethod!userupdate';</script>");

	}
	
	
	
	// 跳转到修改个人信息页面
	public String userupdate3() {
		HttpServletRequest request = this.getRequest();
		request.setAttribute("url", "indexmethod!userupdate4");
		request.setAttribute("title", "修改密码");
		this.setUrl("userupdate3.jsp");
		return SUCCESS;
	}

	// 个人信息操作
	public void userupdate4() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");

		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
	

		User bean = userDao.selectBean(" where deletestatus=0 and username='"+uu.getUsername()+"'  and password='"+password1+"'");
		
		if(bean!=null){
			bean.setPassword(password2);
			userDao.updateBean(bean);
			writer
					.print("<script language=javascript>alert('修改成功');window.location.href='indexmethod!userupdate3';</script>");
		}else{
			writer
			.print("<script language=javascript>alert('修改失败，原密码错误');window.location.href='indexmethod!userupdate3';</script>");
		}
		
		

	}
	
	
	
	private YuyueDao yuyueDao;

	public YuyueDao getYuyueDao() {
		return yuyueDao;
	}

	public void setYuyueDao(YuyueDao yuyueDao) {
		this.yuyueDao = yuyueDao;
	}
	
	
	//跳转到预约挂号页面
	public String yuyue() throws ParseException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		String did = request.getParameter("did");
		
		if(uu==null){
			writer
			.print("<script language=javascript>alert('请先登录');window.location.href='indexmethod!doctorshow?id="+did+"';</script>");
			return null ;
		}
		
		
		
		Doctor bean = doctorDao.selectBean(" where id= "+did);
		//可预约最近一周的门诊号
		
		
		String riqi = Util.getRiqi2();//日期格式：20170106
		
		for(int i=1;i<8;i++){
			
			String riqi2 = (Integer.parseInt(riqi)+i)+"";
			
			
			String week = Util.getWeekOfDate(Util.parseRiqi2(riqi2));
			
			
			Doctor doc = doctorDao.selectBean(" where id="+bean.getId()+" and week like '%"+week+"%' ");
			
			if(doc!=null){
				Yuyue yuyue = yuyueDao.selectBean(" where doctor.id="+doc.getId()+" and riqi="+riqi2+" and week='"+week+"' ");
				if(yuyue==null){
					yuyue = new Yuyue();
					yuyue.setDoctor(doc);
					yuyue.setRiqi(Integer.parseInt(riqi2));
					yuyue.setWeek(week);
					yuyueDao.insertBean(yuyue);
				}
				
				
			}
			
			
		}

		request.setAttribute("bean", bean);
		
		
		List<Yuyue>  list = yuyueDao.selectBeanList(0, 9999, " where doctor.id="+did+"  and riqi>"+riqi+" and  riqi<"+(Integer.parseInt(riqi)+8)+"");
		
		request.setAttribute("list", list);
		
		
		request.setAttribute("url", "indexmethod!yuyueadd?did="+bean.getId());
		request.setAttribute("title", "预约挂号");
		this.setUrl("yuyue.jsp");
		return SUCCESS;
	}
	
	private RecordDao recordDao;

	public RecordDao getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
	}
	
	

	//预约挂号操作
	public void yuyueadd() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");

		String yuyueid = request.getParameter("yuyueid");
		
		String did = request.getParameter("did");
		
		if(yuyueid==null){
			writer
			.print("<script language=javascript>alert('预约失败，请选择预约的日期');window.location.href='indexmethod!yuyue?did="+did+"';</script>");
			return ;
		}
	

		Yuyue yuyue = yuyueDao.selectBean(" where id = "+yuyueid);
		
		if(yuyue.getDoctor().getRenshu()-yuyue.getShiji()<=0){
			writer
			.print("<script language=javascript>alert('预约失败，当天该医生已经预约满');window.location.href='indexmethod!yuyue?did="+yuyue.getDoctor().getId()+"';</script>");
			return ;
		}
		
	
		
		
		
		Record bean = recordDao.selectBean(" where deletestatus=0  and status!='已取消' and   yuyue.id="+yuyue.getId()+" and user.id="+uu.getId()+"  ");
		if(bean!=null){
			writer
			.print("<script language=javascript>alert('预约失败，您已经在该日期预约过该医生，不可重复预约');window.location.href='indexmethod!yuyue?did="+yuyue.getDoctor().getId()+"';</script>");
			return ;
		}
		
		bean = new Record();
		bean.setUser(uu);
		bean.setYuyue(yuyue);
		bean.setYtime(Util.getTime());
		bean.setRiqi(yuyue.getRiqi());
		bean.setStatus("预约成功");
		recordDao.insertBean(bean);
		
		yuyue.setShiji(yuyue.getShiji()+1);
		
		yuyueDao.updateBean(yuyue);
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!recordlist';</script>");
		
		

	}
	
	
	
	//我的挂号列表
	public String recordlist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		
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
		
		
		String ktitle = request.getParameter("ktitle");
		
		String name = request.getParameter("name");
		
		
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
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		
		sb.append("  deletestatus=0  and user.id="+uu.getId()+" order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = recordDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		List<Record> list = recordDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where);
		
		
		
		request.setAttribute("recordlist",list );
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!recordlist", "共有" + total + "条记录"));

		request.setAttribute("riqi", Integer.parseInt(Util.getRiqi2()));
		
		request.setAttribute("url", "indexmethod!recordlist");
		request.setAttribute("title", "我的挂号");
		this.setUrl("recordlist.jsp");
		return SUCCESS;
	}
	
	
	
	//取消预定操作
	public void recorddelete() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		
	

		Record bean = recordDao.selectBean(" where id= "+request.getParameter("id"));
		
		bean.setStatus("已取消");
		
		recordDao.updateBean(bean);
		
		
		Yuyue yuyue = yuyueDao.selectBean(" where id= "+bean.getYuyue().getId());
		
		yuyue.setShiji(yuyue.getShiji()-1);
		
		yuyueDao.updateBean(yuyue);
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!recordlist';</script>");
		
		

	}
	
	
	private LiuyanDao liuyanDao;

	public LiuyanDao getLiuyanDao() {
		return liuyanDao;
	}

	public void setLiuyanDao(LiuyanDao liuyanDao) {
		this.liuyanDao = liuyanDao;
	}
	
	
	//跳转到留言页面
	public String liuyanadd() throws ParseException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		if(uu==null){
			writer
			.print("<script language=javascript>alert('请先登录');window.location.href='.';</script>");
			return null ;
		}
		
		String did = request.getParameter("did");
		
		
		
		
		request.setAttribute("url", "indexmethod!liuyanadd2?did="+did);
		request.setAttribute("title", "在线留言");
		this.setUrl("liuyanadd.jsp");
		return SUCCESS;
	}
	
	
	//留言操作
	public void liuyanadd2() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();

		String lcontent = request.getParameter("lcontent");
		String ltitle = request.getParameter("ltitle");
		String did = request.getParameter("did");
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		
		Liuyan bean = new Liuyan();
		
		Doctor doctor = doctorDao.selectBean(" where id= "+did);
		
		bean.setDoctor(doctor);
		
		bean.setCtime(Util.getRiqi());
		bean.setLcontent(lcontent);
		bean.setLtitle(ltitle);
		bean.setStatus("未回复");
		bean.setUser(uu);
		
		liuyanDao.insertBean(bean);
		
		
		writer
		.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!liuyanlist';</script>");

	}
	
	
	//我的留言列表
	public String liuyanlist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		String ltitle = request.getParameter("ltitle");
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		
		if (ltitle != null && !"".equals(ltitle)) {

			sb.append(" ltitle like '%" + ltitle + "%'");
			sb.append(" and ");
			request.setAttribute("ltitle", ltitle);
		}
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		
		sb.append("  deletestatus=0  and user.id="+uu.getId()+" order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = liuyanDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("liuyanlist", liuyanDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!liuyanlist", "共有" + total + "条记录"));

		request.setAttribute("riqi", Integer.parseInt(Util.getRiqi2()));
		
		request.setAttribute("url", "indexmethod!liuyanlist");
		request.setAttribute("title", "我的留言");
		this.setUrl("liuyanlist.jsp");
		return SUCCESS;
	}
	
	
	//删除留言操作
	public void liuyandelete() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		
	

		Liuyan bean = liuyanDao.selectBean(" where id= "+request.getParameter("id"));
		
		bean.setDeletestatus(1);
		
		liuyanDao.updateBean(bean);
		

		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!liuyanlist';</script>");
		
		

	}
	
	
	//跳转到查看留言页面
	public String liuyanupdate3() throws ParseException {
		HttpServletRequest request = this.getRequest();
		
		
		Liuyan bean = liuyanDao.selectBean(" where id= "+request.getParameter("id"));
		
		request.setAttribute("bean", bean);
		
		
		request.setAttribute("title", "查看留言详情");
		this.setUrl("liuyanupdate3.jsp");
		return SUCCESS;
	}
	
	
	private PicDao picDao;

	public PicDao getPicDao() {
		return picDao;
	}

	public void setPicDao(PicDao picDao) {
		this.picDao = picDao;
	}
	
	
	private JianchaDao jianchaDao;

	public JianchaDao getJianchaDao() {
		return jianchaDao;
	}

	public void setJianchaDao(JianchaDao jianchaDao) {
		this.jianchaDao = jianchaDao;
	}
	
	
	//预约检查列表
	public String jianchalist() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		String jname = request.getParameter("jname");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		if (jname != null && !"".equals(jname)) {

			sb.append("jname like '%" + jname + "%'");
			sb.append(" and ");
			request.setAttribute("jname", jname);
		}
		
		
		sb.append("  deletestatus=0  order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = jianchaDao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("jianchalist", jianchaDao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!jianchalist", "共有" + total + "条记录"));

		request.setAttribute("url", "indexmethod!jianchalist");
		request.setAttribute("title", "预约检查");
		this.setUrl("jianchalist.jsp");
		return SUCCESS;
	}
	
	
	//跳转到检查详情页面
	public String jianchashow() {
		HttpServletRequest request = this.getRequest();
		Jiancha bean = jianchaDao.selectBean(" where id= "+ request.getParameter("id"));
		
		jianchaDao.updateBean(bean);
		
		request.setAttribute("title", "检查详情");
		request.setAttribute("bean", bean);
		this.setUrl("jianchashow.jsp");
		return SUCCESS;
	}
	
	
	private Yuyue2Dao yuyue2Dao;

	public Yuyue2Dao getYuyue2Dao() {
		return yuyue2Dao;
	}

	public void setYuyue2Dao(Yuyue2Dao yuyue2Dao) {
		this.yuyue2Dao = yuyue2Dao;
	}
	
	
	private Record2Dao record2Dao;

	
	public Record2Dao getRecord2Dao() {
		return record2Dao;
	}

	public void setRecord2Dao(Record2Dao record2Dao) {
		this.record2Dao = record2Dao;
	}
	
	

	//跳转到预约检查页面
	public String yuyue2() throws ParseException {
		HttpServletRequest request = this.getRequest();
		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		String jid = request.getParameter("jid");
		
		if(uu==null){
			writer
			.print("<script language=javascript>alert('请先登录');window.location.href='indexmethod!jianchashow?id="+jid+"';</script>");
			return null ;
		}
		
		
		
		Jiancha bean = jianchaDao.selectBean(" where id= "+jid);
		//可预约最近一周的检查
		
		
		String riqi = Util.getRiqi2();//日期格式：20170106
		
		for(int i=1;i<8;i++){
			
			String riqi2 = (Integer.parseInt(riqi)+i)+"";
			
			
			String week = Util.getWeekOfDate(Util.parseRiqi2(riqi2));
			
			
			Jiancha jiancha = jianchaDao.selectBean(" where id="+bean.getId()+" and week like '%"+week+"%' ");
			
			if(jiancha!=null){
				Yuyue2 yuyue2 = yuyue2Dao.selectBean(" where jiancha.id="+jiancha.getId()+" and riqi="+riqi2+" and week='"+week+"' ");
				if(yuyue2==null){
					yuyue2 = new Yuyue2();
					yuyue2.setJiancha(jiancha);
					yuyue2.setRiqi(Integer.parseInt(riqi2));
					yuyue2.setWeek(week);
					yuyue2Dao.insertBean(yuyue2);
				}
				
				
			}
			
			
		}

		request.setAttribute("bean", bean);
		
		
		List<Yuyue2>  list = yuyue2Dao.selectBeanList(0, 9999, " where jiancha.id="+jid+"  and riqi>"+riqi+" and  riqi<"+(Integer.parseInt(riqi)+8)+"");
		
		request.setAttribute("list", list);
		
		
		request.setAttribute("url", "indexmethod!yuyue2add?jid="+bean.getId());
		request.setAttribute("title", "预约检查");
		this.setUrl("yuyue2.jsp");
		return SUCCESS;
	}
	
	
	
	

	//预约检查操作
	public void yuyue2add() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");

		String yuyue2id = request.getParameter("yuyue2id");
		
		String jid = request.getParameter("jid");
		
		if(yuyue2id==null){
			writer
			.print("<script language=javascript>alert('预约失败，请选择预约的日期');window.location.href='indexmethod!yuyue2?jid="+jid+"';</script>");
			return ;
		}
	

		Yuyue2 yuyue2 = yuyue2Dao.selectBean(" where id = "+yuyue2id);
		
		if(yuyue2.getJiancha().getRenshu()-yuyue2.getShiji()<=0){
			writer
			.print("<script language=javascript>alert('预约失败，当天该检查已经预约满');window.location.href='indexmethod!yuyue2?jid="+yuyue2.getJiancha().getId()+"';</script>");
			return ;
		}
		
	
		
		
		
		Record2 bean = record2Dao.selectBean(" where deletestatus=0 and  yuyue2.id="+yuyue2.getId()+" and user.id="+uu.getId()+"  ");
		if(bean!=null){
			writer
			.print("<script language=javascript>alert('预约失败，您已经在该日期预约过该医生，不可重复预约');window.location.href='indexmethod!yuyue2?jid="+yuyue2.getJiancha().getId()+"';</script>");
			return ;
		}
		
		bean = new Record2();
		bean.setUser(uu);
		bean.setYuyue2(yuyue2);
		bean.setYtime(Util.getTime());
		bean.setRiqi(yuyue2.getRiqi());
		bean.setStatus("预约成功");
		record2Dao.insertBean(bean);
		
		yuyue2.setShiji(yuyue2.getShiji()+1);
		
		yuyue2Dao.updateBean(yuyue2);
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!record2list';</script>");
		
		

	}
	
	
	
	//我的检查列表
	public String record2list() throws Exception {

		HttpServletRequest request = this.getRequest();
		
		
		
		String jname = request.getParameter("jname");
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(" where ");

		
		
		if (jname != null && !"".equals(jname)) {

			sb.append(" yuyue2.jiancha.jname like '%" + jname + "%'");
			sb.append(" and ");
			request.setAttribute("jname", jname);
		}
		
		HttpSession session = request.getSession();
		User uu = (User)session.getAttribute("qiantai");
		
		sb.append("  deletestatus=0  and user.id="+uu.getId()+" order by id desc ");
	
		
		String where = sb.toString();


		int currentpage = 1;
		int pagesize = 15;
		if (request.getParameter("pagenum") != null) {
			currentpage = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		int total = record2Dao.selectBeanCount(where.replaceAll("order by id desc", ""));
		
		request.setAttribute("record2list", record2Dao.selectBeanList((currentpage - 1)* pagesize, pagesize, where));
		
		request.setAttribute("pagerinfo", Pager.getPagerNormal(total, pagesize,currentpage, "indexmethod!record2list", "共有" + total + "条记录"));

		request.setAttribute("riqi", Integer.parseInt(Util.getRiqi2()));
		
		request.setAttribute("url", "indexmethod!record2list");
		request.setAttribute("title", "我的检查");
		this.setUrl("record2list.jsp");
		return SUCCESS;
	}
	
	
	
	//取消预定操作
	public void record2delete() throws IOException {
		HttpServletRequest request = this.getRequest();

		PrintWriter writer = this.getPrintWriter();
		
		
	

		Record2 bean = record2Dao.selectBean(" where id= "+request.getParameter("id"));
		
		bean.setStatus("已取消");
		
		record2Dao.updateBean(bean);
		
		
		Yuyue2 yuyue2 = yuyue2Dao.selectBean(" where id= "+bean.getYuyue2().getId());
		
		yuyue2.setShiji(yuyue2.getShiji()-1);
		
		yuyue2Dao.updateBean(yuyue2);
		
		
		writer.print("<script language=javascript>alert('操作成功');window.location.href='indexmethod!record2list';</script>");
		
		

	}
	
}
