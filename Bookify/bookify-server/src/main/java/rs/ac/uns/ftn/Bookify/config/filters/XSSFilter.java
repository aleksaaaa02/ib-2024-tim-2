package rs.ac.uns.ftn.Bookify.config.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import rs.ac.uns.ftn.Bookify.config.wrappers.request.XSSRequestWrapper;

import java.io.IOException;

public class XSSFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XSSRequestWrapper((HttpServletRequest)request), response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
