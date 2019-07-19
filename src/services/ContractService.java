package services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {
	
	
	
	private OnlinePaymentService onlinePaymentService;
	
	
	
	
	public ContractService(OnlinePaymentService onlinePaymentService) {

		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, Integer months) {
		
		double basicQuota = contract.getTotalValue()/months;
		
		for(int i=1; i<=months; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(contract.getDate());
			calendar.add(Calendar.MONTH, 1);
			Date date = calendar.getTime();
			
			double updateQuota = basicQuota + onlinePaymentService.interest(basicQuota, i);
			double fullQuota = updateQuota + onlinePaymentService.paymentFee(basicQuota);
			
			contract.addAmount(new Installment(date, fullQuota));
					
		}
	}
	
}
