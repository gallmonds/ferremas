package com.ferremas.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceRequest {
    private LocalDateTime date;
    private BigDecimal amount;

    public PriceRequest(LocalDateTime date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}