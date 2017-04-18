package com.kxt.goldtransaction.bean;

import java.io.Serializable;

/**
 * 首界面广告
 * 
 * @author PC
 *
 */
public class LoginBean implements Serializable {


	/**
	 * oper_flag : 1
	 * user_id : 1089117276
	 * password : 5ea9144fa6afff4d0559d2f4a6c10eda
	 * lan_ip : 112.123.21.21
	 * www_ip : 120.36.12.54
	 * session_key : 5342342423
	 */

	private int oper_flag;
	private String user_id;
	private String password;
	private String lan_ip;
	private String www_ip;
	private String session_key;
	private String  ExchCode;
	private String SerialNo;
	private String RspCode;
	private String RspMsg;
	private String UserID;
	private String rsp_encrypt_mode;

	public String getRsp_encrypt_mode() {
		return rsp_encrypt_mode;
	}

	public void setRsp_encrypt_mode(String rsp_encrypt_mode) {
		this.rsp_encrypt_mode = rsp_encrypt_mode;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getRspCode() {
		return RspCode;
	}

	public void setRspCode(String rspCode) {
		RspCode = rspCode;
	}

	public String getRspMsg() {
		return RspMsg;
	}

	public void setRspMsg(String rspMsg) {
		RspMsg = rspMsg;
	}

	public String getSerialNo() {
		return SerialNo;
	}

	public void setSerialNo(String serialNo) {
		SerialNo = serialNo;
	}

	public String getExchCode() {
		return ExchCode;
	}

	public void setExchCode(String exchCode) {
		ExchCode = exchCode;
	}

	public int getOper_flag() {
		return oper_flag;
	}

	public void setOper_flag(int oper_flag) {
		this.oper_flag = oper_flag;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLan_ip() {
		return lan_ip;
	}

	public void setLan_ip(String lan_ip) {
		this.lan_ip = lan_ip;
	}

	public String getWww_ip() {
		return www_ip;
	}

	public void setWww_ip(String www_ip) {
		this.www_ip = www_ip;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
}
