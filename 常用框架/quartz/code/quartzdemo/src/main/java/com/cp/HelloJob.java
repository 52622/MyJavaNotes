package com.cp;

import org.quartz.*;

import javax.sound.midi.Soundbank;
import java.util.Date;

/**
 * @author copywang
 * @description
 * @create 2018-10-14 15:33
 */
public class HelloJob implements Job {

    //自动注入
    String triggerStringValue;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //key: jobGroup1.job1
        JobKey key = context.getJobDetail().getKey();
        //JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        JobDataMap dataMap = context.getMergedJobDataMap();
        //JobDataMap dataMap = context.getTrigger().getJobDataMap();
        String stringValue = dataMap.getString("stringValue");
        float floatValue = dataMap.getFloat("floatValue");
        //String triggerStringValue = dataMap.getString("triggerStringValue");
        System.out.println("key: " + key);
        System.out.println("stringValue: " + stringValue);
        System.out.println("floatValue: " + floatValue);
        System.out.println("triggerStringValue: " + triggerStringValue);
        System.out.println("hello working " + new Date());
    }

    public void setTriggerStringValue(String triggerStringValue) {
        this.triggerStringValue = triggerStringValue;
    }
}
