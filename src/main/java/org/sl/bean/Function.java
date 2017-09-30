package org.sl.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hasee on 2017/9/30.
 * 功能实体
 */
public class Function implements Serializable,Cloneable {
    private Integer id;//主键ID
    private String functionCode;//功能编码
    private String functionName;//功能名称
    private String funcUrl;//功能URL
    private Integer parentId;//父ID
    private Date creationTime;//创建时间
    private Integer roleId;//角色ID

    public Function() {
    }

    public Function(Integer idbig, String functionCode,
                    String functionName, String funcUrl, Integer parentId, Date creationTime) {
        this.id = idbig;
        this.functionCode = functionCode;
        this.functionName = functionName;
        this.funcUrl = funcUrl;
        this.parentId = parentId;
        this.creationTime = creationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFuncUrl() {
        return funcUrl;
    }

    public void setFuncUrl(String funcUrl) {
        this.funcUrl = funcUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", functionCode='" + functionCode + '\'' +
                ", functionName='" + functionName + '\'' +
                ", funcUrl='" + funcUrl + '\'' +
                ", parentId=" + parentId +
                ", creationTime=" + creationTime +
                '}';
    }
}
