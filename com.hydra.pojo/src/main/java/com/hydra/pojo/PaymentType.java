package com.hydra.pojo;

public enum PaymentType implements Comparable<PaymentType> {
	A,//替你付
	C,//刷卡
	D,//代收
	E,//钱包扩展
	F,//基金
	G,//红包
	S,//快捷
	W;//钱包
	
	private final static String[] PAYMENT_NAME = {"替你支付", "刷卡支付", "代收支付", "钱包支付", "理财支付", "红包支付", "快捷支付", "零钱支付"};
	private static final String[] PAYMENT_TYPE = {"A", "C", "D", "E", "F", "G", "S", "W"};
	private static final int[] iValues = {1, 2, 4, 8, 16, 32, 64, 128};
	
	public static PaymentType getInstance(Object paymentType){
		if(paymentType instanceof String){
			if(PAYMENT_TYPE[0].equals(paymentType)){
				return PaymentType.A;
			}else if(PAYMENT_TYPE[1].equals(paymentType)){
				return PaymentType.C;
			}else if(PAYMENT_TYPE[2].equals(paymentType)){
				return PaymentType.D;
			}else if(PAYMENT_TYPE[3].equals(paymentType)){
				return PaymentType.E;
			}else if(PAYMENT_TYPE[4].equals(paymentType)){
				return PaymentType.F;
			}else if(PAYMENT_TYPE[5].equals(paymentType)){
				return PaymentType.G;
			}else if(PAYMENT_TYPE[6].equals(paymentType)){
				return PaymentType.S;
			}else if(PAYMENT_TYPE[7].equals(paymentType)){
				return PaymentType.W;
			}
		}
		throw new RuntimeException();
	}
	
	public String getName(){
		switch(this){
			case A: return PAYMENT_NAME[0];
			case C: return PAYMENT_NAME[1];
			case D: return PAYMENT_NAME[2];
			case E: return PAYMENT_NAME[3];
			case F: return PAYMENT_NAME[4];
			case G: return PAYMENT_NAME[5];
			case S: return PAYMENT_NAME[6];
			case W: return PAYMENT_NAME[7];
		}
		return null;
	}
	
	public int getValue(){
		switch(this){
			case A : return iValues[0];
			case C : return iValues[1];
			case D : return iValues[2];
			case E : return iValues[3];
			case F : return iValues[4];
			case G : return iValues[5];
			case S : return iValues[6];
			case W : return iValues[7];
			default : throw new RuntimeException();
		}
	}
}
