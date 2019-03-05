package com.gz.edu.service.kafka;

import com.google.common.base.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaListenerComent {

    @KafkaListener(id = "tut", topics = "kafka.tut")
    public void listen(ConsumerRecord<?, ?> record, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        //判断是否NULL
        Optional<?> kafkaMessage = Optional.of(record.value());
        if (kafkaMessage.isPresent()) {
            //获取消息
            Object message = kafkaMessage.get();

            log.info("Receive： +++++++++++++++ Topic:" + topic);
            log.info("Receive： +++++++++++++++ Record:" + record);
            log.info("Receive： +++++++++++++++ Message:" + message);
        }
    }

}
