package com.hydra.blank.trans.test.spring.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hydra.blank.trans.test.spring.BaseJunit4Test;
import com.hydra.blank.trans.util.AsyncService;
import com.hydra.core.util.CommonUtil;

public class TestAsync extends BaseJunit4Test{
	@Autowired
	private AsyncService asyncService;
	@Test
	public void testAsync(){
		List<Future<Long>> res = new ArrayList<Future<Long>>();
		Future<Long> f0 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(1000);
		});
		res.add(f0);
		Future<Long> f1 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(2000);
			throw new RuntimeException();
		});
		res.add(f1);
		Future<Long> f2 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(CommonUtil.MAX_WAIT_TIME);
		});
		res.add(f2);
		Future<Long> f3 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(4000);
		});
		res.add(f3);
		Future<Long> f4 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(6000);
		});
		res.add(f4);
		CommonUtil.waitDoneOrTimeout(res);
	}
	
	@Test
	public void testAsyncOverPoolSize(){
		List<Future<Long>> res = new ArrayList<Future<Long>>();
		Future<Long> f0 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f0);
		Future<Long> f1 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
			throw new RuntimeException();
		});
		res.add(f1);
		Future<Long> f2 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(CommonUtil.MAX_WAIT_TIME);
		});
		res.add(f2);
		Future<Long> f3 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f3);
		Future<Long> f4 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f4);
		Future<Long> f5 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f5);
		Future<Long> f6 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f6);
		Future<Long> f7 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f7);
		Future<Long> f8 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f8);
		Future<Long> f9 = asyncService.asyncMethodWithNoException(() -> {
			CommonUtil.sleep(20000);
		});
		res.add(f9);
		CommonUtil.waitDoneOrTimeout(res);
	}
}
