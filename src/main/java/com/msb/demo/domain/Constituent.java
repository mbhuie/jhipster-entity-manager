package com.msb.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Constituent.
 */
@Entity
@Table(name = "constituent")
public class Constituent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ric")
    private String ric;

    @Column(name = "isin")
    private String isin;

    @Column(name = "sedol")
    private String sedol;

    @Column(name = "currency")
    private String currency;

    @Column(name = "issue_type")
    private String issueType;

    @Column(name = "mic")
    private String mic;

    @Column(name = "center_code")
    private String centerCode;

    @Column(name = "shares_outstanding")
    private Long sharesOutstanding;

    @ManyToOne
    @JsonIgnoreProperties("constituents")
    private Index index;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRic() {
        return ric;
    }

    public Constituent ric(String ric) {
        this.ric = ric;
        return this;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public String getIsin() {
        return isin;
    }

    public Constituent isin(String isin) {
        this.isin = isin;
        return this;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getSedol() {
        return sedol;
    }

    public Constituent sedol(String sedol) {
        this.sedol = sedol;
        return this;
    }

    public void setSedol(String sedol) {
        this.sedol = sedol;
    }

    public String getCurrency() {
        return currency;
    }

    public Constituent currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIssueType() {
        return issueType;
    }

    public Constituent issueType(String issueType) {
        this.issueType = issueType;
        return this;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public String getMic() {
        return mic;
    }

    public Constituent mic(String mic) {
        this.mic = mic;
        return this;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public Constituent centerCode(String centerCode) {
        this.centerCode = centerCode;
        return this;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public Long getSharesOutstanding() {
        return sharesOutstanding;
    }

    public Constituent sharesOutstanding(Long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
        return this;
    }

    public void setSharesOutstanding(Long sharesOutstanding) {
        this.sharesOutstanding = sharesOutstanding;
    }

    public Index getIndex() {
        return index;
    }

    public Constituent index(Index index) {
        this.index = index;
        return this;
    }

    public void setIndex(Index index) {
        this.index = index;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Constituent)) {
            return false;
        }
        return id != null && id.equals(((Constituent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Constituent{" +
            "id=" + getId() +
            ", ric='" + getRic() + "'" +
            ", isin='" + getIsin() + "'" +
            ", sedol='" + getSedol() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", issueType='" + getIssueType() + "'" +
            ", mic='" + getMic() + "'" +
            ", centerCode='" + getCenterCode() + "'" +
            ", sharesOutstanding=" + getSharesOutstanding() +
            "}";
    }
}
