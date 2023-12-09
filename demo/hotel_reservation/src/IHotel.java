package com.oracle.demo;


import java.text.*;
import java.sql.*;
import com.oracle.demo.struct.HotelDetails;
import com.oracle.demo.struct.HotelShort;


public interface IHotel {
    public HotelDetails getDetails(String hotelName,
        String City,
        String State)
        throws SQLException;

    public String reserveHotel(String hotelName,
                              String city, 
                              String state,
                              Integer Occupant,
                              String checkin, 
                              String checkout, 
                              String occupantName) 
        throws SQLException, ParseException;

    public HotelShort[] getList(String city,
        String state,
        String checkIn, String checkOut, Integer occupants) 
        throws ParseException, SQLException;
  
}
