package org.sl.bean;


import java.io.Serializable;

public class DataDictionary implements Serializable,Cloneable{
	private Integer id;//主键ID
	private String typeCode;//类型编码
	private String typeName;//类型名称
	private Integer valueId;//类型值ID
	private String valueName;//类型值Name

	public DataDictionary() {
	}

	public DataDictionary(Integer id, String typeCode,
						  String typeName, Integer valueId, String valueName) {
		this.id = id;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.valueId = valueId;
		this.valueName = valueName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getValueId() {
		return valueId;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	@Override
	public String toString() {
		return "DataDictionary{" +
				"id=" + id +
				", typeCode='" + typeCode + '\'' +
				", typeName='" + typeName + '\'' +
				", valueId=" + valueId +
				", valueName='" + valueName + '\'' +
				'}';
	}
}
