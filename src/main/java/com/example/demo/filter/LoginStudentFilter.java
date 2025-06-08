package com.example.demo.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/student/*")
public class LoginStudentFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理があればここに書く。
    }
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
		//キャストしてHttpServletRequest/HttpServletResponseを使えるようにする
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		//セッションを取得(なければnullになるように)
		HttpSession session = req.getSession(false);
		
		//スルーするURLを設定
		String servletPath = req.getServletPath();
		boolean isLoginPage = servletPath.equals("/student/login");
		boolean isFragment = 
				servletPath.startsWith("/student/fragments/");
		boolean isStaticResouce = servletPath.startsWith("/css/")
				|| servletPath.startsWith("/js/") || servletPath.startsWith("/images/");
		
		if (isLoginPage || isFragment || isStaticResouce) {
			chain.doFilter(request, response);
			return;
		}
		
		if (session == null || session.getAttribute("loginStudent") == null) {
			resp.sendRedirect(req.getContextPath() + "/");
			return;
		}
		
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 終了時のリソース解放など。なくてもOK
    }
}
