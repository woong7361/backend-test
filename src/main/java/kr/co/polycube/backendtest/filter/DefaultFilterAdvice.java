package kr.co.polycube.backendtest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.polycube.backendtest.error.dto.ErrorResponse;
import kr.co.polycube.backendtest.error.exception.CustomException;
import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class DefaultFilterAdvice extends OncePerRequestFilter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * filter에서 사용자 지정 exception이 발생했을때 그에 해당하는 response를 작성해준다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException exception) {
            setErrorResponse(response, exception.getErrorMessage());
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            DefaultErrorMessage errorMessage
    ) throws IOException {
        response.setStatus(errorMessage.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(ErrorResponse.from(errorMessage)));
    }
}
