/**  
 * @Title: ExceptionUtil.java  
 * @Package cn.edu.fjnu.towide.utils
 * @author CaoZhengxi  
 * @date 2018年7月24日  
 */  
package com.fjnu.assetsManagement.util;

import com.fjnu.assetsManagement.entity.ResponseData;
import com.fjnu.assetsManagement.enums.IReasonOfFailure;
import com.fjnu.assetsManagement.exception.RequestFailureException;
import org.springframework.stereotype.Component;

/**  
 * @ClassName: ExceptionUtil    抛出异常
 * @author lwn
 * @date
 *    
 */
@Component
public class ExceptionUtil {

	/**  
	 * 抛出自定义异常原因
	 */  
	public  static RequestFailureException setFailureMsgAndThrow(ResponseData responseData, IReasonOfFailure reasonOfFailure) {
		ResponseDataUtil.setResponseDataWithFailureInfo(responseData, reasonOfFailure);
		RequestFailureException requestFailureException=new RequestFailureException();
		requestFailureException.setResponseData(responseData);
		throw requestFailureException;
	}

}
