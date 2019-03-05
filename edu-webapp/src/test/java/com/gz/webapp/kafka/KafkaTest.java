package com.gz.webapp.kafka;

import com.gz.EduApplication;
import com.gz.edu.model.user.User;
import com.gz.edu.service.kafka.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(classes = EduApplication.class, initializers = ConfigFileApplicationContextInitializer.class)
public class KafkaTest {

    @Autowired
    private KafkaSender<User> kafkaSender;

    @Test
    public void kafkaSend() throws InterruptedException {
        //模拟发消息
        User user = new User();
        user.setUsername("张三");
        kafkaSender.send(user);
        Thread.sleep(3000);
    }


}
