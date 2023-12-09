package com.oracle.demo;


/* $Header: HotelAdmin.java 06-sep-2002.11:22:44 erajkovi Exp $ */

/* Copyright (c) 2002, Oracle Corporation.  All rights reserved.  */

/*
 DESCRIPTION
 Admin for administering hotel_main and hotel_room tables
 methods :
 addNewHotel() - To add a new Hotel into HOTEL_MAIN table 
 getHotelStatus() - To look at the reservation status of a Hotel
 peekHotelMain() - To list out the contetnts of HOTEL_MAIN table
 peekHotelRoom() - To list out the contents of HOTEL_ROOM table
 

 PRIVATE CLASSES
 -none

 NOTES
 Provides methods to add new hotel ,get the booking status of a hotel , look
 into hotel_main and hotel_room tables

 MODIFIED    (MM/DD/YY)
 erajkovi   09/06/02 - erajkovi_copy_samples
 anchatte   06/26/02 - 
 anchatte   06/07/02 - anchatte_hotel_reservation
 Anirban    03/08/02 - Creation
 */

/**
 *  @version $Header: HotelAdmin.java 06-sep-2002.11:22:44 erajkovi Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */

import  com.oracle.demo.db.*;
import java.sql.*;
import java.text.*;
import java.util.Date;
import java.util.*;
import com.oracle.demo.struct.HotelDetails;
import com.oracle.demo.struct.HotelStatus;


public class HotelAdmin implements IHotelAdmin {

    public HotelAdmin() {}

    /*
     * Method to add a new hotel into the HOTEL_MAIN table
     * 
     */

    public String addNewHotel(String hotelname,
                              String City,
                              String State,
                              String type,
                              Float rate, 
                              String Ameneties,
                              String Address)
                              throws SQLException {
   
        String  result = "";
        Connection conn = DatabaseUtil.getConnection();

        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
   
        String selectQ = "select * from hotel_main  where hotelname='" + hotelname + "'" +
            " AND CITY='" + City + "' AND STATE ='" + State + "'";
     
        if (Hotel.DEBUG)System.out.println(selectQ);
   
        String theQuerry = "insert into hotel_main values('" + hotelname + "','" + City + "','" + State +
            "','" + type + "'," + rate.floatValue() + ",'" + Ameneties + "','" + Address + "')";
       
        if (Hotel.DEBUG)System.out.println(theQuerry);
        try {
            ResultSet rs = stmt.executeQuery(selectQ);

            if (rs.next()) 
                result = "This particular Hotel is already present";
            else {
                if (stmt.executeUpdate(theQuerry) != 0)
                    result = "Hotel Succesfully Added";
            }
            
        } catch (SQLException e) {

            if (Hotel.DEBUG)System.out.println(e);
            throw e;
  
        } finally {
            conn.close();
            stmt.close();
        }
        return result;
  
    }

    /*
     * Method to get the details of a particular hotel from the HOTEL_MAIN table
     */
  
    public HotelDetails[] peekHotelMain() throws SQLException {
        ArrayList ar = new ArrayList(10);
        ResultSet rs = null;
        //querry and get the status for that hotel
        Connection conn = DatabaseUtil.getConnection();

        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
        String theQuerry = "SELECT *  FROM HOTEL_MAIN ORDER BY STATE";

        if (Hotel.DEBUG)System.out.println(theQuerry);
        try {
            rs = stmt.executeQuery(theQuerry);
            while (rs.next()) {
                HotelDetails hl = new HotelDetails();

                hl.setHotelName(rs.getString("hotelname"));
                hl.setType(rs.getString("type"));
                hl.setRate(new Float(rs.getFloat("rate")));
                hl.setAmeneties(rs.getString("ameneties"));
                hl.setAddress(rs.getString("address"));
                hl.setCity(rs.getString("city"));
                hl.setState(rs.getString("state"));
                ar.add(hl);
            }
                
        } catch (SQLException e) {
            if (Hotel.DEBUG)System.out.println(e);
            throw e;
        } finally {
            conn.close();
            stmt.close();
        }
        return (HotelDetails[]) ar.toArray(new HotelDetails[0]);

    }

    /*
     * Method to get the room details of all the hotels in HOTEL_ROOM table
     */



    public HotelStatus[] peekHotelRoom()
        throws SQLException {
        ArrayList ar = new ArrayList(10);
        ResultSet rs = null;
        //querry and get the status for that hotel
        Connection conn = DatabaseUtil.getConnection();

        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
        String theQuerry = "SELECT *  FROM HOTEL_ROOM ORDER BY HOTELNAME , STATE ";

        if (Hotel.DEBUG)System.out.println(theQuerry);
        try {
            rs = stmt.executeQuery(theQuerry);
            while (rs.next()) {
                HotelStatus hl = new HotelStatus();

                hl.setHotelName(rs.getString("hotelname"));
                hl.setCity(rs.getString("city"));
                hl.setState(rs.getString("state"));
                hl.setRoomType(rs.getString("roomtype"));
                hl.setCheckin(rs.getString("checkin"));
                hl.setCheckout(rs.getString("checkout"));
                hl.setBookedBy(rs.getString("bookedby"));
                ar.add(hl);
            }
                
        } catch (SQLException e) {
            if (Hotel.DEBUG)System.out.println(e);
            throw e;
        } finally {
            conn.close();
            stmt.close();
        }
        return (HotelStatus[]) ar.toArray(new HotelStatus[0]);

    }

    /*
     * Method to get the status of all bookings for a particular hotel
     */


    public HotelStatus[] getHotelStatus(String hotelName,
                                        String city, 
                                        String state)
                                        throws SQLException {

        ArrayList ar = new ArrayList(10);
        ResultSet rs = null;
        //querry and get the status for that hotel
        Connection conn = DatabaseUtil.getConnection();

        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
        String theQuerry = "SELECT *  FROM HOTEL_ROOM WHERE HOTELNAME ='" + hotelName +
            "' AND CITY='" + city + "' AND STATE='" + state + "'";;
        if (Hotel.DEBUG)System.out.println(theQuerry);
        try {
            rs = stmt.executeQuery(theQuerry);
            while (rs.next()) {
                HotelStatus hotelStatus = new HotelStatus();

                hotelStatus.setRoomType(rs.getString("ROOMTYPE"));
                hotelStatus.setBookedBy(rs.getString("BOOKEDBY"));
                hotelStatus.setCheckin(Hotel.inputdf.format(rs.getDate("CHECKIN")));
                hotelStatus.setCheckout(Hotel.inputdf.format(rs.getDate("CHECKOUT")));
                ar.add(hotelStatus);
            }
                
        } catch (SQLException e) {

            if (Hotel.DEBUG)System.out.println(e);
            throw e;
  
        } finally {
            conn.close();
            stmt.close();
        }
        return (HotelStatus[]) ar.toArray(new HotelStatus[0]);

    }
 
}
