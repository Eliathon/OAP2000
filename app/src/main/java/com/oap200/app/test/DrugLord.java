
// DrugLord is a new Child Class of the Superclass CocainDealers
public Class DrugLord extends CocainDealers{
//New data fields aditionaly for the child class
private String DrugFamily;
// Initializing of variables in the new class included superclass and the new String DrugFamily
public DrugLord  (String Nickname ,Double DistributionPrice , Double MonthlyVolum ,  
Boolean DealerIsActive, String DrugFamily){
super(Nickname,DistributionPrice,MonthlyVolum,DealerIsACtive, DrugFamily);

}


// Getters And Setters for the New Field Nationality
public void Nationality(String Nationality)
{
this.nationality=nationality;
}
public String nationality (){

return nationality;
}

@Override
public void DrugLordInfo(){
Super.CocainDealersInfo();
System.out.println("Drugdlords nickname " +getnickName());
System.out.println("Druglords family " +getdrugFamily());
System.out.println("Druglords price " +getdistributionPrice());
System.out.println("Druglords monthly volum" +getmonthlyVolum());
System.out.println("Druglordsstatus " +getdealerIsactive());
System.out.println("Druglords nationallity" +getnationality());
}





}