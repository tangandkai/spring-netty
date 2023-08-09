package com.tang.boot.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 自定义缓存切面
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 18:40
 */
@Aspect
@Component(value = "cacheServiceAspect")
@Slf4j
public class CacheServiceAspect {

    //这里通过一个容器初始化监听器，根据外部配置的@EnableCaching注解控制缓存开关
    private boolean cacheEnable;
//
//    private CacheFactory cacheFactory;
//
//    @Autowired
//    public void setCacheFactory(CacheFactory cacheFactory) {
//        this.cacheFactory = cacheFactory;
//    }

    /**
     * 针对方法级别拦截
     * 拦截带有 @CacheService 注解的方法
     * @param joinPoint
     * @param enableCacheService
     * @return
     * @throws Throwable
     */
    @Around("@annotation(enableCacheService)")
    public Object dealCacheService(ProceedingJoinPoint joinPoint, CacheService enableCacheService) throws Throwable {
//        try {
//            //得到被代理的方法
//            final Method method = getMethod(joinPoint);
//            //得到被代理的方法上的注解
//            final CacheService cacheService = method.getAnnotation(CacheService.class);
//            //所有参数
//            final Object[] args = joinPoint.getArgs();
//            //如果没有开启缓存，直接调用处理方法返回
//            if (!cacheEnable){
//                return joinPoint.proceed(args);
//            }
//            final String parseKey = parseKey(cacheService.fieldKey(), method, args);
//            if (StringUtils.isEmpty(parseKey)){
//                return joinPoint.proceed();
//            }
//            // 得到被代理方法的返回值类型
//            Class returnType = ((MethodSignature) joinPoint.getSignature()).getReturnType();
//            final CacheService.CacheOperation cacheOperation = cacheService.cacheOperation();
//            final String cacheName = cacheService.cacheName();
//            final long time = cacheService.expireTime();
//            final TimeUnit timeUnit = cacheService.timeUnit();
//            String result = "";
//            switch (cacheOperation){
//                case QUERY:
//                    //当为查询操作，先从缓存中查询
//                    result = cacheFactory.getFromJimDb(cacheName, parseKey);
//                    if (null==result){
//                        final String serialize = JsonUtil.serialize(joinPoint.proceed(args));
//                        result = serialize;
//                        //如果缓存中为空，则将获取到的数据放入到缓存
//                        cacheFactory.putDataToJimDb(cacheName,parseKey,serialize,time,timeUnit);
//                    }
//                    break;
//                case DELETE:
//                case UPDATE:
//                    cacheFactory.delFromJimDb(cacheName,parseKey);
//                    break;
//                default:
//                    break;
//            }
//            if (null!=result && !result.equals("")){
//                return JsonUtil.deserialize(result,returnType);
//            }
//        }catch (Exception e){
//            log.error("dealCacheService error ,joinPoint {}",joinPoint.getSignature());
//        }
        return joinPoint.proceed();
    }

    /**
     * 获取method
     * @param joinPoint
     * @return
     */
    private Method getMethod(JoinPoint joinPoint){
        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod();
    }

    /**
     * 解析spel
     * @param fieldKey
     * @param method
     * @param args
     * @return
     */
    private String parseKey(String fieldKey,Method method,Object[] args){
        //获取拦截方法参数列表
        final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        final String[] params = parameterNameDiscoverer.getParameterNames(method);
        //使用spel进行key解析
        final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        //spel上下文
        final StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //把方法参数放入spel上下
        for (int i=0;i<params.length;i++){
            standardEvaluationContext.setVariable(params[i],args[i]);
        }
        return spelExpressionParser.parseExpression(fieldKey).getValue(standardEvaluationContext,String.class);
    }

    public void setCacheEnable(boolean cacheEnable) {
        this.cacheEnable = cacheEnable;
    }
}
