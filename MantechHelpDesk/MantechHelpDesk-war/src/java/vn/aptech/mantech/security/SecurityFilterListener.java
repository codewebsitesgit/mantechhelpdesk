package vn.aptech.mantech.security;

import javax.servlet.*;
import java.io.IOException;
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import vn.aptech.mantech.entity.UserAccount;
import vn.aptech.mantech.utils.RolesUtils;

public class SecurityFilterListener implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        UserAccount user = (UserAccount) ((HttpServletRequest) servletRequest)
                .getSession().getAttribute("userSession");
        Subject subject = (Subject) session.getAttribute(RolesUtils.SUBJECT);
        if (subject == null) {
            subject = new Subject();
            session.setAttribute(RolesUtils.SUBJECT, subject);
        }

        final String page = getRequestPage(request.getRequestURI());
        if (user != null) {
            checkPermission(user, subject, page);
        } else {
            if (page != null && (!page.equals("index.xhtml")
                    && !page.equals("faqs.xhtml") 
                    && !page.equals("error.xhtml"))) {
                throw new ServletException("Security permission violated!");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getRequestPage(final String rawPage) {
        String page = "index.xhtml";
        if (rawPage.endsWith(".xhtml")) {
            String[] frags = rawPage.split("/");
            page = frags[frags.length - 1];
        }
        return page;
    }

    @Override
    public void destroy() {

    }

    private void checkPermission(UserAccount user,
            Subject subject, String page) throws ServletException {
        if (!hasPermission(user.getRoleID().getRoleName(), subject, page)) {
            throw new ServletException("Security permission violated!");
        }
    }

    private boolean hasPermission(String roleName, Subject subject, String page) {
        Iterator iter = subject.getPrincipals().iterator();
        while (iter.hasNext()) {
            SecurityRole p = (SecurityRole) iter.next();
            if (p.hasPermission(roleName, page)) {
                return true;
            }
        }
        return false;
    }

}
