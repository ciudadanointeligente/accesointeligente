package org.accesointeligente.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.gilead.pojo.gwt.LightEntity;

public class SessionData extends LightEntity implements Serializable {
	private static final long serialVersionUID = 2614960941846154232L;

	private Map<String, Object> data;

	public SessionData () {
		data = new HashMap<String, Object> ();
	}

	public void setData (Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData () {
		return data;
	}
}
