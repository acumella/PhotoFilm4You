package managedbean;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();

			String[] routesAdminCustomer = {
				"listAllActiveRentsView"
			};
			String[] routesAdmin = { "registerAdministratorView", "listPendingPenalizationsView" };
			String[] routesCustomer = { "updateCustomerDataView", "createRentView"};
			for (int i = 0; i < routesAdminCustomer.length; i++) {
				if (reqURI.indexOf(routesAdminCustomer[i]) >= 0) {
					if (ses.getAttribute("role").equals("ROLE_ADMIN") || ses.getAttribute("role").equals("ROLE_CUSTOMER")) {
						chain.doFilter(request, response);
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/loginView.xhtml");
					}
					return;
				}
			}
			for (int i = 0; i < routesAdmin.length; i++) {
				if (reqURI.indexOf(routesAdmin[i]) >= 0) {
					if (ses.getAttribute("role").equals("ROLE_ADMIN")) {
						chain.doFilter(request, response);
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/loginView.xhtml");
					}
					return;
				}
			}
			for (int i = 0; i < routesCustomer.length; i++) {
				if (reqURI.indexOf(routesCustomer[i]) >= 0) {
					if (ses.getAttribute("role").equals("ROLE_CUSTOMER")) {
						chain.doFilter(request, response);
					} else {
						resp.sendRedirect(reqt.getContextPath() + "/loginView.xhtml");
					}
					return;
				}
			}
			chain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}
