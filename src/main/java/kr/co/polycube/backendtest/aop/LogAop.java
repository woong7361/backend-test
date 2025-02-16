package kr.co.polycube.backendtest.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogAop {
    public static final String USER_AGENT = "user-agent";

    /**
     *  해당하는 작업 전 요청자의 user-agent를 출력한다.
     */
    @Around("@within(UserAgentLog) || @annotation(UserAgentLog)")
    public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest httpRequest = getHttpServletRequestFromContextHolder();

        log.info("user-agent: {}", httpRequest.getHeader(USER_AGENT));

        return pjp.proceed();
    }

    private HttpServletRequest getHttpServletRequestFromContextHolder() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes();
        return requestAttributes.getRequest();
    }
}
