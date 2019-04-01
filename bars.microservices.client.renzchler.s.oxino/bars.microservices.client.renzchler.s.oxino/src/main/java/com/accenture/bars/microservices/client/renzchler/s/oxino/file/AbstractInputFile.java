 package com.accenture.bars.microservices.client.renzchler.s.oxino.file;

import java.io.File;

public abstract class AbstractInputFile implements IInputFile{
	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
