package com.stock.entity;

import com.stock.util.CacluateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/12/2016.
 */
@Entity
@Table(name="FLUCTUATE")
public class Fluctuate implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name="STOCK_CODE")
    private String code;
    @Column(name="INCREASE")
    private String increase;
    @Column(name="FALL")
    private String fall;
    @Column(name="VOLUME")
    private Double volume;
    @Column(name="YESTERDAY_VOLUME")
    private Double yesterdayVolume;
    @Column(name="PRICE")
    private Double price;
    @Column(name="DATE")
    private Date date;
    @Transient
    private Double ratio;
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

    public String getIncrease() {
        return increase;
    }

    public void setIncrease(String increase) {
        this.increase = increase;
    }

    public String getFall() {
        return fall;
    }

    public void setFall(String fall) {
        this.fall = fall;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getYesterdayVolume() {
        return yesterdayVolume;
    }

    public void setYesterdayVolume(Double yesterdayVolume) {
        this.yesterdayVolume = yesterdayVolume;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Transient
    public String getShowDate(){
        return CacluateUtil.format(date);
    }
    public String getRatio() {
        if(volume != null  && yesterdayVolume != null){
            return CacluateUtil.format((volume - yesterdayVolume)/yesterdayVolume *100);
        }else{
            return "- -";
        }
    }
}
