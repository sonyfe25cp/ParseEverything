package com.parseeverything.pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class PageStoreWriter {
	private static final String DEFAULT_ENCODING = "UTF-8";
	private BufferedWriter writer;

	public PageStoreWriter(File file) {
		try {
			OutputStream outputStream = FileUtils.openOutputStream(file);
			writer = new BufferedWriter(new OutputStreamWriter(
					outputStream, DEFAULT_ENCODING));
		} catch (Exception e) {
			throw new PageStoreException(e);
		}
	}

	public PageStoreWriter(BufferedWriter writer) {
		this.writer = writer;
	}

	public void store(StoredPage page) {
		try {
			writer.write(page.toString());
		} catch (Exception e) {
			throw new PageStoreException(e);
		}
	}

	public void storeAll(Collection<StoredPage> pages) {
		for (StoredPage page : pages) {
			store(page);
		}
	}

	public void close() {
		IOUtils.closeQuietly(writer);
	}

}
