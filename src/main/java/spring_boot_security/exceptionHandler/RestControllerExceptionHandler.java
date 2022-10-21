package spring_boot_security.exceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring_boot_security.controller.RestApiController;

@RestControllerAdvice(basePackageClasses = RestApiController.class)
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
