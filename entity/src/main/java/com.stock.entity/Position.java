package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kerno on 1/12/2016.
 */
@Entity
@Table(name="POSITION")
public class Position implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="CODE")
    private String code;

    @Column(name="EVALUATE")
    private Double evaluate;

    @Column(name="POWER")
    private Double power;

    @Column(name="FOLLOW_POWER")
    private Double followPower;

    @Column(name="CREATE_DATE")
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Double evaluate) {
        this.evaluate = evaluate;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getFollowPower() {
        return followPower;
    }

    public void setFollowPower(Double followPower) {
        this.followPower = followPower;
    }
}
