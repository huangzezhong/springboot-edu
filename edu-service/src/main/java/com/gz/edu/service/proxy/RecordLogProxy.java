package com.gz.edu.service.proxy;

import com.gz.edu.service.log.RecordLogService;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RecordLogProxy<T> implements InvocationHandler {

    private T target;

    public RecordLogProxy(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("----开始操作前----");
        //log.info("参数，{}",args[0].toString());
        //当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(target, args);
        log.info("----开始操作后----");
        return null;
    }
}
