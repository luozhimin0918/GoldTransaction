package com.kxt.goldtransaction.bean;

import com.kxt.goldtransaction.util.RSAUtils;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;

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
	private byte[]  EMIWen;
	private String jsonString;

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public byte[]  getEMIWen() {
		return EMIWen;
	}

	public void setEMIWen(byte[] EMIWen) {
		this.EMIWen = EMIWen;
	}

	public String getHeadA(String jsonStr,String miFlag){
		int l = this.toJSON(jsonStr,miFlag).length + 15;
		return String.format("%08d", l);
	}

	public byte[] toJSON(String jsonStr,String miFlag){
		try {
			jsonStr=jsonStr.replace("exchCode","ExchCode");
			jsonStr=jsonStr.replace("rspCode","RspCode");
			jsonStr=jsonStr.replace("serialNo","SerialNo");
			jsonStr=jsonStr.replace("rspMsg","RspMsg");
			jsonStr=jsonStr.replace("userID","UserID");
			this.jsonString=jsonStr;
			byte[] res=jsonStr.getBytes("GBK");

			switch (miFlag){
				case "1":
					// 从文件中得到公钥

					try {
						InputStream jjjj=this.getClass().getClassLoader().getResourceAsStream("assets/"+"rsa_pubic_C080.pem");
						PublicKey publicKey = RSAUtils.loadPublicKey(jjjj);
						res=RSAUtils.encryptData(res,publicKey);//分成100字节一个个加密
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "0":
					break;
			}
			this.EMIWen=res;

			return res;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

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
