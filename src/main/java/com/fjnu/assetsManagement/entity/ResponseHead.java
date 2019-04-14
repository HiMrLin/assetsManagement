package com.fjnu.assetsManagement.entity;

import lombok.Data;

@Data
public class ResponseHead {
	private String code;
	private String zh_msg;
	private String en_msg;
	private String functionNo;
}
