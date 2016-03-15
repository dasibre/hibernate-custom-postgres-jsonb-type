package com.jsonb.hibernate.custom;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

public class JsonBUserType implements UserType {
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String JSONB_TYPE = "jsonb";
	
	
	@Override
	public int[] sqlTypes() {
		return new int[] {Types.JAVA_OBJECT };
	}

	@Override
	public Class returnedClass() {
		return JsonBUserType.class;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) {
			return true;
		}
		if ((x == null) || (y == null)) {
			return false;
		}
		return x.equals(y);
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		try {
			final String json = rs.getString(names[0]);
			return json == null ? null : MAPPER.readValue(json, returnedClass()); 
		} catch (IOException ex) {
			throw new HibernateException(ex);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		try {
			final String json = value == null ? null : MAPPER.writeValueAsString(value);
			PGobject pgo = new PGobject();
			pgo.setType(JSONB_TYPE);
			pgo.setValue(json);
			st.setObject(index,pgo);
		} catch(JsonProcessingException ex) {
			throw new HibernateException(ex);
		}
		
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (String) this.deepCopy(value);
	}

	@Override
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return this.deepCopy(cached);
	}

	@Override
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		// TODO Auto-generated method stub
		return original;
	}
	
}
