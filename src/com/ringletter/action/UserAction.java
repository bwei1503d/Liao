package com.ringletter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ringletter.bean.Album;
import com.ringletter.bean.Chat;
import com.ringletter.bean.Relationship;
import com.ringletter.bean.User;
import com.ringletter.cipher.CipherUtils;
import com.ringletter.cipher.JNCryptorUtils;
import com.ringletter.cipher.SecurityUtils;
import com.ringletter.service.UserService;
import com.ringletter.utils.StringUtils;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private User user;
	private Album album;
	private Chat chat;
	private Relationship relationship;
	@Autowired
	private UserService userService;

	public static final String IMAGE_HEADER = "http://qhb.2dyt.com";

	/**
	 * 参数不对
	 * */
	public static int PRARMS_ERROR = 300;
	
	
	

	/**
	 * 用户未登录
	 * */
	public static int UNLOGIN_ERROR = 301;

	/**
	 * 服务端异常
	 * */
	public static int SERVER_ERROR = 302;
	/**
	 * 失败 登录失败 注册失败 上传失败 手机号重复 添加好友失败 没有聊天记录 没有好友记录 没有相册记录
	 * */
	public static int ALL_ERROR = 303;
	
	/**
	 * sign error 
	 */
	public static int SIGN_ERROR = 304;
	
	public static String SIGN_ERROR_INFOR = "验签失败" ;
	/**
	 * 成功
	 * */
	public static int SUCCESS = 200;

	/**
	 * 参数不全
	 */
	public void paramsError(JSONObject jsonObject) {
		try {

			jsonObject.put("result_code", PRARMS_ERROR);
			jsonObject.put("result_message", "参数不正确");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 登录
	public void login() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			if (user != null && !StringUtils.isEmpty(user.getPhone())
					&& !StringUtils.isEmpty(user.getPassword()) 
					&& !StringUtils.isEmpty(user.getSign())
					&& !StringUtils.isEmpty(user.getSecretkey())) {
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("user.phone", user.getPhone());
				params.put("user.password", user.getPassword());
				params.put("user.secretkey", user.getSecretkey());
				if(user.getLat() != 0.0 && user.getLng() != 0.0){
					params.put("user.lat", user.getLat()+"");
					params.put("user.lng", user.getLng()+"");
				}
				
				if(!CipherUtils.vaildSign(params, user.getSign())){
					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message",
							URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
					writer.println(jsonObject.toString());
					return ;
				}
				
				String secretkey = user.getSecretkey() ;
				String rsaDecode =  SecurityUtils.decrypt(secretkey);
				String phone = JNCryptorUtils.getInstance().decryptData(user.getPhone(), rsaDecode);
				user.setPhone(phone);
				
				// 解密用户的手机号码 手机号码在传输中 隐私数据
//				JNCryptorUtils.getInstance().decryptData("LcmJxmzKMlmp0GfahT3+jQ==", "837eCi8010n54Pqc");

				

				User loginUser = userService.login(user);

				if (loginUser != null) {

					ServletActionContext.getContext().getSession()
							.put("loginUser", loginUser);
					jsonObject.put("result_code", SUCCESS);
					jsonObject.put("result_message", "success");
					long laftTime = System.currentTimeMillis();
					long createTime = loginUser.getCreatetime();
					jsonObject2.put("userId", loginUser.getUserId());
					jsonObject2.put("nickname", loginUser.getNickname());
					jsonObject2.put("password", loginUser.getPassword());
					jsonObject2.put("gender", loginUser.getGender());
					jsonObject2.put("area", loginUser.getArea());
					jsonObject2.put("phone", loginUser.getPhone());
					jsonObject2.put("introduce", loginUser.getIntroduce());
					jsonObject2.put("imagePath", loginUser.getImagePath());
					jsonObject2.put("lat", loginUser.getLat());
					jsonObject2.put("lng", loginUser.getLng());
					jsonObject2.put("lasttime", laftTime);
					jsonObject2.put("createtime", createTime);
					jsonObject.put("data", jsonObject2);
				} else {
					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message",
							URLDecoder.decode("登录失败,用户名密码错误", "utf-8"));
				}

			} else {

				jsonObject.put("result_code", PRARMS_ERROR);
				jsonObject.put("result_message",
						URLDecoder.decode("参数不正确!", "utf-8"));
			}

			writer.println(jsonObject.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			// writer.flush();
			// writer.close();
		}

	}

	// 注册
	public void add() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		JSONObject jo = new JSONObject();
		JSONObject jo1 = new JSONObject();
		try {
			
			
			if (user != null && !StringUtils.isEmpty(user.getPhone())
					&& !StringUtils.isEmpty(user.getPassword())
					&& !StringUtils.isEmpty(user.getNickname())
					&& !StringUtils.isEmpty(user.getGender())
					&& !StringUtils.isEmpty(user.getArea())
					&& !StringUtils.isEmpty(user.getAge())
					&& !StringUtils.isEmpty(user.getIntroduce())) {
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("user.phone", user.getPhone());
				params.put("user.password", user.getPassword());
				params.put("user.nickname", user.getNickname());
				params.put("user.gender", user.getGender());
				params.put("user.age", user.getAge());
				params.put("user.area", user.getArea());
				if(user.getLat() != 0.0 && user.getLng() != 0.0){
					params.put("user.lat", user.getLat()+"");
					params.put("user.lng", user.getLng()+"");
				}
				params.put("user.introduce", user.getIntroduce());
				
				
				if(!CipherUtils.vaildSign(params, user.getSign())){
					jo.put("result_code", ALL_ERROR);
					jo.put("result_message",
							URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
					out.println(jo.toString());

					return ;
				}
				

				Boolean result = userService.selectByPhone(this.user);

				if (result == true) {

					long d = System.currentTimeMillis();
					user.setCreatetime(d);
					user.setLasttime(d);
					user.setYxpassword(StringUtils.getStringRandom(8));

					if(user.getGender().equals("男")){
						user.setImagePath("http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg");
						user.setPicWidth(510);
						user.setPicHeight(507);
					}else{
						user.setPicWidth(700);
						user.setPicHeight(742);
						user.setImagePath("http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_woman.jpg");
					}
					boolean isreg = userService.register(user);
					if (isreg) {
						ActionContext.getContext().getSession()
								.put("registerUser", user);

						ServletActionContext.getContext().getSession()
								.put("loginUser", user);

						jo.put("result_code", SUCCESS);
						jo.put("result_message", "success");
						jo1.put("userId", user.getUserId());
						jo1.put("area", user.getArea());
						jo1.put("gender", user.getGender());
						jo1.put("introduce", user.getIntroduce());
						jo1.put("nickname", user.getNickname());
						jo1.put("password", user.getPassword());
						jo1.put("phone", user.getPhone());
						jo1.put("createtime", d);
						jo1.put("yxpassword", user.getYxpassword());
						jo.put("data", jo1);
					} else {
						jo.put("result_code", SERVER_ERROR);
						jo.put("result_message", "注册失败");
					}

				} else {
					jo.put("result_code", ALL_ERROR);
					jo.put("result_message", "手机号已存在");
				}
			} else {
				jo.put("result_code", PRARMS_ERROR);
				jo.put("result_message", "参数不正确");
			}
			out.println(jo.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			// out.flush();
			// out.close();
		}

	}

	// 上传图片 形象照
	public void uploadImage() throws Exception {

		System.out.println("uploadImage");
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		if (loginUser != null) {
			if (user.getFile() != null && user.getFile().isFile()
					&& !StringUtils.isEmpty(user.getCurrenttimer())
					&& !StringUtils.isEmpty(user.getSign())
					&& user.getPicWidth() != 0 && user.getPicHeight() != 0  ) {
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("user.currenttimer", user.getCurrenttimer());
				params.put("user.picWidth", user.getPicWidth()+"");
				params.put("user.picHeight", user.getPicHeight()+"");
				
				if(!CipherUtils.vaildSign(params, user.getSign())){
					jo.put("result_code",SIGN_ERROR);
					jo.put("result_message",
							URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
					out.println(jo.toString());

					return ;
				}
				
				// 删掉原图片
				try {
					String ouldpath = loginUser.getImagePath();
					if (!StringUtils.isEmpty(ouldpath)) {
						File f = new File(ouldpath);
						if(f.exists()){
							f.delete();
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				String realPath = ServletActionContext.getServletContext()
						.getRealPath("/images");
				System.out.println(realPath);
				String newFileName = UUID.randomUUID().toString()
						+ user.getFileFileName().substring(
								user.getFileFileName().lastIndexOf("."));
				InputStream is = null;
				OutputStream os = null;
				try {
					is = new FileInputStream(user.getFile());
					os = new FileOutputStream(realPath + "/" + newFileName);
					int i = is.read();
					while (i != -1) {
						os.write(i);
						i = is.read();
					}
					String filename = IMAGE_HEADER + File.separator
							+ "MyInterface" + File.separator + "images"
							+ File.separator + newFileName;

					loginUser.setImagePath(filename);
					loginUser.setPicWidth(user.getPicWidth());
					loginUser.setPicHeight(user.getPicHeight());

					userService.uploadHeadPortrait(loginUser);

					jo.put("headImagePath", filename);

					jo.put("result_code", SUCCESS);
					jo.put("result_message", "上传成功");

				} catch (Exception e) {
					e.printStackTrace();
					jo.put("result_code", SERVER_ERROR);
					jo.put("result_message", "上传失败");
				} finally {
					is.close();
					os.close();
				}

			} else {
				jo.put("result_code", PRARMS_ERROR);
				jo.put("result_message", "参数不正确");
			}
		} else {
			jo.put("result_code", UNLOGIN_ERROR);
			jo.put("result_message", "您还没有登录");
		}

		out.println(jo.toString());
	}

	// 修改密码
	public void updatePassword() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();

		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");

		if (loginUser != null) {

			String newPassword = request.getParameter("newPassword");
			if (!StringUtils.isEmpty(newPassword)) {
				User user2 = userService.updatePassword(newPassword);
				if (user2 != null) {

					jsonObject.put("result_code", SUCCESS);
					jsonObject.put("result_message", "success");

					long laftTime = System.currentTimeMillis();
					long createTime = user2.getCreatetime();
					jsonObject2.put("userId", user2.getUserId());
					jsonObject2.put("nickname", user2.getNickname());
					jsonObject2.put("password", user2.getPassword());
					jsonObject2.put("gender", user2.getGender());
					jsonObject2.put("area", user2.getArea());
					jsonObject2.put("phone", user2.getPhone());
					jsonObject2.put("introduce", user2.getIntroduce());
					jsonObject2.put("imagePath", user2.getImagePath());
					jsonObject2.put("lasttime", laftTime);
					jsonObject2.put("createtime", createTime);
					jsonObject.put("data", jsonObject2);

					ServletActionContext.getContext().getSession().clear();
				} else {
					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message", "新密码与旧密码相同!");
				}
			} else {
				jsonObject.put("result_code", PRARMS_ERROR);
				jsonObject.put("result_message", "参数错误");
			}
		} else {
			jsonObject.put("result_code", UNLOGIN_ERROR);
			jsonObject.put("result_message", "您还没有登录");
		}
		writer.println(jsonObject.toString());
		writer.flush();

	}

	// 修改基本信息
	public void updateUser() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		try {
			if (loginUser != null) {

				if (user != null && !StringUtils.isEmpty(user.getPhone())
						&& !StringUtils.isEmpty(user.getPassword())
						&& !StringUtils.isEmpty(user.getUserId().toString())) {

					User user2 = userService.updateUser(user);
					
					if (user2 != null) {
						ServletActionContext.getContext().getSession()
								.put("loginUser", user2);

						jsonObject.put("result_code", SUCCESS);
						jsonObject.put("result_message", "success");
						long laftTime = System.currentTimeMillis();
						long createTime = user2.getCreatetime();

						jsonObject2.put("userId", user2.getUserId());
						jsonObject2.put("nickname", user2.getNickname());
						jsonObject2.put("password", user2.getPassword());
						jsonObject2.put("gender", user2.getGender());
						jsonObject2.put("area", user2.getArea());
						jsonObject2.put("phone", user2.getPhone());
						jsonObject2.put("introduce", user2.getIntroduce());
						jsonObject2.put("imagePath", user2.getImagePath());
						jsonObject2.put("lasttime", laftTime);
						jsonObject2.put("createtime", createTime);
						jsonObject.put("data", jsonObject2);
					} else {
						jsonObject.put("result_code", ALL_ERROR);
						jsonObject.put("result_message", "修改失败");
					}
				} else {
					jsonObject.put("result_code", PRARMS_ERROR);
					jsonObject.put("result_message", "参数错误");
				}
			} else {
				jsonObject.put("result_code", UNLOGIN_ERROR);
				jsonObject.put("result_message", "用户未登录");
			}
		} catch (JSONException e) {
			e.printStackTrace();
			jsonObject.put("result_code", SERVER_ERROR);
			jsonObject.put("result_message", "修改失败");
		}
		writer.println(jsonObject.toString());
		writer.flush();
	}

//	
//	// 修改基本信息中的修改头像
//	public void updateuploadImage() throws Exception {
//
//		HttpServletResponse response = ServletActionContext.getResponse();
//		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		JSONObject jo = new JSONObject();
//		User loginUser = (User) ServletActionContext.getContext().getSession()
//				.get("loginUser");
//		if (loginUser != null) {
//
//			if (user != null && user.getFile().isFile()
//					&& user.getFile() != null) {
//
//				// 删掉原图片
//				String ouldpath = loginUser.getImagePath();
//				if (!StringUtils.isEmpty(ouldpath)) {
//					File f = new File(ouldpath);
//					f.delete();
//				}
//
//				String realPath = ServletActionContext.getServletContext()
//						.getRealPath("/images");
////				System.out.println(realPath);
////				System.out.println(user.getFileFileName());
//
//				String newFileName = UUID.randomUUID().toString()
//						+ user.getFileFileName().substring(
//								user.getFileFileName().lastIndexOf("."));
//
//				InputStream is = null;
//				OutputStream os = null;
//				try {
//					is = new FileInputStream(user.getFile());
//					os = new FileOutputStream(realPath + "/" + newFileName);
//					int i = is.read();
//					while (i != -1) {
//						os.write(i);
//						i = is.read();
//					}
//					String filename = IMAGE_HEADER + File.separator
//							+ "MyInterface" + File.separator + "images"
//							+ File.separator + newFileName;
//					loginUser.setImagePath(filename);
//					userService.uploadHeadPortrait(loginUser);
//
//					jo.put("headImagePath", filename);
//					jo.put("result_code", SUCCESS);
//					jo.put("result_message", "修改成功");
//				} catch (Exception e) {
//					e.printStackTrace();
//					jo.put("result_code", SERVER_ERROR);
//					jo.put("result_message", "修改失败");
//				} finally {
//					is.close();
//					os.close();
//				}
//			} else {
//				jo.put("result_code", PRARMS_ERROR);
//				jo.put("result_message", "参数异常");
//			}
//		} else {
//			jo.put("result_code", UNLOGIN_ERROR);
//			jo.put("result_message", "用户未登录");
//		}
//		out.println(jo.toString());
//		out.flush();
//		// out.close();
//	}

	// 将照片上传到相册中去
	public void uploadPhotoAlbum() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");

		if (loginUser != null) {

			if (user!= null && user.getFile() != null && user.getFile().isFile()
					&& !StringUtils.isEmpty(user.getCurrenttimer())
					&& !StringUtils.isEmpty(user.getSign())
					&& user.getPicWidth() != 0 && user.getPicHeight() != 0 ) {
				
				
				Map<String,String> params = new HashMap<String, String>();
				params.put("user.currenttimer", user.getCurrenttimer());
				params.put("user.picWidth", user.getPicWidth()+"");
				params.put("user.picHeight", user.getPicHeight()+"");
				
				if(!CipherUtils.vaildSign(params, user.getSign())){
					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message",
							URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
					writer.println(jsonObject.toString());
					return ;
				}

				String realPath = ServletActionContext.getServletContext()
						.getRealPath("/images");

				String newFileName = UUID.randomUUID().toString()
						+ user.getFileFileName().substring(
								user.getFileFileName().lastIndexOf("."));
				InputStream is = null;
				OutputStream os = null;
				try {
					is = new FileInputStream(user.getFile());
					os = new FileOutputStream(realPath + "/" + newFileName);
					int i = is.read();
					while (i != -1) {
						os.write(i);
						i = is.read();
					}
					String filename = IMAGE_HEADER + File.separator
							+ "MyInterface" + File.separator + "images"
							+ File.separator + newFileName;

					// 为相册赋值
					if(album == null){
						album = new Album();
					}
					album.setUserId(loginUser.getUserId());
					album.setImagePath(filename);
					album.setTimer(System.currentTimeMillis());
					
					album.setPicWidth(user.getPicWidth());
					album.setPicHeight(user.getPicHeight());

					userService.uploadImageToAlbum(album);
					jsonObject.put("headImagePath", filename);
					jsonObject.put("result_code", SUCCESS);
					jsonObject.put("result_message", "上传成功");
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put("result_code", SERVER_ERROR);
					jsonObject.put("result_message", "上传失败");
				} finally {
					is.close();
					os.close();
				}
			} else {
				jsonObject.put("result_code", PRARMS_ERROR);
				jsonObject.put("result_message", "参数异常");
			}

		} else {
			jsonObject.put("result_code", UNLOGIN_ERROR);
			jsonObject.put("result_message", "用户未登录");
		}
		writer.println(jsonObject);
	}

	// 查询出所有注册过的用户 并分页
	public void selectAllUser() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();

//		User loginUser = (User) ServletActionContext.getContext().getSession()
//				.get("loginUser");

		String pageIndex = ServletActionContext.getRequest().getParameter(
				"pageIndex");
		String pageSize = ServletActionContext.getRequest().getParameter(
				"pageSize");
		String sign = ServletActionContext.getRequest().getParameter(
				"user.sign");
		String currtime = ServletActionContext.getRequest().getParameter("user.currenttimer");
		JSONObject jo = new JSONObject();

		Integer pageSum = 0;
		if (StringUtils.isCurrtime(currtime)&& ! StringUtils.isEmpty(currtime) && StringUtils.isDigit(pageIndex) && StringUtils.isDigit(pageSize) && !StringUtils.isEmpty(pageIndex) && !StringUtils.isEmpty(pageSize)&& !StringUtils.isEmpty(sign)) {
			
			
			
			Map<String,String> params = new HashMap<String, String>();
			params.put("pageIndex", pageIndex);
			params.put("pageSize", pageSize);
			params.put("user.currenttimer", currtime);
			if(!CipherUtils.vaildSign(params, sign)){
				jo.put("result_code", ALL_ERROR);
				jo.put("result_message",
						URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
				writer.println(jo.toString());
				return ;
			}
			
			List<User> allUsers = userService.selectAllUser(Integer.parseInt(pageIndex), Integer.parseInt(pageSize) , Long.parseLong(currtime));
			for(int i=0;i<allUsers.size();i++){
				allUsers.get(i).setPassword("");
			}
			pageSum = allUsers.size();
			Gson g = new Gson();
			String ujson = g.toJson(allUsers);
			JSONArray ja = new JSONArray(ujson);
			jo.put("data", ja);
			jo.put("pageCount", "共" + pageSum + "条记录");
			jo.put("result_code", SUCCESS);
			jo.put("result_message", "查询成功");
		} else {
			jo.put("result_code", PRARMS_ERROR);
			jo.put("result_message", "参数异常");
		}
		writer.println(jo.toString());

	}

	// 通过iD查询用户的详情信息
	// http://localhost:8080/MyoneInterface/userAction_selectUserById.action?user.userId=2
	public void selectUserById() throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");

		PrintWriter writer = response.getWriter();

		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();

		if (user!=null && user.getUserId() != null && user.getUserId() != 0
				&& !StringUtils.isEmpty(user.getSign()) ) {
			
			Map<String,String> params = new HashMap<String, String>();
			params.put("user.userId", user.getUserId()+"");
			
			
			if(!CipherUtils.vaildSign(params, user.getSign())){
				jsonObject.put("result_code", ALL_ERROR);
				jsonObject.put("result_message",
						URLDecoder.decode(SIGN_ERROR_INFOR, "utf-8"));
				writer.println(jsonObject.toString());
				return ;
			}
			
			User u = userService.selectUserById(user);

			jsonObject.put("result_code", SUCCESS);
			jsonObject.put("result_message", "success");

			long laftTime = System.currentTimeMillis();
			long createTime = u.getCreatetime();
			jsonObject2.put("userId", u.getUserId());
			jsonObject2.put("nickname", u.getNickname());
			jsonObject2.put("gender", loginUser.getGender());
			jsonObject2.put("area", loginUser.getArea());
			jsonObject2.put("introduce", loginUser.getIntroduce());
			jsonObject2.put("imagePath", loginUser.getImagePath());
			jsonObject2.put("lasttime", laftTime);
			jsonObject2.put("createtime", createTime);
			jsonObject2.put("photolist",u.getListAlbum());
			jsonObject.put("data", jsonObject2);
		} else {
			jsonObject.put("result_code", PRARMS_ERROR);
			jsonObject.put("result_message", "参数异常");
		}
		writer.println(jsonObject);
	}

	// 添加好友
	public void addFriends() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			ServletActionContext.getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			JSONObject jsonObject = new JSONObject();
			// loginUser由登陆成功以后的session中得到
			User loginUser = (User) ServletActionContext.getContext()
					.getSession().get("loginUser");

			if (loginUser != null) {
				if (relationship != null && relationship.getFriendId() != null
						&& !StringUtils.isEmpty(relationship.getGroupName())
						&& relationship.getFriendId() != 0) {

					relationship.setUserId(loginUser.getUserId());

					boolean checkAddUser = userService
							.checkAddUser(relationship);
					if (checkAddUser == false) {
						userService.addUserrelationship(relationship);
						jsonObject.put("result_code", SUCCESS);
						jsonObject.put("result_message", "添加好友成功");
					} else {
						jsonObject.put("result_code", ALL_ERROR);
						jsonObject.put("result_message", "添加好友失败,添加用户不存在");
					}

				} else {

					jsonObject.put("result_code", PRARMS_ERROR);
					jsonObject.put("result_message", "参数异常");

				}

			} else {
				jsonObject.put("result_code", UNLOGIN_ERROR);
				jsonObject.put("result_message", "用户未登录");
			}
			writer.println(jsonObject.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 发送信息
	public void chatMessage() {

		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			ServletActionContext.getRequest().setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");

			// loginUser由登陆成功以后的session中得到
			User loginUser = (User) ServletActionContext.getContext()
					.getSession().get("loginUser");

			PrintWriter writer = response.getWriter();
			JSONObject jsonObject = new JSONObject();

			if (loginUser != null) {
				if (chat != null && chat.getUserId() != null
						&& chat.getUserId() != 0 && chat.getTouserId() != null
						&& chat.getTouserId() != 0
						&& chat.getTouserId() != chat.getUserId()
						&& !StringUtils.isEmpty(chat.getMessage())) {
					long thisTime = System.currentTimeMillis();
					chat.setMessageTime(thisTime);
					String insertChat = userService.insertChat(chat);

					if (insertChat.contains("success")) {
						jsonObject.put("result_code", SUCCESS);
						jsonObject.put("result_message", "success");
					} else {
						jsonObject.put("result_code", SERVER_ERROR);
						jsonObject.put("result_message", "聊天失败");
					}
				} else {
					jsonObject.put("result_code", PRARMS_ERROR);
					jsonObject.put("result_message", "参数错误");

				}

			} else {
				jsonObject.put("result_code", UNLOGIN_ERROR);
				jsonObject.put("result_message", "用户未登录");
			}

			writer.println(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 查询出来用户的所有好友并分页
	// pageIndex 第几页起， pageSize 每页显示多少条
	public void selectAllUserAndFriend() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");

		PrintWriter writer = response.getWriter();
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");

		String pageIndex = ServletActionContext.getRequest().getParameter(
				"pageIndex");
		String pageSize = ServletActionContext.getRequest().getParameter(
				"pageSize");

		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		if (loginUser != null) {

			if (!StringUtils.isEmpty(pageIndex)
					&& !StringUtils.isEmpty(pageSize)) {

				List<User> selectAllUserAndFriend = userService
						.selectAllUserAndFriend(loginUser,
								Integer.parseInt(pageIndex),
								Integer.parseInt(pageSize));

				if (selectAllUserAndFriend != null
						&& selectAllUserAndFriend.size() > 0) {

					String json = gson.toJson(selectAllUserAndFriend);
					jsonObject.put("result_code", SUCCESS);
					jsonObject.put("result_message", "success");
					JSONArray jsonArray = new JSONArray(json);
					jsonObject.put("data", jsonArray);

				} else {

					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message", "没有好友");

				}
			} else {
				jsonObject.put("result_code", SERVER_ERROR);
				jsonObject.put("result_message", "参数异常");
			}

		} else {
			jsonObject.put("result_code", UNLOGIN_ERROR);
			jsonObject.put("result_message", "用户未登录");
		}
		writer.println(jsonObject.toString());
	}

	// 查看聊天记录 并分页
	public void selectChat() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String pageIndex = request.getParameter("pageIndex");
		String pageSize = request.getParameter("pageSize");

		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();

		if (loginUser != null) {
			if (!StringUtils.isEmpty(pageIndex)
					&& !StringUtils.isEmpty(pageSize)) {
				List<Chat> selectChatList = userService
						.selectChat(chat, Integer.parseInt(pageIndex),
								Integer.parseInt(pageSize));

				// JSONObject jsonObject2 = new JSONObject();
				Gson g = new Gson();
				String chatjson = g.toJson(selectChatList);
				JSONArray ja = new JSONArray(chatjson);
				if (selectChatList != null && selectChatList.size() > 0) {
					jsonObject.put("data", ja);
					jsonObject.put("result_code", SUCCESS);
					jsonObject.put("result_message", "success");
				} else {
					jsonObject.put("result_code", ALL_ERROR);
					jsonObject.put("result_message", "没有聊天记录");
					jsonObject.put("data", ja);
				}
			} else {
				jsonObject.put("result_code", PRARMS_ERROR);
				jsonObject.put("result_message", "参数异常");
			}

		} else {
			jsonObject.put("result_code", UNLOGIN_ERROR);
			jsonObject.put("result_message", "用户未登录");

		}
		writer.println(jsonObject.toString());

	}

	// 查询用户的相册信息
	// 必须登录
	public void selectUserAlbum() {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = response.getWriter();
			JSONObject jsonObject = new JSONObject();
			User loginUser = (User) ServletActionContext.getContext()
					.getSession().get("loginUser");

			if (loginUser != null) {

				if (album != null && album.getUserId() != 0
						&& album.getAlbumId() != null) {
					List<Album> selectUserAlbum = userService
							.selectUserAlbum(album);

					if (selectUserAlbum != null && selectUserAlbum.size() > 0) {
						Gson g = new Gson();
						String albumJson = g.toJson(selectUserAlbum);
						JSONArray jsonArray = new JSONArray(albumJson);
						jsonObject.put("result_code", SUCCESS);
						jsonObject.put("data", jsonArray);
						jsonObject.put("result_message", "success");
					} else {
						jsonObject.put("result_code", ALL_ERROR);
						jsonObject.put("result_message", "相册为空");
					}
				} else {
					jsonObject.put("result_code", PRARMS_ERROR);
					jsonObject.put("result_message", "参数异常");
				}

			} else {
				jsonObject.put("result_code", UNLOGIN_ERROR);
				jsonObject.put("result_message", "用户未登录");
			}
			writer.println(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void selectAlbum() throws Exception {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}

}
