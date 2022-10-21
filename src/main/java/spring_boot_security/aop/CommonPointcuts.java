package spring_boot_security.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class CommonPointcuts {

    /*
        @within - check annotation on the class level
    */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {

    }

    /*
        within - check class type name
     */
    @Pointcut("within(spring_boot_security.service.*Service)")
    public void isServiceLayer() {

    }

}
