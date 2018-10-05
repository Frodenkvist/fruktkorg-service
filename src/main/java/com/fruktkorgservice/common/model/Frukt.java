package com.fruktkorgservice.common.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fruktkorgservice.common.model.dto.FruktCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "frukt")
public class Frukt {
    @Id
    @SequenceGenerator(name = "frukt_frukt_id_seq", sequenceName = "frukt_frukt_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "frukt_frukt_id_seq")
    @Column(name = "frukt_id", updatable = false)
    private long id;

    @NotNull
    @Column(name = "type")
    private String type;

    @Column(name = "amount")
    private int amount;

    @OneToOne
    @JoinColumn(name = "fruktkorg_id")
    @JsonBackReference
    private Fruktkorg fruktkorg;

    public Frukt(FruktCreateDTO frukt) {
        this.type = frukt.getType();
        this.amount = frukt.getAmount();
        this.fruktkorg = frukt.getFruktkorg();
    }

    public Frukt(FruktUpdateDTO frukt) {
        this.id = frukt.getId();
        this.type = frukt.getType();
        this.amount = frukt.getAmount();
        this.fruktkorg = frukt.getFruktkorg();
    }

    @Override
    public String toString() {
        return "Frukt{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", fruktkorg=" + fruktkorg.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        int idNotSet = 0;
        if (getClass() != o.getClass()) {
            return false;
        }

        if (this.getId() == idNotSet) {
            return false;
        }

        Frukt frukt = (Frukt) o;
        return this.getId() == frukt.getId();
    }
}
