package com.ringletter.utils;

import java.util.List;


public class UserEnd {

	
	private String action;
	private String application;
	private String applicationName;
	private String duration;
	private List<Entities> entities;
	private String organization;
	private String path;
	private long timestamp;
	private String uri;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public List<Entities> getEntities() {
		return entities;
	}

	public void setEntities(List<Entities> entities) {
		this.entities = entities;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public UserEnd(String action, String application, String applicationName,
			String duration, List<Entities> entities, String organization,
			String path, long timestamp, String uri) {
		super();
		this.action = action;
		this.application = application;
		this.applicationName = applicationName;
		this.duration = duration;
		this.entities = entities;
		this.organization = organization;
		this.path = path;
		this.timestamp = timestamp;
		this.uri = uri;
	}

	public UserEnd() {
		super();
	}

}

class Entities {
	private boolean activated;
	private long created;
	private long modified;
	private String type;
	private String username;
	private String uuid;

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getModified() {
		return modified;
	}

	public void setModified(long modified) {
		this.modified = modified;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Entities(boolean activated, long created, long modified,
			String type, String username, String uuid) {
		super();
		this.activated = activated;
		this.created = created;
		this.modified = modified;
		this.type = type;
		this.username = username;
		this.uuid = uuid;
	}

	public Entities() {
		super();
	}

}
