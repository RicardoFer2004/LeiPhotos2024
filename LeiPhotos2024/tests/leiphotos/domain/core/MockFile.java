package leiphotos.domain.core;

import java.io.File;

public class MockFile extends File{

	private long size;

	public MockFile(String pathname, long size) {
		super(pathname);
		this.size = size;
	}

	@Override
	public long length() {
		return size;
	}
}
