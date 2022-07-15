package com.tang.blog.service;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class aop {

    @Pointcut("execution( * com.tang.blog.service.Student.getAge(..))")
    private void selectGetAge(){}

    @Around("selectGetAge()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        System.out.println("Around advice");
        Object[] args=proceedingJoinPoint.getArgs();
        if(args.length>0){
            System.out.print("Arguments passed: " );
            for (int i = 0; i < args.length; i++) {
                System.out.print("arg "+(i+1)+": "+args[i]);
            }
        }
        Object result=proceedingJoinPoint.proceed(args);
        System.out.println("Returning " + result);
        return result;
    }
}
