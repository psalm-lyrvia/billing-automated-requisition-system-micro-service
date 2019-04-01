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

public class CSVInputFileImpl extends AbstractInputFile {

	@Override
	public List<Request> readFile() throws BarsException {

		List<Request> request = new ArrayList<>();

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

		String pattern = "^(((0?[1-9]|1[012])/(0?[1-9]|1\\d|2[0-8])|"
				+ "(0?[13456789]|1[012])/(29|30)|(0?[13578]|"
				+ "1[02])/31)/(19|[2-9]\\d)\\d{2}|"
				+ "0?2/29/((19|[2-9]\\d)(0[48]|" + "[2468][048]|[13579][26])|"
				+ "(([2468][048]|[3579][26])00)))$";
		int billingCycle;

		String line = null;
		int row = 0;
		final int fileSize = 23;
		final int bCycleSize = 12;
		final int bCycleIndex = 0;
		final int sDateIndex = 1;
		final int eDateIndex = 2;
		String delimeter = ",";

		try (BufferedReader fileReader = new BufferedReader(new FileReader(
				getFile()))) {

			while ((line = fileReader.readLine()) != null) {

				request.add(new Request());

				if (line.length() > fileSize) {
					request.clear();
					Logger.getLogger(CSVInputFileImpl.class).trace(
							BarsException.NO_SUPPORTED_FILE);
					throw new BarsException(BarsException.NO_SUPPORTED_FILE);
				} else {

					if (!line.isEmpty()) {

						String[] values = line.split(delimeter);
						// Set Billing Cycle
						billingCycle = Integer.parseInt(values[bCycleIndex]);

						if (billingCycle <= bCycleSize) {
							request.get(row).setBillingCycle(billingCycle);
						} else {
							request.clear();
							Logger.getLogger(CSVInputFileImpl.class).error(
									BarsException.BILLING_CYCLE_NOT_ON_RANGE
											+ (row + 1));

							throw new BarsException(
									BarsException.BILLING_CYCLE_NOT_ON_RANGE
											+ (row + 1));
						}

						// Set Start Date
						if (!values[sDateIndex].matches(pattern)) {
							request.clear();
							Logger.getLogger(CSVInputFileImpl.class).error(
									BarsException.INVALID_START_DATE_FORMAT
											+ (row + 1));
							throw new BarsException(
									BarsException.INVALID_START_DATE_FORMAT
											+ (row + 1));
						} else {

							try {
								Date date = formatter.parse(values[sDateIndex]);
								request.get(row).setStartDate(date);
							} catch (ParseException e) {
								Logger.getLogger(CSVInputFileImpl.class).trace(e);
							}

						}

						// Set End Date
						if (!values[eDateIndex].matches(pattern)) {
							request.clear();
							Logger.getLogger(CSVInputFileImpl.class).error(
									BarsException.INVALID_END_DATE_FORMAT
											+ (row + 1));
							throw new BarsException(
									BarsException.INVALID_END_DATE_FORMAT
											+ (row + 1));
						} else {

							try {
								Date date = formatter.parse(values[eDateIndex]);
								request.get(row).setEndDate(date);
							} catch (ParseException e) {
								Logger.getLogger(CSVInputFileImpl.class).trace(e);
							}

						}
					} else {
						request.clear();
						Logger.getLogger(CSVInputFileImpl.class).error(
								BarsException.NO_RECORDS_TO_READ);
						throw new BarsException(
								BarsException.NO_RECORDS_TO_READ);
					}

				}
				row++;
			}

		} catch (FileNotFoundException e) {
			Logger.getLogger(CSVInputFileImpl.class).trace(e);
			throw new BarsException(BarsException.PATH_DOES_NOT_EXIST);
		} catch (IOException e ) {
			Logger.getLogger(CSVInputFileImpl.class).trace(e);
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
