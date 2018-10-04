package com.fruktkorgservice.common.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fruktkorgservice.common.model.dto.FruktCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktUpdateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgCreateDTO;
import com.fruktkorgservice.common.model.dto.FruktkorgUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fruktkorg")
public class Fruktkorg {
    @Id
    @SequenceGenerator(name = "fruktkorg_fruktkorg_id_seq", sequenceName = "fruktkorg_fruktkorg_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fruktkorg_fruktkorg_id_seq")
    @Column(name = "fruktkorg_id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "fruktkorg", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Frukt> fruktList = new ArrayList<>();

    @Column(name = "last_changed")
    private Instant lastChanged;

    public Fruktkorg(FruktkorgCreateDTO fruktkorg) {
        this.name = fruktkorg.getName();
        this.lastChanged = fruktkorg.getLastChanged();
        for (FruktCreateDTO frukt: fruktkorg.getFruktList()) {
            frukt.setFruktkorg(this);
            fruktList.add(new Frukt(frukt));
        }
    }

    public Fruktkorg(FruktkorgUpdateDTO fruktkorg) {
        this.id = fruktkorg.getId();
        this.name = fruktkorg.getName();
        this.lastChanged = fruktkorg.getLastChanged();
        for(FruktUpdateDTO frukt: fruktkorg.getFruktList()) {
            frukt.setFruktkorg(this);
            fruktList.add(new Frukt(frukt));
        }
    }

    @Override
    public String toString() {
        return "Fruktkorg{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fruktList=" + fruktList +
                ", lastChanged=" + lastChanged +
                '}';
    }
}
