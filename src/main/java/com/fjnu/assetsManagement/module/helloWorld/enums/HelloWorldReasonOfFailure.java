package com.fjnu.assetsManagement.module.helloWorld.enums;

import com.fjnu.assetsManagement.enums.IReasonOfFailure;

public enum HelloWorldReasonOfFailure implements IReasonOfFailure {

	NAME_IS_NOT_BLANK("name is not blank", "一级部门不可移动");

	private String en_msg;
	private String zh_msg;

	HelloWorldReasonOfFailure(String en_msg, String zh_msg) {
		this.en_msg = en_msg;
		this.zh_msg = zh_msg;
	}

	@Override
	public String getZhMsgOfFailure() {
		return zh_msg;
	}

	@Override
	public String getEnMsgOfFailure() {
		return en_msg;
	}
	

}
