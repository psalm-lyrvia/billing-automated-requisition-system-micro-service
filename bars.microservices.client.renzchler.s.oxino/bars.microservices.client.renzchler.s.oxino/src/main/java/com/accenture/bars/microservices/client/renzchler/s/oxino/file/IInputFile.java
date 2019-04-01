package com.accenture.bars.microservices.client.renzchler.s.oxino.file;

import java.io.File;
import java.util.List;

import com.accenture.bars.microservices.client.renzchler.s.oxino.domain.Request;
import com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;

public interface IInputFile {
	public List<Request> readFile() throws BarsException, com.accenture.bars.microservices.client.renzchler.s.oxino.exception.BarsException;
	public void setFile(File file);
	public File getFile();
}
