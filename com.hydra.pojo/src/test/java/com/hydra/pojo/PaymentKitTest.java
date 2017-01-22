package com.hydra.pojo;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PaymentKitTest{
	@Test
	public void testSayHello(){
		Map<String, Object> paymentType = new HashMap<String, Object>();
		paymentType.put("PaymentType", "G");
		PaymentKit pk = new PaymentKit.BuildTool().buildGPayment(paymentType).build();
		assertEquals(true, pk.hasG);
		assertEquals(false, pk.hasW);
	}
}