/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 The BioUno team, Bruno P. Kinoshita
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.biouno.figshare.v1.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * An article.
 *
 * <p>#Thread-safe#</p>
 *
 * @since 0.1
 */
public final class Article {

	@SerializedName("article_id")
	private final Long articleId;
	private final String title;
	@SerializedName("master_publisher_id")
	private final Long masterPublisherId;
	@SerializedName("defined_type")
	private final String definedType;
	private final String status;
	private final Long version;
	@SerializedName("published_date")
	private final String publishedDate;
	private final String description;
	@SerializedName("description_nohtml")
	private final String descriptionNohtml;
	@SerializedName("total_size")
	private final String totalSize;
	private final List<Author> authors;
	private final List<Tag> tags;
	private final List<File> files;
	private final List<Link> links;

	/**
	 * Constructor.
	 *
	 * @param articleId article ID
	 * @param title title
	 * @param masterPublisherId master publisher ID
	 * @param definedType defined type
	 * @param status status
	 * @param version version
	 * @param publishedDate published date
	 * @param description description
	 * @param descriptionNohtml raw description
	 * @param totalSize total size (999.99 KB)
	 */
	public Article(Long articleId, String title, Long masterPublisherId,
			String definedType, String status, Long version,
			String publishedDate, String description, String descriptionNohtml,
			String totalSize) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.masterPublisherId = masterPublisherId;
		this.definedType = definedType;
		this.status = status;
		this.version = version;
		this.publishedDate = publishedDate;
		this.description = description;
		this.descriptionNohtml = descriptionNohtml;
		this.totalSize = totalSize;
		authors = new LinkedList<>();
		tags = new LinkedList<>();
		files = new LinkedList<>();
		links = new LinkedList<>();
	}

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the masterPublisherId
	 */
	public Long getMasterPublisherId() {
		return masterPublisherId;
	}

	/**
	 * @return the definedType
	 */
	public String getDefinedType() {
		return definedType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @return the publishedDate
	 */
	public String getPublishedDate() {
		return publishedDate;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the descriptionNohtml
	 */
	public String getDescriptionNohtml() {
		return descriptionNohtml;
	}

	/**
	 * @return the totalSize
	 */
	public String getTotalSize() {
		return totalSize;
	}

	/**
	 * @return the authors
	 */
	public List<Author> getAuthors() {
		return Collections.unmodifiableList(authors);
	}

	/**
	 * @return the tags
	 */
	public List<Tag> getTags() {
		return Collections.unmodifiableList(tags);
	}

	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return Collections.unmodifiableList(files);
	}

	/**
	 * @return the links
	 */
	public List<Link> getLinks() {
		return Collections.unmodifiableList(links);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((articleId == null) ? 0 : articleId.hashCode());
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result
				+ ((definedType == null) ? 0 : definedType.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((descriptionNohtml == null) ? 0 : descriptionNohtml
						.hashCode());
		result = prime * result + ((files == null) ? 0 : files.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime
				* result
				+ ((masterPublisherId == null) ? 0 : masterPublisherId
						.hashCode());
		result = prime * result
				+ ((publishedDate == null) ? 0 : publishedDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((totalSize == null) ? 0 : totalSize.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Article other = (Article) obj;
		if (articleId == null) {
			if (other.articleId != null)
				return false;
		} else if (!articleId.equals(other.articleId))
			return false;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (definedType == null) {
			if (other.definedType != null)
				return false;
		} else if (!definedType.equals(other.definedType))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (descriptionNohtml == null) {
			if (other.descriptionNohtml != null)
				return false;
		} else if (!descriptionNohtml.equals(other.descriptionNohtml))
			return false;
		if (files == null) {
			if (other.files != null)
				return false;
		} else if (!files.equals(other.files))
			return false;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (masterPublisherId == null) {
			if (other.masterPublisherId != null)
				return false;
		} else if (!masterPublisherId.equals(other.masterPublisherId))
			return false;
		if (publishedDate == null) {
			if (other.publishedDate != null)
				return false;
		} else if (!publishedDate.equals(other.publishedDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (totalSize == null) {
			if (other.totalSize != null)
				return false;
		} else if (!totalSize.equals(other.totalSize))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", title=" + title
				+ ", masterPublisherId=" + masterPublisherId + ", definedType="
				+ definedType + ", status=" + status + ", version=" + version
				+ ", publishedDate=" + publishedDate + ", description="
				+ description + ", descriptionNohtml=" + descriptionNohtml
				+ ", totalSize=" + totalSize + ", authors=" + authors
				+ ", tags=" + tags + ", files=" + files + ", links=" + links
				+ "]";
	}
	
}
