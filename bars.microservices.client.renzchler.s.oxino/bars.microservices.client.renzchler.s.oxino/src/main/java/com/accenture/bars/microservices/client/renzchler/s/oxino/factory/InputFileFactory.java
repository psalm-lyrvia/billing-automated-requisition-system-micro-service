package com.accenture.bars.microservices.client.renzchler.s.oxino.factory;

import java.io.File;

import org.jboss.logging.Logger;

import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.CSVInputFileImpl;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.IInputFile;
import com.accenture.bars.microservices.client.renzchler.s.oxino.file.TextInputFileImpl;

public class InputFileFactory {

	public InputFileFactory() {
		super();
	}

	public static InputFileFactory getInstance() {
		return new InputFileFactory();
	}

	public IInputFile getInputFile(File file) throws BarsException {

		String extension;

		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		extension = fileName.substring(index + 1);

		try {
			if (extension.equals("txt")) {
				return new TextInputFileImpl();
			} else if (extension.equals("csv")) {
				return new CSVInputFileImpl();
			} else {
				return null;
			}
		} catch (NullPointerException e) {
			Logger.getLogger(InputFileFactory.class).trace(e);
			throw new BarsException(BarsException.PATH_DOES_NOT_EXIST);
		}

	}

}
