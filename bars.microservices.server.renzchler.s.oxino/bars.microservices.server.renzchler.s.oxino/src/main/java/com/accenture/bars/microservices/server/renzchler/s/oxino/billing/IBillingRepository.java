package com.accenture.bars.microservices.server.renzchler.s.oxino.billing;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IBillingRepository extends JpaRepository<Billing, Integer> {

	public List<Billing> findAllByBillingCycleAndStartDateAndEndDate(int billingCycle,
			String startDate, String endDate);

	public Collection<Billing> findByAccountAccountId(int id);


}
