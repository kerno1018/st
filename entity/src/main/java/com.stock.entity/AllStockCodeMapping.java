package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 1/14/2016.
 */
@Entity
@Table(name="ALL_STOCK_CODE_MAPPING")
public class AllStockCodeMapping implements Serializable {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name="CODE")
    private String code;
    @Column(name="NAME")
    private String name;
    @Column(name="MAPPING")
    private String mapping;
    @Column(name="HAS_VALUE")
    private Boolean hasValue;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Boolean getHasValue() {
        return hasValue;
    }

    public void setHasValue(Boolean hasValue) {
        this.hasValue = hasValue;
    }

    @Override
    public boolean equals(Object obj) {
        return code.equals(((StockMap)obj).getCode());
    }
}
