package com.stock.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kerno on 16-1-12.
 */
@Entity
@Table(name="USER")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;
    @Column(name="USERNAME")
    private String username;
    @Column(name="PASSWORD")
    private String password;

    @Column(name="LOGIN_ACCOUNT")
    private String loginAccount;
    @Column(name="LOGIN_PWD")
    private String loginPassword;

    @Column(name="EMAIL")
    private String email;
    @Column(name="ENABLE")
    private Boolean enable;
    @Column(name="AUTO")
    private Boolean auto;

    @OneToOne(cascade = CascadeType.ALL,optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="ACCOUNT_ID")
    private Account account;

    @Transient
    private LogInfo log;

    public LogInfo getLog() {
        return log;
    }

    public void setLog(LogInfo log) {
        this.log = log;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account == null ? new Account():account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Boolean getAuto() {
        return auto;
    }

    public void setAuto(Boolean auto) {
        this.auto = auto;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }
}
