package spring_boot_security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@Order(1)
public class FirstAspect {

    /*
        this - check AOP proxy class type
        target - check target object class type
     */
    @Pointcut("this(org.springframework.data.repository.Repository)")
//    @Pointcut("target(org.springframework.data.repository.Repository)")
    public void isRepository() {

    }

    /*
        @annotation - check annotation on method lavel
     */
    @Pointcut("spring_boot_security.aop.CommonPointcuts.isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {

    }

    /*
        args - check method param type
        * - any param type
        .. - 0+ any params type
     */
    @Pointcut("spring_boot_security.aop.CommonPointcuts.isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelParam() {

    }

    /*
        @args - check annotation on the param type
        * - any param type
        .. - 0+ any params type
     */
    @Pointcut("spring_boot_security.aop.CommonPointcuts.isControllerLayer() && @args(lombok.Data,..)")
    public void hasDataLombokParamAnnotation() {

    }

    /*
        bean - check bean name
     */
    @Pointcut("bean(*Service)")
    public void isServiceImplLayerBean() {

    }

    /*
        execution(modifiers-pattern? return-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
                 (модификатор доступа, возвращаемый тип, класс метода, название метода(параметр(ы)), какое исключение ожидаем)
        ? - optional param
     */
    @Pointcut("execution(public * spring_boot_security.service.*Service.findUserById(*))")
    public void anyFindByIdServiceMethod() {

    }

    @Before(value = "anyFindByIdServiceMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @annotation(transactional)",
            // @annotation(transactional) - если аннотация над методом @within(transactional) - если аннотация над классом
            argNames = "joinPoint,id,service,serviceProxy,transactional")
//    @Before("execution(public * spring_boot_security.service.*Service.findUserById(*))")
    public void addLogging(JoinPoint joinPoint, Object id, Object service, Object serviceProxy, Transactional transactional) {
        log.info("---log--- Before - Invoked findById method in class {}, with id {} ---log---", service, id);
    }

    @AfterReturning(value = "anyFindByIdServiceMethod() " +
            "&& target(service)",
            returning = "result",
            argNames = "service,result")
    public void addLoggingAfterReturning(Object service, Object result) {
        log.info("---log--- After Returning - Invoked findById method in class {}, result {} ---log---", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() " +
            "&& target(service)",
            throwing = "exception")
    public void addLoggingAfterThrowing(Object service, Throwable exception) {
        log.info("---log--- After Throwing - Invoked findById method in class {}, exception {}:{} ---log---", service, exception.getClass(), exception.getMessage());
    }

    @After(value = "anyFindByIdServiceMethod() " +
            "&& target(service)")
    public void addLoggingAfter(Object service) {
        log.info("---log--- After (finally) - Invoked findById method in class {} ---log---", service);
    }
}

