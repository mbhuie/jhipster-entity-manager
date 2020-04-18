package com.msb.demo.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Exchange.
 */
@Entity
@Table(name = "exchange")
public class Exchange implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mic")
    private String mic;

    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(unique = true)
    private Stock mic;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMic() {
        return mic;
    }

    public Exchange mic(String mic) {
        this.mic = mic;
        return this;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getName() {
        return name;
    }

    public Exchange name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stock getMic() {
        return mic;
    }

    public Exchange mic(Stock stock) {
        this.mic = stock;
        return this;
    }

    public void setMic(Stock stock) {
        this.mic = stock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Exchange)) {
            return false;
        }
        return id != null && id.equals(((Exchange) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Exchange{" +
            "id=" + getId() +
            ", mic='" + getMic() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
