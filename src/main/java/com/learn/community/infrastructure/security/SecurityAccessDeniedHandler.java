package com.learn.community.infrastructure.security;

import com.learn.community.infrastructure.config.IApplicationConfig;
import com.learn.community.infrastructure.response.ResponseCode;
import com.learn.community.infrastructure.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户访问没有权限资源的处理
 * @date 2019-7-12 15:02:00
 */

@Component("securityAccessDeniedHandler")
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		log.info(request.getRequestURL()+"没有权限");
		ResponseUtils.renderJson(request, response, ResponseCode.AUTH_LACK, applicationConfig.getOrigins());
	}
}
