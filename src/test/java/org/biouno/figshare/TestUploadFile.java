package org.biouno.figshare;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for uploading a file.
 *
 * @since 0.1
 */
public class TestUploadFile {

	private String json;
	
	@Before
	public void setUp() throws Exception {
		String jsonFileLocation = getClass().getResource("/upload.json").getFile();
		json = FileUtils.readFileToString(new File(jsonFileLocation));
	}
	
	@Test
	public void testJsonToObjects() {
		org.biouno.figshare.v1.model.File file = FigShareClient.to(
				"", 
				1, 
				"", 
				"", 
				"", 
				"")
			.readFileFromJson(json)
		;
		assertEquals("File name doesn't match", "gDv6mJS.gif", file.getName());
	}
	
}
