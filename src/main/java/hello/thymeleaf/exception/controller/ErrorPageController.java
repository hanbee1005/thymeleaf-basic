package hello.thymeleaf.exception.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {
    // RequestDispatcher 상수로 정의되어 있음
    private static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    private static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    private static final String ERROR_MESSAGE = "javax.servlet.error.message";
    private static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    private static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    private static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String error404(HttpServletRequest request, HttpServletResponse response) {
        log.error("errorPage 404");
        printErrorInfo(request);
        return "exception/error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String error500(HttpServletRequest request, HttpServletResponse response) {
        log.error("errorPage 500");
        printErrorInfo(request);
        return "exception/error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));

        log.info("dispatcherType: {}", request.getDispatcherType());
    }
}
