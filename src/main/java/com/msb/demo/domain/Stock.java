package com.msb.demo.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Stock.
 */
@Entity
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ric")
    private String ric;

    @Column(name = "name")
    private String name;

    @Column(name = "mic")
    private String mic;

    @Column(name = "lot_size")
    private Integer lotSize;

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

    public Stock ric(String ric) {
        this.ric = ric;
        return this;
    }

    public void setRic(String ric) {
        this.ric = ric;
    }

    public String getName() {
        return name;
    }

    public Stock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMic() {
        return mic;
    }

    public Stock mic(String mic) {
        this.mic = mic;
        return this;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public Integer getLotSize() {
        return lotSize;
    }

    public Stock lotSize(Integer lotSize) {
        this.lotSize = lotSize;
        return this;
    }

    public void setLotSize(Integer lotSize) {
        this.lotSize = lotSize;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Stock)) {
            return false;
        }
        return id != null && id.equals(((Stock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Stock{" +
            "id=" + getId() +
            ", ric='" + getRic() + "'" +
            ", name='" + getName() + "'" +
            ", mic='" + getMic() + "'" +
            ", lotSize=" + getLotSize() +
            "}";
    }
}
