package org.accesointeligente.model;

import org.accesointeligente.shared.FileType;

import net.sf.gilead.pojo.gwt.LightEntity;

public class Attachment extends LightEntity {
	private static final long serialVersionUID = 292919972917600668L;

	private Long id;
	private String name;
	private Response response;
	private FileType type;
	private String url;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FileType getType() {
		return type;
	}

	public void setType(FileType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
