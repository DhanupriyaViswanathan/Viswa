package com.vsr.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    private String productName;
    private String productDescription;
    private Double rate; // Double or BigDecimal recommended for currency
    private LocalDate makeDate;
    private LocalDate expireDate;
    @Column(name = "active_flag")
    private Integer activeFlag;

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public LocalDate getMakeDate() { return makeDate; }
    public void setMakeDate(LocalDate makeDate) { this.makeDate = makeDate; }

    public LocalDate getExpireDate() { return expireDate; }
    public void setExpireDate(LocalDate expireDate) { this.expireDate = expireDate; }

    public Integer getActiveFlag() { return activeFlag; }
    public void setActiveFlag(Integer activeFlag) { this.activeFlag = activeFlag; }
}