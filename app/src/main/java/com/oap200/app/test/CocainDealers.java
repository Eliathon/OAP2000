package com.oap200.app.test;

public class CocainDealers {

    // Datafields for the class
    private String Nickname;
    private String DrugFamily;
    private Double DistributionPrice;
    private Double MonthlyVolum;
    private Boolean DealerIsActive;

    // Constructors
    public CocainDealers(String nickName, String drugFamily, int distributionPrice, Double monthlyVolum,
            Boolean dealerIsActive) {
        this.nickName = nickName;
        this.distributionPrice = distributionPrice;
        this.monthlyVolum = monthlyVolum;
        this.drugFamily = drugFamily;
        this.dealerIsActive = dealerIsActive;

    }

    // Getters And Setters
    // Getter And Setter for Nickname
    public void SetnickName(String nickName) {
        this.nickName = nickNameickname;
    }

    public String GetnickName() {

        return nickName;

    }

    // Getter And Setter for Distribution Price

    public void SetdistributionPrice(Double distributionPrice) {
        this.distributionPrice = distributionPriceistributionPrice;
    }

    public Double GetdistributionPrice() {

        return distributionPrice;

    }

    // Getter And Setter for MonthlyVolum

    public void SetmonthlyVolum(Double monthlyVolum) {
        this.monthlyVolum = monthlyVolum;
    }

    public Double GetmonthlyVolum() {
        return monthlyVolum;

    }

    // Getter And Setter for Cartelboss
    public void SetDrugFamily(String DrugFamily) {
        this.drugFamily = drugFamily;
    }

    public String GetdrugFamily() {

        return drugFamily;

    }

    // Getter And Setter for DealerIsACtive

    public void SetDealerIsActive(Boolean DealerIsACtive) {
        this.dealerIsACtive = dealerIsACtive;
    }

    public Boolean GetdealerIsActive() {
        return dealerIsACtive;

    }

public void CocainDealersInfo()
{
System.out.println("Drugdealers nickname " +nickName);
System.out.println("Drugdealers family " +drugFamily);
System.out.println("Drugdealers price " +distributionPrice);
System.out.println("Drugdealers monthly volum" +monthlyVolum);
System.out.println("Drugdealers status " +isactive);

}