package com.ringletter.utils;

import java.util.List;

public class Message {
	private String target_type = "users";
	private List<String> target;
	private Msg msg;
	private String from;

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public List<String> getTarget() {
		return target;
	}

	public void setTarget(List<String> target) {
		this.target = target;
	}

	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Message(String target_type, List<String> target, Msg msg, String from) {
		super();
		this.target_type = target_type;
		this.target = target;
		this.msg = msg;
		this.from = from;
	}

	public Message() {
		super();
	}

}

class Msg {
	private String type = "txt";
	private String msg;// ��Ϣ����

	public String getType() {
		return type;
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Msg(String type, String msg) {
		super();
		this.type = type;
		this.msg = msg;
	}

	public Msg() {
		super();
	}

}
