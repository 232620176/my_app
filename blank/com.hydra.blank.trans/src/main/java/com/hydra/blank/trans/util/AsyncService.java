package com.hydra.blank.trans.util;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import com.hydra.core.common.interfaces.Callable;

public class AsyncService {
	private static Logger logger = LoggerFactory.getLogger(AsyncService.class);
	
	@Async
	public Future<Long> asyncMethod(Callable call){
		return asyncMethodTempalte(() -> call.call());
	}
	
	@Async
	public Future<Long> asyncMethodWithNoException(Callable call){
		return asyncMethodTempalte(() ->{
			try {
				call.call();
			} catch (Exception e) {
				logger.error("{}", e);
			}
		});
	}
	
	private Future<Long> asyncMethodTempalte(Callable call){
		long begin = System.currentTimeMillis();
		call.call();
		long end = System.currentTimeMillis();
		long res = end - begin;
		logger.info("用时：" + res + "ms...");
		return new AsyncResult<Long>(res);
	}
}
