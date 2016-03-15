package com.jsonb.hibernate.custom;

import com.jsonb.hibernate.custom.JsonBUserType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.json.simple.JSONObject;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name="JSONB_ENTITY")
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBUserType.class)})
public class JsonBEntity {
	@Id
	@GeneratedValue
	private Long id;
	
	@Type(type="jsonb")
	private JSONObject jsonData;
	
	public JSONObject getData() {
		return jsonData;
	}
	
	public void setData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
