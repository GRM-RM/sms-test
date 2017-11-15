package com.grm.springboot.smstest.dto;

import java.io.Serializable;

/**
 * A unique of the lion
 *
 * @author grm
 */
public class CusDto implements Serializable{
    private Long cid;

    private String token;

    private String cname;

    private Long maxday;

    private Integer maxphone;

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Long getMaxday() {
        return maxday;
    }

    public void setMaxday(Long maxday) {
        this.maxday = maxday;
    }

    public Integer getMaxphone() {
        return maxphone;
    }

    public void setMaxphone(Integer maxphone) {
        this.maxphone = maxphone;
    }
}
