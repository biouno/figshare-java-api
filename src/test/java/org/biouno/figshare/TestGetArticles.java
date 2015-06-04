package org.biouno.figshare;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.biouno.figshare.v1.model.Article;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for retrieving articles.
 *
 * @since 0.1
 */
public class TestGetArticles {

	private String json;
	
	@Before
	public void setUp() throws Exception {
		String jsonFileLocation = getClass().getResource("/articles.json").getFile();
		json = FileUtils.readFileToString(new File(jsonFileLocation));
	}
	
	@Test
	public void testJsonToObjects() {
		List<Article> articles = FigShareClient.to(
				"", 
				1, 
				"", 
				"", 
				"", 
				"")
			.readArticlesFromJson(json)
		;
		assertEquals("Articles size doesn't match", 1, articles.size());
		assertEquals("Wrong article ID", Long.valueOf(123456789L), articles.get(0).getArticleId());
	}
	
}
