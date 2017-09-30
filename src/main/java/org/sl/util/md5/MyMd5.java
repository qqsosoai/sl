package org.sl.util.md5;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MD5加密 
 * @author hasee
 *
 */
public class MyMd5 {
	private static final String HEX_NUMS="0123456789ABCDEF";//判断十六进制字符串下标用
	private static final int SATL_LENGTH=6;//输出为44位16进制
	private static final String REP="^[0-9|A-F]{44}$";//判断字符串
	/**
	 * 将密码通过MD5加密并加入盐数据
	 * @param password 需要加密的密码
	 * @return 加密密码
	 */
	public static String toMd5String(String password){
		byte[] pwd = null;//存放加密密码数组
		byte[] satl = new byte[SATL_LENGTH];//创建盐变量
		SecureRandom random = new SecureRandom();
		random.nextBytes(satl);//获取随机数
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("md5");//创建摘要对象
			md.update(satl);
			md.update(password.getBytes("utf-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] temp=md.digest();
		pwd=new byte[temp.length+SATL_LENGTH];
		System.arraycopy(satl, 0, pwd, 0, SATL_LENGTH);
		System.arraycopy(temp, 0, pwd, SATL_LENGTH, temp.length);
		return toMd5String(pwd);
	}
	/**
	 * 检查密码是否符合加密密码
	 * @param userPassword 用户登录密码
	 * @param md5String 数据库加密密码
	 * @return 返回是否一致
	 */
	public static boolean isMd5String(String userPassword,String md5String){
		boolean flag=false;//判断输入字符串是否一致
		Pattern pattern=Pattern.compile(REP);
		Matcher matcher=pattern.matcher(md5String);
		if (matcher.matches()) {
			byte [] pwd=toBytes(md5String);
			byte[] satl=new byte[SATL_LENGTH];
			System.arraycopy(pwd, 0, satl, 0, SATL_LENGTH);
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("md5");
				md.update(satl);
				md.update(userPassword.getBytes("utf-8"));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			byte[] temp=md.digest();
			byte[] oldpwd=new byte[pwd.length-SATL_LENGTH];
			System.arraycopy(pwd, SATL_LENGTH, oldpwd, 0, oldpwd.length);
			if (Arrays.equals(temp, oldpwd)) {
				flag=true;
			}else {
				flag=false;
			}
		}else {
			throw new NoMyMd5Exception("加密口令不是MyMd5类生成的");
		}
		return flag;
	}
	/**
	 * 将byte数组转换为字符串
	 * @param pwd
	 * @return 加密字符串
	 */
	private static String toMd5String(byte[] pwd){
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < pwd.length; i++) {
			String temp=Integer.toHexString(pwd[i]&0xff);
			if (temp.length()==1) {
				temp="0"+temp;
			}
			sb.append(temp.toUpperCase());
		}
		return sb.toString();
	}
	/**
	 * 将加密字符串转换为byte数组
	 * @param pwd 加密字符串
	 * @return byte数组
	 */
	private static byte[] toBytes(String pwd){
		int len=pwd.length()/2;
		byte[] result=new byte[len];
		char[] pwdChar=pwd.toCharArray();
		for (int i = 0; i < len; i++) {
			int temp=i*2;
			result[i]=(byte)((HEX_NUMS.indexOf(pwdChar[temp])<<4)|(HEX_NUMS.indexOf(pwdChar[temp+1])));
		}
		return result;
	}
}
