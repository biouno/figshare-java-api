package org.biouno.figshare;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.biouno.figshare.v1.model.Article;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for creating an article.
 *
 * @since 0.1
 */
public class TestCreateArticle {

	private String json;
	
	@Before
	public void setUp() throws Exception {
		String jsonFileLocation = getClass().getResource("/article.json").getFile();
		json = FileUtils.readFileToString(new File(jsonFileLocation));
	}
	
	@Test
	public void testJsonToObjects() {
		Article article = FigShareClient.to(
				"", 
				1, 
				"", 
				"", 
				"", 
				"")
			.readArticleFromJson(json)
		;
		assertEquals("Wrong article ID", Long.valueOf(123L), article.getArticleId());
	}
	
}
