package com.suddenly.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class TimedTask {

    private Logger logger = LogManager.getLogger(getClass());

    @Scheduled(cron = "0/5 * * * * *")
    public void test() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
    }

}
