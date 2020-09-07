/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.Hardware.Model;

/**
 *
 * @author dinusha
 */
public class Delivery {
    private String city;
    private String cityid;
    private Double amount;

    public Delivery() {
    }

    public Delivery(String city, String cityid, Double amount) {
        this.city = city;
        this.cityid = cityid;
        this.amount = amount;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the cityid
     */
    public String getCityid() {
        return cityid;
    }

    /**
     * @param cityid the cityid to set
     */
    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
}
