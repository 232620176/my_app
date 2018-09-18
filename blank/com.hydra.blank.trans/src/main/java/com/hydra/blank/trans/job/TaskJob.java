package com.hydra.blank.trans.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component("taskJob")
@Slf4j
public class TaskJob {
    @Scheduled(cron = "*/2 * * * * ?")
    public void loggerJob() {
        log.info("task job ...");
    }
}
