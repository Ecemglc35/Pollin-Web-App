package ca.sheridancollege.gulec.security;

import java.io.IOException;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoggingAccessDeniedHandler implements AccessDeniedHandler{

	@Override
	public void handle(jakarta.servlet.http.HttpServletRequest request,
	jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException)
	throws IOException, jakarta.servlet.ServletException {
		
		//If there is a bad login this class will log it and deny the access
		//to the secure page by retrieving the error page 
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

	if (auth != null) {
	System.out.println(auth.getName()
	+ " was trying to access protected resource: "
	+ request.getRequestURI());
	}

	response.sendRedirect(request.getContextPath() + "/permission-denied");

	}

}
