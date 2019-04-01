package com.accenture.bars.microservices.server.renzchler.s.oxino.record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.accenture.bars.microservices.server.renzchler.s.oxino.billing.IBillingRepository;
import com.accenture.bars.microservices.server.renzchler.s.oxino.request.IRequestRepository;
import com.accenture.bars.microservices.server.renzchler.s.oxino.request.Request;

@Service
public class RecordService {

	@Autowired
	private IRequestRepository request;

	@Autowired
	private IBillingRepository billing;

	public List<Record> getRecord() throws ParseException {

		List<Record> record = new ArrayList<>();

		List<Request> req = request.findAll();

		int[] counter = {0};
		req.forEach(r -> {

			billing.findAllByBillingCycleAndStartDateAndEndDate(
					r.getBilling_cycle(), r.getStart_date(), r.getEnd_date())
					.forEach(
							(e) -> {

								record.add(new Record());

								record.get(counter[0]).setBillingCycle(
										e.getBillingCycle());
								record.get(counter[0]).setAccountName(
										e.getAccount().getAccountName());
								record.get(counter[0]).setCustomerFirstName(
										e.getAccount().getCustomer()
												.getFirstName());
								record.get(counter[0]).setCustomerLastName(
										e.getAccount().getCustomer()
												.getLastName());
								record.get(counter[0]).setAmount(e.getAmount());
								record.get(counter[0]).setsDate(e.getStartDate());
								record.get(counter[0]).seteDate(e.getEndDate());
								Date sDate;
								Date eDate;
								try {
									sDate = new SimpleDateFormat("yyyy-MM-dd")
											.parse(e.getStartDate());
									eDate = new SimpleDateFormat("yyyy-MM-dd")
											.parse(e.getEndDate());
									record.get(counter[0]).setStartDate(sDate);

									record.get(counter[0]).setEndDate(eDate);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								 counter[0]++;
							});

		});

		return record;

	}

}
