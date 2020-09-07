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
public class Item {
    private String itemid;
    private String itemname;
    private Double unitprice;
    private int qtyonhand;
    private String itemdescription;

    public Item() {
    }

    public Item(String itemid, String itemname, Double unitprice, int qtyonhand, String itemdescription) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.unitprice = unitprice;
        this.qtyonhand = qtyonhand;
        this.itemdescription = itemdescription;
    }

    /**
     * @return the itemid
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * @param itemid the itemid to set
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    /**
     * @return the itemname
     */
    public String getItemname() {
        return itemname;
    }

    /**
     * @param itemname the itemname to set
     */
    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    /**
     * @return the unitprice
     */
    public Double getUnitprice() {
        return unitprice;
    }

    /**
     * @param unitprice the unitprice to set
     */
    public void setUnitprice(Double unitprice) {
        this.unitprice = unitprice;
    }

    /**
     * @return the qtyonhand
     */
    public int getQtyonhand() {
        return qtyonhand;
    }

    /**
     * @param qtyonhand the qtyonhand to set
     */
    public void setQtyonhand(int qtyonhand) {
        this.qtyonhand = qtyonhand;
    }

    /**
     * @return the itemdescription
     */
    public String getItemdescription() {
        return itemdescription;
    }

    /**
     * @param itemdescription the itemdescription to set
     */
    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }
    
}
