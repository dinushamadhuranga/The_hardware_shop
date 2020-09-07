/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Model;

import java.sql.Date;

/**
 *
 * @author dinusha
 */
public class Example {
    private String rid;
    private String cid;
    private String cname;
    private String rtid;
    private Date date;

    public Example() {
    }

    public Example(String rid, String cid, String cname, String rtid, Date date) {
        this.rid = rid;
        this.cid = cid;
        this.cname = cname;
        this.rtid = rtid;
        this.date = date;
    }

    /**
     * @return the rid
     */
    public String getRid() {
        return rid;
    }

    /**
     * @param rid the rid to set
     */
    public void setRid(String rid) {
        this.rid = rid;
    }

    /**
     * @return the cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid the cid to set
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * @return the cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname the cname to set
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return the rtid
     */
    public String getRtid() {
        return rtid;
    }

    /**
     * @param rtid the rtid to set
     */
    public void setRtid(String rtid) {
        this.rtid = rtid;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
