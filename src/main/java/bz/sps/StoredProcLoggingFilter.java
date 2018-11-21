package bz.sps;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

/*
 * фильтр, регистрируется самым верхним в StoredProcApplication
 * логирует тело ответа, которое накапливается в responseWrapper
 */
@Component
public class StoredProcLoggingFilter extends OncePerRequestFilter {

	private static final Logger logger = LogManager.getLogger();

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

		filterChain.doFilter(requestWrapper, responseWrapper);

		String servletPath = requestWrapper.getServletPath();
		if (servletPath.startsWith("/sps/")) {
			String requestQuery = requestWrapper.getQueryString();
			logger.info(servletPath + (requestQuery == null || requestQuery.isEmpty() ? "" : "?" + requestQuery));
			logger.info(new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8));
		}
		responseWrapper.copyBodyToResponse();
	}
}