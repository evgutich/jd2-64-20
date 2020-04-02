package by.academy.it.travelcompany.filter;

import by.academy.it.travelcompany.user.User;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*","/admin/*"}, dispatcherTypes = DispatcherType.REQUEST)
public class NoUserFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            super.doFilter(req, res, chain);
        }
    }
}