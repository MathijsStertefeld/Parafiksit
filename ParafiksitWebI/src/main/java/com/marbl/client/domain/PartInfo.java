package com.marbl.client.domain;

import java.math.BigDecimal;

public class PartInfo {

    private String name;
    private BigDecimal price;

    public PartInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
