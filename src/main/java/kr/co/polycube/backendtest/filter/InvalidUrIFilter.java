package kr.co.polycube.backendtest.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.polycube.backendtest.error.exception.CustomException;
import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.regex.Pattern;

import static io.micrometer.common.util.StringUtils.isBlank;

@Slf4j
public class InvalidUrIFilter implements Filter {
    private static final String INVALID_URL_REGEX = "[^a-zA-Z0-9가-힣ㄱ-ㅎㅏ-ㅣ?&=:/]";
    private static final Pattern INVALID_URL_PATTERN = Pattern.compile(INVALID_URL_REGEX);

    /**
     *  요청 URI에 [?, &, =, :, //]를 제외한 특수문제가 존재할때 접속을 차단시키는 filter
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String fullUrl = getFullUrl(httpRequest.getRequestURL().toString(), httpRequest.getQueryString());
        checkUri(fullUrl);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void checkUri(String url) {
        if (INVALID_URL_PATTERN.matcher(url).find()) {
            log.debug("invalid URL: {}", url);

            throw new CustomException(DefaultErrorMessage.INVALID_URL);
        }
    }

    private String getFullUrl(String url, String queryString) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);

        if (!isBlank(queryString)) {
            builder
                    .append("?")
                    .append(queryString);
        }

        return builder.toString();
    }
}
