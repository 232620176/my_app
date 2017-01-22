package com.hydra.pojo;

public enum PayType {
	ZX,//零钱/理财
	ZH,//红包
	XH,//理财+红包
	
	PR,//刷卡
	DS,//代收
	WK,//无卡(快捷)
	XD;//信贷
	
	private static final String[] PAY_TYPE = {"ZX", "ZH", "XH", "PR", "DS", "WK", "XD"};
	private static final String[] PAY_TYPE_NAME = {"零钱", "红包", "零钱+红包", "刷卡", "代收", "快捷", "信贷"};
	
	public static PayType getComplexInstance(PaymentType paymentType){
		switch(paymentType){
			case G: throw new RuntimeException();
			default : return XH;
		}
	}
	
	public static PayType getInstance(PaymentType paymentType){
		return getInstance(paymentType, false);
	}
	
	public static PayType getInstance(PaymentType paymentType, boolean isComplex){
		if(isComplex){
			return getComplexInstance(paymentType);
		}else{
			return getSimpleInstance(paymentType);
		}
	}
	
	public static PayType getSimpleInstance(PaymentType paymentType){
		switch(paymentType){
			case A: return XD;
			case C: return PR;
			case D: return DS;
			case E: 
			case F: 
			case W: return ZX;
			case G: return ZH;
			case S: return WK;
			default : throw new RuntimeException();
		}
	}
	
	public static String getValue(PaymentType paymentType){
		return getValue(paymentType, false);
	}
	
	public static String getValue(PaymentType paymentType, boolean isComplex){
		PayType pt = getInstance(paymentType, isComplex);
		return pt.getValue();
	}
	
	public String getName(){
		switch(this){
			case ZX: return PAY_TYPE_NAME[0];
			case ZH: return PAY_TYPE_NAME[1];
			case XH: return PAY_TYPE_NAME[2];
			case PR: return PAY_TYPE_NAME[3];
			case DS: return PAY_TYPE_NAME[4];
			case WK: return PAY_TYPE_NAME[5];
			case XD: return PAY_TYPE_NAME[6];
			default : throw new RuntimeException();
		}
	}
	
	public String getValue(){
		switch(this){
			case ZX: return PAY_TYPE[0];
			case ZH: return PAY_TYPE[1];
			case XH: return PAY_TYPE[2];
			case PR: return PAY_TYPE[3];
			case DS: return PAY_TYPE[4];
			case WK: return PAY_TYPE[5];
			case XD: return PAY_TYPE[6];
			default : throw new RuntimeException();
		}
	}
}
