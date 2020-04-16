package com.gusrubin.springbootredisjwt.model.user;

import java.util.HashMap;
import java.util.Map;

public enum ERole {

	ROLE_MONITOR("monitor"), ROLE_RECEPTIONIST("receptionist"), ROLE_MANAGER("manager"), ROLE_ADMIN("admin");

	public final String label;

	private ERole(String label) {
		this.label = label;
	}

	private static final Map<String, ERole> BY_LABEL = new HashMap<>();

	static {
		for (ERole e : values()) {
			BY_LABEL.put(e.label, e);
		}
	}

	public static ERole valueOfLabel(String label) {
		return BY_LABEL.get(label);
	}

}
