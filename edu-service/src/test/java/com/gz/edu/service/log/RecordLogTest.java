package com.gz.edu.service.log;

import com.gz.edu.service.log.impl.RecordLogServiceImpl;
import com.gz.edu.service.proxy.RecordLogCglib;
import com.gz.edu.service.proxy.RecordLogProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Slf4j
public class RecordLogTest {

    /**
     * 测试记录日志动态代理(JDK)
     */
    //@Test
    public void testRecordLogProxyByJDK() {
        RecordLogService recordLogService = new RecordLogServiceImpl();
        InvocationHandler handler = new RecordLogProxy(recordLogService);
        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象
         * 第一个参数recordLogService.getClass().getClassLoader()，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数recordLogService.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler，我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        RecordLogService recordLogProxyService = (RecordLogService) Proxy.newProxyInstance(
                recordLogService.getClass().getClassLoader(),
                recordLogService.getClass().getInterfaces(),
                //new Class<?>[]{RecordLogService.class},
                handler);
        recordLogProxyService.record("插入一条日志");
        recordLogProxyService.delete();
    }

    /**
     * 测试记录日志动态代理(Cglib)
     */
    //@Test
    public void testRecordLogProxyByCglib() {
        RecordLogService recordLogService = new RecordLogServiceImpl();
        RecordLogCglib recordLogCglib = new RecordLogCglib();
        RecordLogService recordLogProxyService = (RecordLogService) recordLogCglib.getInstance(recordLogService);
        recordLogProxyService.record("插入一条日志");
        recordLogProxyService.delete();
    }

}
