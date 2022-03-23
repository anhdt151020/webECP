package com.edu.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Entity
public class Menu implements Serializable {
    private int id;
    private Integer parentId;
    private String url;
    private String name;
    private Integer orderIndex;
    private Integer activeFlag;
    private Date createdDate;
    private Date modifiedDate;
    //more field
    private Map<Integer,Integer> mapAuth;
    @Transient
    public Map<Integer, Integer> getMapAuth() {
        return mapAuth;
    }

    public void setMapAuth(Map<Integer, Integer> mapAuth) {
        this.mapAuth = mapAuth;
    }
    //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id", nullable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 100)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "order_index", nullable = true)
    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    @Basic
    @Column(name = "active_flag", nullable = true)
    public Integer getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Integer activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = true)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date", nullable = true)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (id != menu.id) return false;
        if (parentId != null ? !parentId.equals(menu.parentId) : menu.parentId != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;
        if (name != null ? !name.equals(menu.name) : menu.name != null) return false;
        if (orderIndex != null ? !orderIndex.equals(menu.orderIndex) : menu.orderIndex != null) return false;
        if (activeFlag != null ? !activeFlag.equals(menu.activeFlag) : menu.activeFlag != null) return false;
        if (createdDate != null ? !createdDate.equals(menu.createdDate) : menu.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(menu.modifiedDate) : menu.modifiedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (orderIndex != null ? orderIndex.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}
