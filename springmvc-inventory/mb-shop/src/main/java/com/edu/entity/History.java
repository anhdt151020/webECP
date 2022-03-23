package com.edu.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class History implements Serializable {
    private int id;
    private String action;
    private String description;
    private String code;
    private Integer type;
    private Integer productId;
    private Integer qty;
    private BigDecimal price;
    private Integer activeFlag;
    private Date createdDate;
    private Date modifiedDate;
    private ProductInfo productInfoByProductId;

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
    @Column(name = "action", nullable = true, length = 100)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "code", nullable = true, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "product_id", nullable = true)
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "qty", nullable = true)
    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

        History history = (History) o;

        if (id != history.id) return false;
        if (action != null ? !action.equals(history.action) : history.action != null) return false;
        if (description != null ? !description.equals(history.description) : history.description != null)
            return false;
        if (code != null ? !code.equals(history.code) : history.code != null) return false;
        if (type != null ? !type.equals(history.type) : history.type != null) return false;
        if (productId != null ? !productId.equals(history.productId) : history.productId != null) return false;
        if (qty != null ? !qty.equals(history.qty) : history.qty != null) return false;
        if (price != null ? !price.equals(history.price) : history.price != null) return false;
        if (activeFlag != null ? !activeFlag.equals(history.activeFlag) : history.activeFlag != null) return false;
        if (createdDate != null ? !createdDate.equals(history.createdDate) : history.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(history.modifiedDate) : history.modifiedDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    public ProductInfo getProductInfoByProductId() {
        return productInfoByProductId;
    }

    public void setProductInfoByProductId(ProductInfo productInfoByProductId) {
        this.productInfoByProductId = productInfoByProductId;
    }
}
