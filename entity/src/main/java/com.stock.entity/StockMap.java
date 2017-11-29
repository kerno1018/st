package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 1/14/2016.
 */
@Entity
@Table(name="STOCK_MAPPING")
public class StockMap implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="CODE")
    private String code;
    @Column(name="MAPPING")
    private String mapping;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public boolean equals(Object obj) {
        return code.equals(((StockMap)obj).getCode());
    }
}
