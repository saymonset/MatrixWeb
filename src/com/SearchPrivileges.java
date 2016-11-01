package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class SearchPrivileges {
	private Map<String, String> matrix;
	private Map<String, String> querys;

	public SearchPrivileges(Map<String, String> subjects, Map<String, String> querys) {
		super();
		this.matrix = subjects;
		this.querys = querys;
	}

	public List<AccessControl> search() {
		List<AccessControl> result = new ArrayList<AccessControl>();
		String privilegesQuery = null;
		String privilegesSubjects = null;
		AccessControl accessControl = null;
		StringTokenizer st = null;
		for (Map.Entry<String, String> entry : matrix.entrySet()) {
		System.out
				.println("matrix.getKey()=" + entry.getKey() + ",Value=" + entry.getValue());
	}
		for (Map.Entry<String, String> entry : querys.entrySet()) {
		System.out
				.println("entry.getKey()=" + entry.getKey() + ",Value=" + entry.getValue());
	}
		for (Map.Entry<String, String> query : querys.entrySet()) {
			
			List<String> privileges = new ArrayList<String>();
			long startTime = System.currentTimeMillis();
			if (matrix.containsKey(query.getKey())) {
				accessControl = new AccessControl();
				st = new StringTokenizer(query.getKey(), "|");
				accessControl.setSubject(st.nextToken());
				accessControl.setObject(st.nextToken());
				privilegesQuery = query.getValue();
				privilegesSubjects = matrix.get(query.getKey());
				if (null != privilegesQuery) {

					for (int x = 0; x < privilegesQuery.length(); x++) {
						String priv = privilegesQuery.charAt(x) + "";
						if (null != privilegesSubjects && privilegesSubjects.contains(priv)) {
							if (!" ".equalsIgnoreCase(priv)) {
								privileges.add(priv);
							}
						}
					}
					accessControl.setPrivileges(privileges);
				}
				long endTime = System.currentTimeMillis();
				long duration = (endTime - startTime);
				String has = "NO;";
				if (privileges != null && privileges.size() > 0) {
					has = "YES;";
				}
				if ("NO;".equalsIgnoreCase(has)) {
					accessControl.setInfo(has + " time:" + duration + "ms");
				} else {
					accessControl.setInfo(has + accessControl.getSubject() + " " + accessControl.getObject() + " "
							+ accessControl.getPrivileges() + " time:" + duration + "ms");
				}
				result.add(accessControl);
			}
		}
		return result;
	}
}

// NO; time: 0.3 ms
// YES: David Program1 x Program2 x; time 1.0 ms
// 4 3 w; time 3.0
