package com.hydra.blank.trans.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hydra.core.db.sqlmap.SqlMap;
import com.hydra.core.util.CommonUtil;

public class CardService {
	@Autowired
	private SqlMap sqlMap;
	private static final Map<Integer, List<Integer>> HEADER_MAP = new HashMap<>();
	private static final Map<String, String> BANK_MAP = new HashMap<>();
	
	public void reload(){
		synchronized(BANK_MAP){
			BANK_MAP.clear();
			HEADER_MAP.clear();
			prepare();
		}
	}
	
	public void getBankNames(List<Map<String, Object>> cards){
		getBankNames(cards, "CardNo");
	}
	
	public void getBankNames(List<Map<String, Object>> cards, String cardNoKeyName){
		getBankNames(cards, cardNoKeyName, "BankName");
	}
	
	public void getBankNames(List<Map<String, Object>> cards, String cardNoKeyName, String bankKeyName){
		if(null != cards && cards.size() > 0){
			for(Map<String, Object> card : cards){
				String cardNo = CommonUtil.transform(card.get(cardNoKeyName));
				card.put(bankKeyName, getBankNameByCardNo(cardNo));
			}
		}
	}
	
	public String getBankNameByCardNo(String cardNo){
		String res = null;
		Integer len = cardNo.length();
		List<Integer> cardHeaderList = HEADER_MAP.get(len);
		for(Integer headerLen : cardHeaderList){
			res = BANK_MAP.get(cardNo.substring(0, headerLen));
			if(null != res){
				break;
			}
		}
		return res;
	}
	
	private void prepare(){
		List<Map<BigDecimal, String>> bankList = sqlMap.queryForList("card.qryCardHeaderAndBankName");
		for(Map<BigDecimal, String> bankMap : bankList){
			BANK_MAP.put(bankMap.get("cardHeader"), bankMap.get("bankName"));
		}
		List<Map<BigDecimal, BigDecimal>> headerList = sqlMap.queryForList("card.qryCardLenAndCardHeaderLen");
		for(Map<BigDecimal, BigDecimal> headerMap : headerList){
			Integer cardLen = headerMap.get("cardLen").intValue();
			Integer cardHeaderLen = headerMap.get("cardHeaderLen").intValue();
			List<Integer> cardHeaderLenList = HEADER_MAP.get(cardLen);
			if(null == cardHeaderLenList){
				cardHeaderLenList = new ArrayList<>();
				HEADER_MAP.put(cardLen, cardHeaderLenList);
			}
			cardHeaderLenList.add(cardHeaderLen);
		}
	}
}
