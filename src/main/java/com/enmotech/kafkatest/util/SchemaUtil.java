package com.enmotech.kafkatest.util;

import com.enmotech.kafkatest.pojo.Field;
import com.enmotech.kafkatest.pojo.JsonDemo;
import com.enmotech.kafkatest.pojo.payload;
import com.enmotech.kafkatest.pojo.schema;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * com.enmotech.kafkatest.util
 *
 * @author syf
 * @create 2022-08-23-14:22
 * @Description kafka-test
 */
@Component
public class SchemaUtil {
    //经纬度
    Double longitude;
    Double latitude;
    //温度、湿度
    Double temperature;
    Double humidity;
    //时间
    int Timestamp;
    String stringtime;
    //随机字符串
    String randomString;

    long TimeNow;

    public schema getSchema(){
        Field[] fields = Field.getFields();
        schema schema = new schema("struct",fields,false,"demo_table");
        return schema;
    }
    @Bean
    public JsonDemo getJsonDemo(){
        schema schema = getSchema();
        payload payload = new payload();
        JsonDemo jsondemo = new JsonDemo(schema,payload);
        return jsondemo;
    }

    //给payload中的属性赋值
    public void setPayload(int count,payload payload,int target){
        //设置日期时间格式转换
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //生成随机数和随机字符串
        Random random = new Random();
        char[] house = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', '我', '你', '他', '是', '否', '对', '错', '好', '坏', '快', '慢'};

        longitude = random.nextDouble()*180;
        latitude = random.nextDouble()*180;
        //保留两位小数
        temperature = Double.valueOf(String.format("%.2f",(random.nextDouble()*50-10)));
        humidity = Double.valueOf(String.format("%.2f",random.nextDouble()*100));
        TimeNow = System.currentTimeMillis();
        Timestamp = (int) (random.nextLong()+TimeNow);
        stringtime = sdf.format(Timestamp);
        randomString = RandomStringUtils.random(20,house);
        int temp = random.nextInt(10);
        //随机在数据中插入null
        switch (temp){
            case 2:
                longitude = null;
                break;
            case 3:
                latitude = null;
                break;
            case 4:
                temperature = null;
                break;
            case 5:
                humidity = null;
                break;
        }
        if (temp<target){
            randomString = null;
        }
        payload.setId(count);
        payload.setHumidity(humidity);
        payload.setLatitude(latitude);
        payload.setLongitude(longitude);
        payload.setStringtime(stringtime);
        payload.setTemperature(temperature);
        payload.setRandomstring(randomString);
        payload.setTabletime(Timestamp);
    }
}
