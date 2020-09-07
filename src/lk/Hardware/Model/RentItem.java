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
public class RentItem {
    private String rentItemId;
    private String rentItemName;
    private Double unitPerDayPrice;
    private int qtyonhand;
    private String itemdescription;

    public RentItem() {
    }

    public RentItem(String rentItemId, String rentItemName, Double unitPerDayPrice, int qtyonhand, String itemdescription) {
        this.rentItemId = rentItemId;
        this.rentItemName = rentItemName;
        this.unitPerDayPrice = unitPerDayPrice;
        this.qtyonhand = qtyonhand;
        this.itemdescription = itemdescription;
    }

    /**
     * @return the rentItemId
     */
    public String getRentItemId() {
        return rentItemId;
    }

    /**
     * @param rentItemId the rentItemId to set
     */
    public void setRentItemId(String rentItemId) {
        this.rentItemId = rentItemId;
    }

    /**
     * @return the rentItemName
     */
    public String getRentItemName() {
        return rentItemName;
    }

    /**
     * @param rentItemName the rentItemName to set
     */
    public void setRentItemName(String rentItemName) {
        this.rentItemName = rentItemName;
    }

    /**
     * @return the unitPerDayPrice
     */
    public Double getUnitPerDayPrice() {
        return unitPerDayPrice;
    }

    /**
     * @param unitPerDayPrice the unitPerDayPrice to set
     */
    public void setUnitPerDayPrice(Double unitPerDayPrice) {
        this.unitPerDayPrice = unitPerDayPrice;
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
