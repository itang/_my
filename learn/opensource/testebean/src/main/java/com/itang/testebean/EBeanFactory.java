package com.itang.testebean;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;

public class EBeanFactory {
	public static EbeanServer get() {
		// programmatically build a EbeanServer instance
		// specify the configuration...

		ServerConfig config = new ServerConfig();
		config.setName("pgtest");

		// Define DataSource parameters
		DataSourceConfig h2Db = new DataSourceConfig();
		h2Db.setDriver("org.h2.Driver");
		h2Db.setUsername("sa");
		h2Db.setPassword("");
		h2Db.setUrl("dbc:h2:mem:tests;DB_CLOSE_DELAY=-1");
		h2Db.setHeartbeatSql("select count(*) from dual");

		config.setDataSourceConfig(h2Db);

		// specify a JNDI DataSource
		// config.setDataSourceJndiName("someJndiDataSourceName");

		// set DDL options...
		config.setDdlGenerate(true);
		config.setDdlRun(true);

		config.setDefaultServer(false);
		config.setRegister(false);

		// automatically determine the DatabasePlatform
		// using the jdbc driver
		// config.setDatabasePlatform(new PostgresPlatform());

		// specify the entity classes (and listeners etc)
		// ... if these are not specified Ebean will search
		// ... the classpath looking for entity classes.
		config.addClass(ESimple.class);

		// specify jars to search for entity beans
		// config.addJar("someJarThatContainsEntityBeans.jar");

		// create the EbeanServer instance
		EbeanServer server = EbeanServerFactory.create(config);
		return server;
	}
}
