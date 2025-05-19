package com.ferremas.api.dtos;

import java.math.BigDecimal;

public class CreateUpdateProduct {
    private String productCode;
    private String name;
    private String description;
    private String brandCode;
    private Integer categoryId;
    private Integer brandId;
    private int stock;
    private BigDecimal price;

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
