package com.tcb.model;

import com.tcb.util.DefaultParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 数据列表模板类
 * @Date: Create in 2018/1/25 13:35
 * @Modify by WangLei
 */
public class ResultListModel<T>{

	/**
	 * 返回标识
	 */
	private int code= DefaultParam.liSelectError;

	/**
	 * 返回结果条数
	 */
	private int count = 0;

	/**
	 * 返回信息
	 */
	private String msg = "";
	
	/**
	 * 返回数据
	 */
	private List<T> data = new ArrayList<T>();

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSelect() {
		return msg;
	}

	public void setSelect(String select) {
		this.msg = select;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
