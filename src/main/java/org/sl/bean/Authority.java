package org.sl.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hasee on 2017/9/30.
 * 权限实体
 */
public class Authority implements Serializable,Cloneable{
    private Integer id;//主键ID
    private Integer roleId;//角色ID
    private Integer functionId;//功能ID
    private Integer userTypeId;//用户类型ID
    private Date creationTime;//创建时间
    private String createdBy;//创建者

    public Authority() {
    }

    public Authority(Integer id, Integer roleId, Integer functionId,
                     Integer userTypeId, Date creationTime, String createdBy) {
        this.id = id;
        this.roleId = roleId;
        this.functionId = functionId;
        this.userTypeId = userTypeId;
        this.creationTime = creationTime;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", functionId=" + functionId +
                ", userTypeId=" + userTypeId +
                ", creationTime=" + creationTime +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
