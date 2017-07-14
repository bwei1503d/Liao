package com.ringletter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Util {
	// 测试方法
	public static void main(String[] args) {
		/************* 测试得到token（该接口会限流0） *********/
		 getToken();

		/*********** 测试打印数组类型json（临时用） **********/
		// User u = new User();
		// u.setUsername("7777");
		// u.setPassword("787788");
		// List<User> list = new ArrayList<User>();
		// list.add(u);
		// Gson g = new Gson();
		// String json = g.toJson(list);
		// System.out.println(json);

		/*********** 测试发送消息 *************/
		// token = new Token();
		// token.setAccess_token("YWMtOAIJXkywEeewyscEHFOPYgAAAAAAAAAAAAAAAAAAAAHxU8ugTB4R57SHoQhT8SAoAgMAAAFcimWyMgBPGgAO2oOaoRf-hEPCguP5NrmiGQ3G8eokgqjOKxBG9MjOcg");
		// String fromUserName = "test1";
		// List toUserNames = new ArrayList<String>();
		// toUserNames.add("auto1");
		// sendText(fromUserName, toUserNames, "你好啊，今天天气不错");

		/********* 测试注册用户 */
		// token = new Token();
		// token.setAccess_token("YWMtOAIJXkywEeewyscEHFOPYgAAAAAAAAAAAAAAAAAAAAHxU8ugTB4R57SHoQhT8SAoAgMAAAFcimWyMgBPGgAO2oOaoRf-hEPCguP5NrmiGQ3G8eokgqjOKxBG9MjOcg");
		// addUser("auto5", "123456");

		// ********************* 测试修改密码 *//*
		// token = new Token();
		// token.setAccess_token("YWMtOAIJXkywEeewyscEHFOPYgAAAAAAAAAAAAAAAAAAAAHxU8ugTB4R57SHoQhT8SAoAgMAAAFcimWyMgBPGgAO2oOaoRf-hEPCguP5NrmiGQ3G8eokgqjOKxBG9MjOcg");
		// updatePass("test1", "123456");
	}

	private static Token token;

	// 获取token
	public static void getToken() {
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(Final.SURL);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty(Final.CTYPE_KEY, Final.CTYPE_VALUE);
//			httpConn.setRequestProperty(Final.ACCPET_NAME, Final.ACCPET_VALUE);
			httpConn.setRequestMethod(Final.POST);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			// 发送post请求参数
			out = new PrintWriter(httpConn.getOutputStream());
			out.println(Final.TJSON);
			out.flush();
			
			// 读取响应
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				System.out.println("接收到token的响应json" + content.toString());
				Gson g = new Gson();
				token = g.fromJson(content.toString(), Token.class);
				// 打印token
				System.out.println(token.getAccess_token());
			} else {
				System.out.println("请求出现了问题!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (out != null) {
				out.close();
			}
			httpConn.disconnect();
		}
	}

	// 发送消息 可以对多人发送
	public static String sendText(String fromUserName,
			List<String> toUserNames, String TextMassage) {
		// 获取第一步得到的token key;
		String tokenKey = "";
		if (token != null) {
			tokenKey = token.getAccess_token();
		} else {
			getToken();
			tokenKey = token.getAccess_token();
		}
		// 响应的json
		String a = "";
		// 调用发送消息的环信接口
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(Final.SENDURL);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty(Final.CTYPE_KEY, Final.CTYPE_VALUE);
//			httpConn.setRequestProperty(Final.ACCPET_NAME, Final.ACCPET_VALUE);
			httpConn.setRequestProperty(Final.AUTHER, Final.BEARER + " "
					+ tokenKey);
//			httpConn.setRequestProperty(Final.USER_AGENT, Final.USER_VALUE);
			httpConn.setRequestMethod(Final.POST);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);

			// 组装参数
			Message message = new Message();
			// 设置发送给谁,可以发送给多个用户(用户名)
			message.setTarget(toUserNames);
			// 设置是谁发的
			message.setFrom(fromUserName);
			// 设置消息体
			Msg m = new Msg();
			m.setMsg(TextMassage);
			// 得到消息的json
			message.setMsg(m);
			Gson g = new Gson();
			String Messagejson = g.toJson(message);
			System.out.println(Messagejson);
			byte[] b = Messagejson.getBytes("utf-8");
			// 发送参数 并编码
			out = new PrintWriter(httpConn.getOutputStream());
			out.print(new String(b));
			out.flush();
			// 读取响应
			System.out.println(httpConn.getResponseMessage());
			if ("400".equals("httpConn.getResponseCode()")) {
				System.out.println("调用发送消息接口失败：\n失败原因：massage结构错误");
				a = "error";
				getToken();
			}

			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				// 接受到返回的json 暂时未解析 ，只打印
				System.out.println("接收到token的响应json" + content.toString());
				a = content.toString();
			} else {
				getToken();
				System.out.println("请求出现了问题!");
				System.out.println("调用发送消息接口失败：");
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				a = content.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(in != null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.close();
			httpConn.disconnect();
		}
		return a;
	}

	/**
	 * 注册用户
	 * 
	 * @param userName
	 * @param userPass
	 */
	public static void addUser(String userName, String userPass) {
		// 获取第一步得到的token key;
		String tokenKey = "";
		if (token != null) {
			tokenKey = token.getAccess_token();
		} else {
			getToken();
			tokenKey = token.getAccess_token();
		}

		// 调用注册用户的环信接口
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(Final.ADDURL);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty(Final.CTYPE_KEY, Final.CTYPE_VALUE);
//			httpConn.setRequestProperty(Final.ACCPET_NAME, Final.ACCPET_VALUE);
			httpConn.setRequestProperty(Final.AUTHER, Final.BEARER + " "
					+ tokenKey);
//			httpConn.setRequestProperty(Final.USER_AGENT, Final.USER_VALUE);
			httpConn.setRequestMethod(Final.POST);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 设置user 信息
			User u = new User();
			u.setUsername(userName);
			u.setPassword(userPass);
			List<User> list = new ArrayList<User>();
			list.add(u);
			Gson g = new Gson();
			String json = g.toJson(list);
			System.out.println(json);
			byte[] b = json.getBytes("GBK");
			// 发送参数 并编码
			out = new PrintWriter(httpConn.getOutputStream());
			out.print(new String(b, "utf-8"));
			out.flush();
			// 读取响应
			System.out.println(httpConn.getResponseMessage());
			if ("400".equals("httpConn.getResponseCode()")) {
				System.out.println("调用注册用户接口失败：\n失败原因：massage结构错误");
				getToken();
				return;
			}
			if ("401".equals("httpConn.getResponseCode()")) {
				System.out
						.println("调用注册用户接口失败：\n失败原因：未授权[无token、token错误、token过期]");
				getToken();
				return;
			}
			System.out.println("code : " + httpConn.getResponseCode());
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				// 接受到返回的json
				String getJson = content.toString();
				System.out.println("接收到注册用户的响应json" + getJson);
				// 解析该 json
				Gson gson = new Gson();
				UserEnd userEnd = gson.fromJson(getJson, UserEnd.class);
				if (userEnd.getEntities().size() > 0) {
					System.out.println(userEnd.getEntities().get(0)
							.getUsername()
							+ "  用户注册成功，"
							+ "用户uuid "
							+ userEnd.getEntities().get(0).getUuid());
				} else {
					System.out.println("注册用户失败");
				}
			} else {
				getToken();
				System.out.println("注册用户请求出现了问题!");
			}
		} catch (IOException e) {
			// e.printStackTrace();
			System.out.println("注册用户参数异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (out != null) {
				out.close();
			}
			httpConn.disconnect();
		}
	}

	/**
	 * 修改用户的密码（在环信部分）
	 */
	public static void updatePass(String name, String newPassWord) {
		// 获取第一步得到的token key;
		String tokenKey = "";
		if (token != null) {
			tokenKey = token.getAccess_token();
		} else {
			getToken();
			tokenKey = token.getAccess_token();
		}

		// 修改用户密码（环信部分）
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(Final.UPDATEPASSURL1 + name
					+ Final.UPDATEPASSURL2);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestProperty(Final.CTYPE_KEY, Final.CTYPE_VALUE);
			httpConn.setRequestProperty(Final.ACCPET_NAME, Final.ACCPET_VALUE);
			httpConn.setRequestProperty(Final.AUTHER, Final.BEARER + " "
					+ tokenKey);
			httpConn.setRequestProperty(Final.USER_AGENT, Final.USER_VALUE);
			httpConn.setRequestMethod(Final.PUT);
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			// 得到json
			JSONObject j = new JSONObject();
			try {
				j.put("newpassword", newPassWord);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String json = j.toString();

			// 发送参数 并编码
			byte[] b = json.getBytes("GBK");
			out = new PrintWriter(httpConn.getOutputStream());
			out.print(new String(b, "utf-8"));
			out.flush();

			// 读取响应
			System.out.println(httpConn.getResponseMessage());
			if ("400".equals("httpConn.getResponseCode()")) {
				System.out.println("调用修改密码接口失败：\n失败原因：massage结构错误");
				getToken();
				return;
			}
			if ("401".equals("httpConn.getResponseCode()")) {
				System.out
						.println("调用修改密码口失败：\n失败原因：未授权[无token、token错误、token过期]");
				getToken();
				return;
			}
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				// 接受到返回的json
				System.out.println("接收到修改密码的响应json" + content.toString());
				// 解析该 json
				Gson g2 = new Gson();
				UserEnd userEnd = g2
						.fromJson(content.toString(), UserEnd.class);
			} else {
				getToken();
				System.out.println("修改用户密码出现了问题!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (out != null) {
				out.close();
			}
			httpConn.disconnect();
		}
	}
}
