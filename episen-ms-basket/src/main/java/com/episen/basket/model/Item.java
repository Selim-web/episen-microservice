package com.episen.basket.model;

public class Item {

    private Integer gtn;

    private String label;

    private Double unitPrice;

    private Double vat;

    private Integer quantity;

    public Item(Integer gtn, String label, Double unitPrice, Double vat, Integer quantity) {
        this.gtn = gtn;
        this.label = label;
        this.unitPrice = unitPrice;
        this.vat = vat;
        this.quantity = quantity;
    }

    public Integer getGtn() {
        return gtn;
    }

    public void setGtn(Integer gtn) {
        this.gtn = gtn;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
