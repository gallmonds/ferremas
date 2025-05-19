package com.ferremas.api.dtos;

import java.util.List;

public class ProductDTO {
    private Integer id;
    private String productCode;
    private String name;
    private String description;
    private String brandCode;
    private Integer categoryId;
    private Integer brandId;
    private int stock;
    private List<PriceRequest> prices;

    public ProductDTO(Integer id, String productCode, String name, String description,
                      Integer categoryId, Integer brandId, int stock, List<PriceRequest> prices) {
        this.id = id;
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.stock = stock;
        this.prices = prices;
    }

    public Integer getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getBrandCode() {
        return brandCode;
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

    public List<PriceRequest> getPrices() {
        return prices;
    }
}