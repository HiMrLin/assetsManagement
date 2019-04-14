package com.fjnu.assetsManagement.enums;

public enum ReasonOfFailure implements IReasonOfFailure{
	FUNCTION_NO_ARE_INCORRECT("functionNoAreIncorrect","功能号不正确！"),
	USER_DOES_NOT_EXIST("UserDoesNotExist","用户不存在"),
	ILLEGAL_ATTACK_DETECTED_RECORDED("Illegal attack detected, recorded","检测到非法攻击，已记录"),
	USER_DOES_NOT_EXIST_OR_PASSWORD_IS_INCORRECT("UserDoesNotExistOrPasswordIsIncorrect","用户不存在或密码错误"),
	FAILED_TO_GET_VERIFICATION_CODE("FailedToGetVerificationCode","获取验证码失败！"),
	LOGIN_ERROR("LoginError", "您暂无该操作权限，请联系管理员！"),
	DATA_NOT_MATCH("DataNotMatch", "数据与接口需求不匹配"),
	OPERATION_FAILED("operation failed", "操作失败！"),
	FAILED_TO_GET_LOGIN_VERIFICATION_CODE("FailedToGetLoginVerificationCode","获取登陆验证码失败！"),
	PASSWORD_OR_VERIFICATION_CODE_ERROR("PASSWORD_ERROR","密码或验证码错误！"),//此处添加枚举值
    ADD_ERROR("add error", "新增失败"),
    DEL_ERROR("delete error", "删除失败"),
    UPDATE_ERROR("update error", "更新失败"),
	SEARCH_ERROR("search error", "查询失败");


	private String en_msg;
	private String zh_msg;

	ReasonOfFailure(String en_msg, String zh_msg) {
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
