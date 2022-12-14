package com.enmotech.kafkatest.service;

/**
 * com.enmotech.kafkatest.service
 *
 * @author syf
 * @create 2022-08-23-17:32
 * @Description kafka-test
 */

public interface sendService {
    String send(int count,int target,String topic);
    String sendByTime(int time,int quantity,String topic);
    String sendByFrequency(int frequency,String topic);
}
