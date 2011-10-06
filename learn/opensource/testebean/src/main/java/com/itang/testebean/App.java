package com.itang.testebean;

import java.util.LinkedHashMap;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.SqlRow;

public class App {

	public static void main(String[] args) {
		testQuery();
		testEntity();
		
		testProgrammaticallyEbean();
	}

	public static void testProgrammaticallyEbean(){
		EbeanServer ebean = EBeanFactory.get();
	}
	
	public static void testQuery() {
		Watchable w = new TimeWatch();
		w.start();

		String sql = "select count(*) as count from dual";
		SqlRow row = Ebean.createSqlQuery(sql).findUnique();

		w.watch("find");

		Integer i = row.getInteger("count");

		System.out.println("Got " + i + "  - DataSource good.");
		w.end().print();
	}

	public static void testEntity() {
		Watchable w = new TimeWatch();
		w.start();

		ESimple e = new ESimple();
		e.setName("test");
		e.setDescription("something");

		// will insert
		Ebean.save(e);

		w.watch("save1");

		e.setDescription("changed");

		// this will update
		Ebean.save(e);

		w.watch("save2");

		// find the inserted entity by its id
		ESimple e2 = Ebean.find(ESimple.class, e.getId());
		System.out.println("Got " + e2.getDescription());

		Ebean.delete(e);

		w.watch("delete");
		// can use delete by id when you don't have the bean
		// Ebean.delete(ESimple.class, e.getId());

		w.end().print();
	}
}

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
