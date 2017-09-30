package org.sl.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hasee on 2017/9/30.
 * 角色实体
 */
public class Role implements Serializable,Cloneable {
    private Integer id;//主键ID
    private String roleCode;//角色编码
    private String roleName;//角色名称
    private Date createDate;//创建日期
    private Integer isStart;//是否启用（0、未启用1、启用）
    private String createdBy;//创建者

    public Role() {
    }

    public Role(Integer id, String roleCode, String roleName,
                Date createDate, Integer isStart, String createdBy) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.createDate = createDate;
        this.isStart = isStart;
        this.createdBy = createdBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleCode='" + roleCode + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createDate=" + createDate +
                ", isStart=" + isStart +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
