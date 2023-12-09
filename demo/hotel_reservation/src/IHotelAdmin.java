package com.oracle.demo;


import com.oracle.demo.db.*;
import java.sql.*;
import com.oracle.demo.struct.HotelDetails;
import com.oracle.demo.struct.HotelStatus;


public interface IHotelAdmin {

    public String addNewHotel(String hotelname,
                              String City,
                              String State,
                              String type,
                              Float rate,
                              String Ameneties,
                              String Address)
                              throws SQLException;

    public HotelDetails[] peekHotelMain() throws SQLException;

    public HotelStatus[] peekHotelRoom() throws SQLException;

    public HotelStatus[] getHotelStatus(String hotelName,
                                        String city,
                                        String state)
                                        throws SQLException;

}
