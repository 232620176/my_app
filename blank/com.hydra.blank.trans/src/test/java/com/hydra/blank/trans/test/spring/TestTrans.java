package com.hydra.blank.trans.test.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hydra.blank.trans.util.CardService;
import com.hydra.core.db.service.ParametersQueryService;

public class TestTrans extends BaseJunit4Test {
	@Test
	public void testTrans(){
		System.out.println(pqs.get("000686"));
	}
	
	@Test
	public void testCardService(){
		cardService.reload();
		long begin = System.currentTimeMillis();
		cardService.getBankNames(cardInfo, "CardNo", "BankName");
		long end = System.currentTimeMillis();
		System.out.println(end - begin);
	}
	
	private List<Map<String, Object>> cardInfo;
	{
		cardInfo = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("CardNo", "4512900618597334");//兴业银行 信用卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "622909326671364059");//兴业银行 储蓄卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "6013823100028728376");//中国银行 储蓄卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "4096666941852019");//中国银行  信用卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "4270200043571091");//工商银行  信用卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "6222080200017538839");//工商银行  储蓄卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "6226642119059321");//光大银行 储蓄卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "3568400000587261");//光大银行 信用卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "5220010100413600");//北京银行 信用卡
		cardInfo.add(map);
		
		map = new HashMap<>();
		map.put("CardNo", "6029693050115985");//北京银行 储蓄卡
		cardInfo.add(map);
	}
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	@Qualifier("parametersQueryService")
	private ParametersQueryService pqs;
}
