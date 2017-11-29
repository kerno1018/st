package com.stock.bean;

import java.io.Serializable;

/**
 * Created by kerno on 1/8/2016.
 */
public class GroupType implements Serializable{

    private Integer id;
    private String name;
    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
