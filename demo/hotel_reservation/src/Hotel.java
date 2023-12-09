package com.oracle.demo;


/* $Header: Hotel.java 08-may-2003.11:03:53 anchatte Exp $ */

/* Copyright (c) 2002, 2003, Oracle Corporation.  All rights reserved.  */

/*
 DESCRIPTION
 Main class . exposed as a web service .
 methods :
 getDetails() - returns a struct containing details of the hotel 
 getList() - returns a list of Hotels for a particular area 
 reserveHotel() - reserves a particular hotel

 PRIVATE CLASSES
 -none

 NOTES
 Does Hotel search , room reservation 

 MODIFIED    (MM/DD/YY)
 anchatte   05/08/03 - 
 erajkovi   09/06/02 - erajkovi_copy_samples
 anchatte   06/26/02 - 
 anchatte   06/07/02 - anchatte_hotel_reservation
 Anirban    03/08/02 - Creation
 */

/**
 *  @version $Header: Hotel.java 08-may-2003.11:03:53 anchatte Exp $
 *  @author  Anirban Chatterjee
 *  @since   
 */



import com.oracle.demo.db.DatabaseUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.*;

import com.oracle.demo.struct.HotelDetails;
import com.oracle.demo.struct.HotelShort;


public class Hotel implements IHotel { 

    //set this flag to true for server side debug statements 
    public final static boolean DEBUG = false;
    static final SimpleDateFormat inputdf = new SimpleDateFormat("MM/dd/yyyy");
    static final SimpleDateFormat sqldf = new SimpleDateFormat("dd-MMM-yyyy");

    public Hotel() {
        inputdf.setLenient(false);
    }

    /*returns an array of HotelShort - a struct with short desc of Hotel  
     * 
     */
    public HotelShort[] getList(String city,
                                String state,
                                String checkIn,
                                String checkOut,
                                Integer occupants) 
                                throws ParseException, SQLException {
     
        ArrayList returnedHotels = new ArrayList(10);
  
        Date checkInDate = null;
        Date checkOutDate = null;
     
        try {
      
            checkInDate = inputdf.parse(checkIn);         
            checkOutDate = inputdf.parse(checkOut);
            if (DEBUG) {
                System.out.println("City =" + city +
                    " State =" + state + "checkin Date =" + checkInDate.toString() +
                    "checkout date =" + checkOutDate.toString());
            }

            if (checkInDate.after(checkOutDate)) {
                throw new RuntimeException("The checkin date can not be after" +
                        "  the checkout date");
            }
          
            if (!validateOccupants(occupants)) {
                throw new RuntimeException("Number of occupants can be either one or two only");
            }
           
        } catch (ParseException e) {
            if (DEBUG) System.out.println(e);
            throw e;
        }
        //the querry part !
     
   
        ResultSet rs = null;
        Connection conn = DatabaseUtil.getConnection();
     
        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
    
        String theQuerry = "SELECT DISTINCT hotelname,type,rate from HOTEL_MAIN where city='" + city + "' AND " +
            "state ='" + state + "'AND hotelname NOT IN" +                         
            "(SELECT hotelname FROM HOTEL_ROOM where city='" + city +
            "' AND " +
            "state ='" + state +
            "' AND NOT" +
            "(( checkin >'" + sqldf.format(checkOutDate) + "')" +
            "OR" +
            "(checkout <'" + sqldf.format(checkInDate) + "')))";
                   
        if (DEBUG) System.out.println(theQuerry);
            //fire the querry                
        try {
            rs = stmt.executeQuery(theQuerry);
            //get values out of the resultset
     
            while (rs.next()) {
                HotelShort h1 = new HotelShort();

                h1.setHotelName(rs.getString("hotelname"));
                h1.setType(rs.getString("type"));
                h1.setRate(new Float(rs.getFloat("rate")));
                returnedHotels.add(h1);
            }
        } catch (SQLException e) {
            if (DEBUG) System.out.println(e);
            throw e;
        } finally {      
            rs.close();
            stmt.close();
            conn.close();            
        }
             
        HotelShort[] hotels = (HotelShort[]) (returnedHotels.toArray(new HotelShort[0]));

        if (DEBUG)  System.out.println("The array length returned = " + hotels.length);      
     
        return hotels;
 
    }

    /* returns a struct containing details of the hotel 
     * 
     */
    public HotelDetails getDetails(String hotelName,
                                   String City,
                                   String State) 
                                   throws SQLException {

        ResultSet rs = null;
        Connection conn = DatabaseUtil.getConnection();
      
        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
      
        String theQuerry = "SELECT DISTINCT * from HOTEL_MAIN where hotelname='" + hotelName +
            "' AND  city='" + City + "' AND  state ='" + State + "'";
        HotelDetails hl = null;

        try {
            //fire the querry 
            rs = stmt.executeQuery(theQuerry);
            //get values out of the resultset
            
            if (rs.next()) {
     
                hl = new HotelDetails();
                hl.setHotelName(rs.getString("hotelname"));
                hl.setType(rs.getString("type"));
                hl.setRate(new Float(rs.getFloat("rate")));
                hl.setAmeneties(rs.getString("ameneties"));
                hl.setAddress(rs.getString("address"));
                hl.setCity(City);
                hl.setState(State);
            }
        } finally {
      
            rs.close();
            stmt.close();
            conn.close();                
        }
        return  hl;
 
    }

    /*   Method to reserve the hotel  
     * 
     * 
     */
    public String reserveHotel(String hotelName,
                               String city,
                               String state,
                               Integer occupants,
                               String checkin,
                               String checkout,
                               String occupantName)
        throws SQLException, ParseException {

        Date checkInDate = null;
        Date checkOutDate = null;
     
        try {
            checkInDate = inputdf.parse(checkin);
            checkOutDate = inputdf.parse(checkout);
        } catch (ParseException e) {
            if (DEBUG) System.out.println(e);
            throw e;
        }

        String result = "";
        String roomtype = "";

        if (!validateOccupants(occupants))
            return  "Can have only 1 or 2 occupants ";
        else      
            roomtype = getRoomType(occupants);
        
            // update the DB with reservation
    
        Connection conn = DatabaseUtil.getConnection();

        if (conn == null)
            throw new RuntimeException("No connection to the database returned");
        Statement stmt = conn.createStatement();
        String testQuerry = " SELECT hotelname FROM HOTEL_ROOM where city='" + city + "'"
            + "AND state ='" + state + "' AND HOTELNAME ='" + hotelName + "'"
            + "AND NOT"
            + "(( checkin >'" + sqldf.format(checkOutDate) + "')"
            + "OR"
            + "(checkout <'" + sqldf.format(checkInDate) + "'))";

        if (DEBUG) System.out.println(testQuerry);
        String theQuerry = "insert into hotel_room values('" + hotelName + "','"
            + city + "','"
            + state + "','"
            + roomtype + "','"
            + sqldf.format(checkInDate) + "','"
            + sqldf.format(checkOutDate) + "','"
            + occupantName + "')";

        if (DEBUG)System.out.println(theQuerry);
        try {
            ResultSet rs;

            rs = stmt.executeQuery(testQuerry);
 
            if (!rs.next()) {
                if (stmt.executeUpdate(theQuerry) != 0)
                    result = "Succesfully Reserved";
                else
                    result = "Reservation did not happen";
            } else // for !rs.next()
                result = "This room is already reserved for this date";
        } catch (SQLException se) {
            result = "Some problem with Database ,reservation did not happen:" + se.getMessage();
            throw se;
        } finally {
  
            stmt.close();
            conn.close();
 
        }
            return result;
   
    }

    /*
     * Helper methods ,not exposed as a service
     *
     */

    boolean validateOccupants(Integer occupants) {
      
        if (occupants.intValue() > 2 || occupants.intValue() < 1)
            return  false;          
        else 
            return true;

    }

    String getRoomType(Integer occupants) {

        if (occupants.intValue() == 1)
            return "One";
        else      
            return "Two";

    }
  
}
