package com.jsonb.hibernate.custom;

import java.sql.Types;
import org.hibernate.dialect.PostgreSQL82Dialect;

public class JsonBPostgreSQLDialect extends PostgreSQL82Dialect{
	public JsonBPostgreSQLDialect() {
		super();
		registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}
}
