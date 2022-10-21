package spring_boot_security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(2)
public class SecondAspect {

    @Around(value = "spring_boot_security.aop.FirstAspect.anyFindByIdServiceMethod() " +
            "&& target(service) " +
            "&& args(id)",
            argNames = "joinPoint,service,id")
    public Object addLoggingAround(ProceedingJoinPoint joinPoint, Object service, Object id) throws Throwable {
        log.info("---log--- AROUND Before - Invoked findById method in class {}, with id {} ---log---", service, id);
        try {
            Object result = joinPoint.proceed();
            log.info("---log--- AROUND After Returning - Invoked findById method in class {}, result {} ---log---", service, result);
            return result;
        } catch (Throwable exception){
            log.info("---log--- AROUND After Throwing - Invoked findById method in class {}, exception {}:{} ---log---", service, exception.getClass(), exception.getMessage());
            throw exception;
        } finally {
            log.info("---log--- AROUND After (finally) - Invoked findById method in class {} ---log---", service);
        }
    }

}
