package com.oracle.demo.struct;
/* $Header: HotelStatus.java 06-sep-2002.11:22:45 erajkovi Exp $ */

/* Copyright (c) 2002, Oracle Corporation.  All rights reserved.  */

/*
   DESCRIPTION
    a serializable bean for keeping the status of a hotel room

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
 *  @version $Header: HotelStatus.java 06-sep-2002.11:22:45 erajkovi Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */




import java.io.Serializable;

public class HotelStatus implements Serializable{

  private String hotelName;
  private String city;
  private String state;
  private String roomType;
  private String checkin;
  private String checkout;
  private String bookedby;
  public  HotelStatus(){
         
  }


 public   void setRoomType(String roomType){
    this.roomType = roomType;
 }

 public String getRoomType(){
    return roomType;
 }
 public void setBookedBy(String bookedby){
    this.bookedby = bookedby;
 }

 public String getBookedBy(){
    return bookedby;
 }

 public void setCheckin(String checkin){
    this.checkin = checkin;
 }

 public String getCheckin(){
    return checkin;
 }

 public void setCheckout(String checkout){
    this.checkout = checkout;
 }

 public String getCheckout(){
    return checkout;
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

 public  void setHotelName(String hotelName){
       this.hotelName = hotelName;      
  }

  public String  getHotelName(){
       return hotelName;      
  }


  public  String toString(){
    return "roomType = " + this.roomType + " , bookedby = "+ this.bookedby+ 
     " , Checkin = " +this.checkin + " , checkout = "+this.checkout;
     }
}