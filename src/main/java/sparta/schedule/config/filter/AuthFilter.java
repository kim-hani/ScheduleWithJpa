package sparta.schedule.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 회원가입 및 로그인 요청은 필터에서 제외
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        // 게시물 조회는 로그인 없이 가능
        if (requestURI.startsWith("/schedules") && httpRequest.getMethod().equals("GET")) {
            chain.doFilter(request, response);
            return;
        }

        // 세션에서 사용자 ID 가져오기
        Object user = httpRequest.getSession().getAttribute("USER");

        if (user == null) {
            log.warn("Unauthorized access attempt to {}", requestURI);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요합니다.");
            return;
        }

        chain.doFilter(request, response);
    }
}
