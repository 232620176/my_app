package com.hydra.pojo;


import java.util.Map;

public class PaymentKit {
	final boolean hasA;
	final boolean hasC;
	final boolean hasD;
	final boolean hasE;
	final boolean hasF;
	final boolean hasG;
	final boolean hasS;
	final boolean hasW;
	final int paymentSize;
	final int weight;
	final Map<String, Object> aPayment;
	final Map<String, Object> cPayment;
	final Map<String, Object> dPayment;
	final Map<String, Object> ePayment;
	final Map<String, Object> fPayment;
	final Map<String, Object> gPayment;
	final Map<String, Object> sPayment;
	final Map<String, Object> wPayment;
	
	public boolean isHasA() {
		return hasA;
	}

	public boolean isHasC() {
		return hasC;
	}

	public boolean isHasD() {
		return hasD;
	}

	public boolean isHasE() {
		return hasE;
	}

	public boolean isHasF() {
		return hasF;
	}

	public boolean isHasG() {
		return hasG;
	}

	public boolean isHasS() {
		return hasS;
	}

	public boolean isHasW() {
		return hasW;
	}

	public Map<String, Object> getAPayment() {
		return aPayment;
	}

	public Map<String, Object> getCPayment() {
		return cPayment;
	}

	public Map<String, Object> getDPayment() {
		return dPayment;
	}

	public Map<String, Object> getEPayment() {
		return ePayment;
	}

	public Map<String, Object> getFPayment() {
		return fPayment;
	}

	public Map<String, Object> getGPayment() {
		return gPayment;
	}

	public Map<String, Object> getSPayment() {
		return sPayment;
	}

	public Map<String, Object> getWPayment() {
		return wPayment;
	}
	
	public int getWeight(){
		return this.weight;
	}

	private PaymentKit(BuildTool bd){
		this.hasA = bd.hasA;
		this.hasC = bd.hasC;
		this.hasD = bd.hasD;
		this.hasE = bd.hasE;
		this.hasF = bd.hasF;
		this.hasG = bd.hasG;
		this.hasS = bd.hasS;
		this.hasW = bd.hasW;
		this.aPayment = bd.aPayment;
		this.cPayment = bd.cPayment;
		this.dPayment = bd.dPayment;
		this.ePayment = bd.ePayment;
		this.fPayment = bd.fPayment;
		this.gPayment = bd.gPayment;
		this.sPayment = bd.sPayment;
		this.wPayment = bd.wPayment;
		this.paymentSize = bd.paymentSize;
		this.weight = bd.weight;
	}
	
	public boolean canPay(){
		return hasA || hasC || hasD || hasE || hasF || hasG || hasS || hasW;
	}
	
	public boolean hasPaymentType(PaymentType pt){
		switch(pt){
			case A : return hasA;
			case C : return hasC;
			case D : return hasD;
			case E : return hasE;
			case F : return hasF;
			case G : return hasG;
			case S : return hasS;
			case W : return hasW;
			default : return false;
		}
	}
	
	public boolean hasPaymentTypeAndOthers(PaymentType pt){
		return hasPaymentType(pt) && getPaymentSize() > 1;
	}
	
	public int getPaymentSize(){
		return this.paymentSize;
	}
	
	public final static class BuildTool {
		public PaymentKit build(){
			return new PaymentKit(this);
		}
		
		public BuildTool buildPaymentType(Map<String, Object> payment){
			PaymentType pt = PaymentType.getInstance(payment.get("PaymentType"));
			switch(pt){
				case A : return buildAPayment(payment);
				case C : return buildCPayment(payment);
				case D : return buildDPayment(payment);
				case E : return buildEPayment(payment);
				case F : return buildFPayment(payment);
				case G : return buildGPayment(payment);
				case S : return buildSPayment(payment);
				case W : return buildWPayment(payment);
			}
			throw new RuntimeException();
		}
		
		BuildTool buildAPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasA = true;
					paymentSize++;
					weight += PaymentType.A.getValue();
					this.aPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildCPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasC = true;
					paymentSize++;
					weight += PaymentType.C.getValue();
					this.cPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildDPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasD = true;
					paymentSize++;
					weight += PaymentType.D.getValue();
					this.dPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildEPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasE = true;
					paymentSize++;
					weight += PaymentType.E.getValue();
					this.ePayment = payment;
				}
			}
			return this;
		}
		BuildTool buildFPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasF = true;
					paymentSize++;
					weight += PaymentType.F.getValue();
					this.fPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildGPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasG = true;
					paymentSize++;
					weight += PaymentType.G.getValue();
					this.gPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildSPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasS = true;
					paymentSize++;
					weight += PaymentType.S.getValue();
					this.sPayment = payment;
				}
			}
			return this;
		}
		BuildTool buildWPayment(Map<String, Object> payment) {
			synchronized(weight){
				if(payment != null && payment.size() > 0){
					hasW = true;
					paymentSize++;
					weight += PaymentType.W.getValue();
					this.wPayment = payment;
				}
			}
			return this;
		}
		
		boolean hasA;
		boolean hasC;
		boolean hasD;
		boolean hasE;
		boolean hasF;
		boolean hasG;
		boolean hasS;
		boolean hasW;
		int paymentSize = 0;
		Integer weight = 0;
		Map<String, Object> aPayment;
		Map<String, Object> cPayment;
		Map<String, Object> dPayment;
		Map<String, Object> ePayment;
		Map<String, Object> fPayment;
		Map<String, Object> gPayment;
		Map<String, Object> sPayment;
		Map<String, Object> wPayment;
	}
}
