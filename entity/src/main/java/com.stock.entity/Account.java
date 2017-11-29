package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 1/12/2016.
 */
@Entity
@Table(name="ACCOUNT")
public class Account implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name="COOKIE",length = 2000)
    private String cookie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
