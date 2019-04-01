package com.accenture.bars.microservices.client.renzchler.s.oxino.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;

import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Request;
import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;

public class TextInputFileImpl extends AbstractInputFile {

	public TextInputFileImpl() {
		super();
	}

	@Override
	public List<Request> readFile() throws BarsException {

		List<Request> request = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		int billingCycle;
		String startDate;
		String endDate;
		String startYear;
		String startMonth;
		String startDay;
		String endYear;
		String endMonth;
		String endDay;
		Date date;
		String line = null;
		int row = 0;
		final int fileSize = 18;
		final int bCycleSize = 12;
		final int bCycleIndex = 2;
		final int sDateIndex = 2;
		final int sDateEnd = 10;
		final int eDateIndex = 10;
		final int yearIndex = 4;
		final int yearEnd = 8;
		final int monthIndex = 0;
		final int monthEnd = 2;
		final int dayIndex = 2;
		final int dayEnd = 4;
		final int monthSize = 12;
		final int daySize = 31;
		final int yearLength = 4;

		try (BufferedReader fileReader = new BufferedReader(new FileReader(
				getFile()))) {

			if (getFile().length() == 0) {
				Logger.getLogger(TextInputFileImpl.class).error(
						BarsException.NO_RECORDS_TO_READ);
				throw new BarsException(BarsException.NO_RECORDS_TO_READ);
			} else {
				while ((line = fileReader.readLine()) != null) {
					request.add(new Request());
					if (!line.matches("\\d+")
							|| line.substring(0).length() != fileSize) {
						request.clear();
						Logger.getLogger(TextInputFileImpl.class).error(
								BarsException.NO_SUPPORTED_FILE);
						throw new BarsException(BarsException.NO_SUPPORTED_FILE);
					} else {

						// Set Billing Cycle
						billingCycle = Integer.parseInt(line.substring(0,
								bCycleIndex));
						if (billingCycle > bCycleSize) {
							request.clear();
							Logger.getLogger(TextInputFileImpl.class).error(
									BarsException.BILLING_CYCLE_NOT_ON_RANGE
											+ (row + 1));
							throw new BarsException(
									BarsException.BILLING_CYCLE_NOT_ON_RANGE
											+ (row + 1));
						} else {
							request.get(row).setBillingCycle(billingCycle);
						}

						// Set Start Date
						startYear = (String) line.substring(sDateIndex,
								sDateEnd).subSequence(yearIndex, yearEnd);
						startMonth = (String) line.substring(sDateIndex,
								sDateEnd).subSequence(monthIndex, monthEnd);
						startDay = (String) line
								.substring(sDateIndex, sDateEnd).subSequence(
										dayIndex, dayEnd);
						startDate = startMonth + "/" + startDay + "/"
								+ startYear;
						if (Integer.parseInt(startMonth) > monthSize
								|| Integer.parseInt(startDay) > daySize
								|| startYear.length() != yearLength) {
							request.clear();
							Logger.getLogger(TextInputFileImpl.class).error(
									BarsException.INVALID_START_DATE_FORMAT
											+ (row + 1));
							throw new BarsException(
									BarsException.INVALID_START_DATE_FORMAT
											+ (row + 1));
						} else {

							try {
								date = formatter.parse(startDate);
								request.get(row).setStartDate(date);
							} catch (ParseException e) {
								Logger.getLogger(TextInputFileImpl.class).error(e);
							}

						}

						// Set End Date
						endYear = (String) line.substring(eDateIndex)
								.subSequence(yearIndex, yearEnd);
						endMonth = (String) line.substring(eDateIndex)
								.subSequence(monthIndex, monthEnd);
						endDay = (String) line.substring(eDateIndex)
								.subSequence(dayIndex, dayEnd);
						endDate = endMonth + "/" + endDay + "/" + endYear;

						if (Integer.parseInt(endMonth) > monthSize
								|| Integer.parseInt(endDay) > daySize
								|| endYear.length() != yearLength) {
							request.clear();
							Logger.getLogger(TextInputFileImpl.class).error(
									BarsException.INVALID_END_DATE_FORMAT
											+ (row + 1));
							throw new BarsException(
									BarsException.INVALID_END_DATE_FORMAT
											+ (row + 1));
						} else {
							try {
								date = formatter.parse(endDate);
								request.get(row).setEndDate(date);
							} catch (ParseException e) {
								Logger.getLogger(TextInputFileImpl.class)
										.error(e.getMessage());
							}
						}

					}
					row++;
				}
			}

		} catch (FileNotFoundException e) {
			Logger.getLogger(TextInputFileImpl.class).trace(e);
			throw new BarsException(BarsException.PATH_DOES_NOT_EXIST);
		} catch (IOException e) {
			Logger.getLogger(TextInputFileImpl.class).trace(e);
		}

		return request;

	}

	@Override
	public void setFile(File file) {

		super.setFile(file);

	}

	@Override
	public File getFile() {

		return super.getFile();

	}

}
