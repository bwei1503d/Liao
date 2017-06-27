package com.ringletter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
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
	

	//登录
	public void login() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObject2 = new JSONObject();
		try {
			// user.setLasttime(new Date());
			User loginUser = userService.login(user);
			if (loginUser != null) {
				ServletActionContext.getContext().getSession()
						.put("loginUser", loginUser);
				
				jsonObject.put("result_code", 200);
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
				jsonObject2.put("lasttime", laftTime);
				jsonObject2.put("createtime", createTime);
				jsonObject.put("data", jsonObject2);
			} else {
				jsonObject.put("result_code", 400);
				jsonObject.put("result_message", URLDecoder.decode("用户名或密码错误","utf-8"));
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
			if (user != null && user.getPhone() != null) {
				Boolean result = userService.selectByPhone(this.user);
				if (result == true) {
					long d = System.currentTimeMillis();
					user.setCreatetime(d);
					user.setLasttime(d);
					user.setYxpassword(StringUtils.getStringRandom(8));
					
					userService.register(user);
					ActionContext.getContext().getSession()
							.put("registerUser", user);
					jo.put("result_code", 200);
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
					jo.put("result_code", 400);
					jo.put("result_message", "手机号已存在");
				}
			} else {
				jo.put("result_code", 400);
				jo.put("result_message", "注册失败");
			}
			out.println(jo.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			// out.flush();
			// out.close();
		}

	}

	// 上传图片
	public void uploadImage() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		User registerUser = (User) ServletActionContext.getContext()
				.getSession().get("registerUser");
		if (user.getFile() != null && user.getFile().isFile()) {
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
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				is.close();
				os.close();
			}
			registerUser.setImagePath(realPath + "\\" + newFileName);
			userService.uploadHeadPortrait(registerUser);
			jo.put("result_code", 200);
			jo.put("headImagePath", realPath + "\\" + newFileName);
			jo.put("result_message", "上传成功");
		} else {
			jo.put("result_code", 500);
			jo.put("result_message", "您还没有登录");
		}
		out.println(jo.toString());
		// out.flush();
		// out.close();
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
			User user2 = userService.updatePassword(newPassword);
			if (user2 != null) {
				jsonObject.put("result_code", 200);
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
				
				writer.println(jsonObject.toString());
				writer.flush();
				ServletActionContext.getContext().getSession().clear();
			} else {
				jsonObject.put("result_code", 400);
				jsonObject.put("result_message", "新密码与旧密码相同!");
				writer.println(jsonObject.toString());
				writer.flush();
			}
		} else {
			jsonObject.put("result_code", 500);
			jsonObject.put("result_message", "您还没有登录");
			writer.println(jsonObject.toString());
			writer.flush();
		}

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
			if (loginUser != null && user != null
					&& user.getPhone().trim() != null) {
				User user2 = userService.updateUser(user);
				if (user2 != null) {
					ServletActionContext.getContext().getSession()
							.put("loginUser", user2);
					
					jsonObject.put("result_code", 200);
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
					writer.println(jsonObject.toString());
					writer.flush();
				}
			} else {
				jsonObject.put("result_code", 400);
				jsonObject.put("result_message", "修改失败");
				writer.println(jsonObject.toString());
				writer.flush();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 修改基本信息中的修改头像
	public void updateuploadImage() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject jo = new JSONObject();
		if (user != null && user.getFile() != null) {
			Map<String, Object> session = ServletActionContext.getContext()
					.getSession();
			User oulduser = (User) session.get("loginUser");
			// 删掉原图片
			String ouldpath = oulduser.getImagePath();
			File f = new File(ouldpath);
			f.delete();
			String realPath = ServletActionContext.getServletContext()
					.getRealPath("/images");
			System.out.println(realPath);
			System.out.println(user.getFileFileName());
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
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				is.close();
				os.close();
			}
			oulduser.setImagePath(realPath + newFileName);
			userService.uploadHeadPortrait(oulduser);
			jo.put("result_code", 200);
			jo.put("headImagePath", realPath + "\\" + newFileName);
			jo.put("result_message", "修改成功");
		} else {
			jo.put("result_code", 400);
			jo.put("result_message", "修改失败");
		}
		out.println(jo.toString());
		out.flush();
		// out.close();
	}

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
		if (loginUser != null && user.getFile() != null
				&& user.getFile().isFile()) {
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
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				is.close();
				os.close();
			}
			// 为相册赋值
			album.setUserId(loginUser.getUserId());
			album.setImagePath(realPath + "\\" + newFileName);
			userService.uploadImageToAlbum(album);
			jsonObject.put("result_code", 200);
			jsonObject.put("headImagePath", realPath + "\\" + newFileName);
			jsonObject.put("result_message", "上传成功");
		} else {
			jsonObject.put("result_code", 400);
			jsonObject.put("result_message", "上传失败");
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
		User loginUser = (User) ServletActionContext.getContext().getSession()
				.get("loginUser");
		String pageIndex = ServletActionContext.getRequest().getParameter(
				"pageIndex");
		String pageSize = ServletActionContext.getRequest().getParameter(
				"pageSize");
		JSONObject jo = new JSONObject();
		Integer pageSum = 0;
		if (loginUser != null && pageIndex != null && pageSize != null) {
			List<User> allUsers = userService.selectAllUser(
					Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
			pageSum = allUsers.size();
			Gson g = new Gson();
			String ujson = g.toJson(allUsers);
			JSONArray ja = new JSONArray(ujson);
			jo.put("data", ja);
			jo.put("pageCount", "共" + pageSum + "条记录");
			jo.put("result_code", 200);
		} else {
			jo.put("result_code", 400);
			jo.put("result_message", "查询失败");
		}
		writer.println(jo.toString());
	}

	// 通过iD查询出来 用户的详情信息
	// http://localhost:8080/MyoneInterface/userAction_selectUserById.action?user.userId=2
	public void selectUserById() throws Exception {
		ServletActionContext.getRequest().setCharacterEncoding("utf-8");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		User u = userService.selectUserById(user);
		Gson g = new Gson();
		String json = g.toJson(u);
		writer.println(json);
	}

	//添加好友
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
			if (loginUser != null && relationship != null
					&& relationship.getFriendId() != null
					&& relationship.getGroupName()!= null
					&& relationship.getFriendId() != 0) {
				
				relationship.setUserId(loginUser.getUserId());
				
				boolean checkAddUser = userService.checkAddUser(relationship);
				if (checkAddUser == false) {
					userService.addUserrelationship(relationship);
					jsonObject.put("result_code", 200);
					jsonObject.put("result_message", "添加好友成功!");
					writer.println(jsonObject.toString());
					writer.flush();
				} else {
					jsonObject.put("result_code", 400);
					jsonObject.put("result_message", "添加好友失败！");
					writer.println(jsonObject.toString());
					writer.flush();
				}
				
				
			} else {
				jsonObject.put("result_code", 400);
				jsonObject.put("result_message", "添加好友失败！");
				writer.println(jsonObject.toString());
				writer.flush();
			}

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
			PrintWriter writer = response.getWriter();
			
			
			long thisTime = System.currentTimeMillis();			
			chat.setMessageTime(thisTime);
			String insertChat = userService.insertChat(chat);
			JSONObject jsonObject = new JSONObject();
			if (insertChat.contains("success")) {
				jsonObject.put("result_code", 200);
				jsonObject.put("result_message", "success");
				writer.println(jsonObject.toString());
				writer.flush();
			} else {
				jsonObject.put("result_code", 400);
				jsonObject.put("result_message", "error");
				writer.println(jsonObject.toString());
				writer.flush();
			}
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
			List<User> selectAllUserAndFriend = userService
					.selectAllUserAndFriend(loginUser,
							Integer.parseInt(pageIndex),
							Integer.parseInt(pageSize));
			String json = gson.toJson(selectAllUserAndFriend);
			jsonObject.put("result_code", 200);
			jsonObject.put("result_message", "success");
			JSONArray jsonArray = new JSONArray(json);
			jsonObject.put("date", jsonArray);
			writer.println(jsonObject.toString());
			writer.flush();
		} else {
			jsonObject.put("result_code", 400);
			jsonObject.put("result_message", "error");
			writer.println(jsonObject.toString());
			writer.flush();
		}
	}

	// 查看聊天记录 并分页
	public void selectChat() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("utf-8");
		String pageIndex_ = request.getParameter("pageIndex");
		int pageIndex = 0;
		if (!"".equals(pageIndex_)) {
			pageIndex = Integer.parseInt(pageIndex_);
		}
		String pageSize_ = request.getParameter("pageSize");
		int pageSize = 0;
		if (!"".equals(pageSize_)) {
			pageSize = Integer.parseInt(pageSize_);
		}
		List<Chat> selectChatList = userService.selectChat(chat, pageIndex,
				pageSize);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JSONObject jsonObject = new JSONObject();
		// JSONObject jsonObject2 = new JSONObject();
		Gson g = new Gson();
		String chatjson = g.toJson(selectChatList);
		JSONArray ja = new JSONArray(chatjson);
		if (selectChatList != null && selectChatList.size() > 0) {
			jsonObject.put("data", ja);
			jsonObject.put("result_code", 200);
			jsonObject.put("result_message", "success");
			writer.println(jsonObject.toString());
		} else {
			jsonObject.put("result_code", 400);
			jsonObject.put("result_message", "没有聊天记录");
			jsonObject.put("data", ja);
			writer.println(jsonObject.toString());
		}
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
				List<Album> selectUserAlbum = userService
						.selectUserAlbum(album);
				if (selectUserAlbum != null && selectUserAlbum.size() > 0) {
					Gson g = new Gson();
					String albumJson = g.toJson(selectUserAlbum);
					JSONArray jsonArray = new JSONArray(albumJson);
					jsonObject.put("result_code", 200);
					jsonObject.put("data", jsonArray);
					jsonObject.put("result_message", "success");
					writer.println(jsonObject.toString());
				} else {
					jsonObject.put("result_code", 400);
					jsonObject.put("result_message", "相册为空");
					writer.println(jsonObject.toString());
				}
			} else {
				jsonObject.put("result_code", 500);
				jsonObject.put("result_message", "请先登录");
				writer.println(jsonObject.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void selectAlbum() throws Exception{
	
		
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
