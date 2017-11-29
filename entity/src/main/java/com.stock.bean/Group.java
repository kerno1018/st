package com.stock.bean;

import java.io.Serializable;

/**
 * Created by kerno on 1/8/2016.
 */
public class Group implements Serializable{

    private String prop1;

    private GroupType type;

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
}
