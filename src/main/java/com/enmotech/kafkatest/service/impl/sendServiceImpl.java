package com.enmotech.kafkatest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.enmotech.kafkatest.pojo.JsonDemo;
import com.enmotech.kafkatest.service.sendService;
import com.enmotech.kafkatest.util.SchemaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * com.enmotech.kafkatest.service.impl
 *
 * @author syf
 * @create 2022-08-23-17:33
 * @Description kafka-test
 */
@Component
@Slf4j
public class sendServiceImpl implements sendService {
    @Autowired
    SchemaUtil schemaUtil;
    //数据库数据对象
    @Autowired
    JsonDemo jsonDemo;
    String message;
    //记录数据ID
    int count;
    @Autowired
    private KafkaTemplate<String, String> template;

    @Override
    public String send(int count,int target,String topic) {
        //给jsonDemo中的属性赋值，生成数据
        schemaUtil.setPayload(count,jsonDemo.getPayload(),target);
        //配置序列化时不忽略null
        message = JSONObject.toJSONString(jsonDemo, SerializerFeature.WriteMapNullValue);
        //将消息发送到Kafka服务器的名称为“test”的Topic中
        this.template.send(topic, message);
        log.info("message: {}", message);
        return message;
    }

    @Override
    public String sendByTime(int time,int quantity,String topic) {
        long l = System.currentTimeMillis();
        long target = l+(time-1)*1000;
        while (l<target){
            sendByFrequency(quantity,topic);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            l = System.currentTimeMillis();
        }
        return sendByFrequency(quantity,topic);
    }

    @Override
    public String sendByFrequency(int frequency,String topic) {
        int sb = 0;
        while (sb<frequency){
            send(count,2,topic);
            count++;
            sb++;
        }
        return send(count,2,topic);
    }
}
