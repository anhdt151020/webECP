package com.edu.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_detail", schema = "iv_dbtest", catalog = "")
public class ProductDetail {
    private int id;
    private String serial;
    private String name;
    private String description;
    private String battery;
    private String camera;
    private String os;
    private String wireless;
    private String memory;
    private String size;
    private Integer productId;
    private String receiptcode;
    private String issuecode;
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
    @Column(name = "serial", nullable = true, length = 50)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "battery", nullable = true, length = 100)
    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    @Basic
    @Column(name = "camera", nullable = true, length = 100)
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    @Basic
    @Column(name = "os", nullable = true, length = 100)
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Basic
    @Column(name = "wireless", nullable = true, length = 100)
    public String getWireless() {
        return wireless;
    }

    public void setWireless(String wireless) {
        this.wireless = wireless;
    }

    @Basic
    @Column(name = "memory", nullable = true, length = 100)
    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Basic
    @Column(name = "size", nullable = true, length = 100)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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
    @Column(name = "receiptcode", nullable = true, length = 50)
    public String getReceiptcode() {
        return receiptcode;
    }

    public void setReceiptcode(String receiptcode) {
        this.receiptcode = receiptcode;
    }

    @Basic
    @Column(name = "issuecode", nullable = true, length = 50)
    public String getIssuecode() {
        return issuecode;
    }

    public void setIssuecode(String issuecode) {
        this.issuecode = issuecode;
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

        ProductDetail that = (ProductDetail) o;

        if (id != that.id) return false;
        if (serial != null ? !serial.equals(that.serial) : that.serial != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (battery != null ? !battery.equals(that.battery) : that.battery != null) return false;
        if (camera != null ? !camera.equals(that.camera) : that.camera != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (wireless != null ? !wireless.equals(that.wireless) : that.wireless != null) return false;
        if (memory != null ? !memory.equals(that.memory) : that.memory != null) return false;
        if (size != null ? !size.equals(that.size) : that.size != null) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (receiptcode != null ? !receiptcode.equals(that.receiptcode) : that.receiptcode != null) return false;
        if (issuecode != null ? !issuecode.equals(that.issuecode) : that.issuecode != null) return false;
        if (activeFlag != null ? !activeFlag.equals(that.activeFlag) : that.activeFlag != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (serial != null ? serial.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (battery != null ? battery.hashCode() : 0);
        result = 31 * result + (camera != null ? camera.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (wireless != null ? wireless.hashCode() : 0);
        result = 31 * result + (memory != null ? memory.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (receiptcode != null ? receiptcode.hashCode() : 0);
        result = 31 * result + (issuecode != null ? issuecode.hashCode() : 0);
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
