package com.salesmanager.shop.model.cms;

import com.salesmanager.shop.model.entity.Entity;

public class ReadableBanner extends Entity {
	private static final long serialVersionUID = 1L;
	private boolean visible;

	private String title;
	private String imageUrl;
	private String targetUri;

	/**
	 * html,web,schema,
	 */
	private String targetType;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTargetUri() {
		return targetUri;
	}

	public void setTargetUri(String targetUri) {
		this.targetUri = targetUri;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}
