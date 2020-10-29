package com.suddenly.util;

import org.apache.http.client.CookieStore;

/**
 * 用来接收交互返回的结果和附带的cookies
 * @author Lenovo
 *
 */
public class HttpResultWithCookies {

	private CookieStore cookies;
	private String result;
	/**
	 * @return the cookies
	 */
	public CookieStore getCookies() {
		return cookies;
	}
	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(CookieStore cookies) {
		this.cookies = cookies;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
