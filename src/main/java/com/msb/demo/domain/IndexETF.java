package com.msb.demo.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A IndexETF.
 */
@Entity
@Table(name = "index_etf")
public class IndexETF implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "basket_currency")
    private String basketCurrency;

    @Column(name = "as_at_date")
    private LocalDate asAtDate;

    @Column(name = "basket_instrument")
    private String basketInstrument;

    @Column(name = "basket_instrument_type")
    private String basketInstrumentType;

    @Column(name = "basket_minor_units", precision = 21, scale = 2)
    private BigDecimal basketMinorUnits;

    @Column(name = "basket_name")
    private String basketName;

    @Column(name = "basket_ric")
    private String basketRic;

    @Column(name = "basket_type")
    private String basketType;

    @Column(name = "family")
    private String family;

    @Column(name = "fund_currency")
    private String fundCurrency;

    @Column(name = "is_etf")
    private Boolean isEtf;

    @Column(name = "is_etc")
    private Boolean isEtc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBasketCurrency() {
        return basketCurrency;
    }

    public IndexETF basketCurrency(String basketCurrency) {
        this.basketCurrency = basketCurrency;
        return this;
    }

    public void setBasketCurrency(String basketCurrency) {
        this.basketCurrency = basketCurrency;
    }

    public LocalDate getAsAtDate() {
        return asAtDate;
    }

    public IndexETF asAtDate(LocalDate asAtDate) {
        this.asAtDate = asAtDate;
        return this;
    }

    public void setAsAtDate(LocalDate asAtDate) {
        this.asAtDate = asAtDate;
    }

    public String getBasketInstrument() {
        return basketInstrument;
    }

    public IndexETF basketInstrument(String basketInstrument) {
        this.basketInstrument = basketInstrument;
        return this;
    }

    public void setBasketInstrument(String basketInstrument) {
        this.basketInstrument = basketInstrument;
    }

    public String getBasketInstrumentType() {
        return basketInstrumentType;
    }

    public IndexETF basketInstrumentType(String basketInstrumentType) {
        this.basketInstrumentType = basketInstrumentType;
        return this;
    }

    public void setBasketInstrumentType(String basketInstrumentType) {
        this.basketInstrumentType = basketInstrumentType;
    }

    public BigDecimal getBasketMinorUnits() {
        return basketMinorUnits;
    }

    public IndexETF basketMinorUnits(BigDecimal basketMinorUnits) {
        this.basketMinorUnits = basketMinorUnits;
        return this;
    }

    public void setBasketMinorUnits(BigDecimal basketMinorUnits) {
        this.basketMinorUnits = basketMinorUnits;
    }

    public String getBasketName() {
        return basketName;
    }

    public IndexETF basketName(String basketName) {
        this.basketName = basketName;
        return this;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }

    public String getBasketRic() {
        return basketRic;
    }

    public IndexETF basketRic(String basketRic) {
        this.basketRic = basketRic;
        return this;
    }

    public void setBasketRic(String basketRic) {
        this.basketRic = basketRic;
    }

    public String getBasketType() {
        return basketType;
    }

    public IndexETF basketType(String basketType) {
        this.basketType = basketType;
        return this;
    }

    public void setBasketType(String basketType) {
        this.basketType = basketType;
    }

    public String getFamily() {
        return family;
    }

    public IndexETF family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFundCurrency() {
        return fundCurrency;
    }

    public IndexETF fundCurrency(String fundCurrency) {
        this.fundCurrency = fundCurrency;
        return this;
    }

    public void setFundCurrency(String fundCurrency) {
        this.fundCurrency = fundCurrency;
    }

    public Boolean isIsEtf() {
        return isEtf;
    }

    public IndexETF isEtf(Boolean isEtf) {
        this.isEtf = isEtf;
        return this;
    }

    public void setIsEtf(Boolean isEtf) {
        this.isEtf = isEtf;
    }

    public Boolean isIsEtc() {
        return isEtc;
    }

    public IndexETF isEtc(Boolean isEtc) {
        this.isEtc = isEtc;
        return this;
    }

    public void setIsEtc(Boolean isEtc) {
        this.isEtc = isEtc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndexETF)) {
            return false;
        }
        return id != null && id.equals(((IndexETF) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IndexETF{" +
            "id=" + getId() +
            ", basketCurrency='" + getBasketCurrency() + "'" +
            ", asAtDate='" + getAsAtDate() + "'" +
            ", basketInstrument='" + getBasketInstrument() + "'" +
            ", basketInstrumentType='" + getBasketInstrumentType() + "'" +
            ", basketMinorUnits=" + getBasketMinorUnits() +
            ", basketName='" + getBasketName() + "'" +
            ", basketRic='" + getBasketRic() + "'" +
            ", basketType='" + getBasketType() + "'" +
            ", family='" + getFamily() + "'" +
            ", fundCurrency='" + getFundCurrency() + "'" +
            ", isEtf='" + isIsEtf() + "'" +
            ", isEtc='" + isIsEtc() + "'" +
            "}";
    }
}
