package com.itang.uritemplate;

import java.util.LinkedHashMap;
import java.util.Map;

interface Watchable {
	Watchable start();

	Watchable watch();

	Watchable watch(String name);

	Watchable end();

	Watchable print();
}

class TimeWatch implements Watchable {
	Map<String, Long> times = new LinkedHashMap<String, Long>();
	boolean started = false;

	public TimeWatch start() {
		if (!times.containsKey("start")) {
			time("start");
			started = true;
		}
		return this;
	}

	public TimeWatch watch() {
		return watch(null);
	}

	public TimeWatch watch(String tag) {
		ensureStart();
		return time(tag);
	}

	public TimeWatch end() {
		ensureStart();
		return time("end");
	}

	public TimeWatch print() {
		if (times.size() == 0) {
			System.out.println("not start");
		}
		long start = times.get("start");
		for (Map.Entry<String, Long> it : times.entrySet()) {
			String msg = it.getKey() + ":" + it.getValue() + ":"
					+ (it.getValue() - start);
			System.out.println(msg);
		}
		return this;
	}

	private TimeWatch time(String tag) {
		if (tag == null || tag.isEmpty()) {
			tag = "tag-" + times.size() + 1;
		}
		times.put(tag, System.currentTimeMillis());
		return this;
	}

	private void ensureStart() {
		if (!started) {
			start();
		}
	}
}