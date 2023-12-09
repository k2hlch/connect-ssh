package com.oracle.demo.struct;


/* $Header: HotelDetails.java 06-sep-2002.11:22:44 erajkovi Exp $ */

/* Copyright (c) 2002, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    a serializable bean for keeping the details of a hotel

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
 *  @version $Header: HotelDetails.java 06-sep-2002.11:22:44 erajkovi Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */
 import com.oracle.demo.struct.HotelShort;

 public class HotelDetails extends HotelShort {

   private String ameneties;
   private String address;
   private String city;
   private String state;

  public HotelDetails(){
     
  }


  public  void setCity(String city){
    this.city = city;
 }

  public String getCity(){
    return city;
 }
 
  public void setState(String state){
    this.state = state;
 }

   public  String getState(){
    return state;
 }

  public  void setAddress(String address){
    this.address = address;
 }

  public String getAddress(){
    return address;
 }

  public void setAmeneties(String ameneties){
    this.ameneties = ameneties;
 }

  public String getAmeneties(){
    return ameneties;
 }

 public String toString(){
 return "HotelName = " + this.hotelName +  " , State = "+ this.state+
  " , City = "+ this.city+ " , Type = "+ this.type+ 
     " , Rate = " +this.rate+" , Ameneties = "+ this.ameneties +" , Address = " 
     + this.address;
 }
}