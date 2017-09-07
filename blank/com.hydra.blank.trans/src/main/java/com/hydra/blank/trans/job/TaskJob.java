package com.hydra.blank.trans.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskJob")
public class TaskJob {
	@Scheduled(cron="*/2 * * * * ?")
	public void loggerJob(){
		logger.info("task job ...");
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
}
