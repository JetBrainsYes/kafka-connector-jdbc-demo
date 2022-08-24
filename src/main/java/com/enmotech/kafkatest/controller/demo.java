package com.enmotech.kafkatest.controller;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.enmotech.kafkatest.pojo.JsonDemo;
import com.enmotech.kafkatest.service.sendService;
import com.enmotech.kafkatest.util.SchemaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.enmotech.kafkatest.controller
 *
 * @author syf
 * @create 2022-08-18-15:04
 * @Description kafka-test-demo
 *  还可以添加和修改的功能：
 *      1.通过请求指定不同随机数据出现空值的概率
 *      2.schema和payload根据输入的参数修改格式
 */

@RestController
@Slf4j
public class demo {
    @Autowired
    sendService sendServiceImpl;
    //计算数据数量
    int count = 0;

    //指定发送时间(S),单位时间发送数量(条/S),topic名称
    //单位时间发送数量最大值为1.1W条每秒
    @RequestMapping("/sendbytime/{time}/{quantity}/{topic}")
    public String sendByTime(@PathVariable("time")int time,@PathVariable("quantity")int quantity,
    @PathVariable("topic")String topic) {
        return sendServiceImpl.sendByTime(time,quantity,topic);
    }

    //指定数据数量，topic名称
    @RequestMapping("/sendbyfrequency/{frequency}/{topic}")
    public String sendByFrequency(@PathVariable("frequency")int frequency, @PathVariable("topic")String topic) {
        return sendServiceImpl.sendByFrequency(frequency,topic);
    }
}
