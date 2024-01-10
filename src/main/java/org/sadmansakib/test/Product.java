package org.sadmansakib.test;

import java.util.Objects;

public class Product {
    String orgCode;
    String productType;
    String productCode;
    String term;
    Integer tenure;
    boolean popular;
    boolean active;
    Double profit;
    String accountType;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Product(String orgCode, String productType, String productCode, String term, Integer tenure,
                   boolean popular, boolean active, Double profit, String accountType) {
        this.orgCode = orgCode;
        this.productType = productType;
        this.productCode = productCode;
        this.term = term;
        this.tenure = tenure;
        this.popular = popular;
        this.active = active;
        this.profit = profit;
        this.accountType = accountType;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return isPopular() == product.isPopular() && isActive() == product.isActive() &&
                Objects.equals(getOrgCode(), product.getOrgCode()) &&
                Objects.equals(getProductType(), product.getProductType()) &&
                Objects.equals(getProductCode(), product.getProductCode()) &&
                Objects.equals(getTerm(), product.getTerm()) &&
                Objects.equals(getTenure(), product.getTenure()) &&
                Objects.equals(getProfit(), product.getProfit()) &&
                Objects.equals(getAccountType(), product.getAccountType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrgCode(), getProductType(), getProductCode(), getTerm(), getTenure(),
                isPopular(), isActive(), getProfit(), getAccountType());
    }

    @Override
    public String toString() {
        return "Product{" +
                "orgCode='" + orgCode + '\'' +
                ", productType='" + productType + '\'' +
                ", productCode='" + productCode + '\'' +
                ", term='" + term + '\'' +
                ", tenure=" + tenure +
                ", popular=" + popular +
                ", active=" + active +
                ", profit=" + profit +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
