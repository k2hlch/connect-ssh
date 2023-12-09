package com.oracle.demo.struct;
/* $Header: HotelShort.java 06-sep-2002.11:22:45 erajkovi Exp $ */

/* Copyright (c) 2002, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    a serializable bean for keeping the short details of a hotel

   PRIVATE CLASSES
   -none

   NOTES
    provides get and set methods 

   MODIFIED    (MM/DD/YY)
    erajkovi   09/06/02 - erajkovi_copy_samples
    anchatte   06/07/02 - anchatte_hotel_reservation
    Anirban    03/08/03 - Creation
 */

/**
 *  @version $Header: HotelShort.java 06-sep-2002.11:22:45 erajkovi Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */



import java.io.Serializable;

public class HotelShort implements Serializable{

  protected String hotelName;
  protected String type;
  protected Float rate;
 

  public HotelShort()
  {
  }

  

 public  void setHotelName(String hotelName){
       this.hotelName = hotelName;      
  }

  public String  getHotelName(){
       return hotelName;      
  }
  
  public void setType(String type){
       this.type = type;      
  }

  public String  getType(){
       return type;      
  }

  public void setRate(Float rate){
       this.rate = rate;      
  }
  
  public Float getRate(){
       return rate;      
  }


  public  String toString(){
    return "HotelName = " + this.hotelName + " , Type = "+ this.type+ 
     " , Rate = " +this.rate.floatValue();
     }

}