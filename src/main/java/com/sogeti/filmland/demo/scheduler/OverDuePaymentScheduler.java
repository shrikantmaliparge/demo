package com.sogeti.filmland.demo.scheduler;

import com.sogeti.filmland.demo.Constant.ConstantValue;
import com.sogeti.filmland.demo.dao.SubscribedServices;
import com.sogeti.filmland.demo.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class OverDuePaymentScheduler {

	private static final Logger FILMLAND_LOG = LoggerFactory.getLogger("FILMLAND_LOG");
	@Autowired
	private RestService restService;

	/**This method will run the scheduler in each 10 Sec interval to fetch the over due subscription user
	 * 
	 */
	@Scheduled(cron= ConstantValue.CRON_EXPRESSION)
	public void trackOverduePayments() {
		List<SubscribedServices> subscribedServices = restService.getSubscribedService();
		subscribedServices.forEach(subscribedService -> {
			if (isBeforeMonths(-1, subscribedService.getStartDate())) {
				FILMLAND_LOG.warn("OverDueNotification: Mail send {} for over due payment ",
						subscribedService.getUser_name());
			}
		});
	}

	//This method will compare the start date with current date and return response in 0/1(boolean)

	private boolean isBeforeMonths(int months, Date aDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, months);
		return aDate.compareTo(calendar.getTime()) < 0;
	}
}