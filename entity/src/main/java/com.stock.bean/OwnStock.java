package com.stock.bean;

import java.io.Serializable;

/**
 * Created by kerno on 1/8/2016.
 */
public class OwnStock implements Serializable{

    private String id;
    private String name;
    private volatile Double canUseCount;
    private Double ownCount;
    private Double costPrice;
    private Boolean doneWork;
    private Integer sellVolume;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public synchronized Double getCanUseCount() {
        return canUseCount;
    }

    public synchronized void setCanUseCount(Double canUseCount) {
        this.canUseCount = canUseCount;
    }

    public Double getOwnCount() {
        return ownCount;
    }

    public void setOwnCount(Double ownCount) {
        this.ownCount = ownCount;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Boolean getDoneWork() {
        return doneWork;
    }

    public void setDoneWork(Boolean doneWork) {
        this.doneWork = doneWork;
    }

    public Integer getSellVolume() {
        return sellVolume;
    }

    public void setSellVolume(Integer sellVolume) {
        this.sellVolume = sellVolume;
    }

    public void subUseCount(int sellCount) {
        setCanUseCount(canUseCount - sellCount);
    }
}
